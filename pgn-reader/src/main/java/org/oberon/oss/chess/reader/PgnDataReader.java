/*
 * 	Copyright © 2000 Fabien H. Dumay
 *
 * 	Licensed under the Apache License, Version 2.0 (the "License");
 * 	you may not use this file except in compliance with the License.
 * 	You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * 	Unless required by applicable law or agreed to in writing, software
 * 	distributed under the License is distributed on an "AS IS" BASIS,
 * 	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 	See the License for the specific language governing permissions and
 * 	limitations under the License.
 */
package org.oberon.oss.chess.reader;


import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.Game;
import org.oberon.oss.chess.data.GameTermination;
import org.oberon.oss.chess.data.Tag;
import org.oberon.oss.chess.data.element.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.*;

/**
 * .
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@Log4j2
public class PgnDataReader extends PGNImportFormatBaseListener {
    private final PgnGameContainer.Builder builder = PgnGameContainer.builder();

    private PgnDataReader() {

    }

    public static void processPgnData(
          @NotNull FilePgnSectionProvider provider,
          @NotNull PgnGameContainerProcessor processor
    ) throws IOException {
        PgnDataReader reader = new PgnDataReader();
        while (provider.hasNext()) {
            processor.processGameContainer(reader.processInputData(provider.next()));
        }
    }


    private PgnGameContainer processInputData(PgnSection section) throws IOException {

        try (InputStream inputStream = new ByteArrayInputStream(section.getSectionData().getBytes(section.getCharset()))) {
            builder.pgnSection(section);

            Lexer                 lexer             = new PGNImportFormatLexer(CharStreams.fromStream(inputStream));
            CommonTokenStream     commonTokenStream = new CommonTokenStream(lexer);
            PGNImportFormatParser parser            = new PGNImportFormatParser(commonTokenStream);
            ParseTree             parseTree         = parser.parse();
            ParseTreeWalker       walker            = new ParseTreeWalker();
            walker.walk(this, parseTree);
        }
        catch (Exception e) {
            LOGGER.error("Terminated. exception encountered:", e);
        }
        builder.dateTimeRead(LocalDateTime.now());
        return builder.build();
    }


    private ElementSequence.Builder elementSequenceBuilder = null;

    @Override
    public void enterMoveTextSection(PGNImportFormatParser.MoveTextSectionContext ctx) {
        elementSequenceBuilder = ElementSequence.builder();
    }

    @Override
    public void exitMoveTextSection(PGNImportFormatParser.MoveTextSectionContext ctx) {
        gameBuilder.elementSequence(elementSequenceBuilder.build());
        elementSequenceBuilder = null;
    }

    private final Deque<ElementSequence.Builder> builderStack = new ArrayDeque<>();

    @Override
    public void enterRecursiveVariation(PGNImportFormatParser.RecursiveVariationContext ctx) {
        LOGGER.debug("Entering recursive variation at depth {}",builderStack.size());
        builderStack.push(elementSequenceBuilder);
        elementSequenceBuilder = ElementSequence.builder();
    }

    @Override
    public void exitRecursiveVariation(PGNImportFormatParser.RecursiveVariationContext ctx) {
        LOGGER.debug("Leaving recursive variation at depth {}",builderStack.size());
        ElementSequence.Builder oldBuilder = builderStack.pop();
        oldBuilder.element(elementSequenceBuilder.build());
        elementSequenceBuilder = oldBuilder;
    }

    @Override
    public void enterMoveNumberIndication(PGNImportFormatParser.MoveNumberIndicationContext ctx) {
        elementSequenceBuilder.element(new MoveNumberIndication(ctx.getText()));
    }

    @Override
    public void enterSanMove(PGNImportFormatParser.SanMoveContext ctx) {
        elementSequenceBuilder.element(new SanMove(ctx.getText()));
    }

    @Override
    public void enterMoveComment(PGNImportFormatParser.MoveCommentContext ctx) {
        elementSequenceBuilder.element(new MoveComment(ctx.getText()));
    }

    @Override
    public void enterNag(PGNImportFormatParser.NagContext ctx) {
        elementSequenceBuilder.element(new Nag(ctx.getText()));
    }

    @Override
    public void enterRestOfLineComment(PGNImportFormatParser.RestOfLineCommentContext ctx) {
        elementSequenceBuilder.element(new RestOfLineComment(ctx.getText()));
    }

    @Override
    public void enterProcessingInstruction(PGNImportFormatParser.ProcessingInstructionContext ctx) {
        elementSequenceBuilder.element(new ProcessingInstruction(ctx.getText()));
    }

    @Override
    public void enterGameTermination(PGNImportFormatParser.GameTerminationContext ctx) {
        gameBuilder.gameTermination(new GameTermination(ctx.getText()));
    }

    private long start = 0;

    @Override
    public void enterParse(PGNImportFormatParser.ParseContext ctx) {
        start = System.nanoTime();
        LOGGER.trace("enter: enterParse");
    }

    @Override
    public void exitParse(PGNImportFormatParser.ParseContext ctx) {
        builder.parseTime((int) (System.nanoTime() - start));
        LOGGER.trace("leave: enterParse");
    }

    private Game.Builder gameBuilder = null;

    @Override
    public void enterPgnGame(PGNImportFormatParser.PgnGameContext ctx) {
        gameBuilder = Game.builder();
    }

    @Override
    public void exitPgnGame(PGNImportFormatParser.PgnGameContext ctx) {
        builder.game(gameBuilder.build());
        gameBuilder = null;
    }

    private Set<Tag> tagSection = null;

    @Override
    public void enterTagSection(PGNImportFormatParser.TagSectionContext ctx) {
        tagSection = new HashSet<>();
    }

    @Override
    public void exitTagSection(PGNImportFormatParser.TagSectionContext ctx) {
        gameBuilder.tagSection(tagSection);
        tagSection = null;
    }

    private Tag.Builder tagBuilder = null;

    @Override
    public void enterTagPair(PGNImportFormatParser.TagPairContext ctx) {
        tagBuilder = Tag.builder();
    }

    @Override
    public void exitTagPair(PGNImportFormatParser.TagPairContext ctx) {
        tagSection.add(tagBuilder.build());
        tagBuilder = null;
    }

    @Override
    public void enterTagName(PGNImportFormatParser.TagNameContext ctx) {
        tagBuilder.tagName(ctx.getText());
    }

    @Override
    public void enterTagValue(PGNImportFormatParser.TagValueContext ctx) {
        tagBuilder.tagValue(ctx.getText());
    }
}
