package org.oberon.oss.chess.data.board;

import org.oberon.oss.chess.data.enums.ChessBoardField;
import org.oberon.oss.chess.data.enums.ChessPiece;
import org.oberon.oss.chess.data.enums.Color;

/**
 * @author Fabien H. Dumay
 */
public interface Piece {
    Color getColor();

    ChessPiece getChessPiece();

    ChessBoardField getCurrentField();
}
