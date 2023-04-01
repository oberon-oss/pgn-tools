package org.oberon.oss.chess.data.builders;

import org.oberon.oss.chess.data.PgnPly;
import org.oberon.oss.chess.data.util.PgnClassBuilder;

public class PgnPlyBuilder implements PgnClassBuilder<PgnPly> {

    private int plyNumber;

    public PgnPlyBuilder setMoveText(String moveText) {
        this.moveText = moveText;
        return this;
    }

    private String moveText;

    public PgnClassBuilder<PgnPly> setPlyNumber(int plyNumber) {
        this.plyNumber = plyNumber;
        return this;
    }

    @Override
    public PgnPly build() {
        return PgnPly.createPgnPly(this);
    }

    public int getPlyNumber() {
        return plyNumber;
    }

    public String getMoveText() {
        return moveText;
    }
}
