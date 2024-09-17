package org.oberon.oss.chess.data.enums;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Fabien H. Dumay
 */
@Log4j2
public enum ChessBoardField {
    A8, B8, C8, D8, E8, F8, G8, H8,
    A7, B7, C7, D7, E7, F7, G7, H7,
    A6, B6, C6, D6, E6, F6, G6, H6,
    A5, B5, C5, D5, E5, F5, G5, H5,
    A4, B4, C4, D4, E4, F4, G4, H4,
    A3, B3, C3, D3, E3, F3, G3, H3,
    A2, B2, C2, D2, E2, F2, G2, H2,
    A1, B1, C1, D1, E1, F1, G1, H1;

    public static @NotNull ChessBoardFieldIterator iterator() {
        return new ChessBoardFieldIterator();
    }

    public static ChessBoardField getFieldByCoordinates(String rank, int row) {
        return ChessBoardField.valueOf(rank.toUpperCase() + row);
    }

    public static class ChessBoardFieldIterator implements Iterator<ChessBoardField> {
        private final ChessBoardField[] fields = ChessBoardField.values();
        private       int               index  = 0;

        @Override
        public boolean hasNext() {
            return index + 1 < fields.length;
        }

        @Override
        public ChessBoardField next() {
            if (index + 1 == fields.length) throw new NoSuchElementException();
            ++index;
            LOGGER.info("Current field: {}", fields[index]);
            return fields[index];
        }

        public void skipFields(int skipCount) {
            if (skipCount < 2 || skipCount + index >= fields.length) {
                throw new IllegalArgumentException("Invalid skip count: " + skipCount);
            }
            index += skipCount - 1;
        }
    }
}
