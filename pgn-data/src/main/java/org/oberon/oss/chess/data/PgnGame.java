package org.oberon.oss.chess.data;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Set;


@Builder
@Getter
@ToString
public class PgnGame {
    private final Set<PgnTag> tagSet;
    private final List<PgnPly> mainMoveSequence;
}
