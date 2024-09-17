package org.oberon.oss.chess.data.tags.defs;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.oberon.oss.chess.data.enums.TagType;

/**
 * @param <V> Type class of the data contained by the element.
 *
 * @author Fabien H. Dumay
 */
@Log4j2
public abstract class AbstractTag<V> implements TagValueFromString<V> {
    @Getter
    private final TagType tagType;

    @Getter
    private final V tagValue;

    private final String userDefinedTagName;

    protected AbstractTag(@NotNull final TagType tagType, final @NotNull V tagValue) {
        this.tagType       = tagType;
        this.tagValue = tagValue;
        userDefinedTagName = null;
    }

    protected AbstractTag(@NotNull String tagName,  final @NotNull V tagValue) {
        tagType = TagType.getTagType(tagName);
        if (tagType == TagType.USER_DEFINED_TAG) {
            userDefinedTagName = tagName;
        } else {
            userDefinedTagName = null;
        }
        this.tagValue = tagValue;
    }

    public @Nullable String getUserDefinedTagName() {
        if (tagType != TagType.USER_DEFINED_TAG) {
            return userDefinedTagName;
        }
        return null;
    }
}
