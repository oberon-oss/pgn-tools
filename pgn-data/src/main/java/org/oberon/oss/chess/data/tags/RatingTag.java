package org.oberon.oss.chess.data.tags;

import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.enums.TagType;
import org.oberon.oss.chess.data.tags.defs.AbstractTag;
import org.oberon.oss.chess.data.tags.defs.TagValueFromString;

/**
 * @author Fabien H. Dumay
 */
public class RatingTag extends AbstractTag<Integer> {
    private static final TagValueFromString<Integer> converter = Integer::parseInt;

    public RatingTag(@NotNull TagType tagType, @NotNull String value) {
        super(tagType, converter.fromString(value));
    }

    @Override
    public Integer fromString(String tagValue) {
        return Integer.valueOf(tagValue);
    }
}
