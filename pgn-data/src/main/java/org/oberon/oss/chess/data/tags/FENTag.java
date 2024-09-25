package org.oberon.oss.chess.data.tags;

import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.ChessFieldInformation;
import org.oberon.oss.chess.data.enums.TagType;
import org.oberon.oss.chess.data.fen.FENPosition;
import org.oberon.oss.chess.data.fen.FENPositionTranslator;
import org.oberon.oss.chess.data.fen.FENPositionTranslatorImpl;
import org.oberon.oss.chess.data.tags.defs.AbstractTag;
import org.oberon.oss.chess.data.tags.defs.TagValueFromString;

/**
 * @author Fabien H. Dumay
 */
public class FENTag extends AbstractTag<FENPosition<ChessFieldInformation>> {
    private static final TagValueFromString<FENPosition<ChessFieldInformation>> CONVERTER = new FENPositionTagValueFromString();

    public FENTag(@NotNull String tagValue) {
        super(TagType.FEN, CONVERTER.fromString(tagValue));
    }

    private static class FENPositionTagValueFromString implements TagValueFromString<FENPosition<ChessFieldInformation>> {
        private static final FENPositionTranslator<ChessFieldInformation> translator = new FENPositionTranslatorImpl();

        @Override
        public FENPosition<ChessFieldInformation> fromString(String tagValue) {
            return translator.toFENPosition(tagValue);
        }
    }
}
