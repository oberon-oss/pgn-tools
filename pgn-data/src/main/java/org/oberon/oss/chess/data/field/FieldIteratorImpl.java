package org.oberon.oss.chess.data.field;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Fabien H. Dumay
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

    @Override
    public void skipFields(int skipCount) {
        if (skipCount < 1 || skipCount + index >= fields.size()) {
            throw new IllegalArgumentException("Invalid skip count: " + skipCount);
        }
        index += skipCount - 1;
    }

    public static @NotNull FieldIterator<FieldInformation> chessBoardFieldIterator() {
        return new FieldIteratorImpl();
    }

    public static @NotNull FieldIterator<FieldInformation> rankIterator(@NotNull Integer rank) {
        return new FieldIteratorImpl(rank);
    }

    public static @NotNull FieldIterator<FieldInformation> fileIterator(@NotNull String file) {
        return new FieldIteratorImpl(file);
    }

}
