package org.oberon.oss.chess.data.enums;

import lombok.extern.log4j.Log4j2;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Fabien H. Dumay
 */
@Log4j2
public class ChessFieldIterator implements Iterator<ChessField> {
    private final List<ChessField> fields;
    private       int              index = 0;

    public ChessFieldIterator(int rank) {
        fields = ChessField.getRankFields(rank);
    }

    public ChessFieldIterator(String file) {
        fields = ChessField.getFileFields(file);
    }

    @Override
    public boolean hasNext() {
        return index < fields.size();
    }

    @Override
    public ChessField next() {
        if (index == fields.size()) throw new NoSuchElementException();
        LOGGER.trace("Current field: {}", fields.get(index));
        return fields.get(index++);
    }
}
