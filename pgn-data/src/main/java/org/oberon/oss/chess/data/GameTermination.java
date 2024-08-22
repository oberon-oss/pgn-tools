package org.oberon.oss.chess.data;

import lombok.Getter;
import lombok.ToString;
import org.oberon.oss.chess.data.element.Element;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class GameTermination implements Element {
    private final TerminationReason reason;

    public GameTermination(String text) {
        reason = TerminationReason.lookup(text);
    }


    @Getter
    public enum TerminationReason {
        WHITE_WINS("1-0"),
        BLACK_WINS("0-1"),
        DRAWN_GAME("1/2-1/2"),
        UNDECIDED_OR_UNKNOWN("*"),
        ;

        private final String stringRepresentation;

        TerminationReason(String stringRepresentation) {
            this.stringRepresentation = stringRepresentation;
        }


        private static final Map<String, TerminationReason> TERMINATION_REASON_MAP;

        public static TerminationReason lookup(String stringRepresentation) {
            return TERMINATION_REASON_MAP.get(stringRepresentation);
        }

        static {
            Map<String, TerminationReason> map = new HashMap<>();

            for (TerminationReason reason : values()) {
                map.put(reason.stringRepresentation, reason);
            }

            TERMINATION_REASON_MAP = Map.copyOf(map);
        }
    }
}
