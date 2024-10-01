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

import generated.antlr.PGNImportFormatLexer;
import generated.antlr.PGNImportFormatParser;
import generated.antlr.PGNImportFormatParser.*;
import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.Game;
import org.oberon.oss.chess.data.Game.GameBuilder;
import org.oberon.oss.chess.data.GameTermination;
import org.oberon.oss.chess.data.element.*;
import org.oberon.oss.chess.data.element.ElementSequence.ElementSequenceBuilder;
import org.oberon.oss.chess.data.tags.TagSection;
import org.oberon.oss.chess.data.tags.TagSection.TagSectionBuilder;
import org.oberon.oss.chess.data.tags.defs.AbstractTag;
import org.oberon.oss.chess.data.tags.defs.TagBuilder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import generated.antlr.PGNImportFormatBaseListener;

/**
 * .
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@Log4j2
public class PgnDataReader extends PGNImportFormatBaseListener {
    private static final Set<String> UNKNOWN_TAGS = new HashSet<>();

    private final PgnGameContainer.Builder      builder      = PgnGameContainer.builder();
    private final GameBuilder                   gameBuilder  = Game.builder();
    private final ErrorHandler                  errorHandler = new ErrorHandler(builder);
    private final Deque<ElementSequenceBuilder> builderStack = new ArrayDeque<>();

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

    private final TagSectionBuilder                    tagSectionBuilder      = TagSection.builder();
    private       long                                 start                  = 0;
    private       TagBuilder<? extends AbstractTag<?>> tagBuilder             = new TagBuilder<>();
    private       ElementSequenceBuilder               elementSequenceBuilder = ElementSequence.builder();

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
    public void exitMoveTextSection(MoveTextSectionContext ctx) {
        gameBuilder.elementSequence(elementSequenceBuilder.build());
        elementSequenceBuilder = ElementSequence.builder();
    }

    @Override
    public void enterRecursiveVariation(RecursiveVariationContext ctx) {
        LOGGER.debug("Entering recursive variation at depth {}", builderStack.size());
        builderStack.push(elementSequenceBuilder);
        elementSequenceBuilder = ElementSequence.builder();
    }

    @Override
    public void exitRecursiveVariation(RecursiveVariationContext ctx) {
        LOGGER.debug("Leaving recursive variation at depth {}", builderStack.size());
        ElementSequenceBuilder oldBuilder = builderStack.pop();
        oldBuilder.element(elementSequenceBuilder.build());
        elementSequenceBuilder = oldBuilder;
    }

    @Override
    public void enterMoveNumberIndication(MoveNumberIndicationContext ctx) {
        elementSequenceBuilder.element(new MoveNumberIndication(ctx.getText()));
    }

    @Override
    public void enterSanMove(SanMoveContext ctx) {
        elementSequenceBuilder.element(new SanMove(ctx.getText()));
    }

    @Override
    public void enterMoveComment(MoveCommentContext ctx) {
        elementSequenceBuilder.element(new MoveComment(ctx.getText()));
    }


    @Override
    public void enterNag(NagContext ctx) {
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
    public void enterRestOfLineComment(RestOfLineCommentContext ctx) {
        elementSequenceBuilder.element(new RestOfLineComment(ctx.getText()));
    }

    @Override
    public void enterProcessingInstruction(ProcessingInstructionContext ctx) {
        elementSequenceBuilder.element(new ProcessingInstruction(ctx.getText()));
    }

    @Override
    public void enterGameTermination(GameTerminationContext ctx) {
        gameBuilder.gameTermination(new GameTermination(ctx.getText()));
    }

    @Override
    public void enterParse(ParseContext ctx) {
        start = System.nanoTime();
        LOGGER.trace("enter: enterParse");
    }

    @Override
    public void exitParse(ParseContext ctx) {
        builder.setParseTime((int) (System.nanoTime() - start));
        LOGGER.trace("leave: enterParse");
    }

    @Override
    public void exitPgnGame(PgnGameContext ctx) {
        builder.setGame(gameBuilder.build());
    }

    @Override
    public void exitTagSection(TagSectionContext ctx) {
        gameBuilder.tagSection(tagSectionBuilder.build());
    }

    @Override
    public void exitTagPair(TagPairContext ctx) {
        tagSectionBuilder.tag(tagBuilder.build());
        tagBuilder = new TagBuilder<>();
    }

    @Override
    public void enterTagName(TagNameContext ctx) {
        tagBuilder.setTagType(ctx.getText().trim());
    }

    @Override
    public void enterTagValue(TagValueContext ctx) {
        tagBuilder.setValue(ctx.getText());
    }

}
