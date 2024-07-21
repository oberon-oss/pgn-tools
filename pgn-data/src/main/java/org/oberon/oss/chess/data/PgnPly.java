package org.oberon.oss.chess.data;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class PgnPly {
    private final int plyNumber;
    private final String moveText;
    private final String comment;
}
