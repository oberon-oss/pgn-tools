package org.oberon.oss.chess.data;

import org.oberon.oss.chess.data.builders.PgnGameBuilder;
import org.oberon.oss.chess.data.util.BaseClass;
import org.oberon.oss.chess.data.util.PgnClassBuilder;

import java.util.List;
import java.util.Set;

public class PgnGame implements BaseClass<PgnGame> {


    private final Set<PgnTag> tagSet;

    private final List<PgnPly> mainMoveSequence;


    private PgnGame(PgnGameBuilder builder) {
        this.tagSet = builder.getTagSet();
        this.mainMoveSequence = builder.getMainMoveSequence();
    }

    public static PgnGame createGame(PgnGameBuilder pgnGameBuilder) {
        return new PgnGame(pgnGameBuilder);
    }

    @Override
    public PgnClassBuilder<PgnGame> getBuilder() {
        return new PgnGameBuilder();
    }
}
