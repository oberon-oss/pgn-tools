package org.oberon.oss.chess.data.tags.defs;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.tags.TagType;

/**
 * Describes an abstract tag, which can be used as parent for specific tag type instances.
 *
 * @param <V> Type class of the data contained by the element.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@Getter
@Log4j2
public abstract class AbstractTag<V> {
    private final TagType tagType;
    private final V       tagValue;

    /**
     * Creates a new parent instance for a subclass of this class.
     *
     * @param tagType  a value from the {@link TagType} enumeration
     * @param tagValue the value for the tag.
     * @since 1.0.0
     */
    protected AbstractTag(@NotNull final TagType tagType, final @NotNull V tagValue) {
        this.tagType  = tagType;
        this.tagValue = tagValue;
    }

}
