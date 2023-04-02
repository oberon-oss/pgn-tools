package org.oberon.oss.chess.data.util;

/**
 * Defines the contract for builders of PGN related classes.
 *
 * @param <T> Defines the type of object of the target object being build.
 * @since 1.0.0
 */
public interface PgnClassBuilder<T> {

    /**
     * Creates a target object instance of type <b>{@literal <T>}</b>
     *
     * @return the generated object.
     * @since 1.0.0
     */
    T build();
}
