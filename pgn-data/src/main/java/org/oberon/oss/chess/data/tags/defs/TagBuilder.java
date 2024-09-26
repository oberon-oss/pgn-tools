package org.oberon.oss.chess.data.tags.defs;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.enums.TagType;
import org.oberon.oss.chess.data.tags.FENTag;
import org.oberon.oss.chess.data.tags.RatingTag;
import org.oberon.oss.chess.data.tags.StandardTag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Allows the creation of a PGN Tag.
 *
 * @param <V> The target type of the tag to be created.
 *
 * @since 1.0.0
 */
@SuppressWarnings("UnusedReturnValue")
@Getter
public class TagBuilder<V> {
    private static final Pattern STRIP_DOUBLE_QUOTES_FROM_TAG_VALUE = Pattern.compile("^\"(.*)\"$");

    private TagType tagType;
    private String  value;

    /**
     * sets the tag type for the tag under construction. 
     *
     * @param tagTypeName Name of the tag type.
     *
     * @return The current builder object.
     *
     * @since 1.0.0
     */
    public TagBuilder<V> setTagType(@NotNull String tagTypeName) {
        this.tagType = TagType.getTagType(tagTypeName);
        return this;
    }

    /**
     * Sets the value for a tag.
     *
     * @param value The string representation of the tag value.
     *
     * @return The current builder object.
     *
     * @since 1.0.0
     */
    public TagBuilder<V> setValue(@NotNull String value) {
        Matcher matcher = STRIP_DOUBLE_QUOTES_FROM_TAG_VALUE.matcher(value);
        if (matcher.matches()) {
            this.value = matcher.group(1);
        } else {
            this.value = value;
        }
        return this;
    }

    /**
     * Constructs the actual tag.
     * <p>
     * Note:
     * The actual object created
     * @return the created tag.
     *
     * @since 1.0.0
     */

    @SuppressWarnings("unchecked")
    public AbstractTag<V> build() {

        switch (tagType) {
            case FEN -> {
                return (AbstractTag<V>) new FENTag(value);
            }
            case WHITE_ELO, BLACK_ELO, BLACK_USCF, WHITE_USCF -> {
                return (AbstractTag<V>) new RatingTag(tagType, value);
            }
            default -> {
                return (AbstractTag<V>) new StandardTag(tagType, value);
            }
        }
    }

}
