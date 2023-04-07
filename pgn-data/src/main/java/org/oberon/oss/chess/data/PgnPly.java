package org.oberon.oss.chess.data;

import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.builders.PgnPlyBuilder;

public class PgnPly {
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

    public static PgnPlyBuilder getBuilder() {
        return new PgnPlyBuilder();
    }
}
