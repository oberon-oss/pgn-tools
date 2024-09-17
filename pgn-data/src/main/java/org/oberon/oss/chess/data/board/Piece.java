package org.oberon.oss.chess.data.board;

import lombok.Getter;
import org.oberon.oss.chess.data.enums.ChessBoardField;
import org.oberon.oss.chess.data.enums.ChessPiece;
import org.oberon.oss.chess.data.enums.Color;

/**
 * @author Fabien H. Dumay
 */
@Getter
public class Piece {
    private final Color color;
    private final ChessPiece chessPiece;
    private final ChessBoardField chessBoardField;

    public Piece(Color color, ChessPiece chessPiece, ChessBoardField chessBoardField) {
        this.color           = color;
        this.chessPiece      = chessPiece;
        this.chessBoardField = chessBoardField;
    }
}
