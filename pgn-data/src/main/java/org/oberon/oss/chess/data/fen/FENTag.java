package org.oberon.oss.chess.data.fen;

import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.field.FieldInformation;
import org.oberon.oss.chess.data.tags.TagSection;
import org.oberon.oss.chess.data.tags.TagType;
import org.oberon.oss.chess.data.tags.defs.AbstractTag;
import org.oberon.oss.chess.data.tags.defs.TagValueFromString;

/**
 * Defines the FEN tag that may appear in the {@link TagSection} of a PGN notated game.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public class FENTag extends AbstractTag<FENPosition<FieldInformation>> {
    private static final TagValueFromString<FENPosition<FieldInformation>> CONVERTER = new FENPositionTagValueFromString();

    public FENTag(@NotNull String tagValue) {
        super(TagType.FEN, CONVERTER.fromString(tagValue));
    }

    private static class FENPositionTagValueFromString implements TagValueFromString<FENPosition<FieldInformation>> {
        private static final FENPositionTranslator<FieldInformation> translator = new FENPositionTranslatorImpl();

        @Override
        public FENPosition<FieldInformation> fromString(String tagValue) {
            return translator.toFENPosition(tagValue);
        }
    }
}
