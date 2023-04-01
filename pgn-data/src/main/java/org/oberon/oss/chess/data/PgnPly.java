package org.oberon.oss.chess.data;

import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.builders.PgnPlyBuilder;
import org.oberon.oss.chess.data.util.BaseClass;
import org.oberon.oss.chess.data.util.PgnClassBuilder;

public class PgnPly implements BaseClass<PgnPly> {
    private final int plyNumber;
    private final String moveText;

    private PgnPly() {
        throw new IllegalStateException("Private constructor should never be called.");
    }

    private PgnPly(PgnPlyBuilder builder) {
        plyNumber = builder.getPlyNumber();
        moveText = builder.getMoveText();
    }

    public static PgnPly createPgnPly(@NotNull PgnPlyBuilder builder) {
        return new PgnPly(builder);
    }

    @Override
    public PgnClassBuilder<PgnPly> getBuilder() {
        return new PgnPlyBuilder();
    }
}
