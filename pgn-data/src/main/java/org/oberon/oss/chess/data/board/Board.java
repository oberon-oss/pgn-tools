package org.oberon.oss.chess.data.board;

import org.oberon.oss.chess.data.Piece;
import org.oberon.oss.chess.data.field.FieldInformation;

import java.util.Map;

/**
 * @author Fabien H. Dumay
 */
public interface Board<F extends FieldInformation> {
    Map<F, Piece> pieceMapping();
}
