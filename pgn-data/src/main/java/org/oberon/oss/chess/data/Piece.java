package org.oberon.oss.chess.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.oberon.oss.chess.data.enums.ChessPiece;
import org.oberon.oss.chess.data.enums.Color;

import java.util.Objects;

/**
 * @author Fabien H. Dumay
 */
@ToString
@Getter
@AllArgsConstructor
public class Piece {
    private final ChessPiece chessPiece;
    private final Color      pieceColor;

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
