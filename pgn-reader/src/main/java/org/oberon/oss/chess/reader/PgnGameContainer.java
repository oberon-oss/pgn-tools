package org.oberon.oss.chess.reader;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.oberon.oss.chess.data.Game;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class PgnGameContainer {
    private final int           parseTime;
    private final LocalDateTime dateTimeRead;
    private final PgnSection    pgnSection;
    private final Game          game;
}
