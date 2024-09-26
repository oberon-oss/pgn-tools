package org.oberon.oss.chess.data.tags.defs;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.tags.TagType;

/**
 * @param <V> Type class of the data contained by the element.
 *
 * @author Fabien H. Dumay
 */
@Getter
@Log4j2
public abstract class AbstractTag<V> {
    private final TagType tagType;
    private final V       tagValue;

    protected AbstractTag(@NotNull final TagType tagType, final @NotNull V tagValue) {
        this.tagType  = tagType;
        this.tagValue = tagValue;
    }

}
