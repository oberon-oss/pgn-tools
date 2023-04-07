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


import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.oberon.oss.chess.data.PgnTag;
import org.oberon.oss.chess.data.builders.PgnGameBuilder;
import org.oberon.oss.chess.data.builders.PgnTagBuilder;
import org.oberon.oss.chess.reader.PGNImportFormatParser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * .
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public class PGNDataReader extends PGNImportFormatBaseListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(PGNDataReader.class);
    private final List<PgnGameContainer> containerList = new ArrayList<>();
    private final PgnSource source;
    int elementSequenceDepth = 0;

    private PgnGameBuilder gameBuilder;
    private Set<PgnTag> tagSet;
    private PgnTagBuilder tagBuilder;

    public PGNDataReader(PgnSource source) {
        super();
        this.source = source;
    }

    public List<PgnGameContainer> processInputData() throws IOException {

        //noinspection BlockingMethodInNonBlockingContext
        try (InputStream inputStream = source.getSourceLocation().toURL().openStream()) {
            Lexer lexer = new PGNImportFormatLexer(CharStreams.fromStream(inputStream));
            CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
            PGNImportFormatParser parser = new PGNImportFormatParser(commonTokenStream);
            ParseTree parseTree = parser.parse();
            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(this, parseTree);
        }
        return containerList;
    }


    @Override
    public void enterPgnGame(PgnGameContext ctx) {
        gameBuilder = new PgnGameBuilder();
        super.enterPgnGame(ctx);
    }

    @Override
    public void enterTagSection(TagSectionContext ctx) {
        super.enterTagSection(ctx);
        tagSet = new HashSet<>();
    }

    @Override
    public void enterTagPair(TagPairContext ctx) {
        super.enterTagPair(ctx);
        tagBuilder = new PgnTagBuilder();
    }

    @Override
    public void enterTagName(TagNameContext ctx) {
        super.enterTagName(ctx);
        tagBuilder.setTagName(ctx.getText());
    }

    @Override
    public void enterTagValue(TagValueContext ctx) {
        super.enterTagValue(ctx);
        tagBuilder.setTagValue(ctx.getText());
    }

    @Override
    public void exitTagPair(TagPairContext ctx) {
        super.exitTagPair(ctx);
        tagSet.add(tagBuilder.build());
        tagBuilder = null;
    }

    @Override
    public void exitTagSection(TagSectionContext ctx) {
        super.exitTagSection(ctx);
        gameBuilder.setTagSet(tagSet);
    }

    @Override
    public void enterElementSequence(ElementSequenceContext ctx) {
        super.enterElementSequence(ctx);
        ++elementSequenceDepth;
    }

    @Override
    public void exitElementSequence(ElementSequenceContext ctx) {
        super.enterElementSequence(ctx);
        --elementSequenceDepth;

    }

    @Override
    public void exitPgnGame(PgnGameContext ctx) {
        super.exitPgnGame(ctx);
        LOGGER.info("Writing game #{}", containerList.size() + 1);
        containerList.add(new PgnGameContainer(source, gameBuilder.build(), ctx));
        gameBuilder = null;
    }

}
