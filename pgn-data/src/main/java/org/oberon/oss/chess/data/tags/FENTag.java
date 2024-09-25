package org.oberon.oss.chess.data.tags;

import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.enums.TagType;
import org.oberon.oss.chess.data.fen.FENPosition;
import org.oberon.oss.chess.data.fen.FENPositionTranslator;
import org.oberon.oss.chess.data.fen.FENPositionTranslatorImpl;
import org.oberon.oss.chess.data.tags.defs.AbstractTag;
import org.oberon.oss.chess.data.tags.defs.TagValueFromString;

/**
 * @author Fabien H. Dumay
 */
public class FENTag extends AbstractTag<FENPosition> {
    private static final TagValueFromString<FENPosition> CONVERTER = new FENPositionTagValueFromString();

    public FENTag(@NotNull String tagValue) {
        super(TagType.FEN, CONVERTER.fromString(tagValue));
    }

    private static class FENPositionTagValueFromString implements TagValueFromString<FENPosition> {
        private static final FENPositionTranslator translator = new FENPositionTranslatorImpl();

        @Override
        public FENPosition fromString(String tagValue) {
            return translator.toFENPosition(tagValue);
        }
    }
}
