package org.oberon.oss.chess.reader;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.oberon.oss.chess.data.Game;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@ToString
public class PgnGameContainer {
    private final int                             parseTime;
    private final LocalDateTime                   dateTimeRead;
    private final PgnSection                      pgnSection;
    private final Game                            game;
    private final Map<ErrorLogEntry, Set<String>> recordErrors;

    private PgnGameContainer(final int parseTime,
                             final LocalDateTime dateTimeRead,
                             final PgnSection pgnSection,
                             Game game,
                             Map<ErrorLogEntry, Set<String>> recordErrors) {
        this.parseTime    = parseTime;
        this.dateTimeRead = dateTimeRead;
        this.pgnSection   = pgnSection;
        this.game         = game;
        this.recordErrors = Map.copyOf(recordErrors);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Setter
    @Getter(AccessLevel.PACKAGE)
    public static class Builder {
        private       int                             parseTime;
        private       LocalDateTime                   dateTimeRead;
        private       PgnSection                      pgnSection;
        private       Game                            game;
        private final Map<ErrorLogEntry, Set<String>> recordErrors = new HashMap<>();

        private Builder() {
        }

        public PgnGameContainer build() {
            return new PgnGameContainer(parseTime, dateTimeRead, pgnSection, game, recordErrors);
        }
    }
}
