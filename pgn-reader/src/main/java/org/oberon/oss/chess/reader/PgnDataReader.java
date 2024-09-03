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
import org.oberon.oss.chess.data.element.*;
import org.oberon.oss.chess.data.tags.StandardTag;
import org.oberon.oss.chess.data.tags.AbstractTag;
import org.oberon.oss.chess.data.tags.TagType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * .
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@Log4j2
public class PgnDataReader extends PGNImportFormatBaseListener {
    private static final Set<String> UNKNOWN_TAGS = new HashSet<>();

    private final PgnGameContainer.Builder       builder      = PgnGameContainer.builder();
    private final Game.Builder                   gameBuilder  = Game.builder();
    private final ErrorHandler                   errorHandler = new ErrorHandler(builder);
    private final Deque<ElementSequence.Builder> builderStack = new ArrayDeque<>();
    private final Set<AbstractTag<?>>            tagSection   = new HashSet<>();

    private PgnDataReader() {

    }

    public static Set<String> getUnknownTags() {
        return UNKNOWN_TAGS;
    }

    public static void processPgnData(@NotNull FilePgnSectionProvider provider, @NotNull PgnGameContainerProcessor processor) {
        while (provider.hasNext()) {
            processor.processGameContainer(new PgnDataReader().processInputData(provider.next()));
        }
    }


    private long                    start                  = 0;
    private StandardTag.Builder     tagBuilder             = StandardTag.builder();
    private ElementSequence.Builder elementSequenceBuilder = ElementSequence.builder();

    private PgnGameContainer processInputData(PgnSection section) {
        PGNImportFormatParser parser;
        try (InputStream inputStream = new ByteArrayInputStream(section.getSectionData().getBytes(section.getCharset()))) {
            builder.setPgnSection(section);

            Lexer             lexer             = new PGNImportFormatLexer(CharStreams.fromStream(inputStream));
            CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);

            parser = new PGNImportFormatParser(commonTokenStream);

            parser.removeErrorListeners();
            parser.addErrorListener(errorHandler);

            ParseTree       parseTree = parser.parse();
            ParseTreeWalker walker    = new ParseTreeWalker();

            walker.walk(this, parseTree);
        }
        catch (Exception e) {
            errorHandler.applicationError(e);
        }
        builder.setDateTimeRead(LocalDateTime.now());
        return builder.build();
    }

    @Override
    public void exitMoveTextSection(PGNImportFormatParser.MoveTextSectionContext ctx) {
        gameBuilder.elementSequence(elementSequenceBuilder.build());
        elementSequenceBuilder = ElementSequence.builder();
    }

    @Override
    public void enterRecursiveVariation(PGNImportFormatParser.RecursiveVariationContext ctx) {
        LOGGER.debug("Entering recursive variation at depth {}", builderStack.size());
        builderStack.push(elementSequenceBuilder);
        elementSequenceBuilder = ElementSequence.builder();
    }

    @Override
    public void exitRecursiveVariation(PGNImportFormatParser.RecursiveVariationContext ctx) {
        LOGGER.debug("Leaving recursive variation at depth {}", builderStack.size());
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
    public void enterSuffix(PGNImportFormatParser.SuffixContext ctx) {
        switch (ctx.getText()) {
            case "!" -> elementSequenceBuilder.element(new Nag("$1"));
            case "?" -> elementSequenceBuilder.element(new Nag("$2"));
            case "!!" -> elementSequenceBuilder.element(new Nag("$3"));
            case "??" -> elementSequenceBuilder.element(new Nag("$4"));
            case "!?" -> elementSequenceBuilder.element(new Nag("$5"));
            case "?!" -> elementSequenceBuilder.element(new Nag("$6"));
            default -> errorHandler.unknownSuffix(ctx.getText());
        }
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

    @Override
    public void enterParse(PGNImportFormatParser.ParseContext ctx) {
        start = System.nanoTime();
        LOGGER.trace("enter: enterParse");
    }

    @Override
    public void exitParse(PGNImportFormatParser.ParseContext ctx) {
        builder.setParseTime((int) (System.nanoTime() - start));
        LOGGER.trace("leave: enterParse");
    }

    @Override
    public void exitPgnGame(PGNImportFormatParser.PgnGameContext ctx) {
        builder.setGame(gameBuilder.build());
    }

    @Override
    public void exitTagSection(PGNImportFormatParser.TagSectionContext ctx) {
        gameBuilder.tagSection(tagSection);
    }

    @Override
    public void exitTagPair(PGNImportFormatParser.TagPairContext ctx) {
        tagSection.add(tagBuilder.build());
        tagBuilder = StandardTag.builder();
    }

    @Override
    public void enterTagName(PGNImportFormatParser.TagNameContext ctx) {
        try {
            tagBuilder.type(TagType.getTagType(ctx.getText().trim()));
        }
        catch (IllegalArgumentException e) {
            UNKNOWN_TAGS.add(ctx.getText());
        }

    }

    @Override
    public void enterTagValue(PGNImportFormatParser.TagValueContext ctx) {
        tagBuilder.tagValue(ctx.getText());
    }

}
