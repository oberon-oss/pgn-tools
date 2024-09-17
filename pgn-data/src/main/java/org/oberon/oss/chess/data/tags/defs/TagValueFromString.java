package org.oberon.oss.chess.data.tags.defs;

/**
 * Facilitates the conversion from a {@link String} object to an object of type {@literal <V>}
 *
 * @param <V> Target type of the result form a string conversion.
 *
 * @author Fabien H. Dumay
 */
public interface TagValueFromString<V> {
    /**
     * Converts a string to an object of the required target type.
     *
     * @param tagValue String representation of the object to be created.
     *
     * @return an object of type {@literal <V>}
     *
     * @since 1.0.0
     */
    V fromString(String tagValue);
}
