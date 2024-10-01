package org.oberon.oss.chess.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Describes the attribute of an active piece on the chess board.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */

@ToString
@Getter
@AllArgsConstructor
@SuppressWarnings("ClassCanBeRecord")
public class Piece {
    private final ChessPiece chessPiece;
    private final ChessColor pieceColor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piece piece = (Piece) o;
        return chessPiece == piece.chessPiece && pieceColor == piece.pieceColor;
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(chessPiece);
        result = 31 * result + Objects.hashCode(pieceColor);
        return result;
    }
}
