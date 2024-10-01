package org.oberon.oss.chess.data.tags;

import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.tags.defs.AbstractTag;
import org.oberon.oss.chess.data.tags.defs.TagValueFromString;

/**
 * Tag type used for rating tags like ELO or USCF ratings
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public class RatingTag extends AbstractTag<Integer> {
    private static final TagValueFromString<Integer> CONVERTER = Integer::parseInt;

    public RatingTag(@NotNull TagType tagType, @NotNull String value) {
        super(tagType, CONVERTER.fromString(value));
    }
}
