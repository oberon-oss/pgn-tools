package org.oberon.oss.chess.data.field;

import java.util.Iterator;

/**
 * Extends the basic Iterator class with a method to skip fields.
 *
 * @param <F> interface or class that extends the FieldInformation Interface.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public interface FieldIterator<F extends FieldInformation> extends Iterator<F> {

    /**
     * Default implementation, throws a {@link UnsupportedOperationException}. Implementing classes should override this method if a
     * useful skipping method is provided.
     *
     * @param fieldsToSkip Number of fields to skip
     *
     * @throws UnsupportedOperationException If not supported by the implementing class.
     * @since 1.0.0
     */
    default void skipFields(int fieldsToSkip) {
        throw new UnsupportedOperationException("skipFields");
    }
}
