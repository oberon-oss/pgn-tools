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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * .
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@Log4j2
public class PgnDataReader extends PGNImportFormatBaseListener {
    private final long                     start   = System.nanoTime();
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


    public PgnGameContainer processInputData(PgnSection section) throws IOException {

        try (InputStream inputStream = new ByteArrayInputStream(section.getSectionData().getBytes(section.getCharset()))) {
            Lexer                 lexer             = new PGNImportFormatLexer(CharStreams.fromStream(inputStream));
            CommonTokenStream     commonTokenStream = new CommonTokenStream(lexer);
            PGNImportFormatParser parser            = new PGNImportFormatParser(commonTokenStream);
            ParseTree             parseTree         = parser.parse();
            ParseTreeWalker       walker            = new ParseTreeWalker();
            walker.walk(this, parseTree);
        }
        return builder.build();
    }

    @Override
    public void enterParse(PGNImportFormatParser.ParseContext ctx) {
        super.enterParse(ctx);
    }

    @Override
    public void exitParse(PGNImportFormatParser.ParseContext ctx) {
        long end;
        super.exitParse(ctx);
        end = System.nanoTime() - start;

        LOGGER.info("exitParse {} microseconds", end / 1000);
    }
}
