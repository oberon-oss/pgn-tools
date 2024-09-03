package org.oberon.oss.chess.data.tags;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @param <V> Type class of the data contained by the element.
 *
 * @author Fabien H. Dumay
 */
@Log4j2
public abstract class AbstractTag<V> {
    @Getter
    private final TagType tagType;

    private final String userDefinedTagName;

    protected AbstractTag(@NotNull final TagType tagType) {
        this.tagType       = tagType;
        userDefinedTagName = null;
    }

    protected AbstractTag(@NotNull String tagName) {
        tagType = TagType.getTagType(tagName);
        if (tagType == TagType.USER_DEFINED_TAG) {
            userDefinedTagName = tagName;
        } else {
            userDefinedTagName = null;
        }
    }

    public @Nullable String getUserDefinedTagName() {
        if (tagType != TagType.USER_DEFINED_TAG) {
            return userDefinedTagName;
        }
        return null;
    }

    public abstract V getTagValue();
}
