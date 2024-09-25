package org.oberon.oss.chess.data;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.enums.ChessField;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Fabien H. Dumay
 */
@Log4j2
public class ChessFieldIterator implements FieldIterator<ChessFieldInformation> {
    private final List<ChessField> fields;
    private       int              index = 0;

    private ChessFieldIterator() {
        fields = new ArrayList<>(List.of(ChessField.values()));
    }

    private ChessFieldIterator(int rank) {
        fields = ChessField.getRankFields(rank);
    }

    private ChessFieldIterator(String file) {
        fields = ChessField.getFileFields(file);
    }

    @Override
    public boolean hasNext() {
        return index < fields.size();
    }

    @Override
    public ChessFieldInformation next() {
        if (index == fields.size()) throw new NoSuchElementException();
        LOGGER.trace("Current field: {}", fields.get(index));
        return fields.get(index++);
    }

    @Override
    public void skipFields(int skipCount) {
        if (skipCount < 2 || skipCount + index >= fields.size()) {
            throw new IllegalArgumentException("Invalid skip count: " + skipCount);
        }
        index += skipCount - 1;
    }

    public static @NotNull FieldIterator<ChessFieldInformation> chessBoardFieldIterator() {
        return new ChessFieldIterator();
    }

    public static @NotNull FieldIterator<ChessFieldInformation> rankIterator(@NotNull Integer rank) {
        return new ChessFieldIterator(rank);
    }

    public static @NotNull FieldIterator<ChessFieldInformation> fileIterator(@NotNull String file) {
        return new ChessFieldIterator(file);
    }

}
