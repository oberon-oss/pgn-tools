package org.oberon.oss.chess.data.builders;

import org.oberon.oss.chess.data.PgnGame;
import org.oberon.oss.chess.data.PgnPly;
import org.oberon.oss.chess.data.PgnTag;
import org.oberon.oss.chess.data.util.PgnClassBuilder;

import java.util.List;
import java.util.Set;


/**
 * Builder to create {@link PgnGame} objects.
 *
 * @since 1.0.0
 */
public class PgnGameBuilder implements PgnClassBuilder<PgnGame> {
    private Set<PgnTag> tagSet;
    private List<PgnPly> mainMoveSequence;

    @Override
    public PgnGame build() {
        return PgnGame.createGame(this);
    }

    public List<PgnPly> getMainMoveSequence() {
        return mainMoveSequence;
    }

    public PgnGameBuilder setMainMoveSequence(List<PgnPly> mainMoveSequence) {
        this.mainMoveSequence = mainMoveSequence;
        return this;
    }

    public Set<PgnTag> getTagSet() {
        return tagSet;
    }

    public PgnGameBuilder setTagSet(Set<PgnTag> tagSet) {
        this.tagSet = tagSet;
        return this;
    }
}
