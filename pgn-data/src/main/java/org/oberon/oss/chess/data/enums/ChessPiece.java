package org.oberon.oss.chess.data.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fabien H. Dumay
 */
@Getter
public enum ChessPiece {
    KING("K", "k"),
    QUEEN("Q", "q"),
    ROOK("R", "r"),
    BISHOP("B", "b"),
    KNIGHT("K", "k"),
    PAWN("P", "p");

    private final String forWhite;
    private final String forBlack;

    ChessPiece(String forWhite, String forBlack) {
        this.forWhite = forWhite;
        this.forBlack = forBlack;
    }

    private static final Map<String,ChessPiece> LOOKUP_MAP;
    static {
        Map<String,ChessPiece> map = new HashMap<>();
        for (ChessPiece value : ChessPiece.values()) {
            map.put(value.forWhite, value);
            map.put(value.forBlack, value);
        }
        LOOKUP_MAP = Map.copyOf(map);
    }

    public static ChessPiece lookup(String fenType) {
        return LOOKUP_MAP.get(fenType);
    }
}
