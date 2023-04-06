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
import org.oberon.oss.chess.reader.PGNImportFormatParser.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * .
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public class PGNDataReader extends PGNImportFormatBaseListener {

	private static final Logger            LOGGER = LoggerFactory.getLogger(PGNDataReader.class);
	private final        PGNEventProcessor processor;
	int elementSequenceDepth = 0;
	int plyNumber            = 1;
	private String tagName;
	private String tagValue;

	public PGNDataReader(PGNEventProcessor processor) {
		super();
		this.processor = processor;
	}

	public void processInputData(InputStream inputStream) throws IOException {

		Lexer                 lexer             = new PGNImportFormatLexer(CharStreams.fromStream(inputStream));
		CommonTokenStream     commonTokenStream = new CommonTokenStream(lexer);
		PGNImportFormatParser parser            = new PGNImportFormatParser(commonTokenStream);
		ParseTree             parseTree         = parser.parse();
		ParseTreeWalker       walker            = new ParseTreeWalker();
		walker.walk(this, parseTree);
	}

	@Override
	public void enterElementSequence(ElementSequenceContext ctx) {
		super.enterElementSequence(ctx);
		++elementSequenceDepth;
		processor.startMoveSequence(plyNumber);
	}

	@Override
	public void exitElementSequence(ElementSequenceContext ctx) {
		super.enterElementSequence(ctx);
		--elementSequenceDepth;
		processor.terminateMoveSequence();
	}

	@Override
	public void enterTagName(TagNameContext ctx) {
		super.enterTagName(ctx);
		tagName = ctx.getText();

	}

	@Override
	public void enterTagValue(TagValueContext ctx) {
		super.enterTagValue(ctx);
		tagValue = ctx.getText();
	}

	@Override
	public void exitTagPair(TagPairContext ctx) {
		super.exitTagPair(ctx);
		processor.addTag(tagName, tagValue);
	}

	@Override
	public void enterPgnGame(PgnGameContext ctx) {
		super.enterPgnGame(ctx);
		processor.startGame();
	}

	@Override
	public void exitPgnGame(PgnGameContext ctx) {
		super.exitPgnGame(ctx);
		processor.endGame();
	}

}
