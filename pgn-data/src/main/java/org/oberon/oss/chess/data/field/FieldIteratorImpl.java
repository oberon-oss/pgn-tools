package org.oberon.oss.chess.data.field;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Provides access to various iterators of implementations of the {@link FieldInformation} interface.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@Log4j2
public class FieldIteratorImpl implements FieldIterator<FieldInformation> {
    private final List<ChessField> fields;
    private       int              index = 0;

    private FieldIteratorImpl() {
        fields = new ArrayList<>(List.of(ChessField.values()));
    }

    private FieldIteratorImpl(int rank) {
        fields = ChessField.getRankFields(rank);
    }

    private FieldIteratorImpl(String file) {
        fields = ChessField.getFileFields(file);
    }

    @Override
    public boolean hasNext() {
        return index < fields.size();
    }

    @Override
    public FieldInformation next() {
        if (index == fields.size()) throw new NoSuchElementException();
        LOGGER.trace("Current field: {}", fields.get(index));
        return fields.get(index++);
    }


    /**
     * {@summary}
     * <p>
     * Provides a method to move the index for the iterator forward to instruct the iterator to skip a specified number of fields.
     *
     * @throws IllegalArgumentException if the skip count {@literal <= 1} or if the current index + skip count would refer to an
     *                                  element outside the underlying data structure
     */
    @Override
    public void skipFields(int skipCount) {
        if (skipCount < 1 || skipCount + index >= fields.size()) {
            throw new IllegalArgumentException("Invalid skip count: " + skipCount);
        }
        index += skipCount - 1;
    }

    /**
     * Returns an iterator over the fields on the chess board, as enumerated by the {@link ChessField} enum.
     * <p>
     * The returned iterator will start with the field {@link ChessField#A8 A8}, and will end with the field
     * {@link ChessField#H1 H1}
     *
     * @return An iterator over the chess boards fields from {@link ChessField#A8 A8}...{@link ChessField#H1 H1}
     *
     * @since 1.0.0
     */
    public static @NotNull FieldIterator<FieldInformation> chessBoardFieldIterator() {
        return new FieldIteratorImpl();
    }

    /**
     * Returns an iterator for the fields in a rank (horizontal line) of a chess board.
     * <p>
     * The iterator will return the fields in the order from A...H
     *
     * @param rank The rank to return an iterator for. Must be in the range 1...8
     *
     * @return an iterator over the fields in the specified rank, ordered from A...H
     *
     * @since 1.0.0
     */
    public static @NotNull FieldIterator<FieldInformation> rankIterator(@NotNull Integer rank) {
        return new FieldIteratorImpl(rank);
    }

    /**
     * Returns an iterator for the fields in a file (vertical line) of a chess board.
     * <p>
     * The iterator will return the fields in the order from 8...1
     *
     * @param file The file to return an iterator for. Must be in the range A...H
     *
     * @return an iterator over the fields in the specified file, ordered from 8...1
     *
     * @since 1.0.0
     */
    public static @NotNull FieldIterator<FieldInformation> fileIterator(@NotNull String file) {
        return new FieldIteratorImpl(file);
    }

}
