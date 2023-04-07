package org.oberon.oss.chess.data;

import org.oberon.oss.chess.data.builders.PgnGameBuilder;

import java.util.List;
import java.util.Set;

public class PgnGame  {
    private final Set<PgnTag> tagSet;

    private final List<PgnPly> mainMoveSequence;

    private PgnGame(PgnGameBuilder builder) {
        this.tagSet = builder.getTagSet();
        this.mainMoveSequence = builder.getMainMoveSequence();
    }

    public static PgnGame createGame(PgnGameBuilder pgnGameBuilder) {
        return new PgnGame(pgnGameBuilder);
    }


    public static PgnGameBuilder getBuilder() {
        return new PgnGameBuilder();
    }
}
