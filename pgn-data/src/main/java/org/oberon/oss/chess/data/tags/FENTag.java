package org.oberon.oss.chess.data.tags;

import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.board.FENDefinedPosition;
import org.oberon.oss.chess.data.enums.TagType;
import org.oberon.oss.chess.data.tags.defs.AbstractTag;
import org.oberon.oss.chess.data.tags.defs.TagValueFromString;

/**
 * @author Fabien H. Dumay
 */
public class FENTag extends AbstractTag<FENDefinedPosition> {
    private static final TagValueFromString<FENDefinedPosition> converter = FENDefinedPosition::new;

    public FENTag(@NotNull String tagValue) {
        super(TagType.FEN, converter.fromString(tagValue));
    }

    @Override
    public FENDefinedPosition fromString(String tagValue) {
        return null;
    }
}
