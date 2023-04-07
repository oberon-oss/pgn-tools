package org.oberon.oss.chess.reader;

import org.oberon.oss.chess.data.PgnGame;

import java.time.LocalDateTime;

public class PgnGameContainer {
    private final PgnSource source;
    private final PgnGame game;;

    private final int startLine;
    private final int endLine;

    private final LocalDateTime dateTimeRead;

    public PgnGameContainer(PgnSource source, PgnGame game, PGNImportFormatParser.PgnGameContext ctx) {
        this.source = source;
        this.game = game;
        startLine = ctx.getStart().getLine();
        endLine = ctx.getStop().getLine();
        dateTimeRead = LocalDateTime.now();
    }
}
