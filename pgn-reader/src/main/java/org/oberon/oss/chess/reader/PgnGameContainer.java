package org.oberon.oss.chess.reader;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.oberon.oss.chess.data.PgnGame;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
public class PgnGameContainer {
    private final PgnSource source;
    private final PgnGame   game;
    private final int       startLine;
    private final int       endLine;
    private final int       parseTime;
    private final LocalDateTime dateTimeRead;

}
