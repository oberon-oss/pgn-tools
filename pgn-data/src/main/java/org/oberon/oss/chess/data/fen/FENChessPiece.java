package org.oberon.oss.chess.data.fen;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.ChessPiece;
import org.oberon.oss.chess.data.Piece;

import java.util.HashMap;
import java.util.Map;

import static org.oberon.oss.chess.data.ChessColor.BLACK;

/**
 * Extends the information contained in the {@link ChessPiece} enumeration by adding the representation of the black and white
 * pieces as defined in a FEN setup string.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@Getter
public enum FENChessPiece {
    KING(ChessPiece.KING, "K", "k"),
    QUEEN(ChessPiece.QUEEN, "Q", "q"),
    ROOK(ChessPiece.ROOK, "R", "r"),
    BISHOP(ChessPiece.BISHOP, "B", "b"),
    KNIGHT(ChessPiece.KNIGHT, "N", "n"),
    PAWN(ChessPiece.PAWN, "P", "p");

    private final ChessPiece chessPiece;
    private final String     forWhite;
    private final String     forBlack;

    FENChessPiece(ChessPiece chessPiece, String forWhite, String forBlack) {
        this.chessPiece = chessPiece;
        this.forWhite   = forWhite;
        this.forBlack   = forBlack;
    }

    private static final Map<String, FENChessPiece> LOOKUP_MAP;

    static {
        Map<String, FENChessPiece> map = new HashMap<>();
        for (FENChessPiece value : FENChessPiece.values()) {
            map.put(value.forWhite, value);
            map.put(value.forBlack, value);
        }
        LOOKUP_MAP = Map.copyOf(map);
    }

    /**
     * Performs a lookup of the specified fen type string.
     *
     * @param fenType The fen type string to lookup. This must be one of the following values:
     *                <ul>
     *                <li>r: black rook</li>
     *                <li>n: black knight</li>
     *                <li>b: black bishop</li>
     *                <li>q: black queen</li>
     *                <li>k: black king</li>
     *                <li>R: white rook</li>
     *                <li>N: white knight</li>
     *                <li>B: white bishop</li>
     *                <li>Q: white queen</li>
     *                <li>K: white king</li>
     *                </ul>
     *
     * @return the FEN chess piece.
     *
     * @throws IllegalArgumentException if the value for the 'fenType' parameter is not in the above specified list.
     * @since 1.0.0
     */
    public static FENChessPiece lookupFENChessPiece(String fenType) {
        FENChessPiece piece = LOOKUP_MAP.get(fenType);
        if (piece == null) {
            throw new IllegalArgumentException(fenType + " is not a valid fen piece");
        }
        return piece;
    }

    /**
     * Returns the correct single character representation of the provided pieces based on its type and color.
     *
     * @param piece The piece to return the character representation for.
     *
     * @return The character representation of the provided piece
     *
     * @since 1.0.0
     */
    public static String getFENCharacterForPiece(@NotNull Piece piece) {
        return switch (piece.getChessPiece()) {
            case KING -> piece.getPieceColor() == BLACK ? KING.getForBlack() : KING.getForWhite();
            case QUEEN -> piece.getPieceColor() == BLACK ? QUEEN.getForBlack() : QUEEN.getForWhite();
            case ROOK -> piece.getPieceColor() == BLACK ? ROOK.getForBlack() : ROOK.getForWhite();
            case BISHOP -> piece.getPieceColor() == BLACK ? BISHOP.getForBlack() : BISHOP.getForWhite();
            case KNIGHT -> piece.getPieceColor() == BLACK ? KNIGHT.getForBlack() : KNIGHT.getForWhite();
            case PAWN -> piece.getPieceColor() == BLACK ? PAWN.getForBlack() : PAWN.getForWhite();
            default -> throw new IllegalArgumentException("Enum value " + piece + " Not supported");
        };
    }
}
