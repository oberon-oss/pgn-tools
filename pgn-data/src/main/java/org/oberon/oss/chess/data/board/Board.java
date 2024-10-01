package org.oberon.oss.chess.data.board;

import org.oberon.oss.chess.data.Piece;
import org.oberon.oss.chess.data.field.FieldInformation;

import java.util.Map;

/**
 * Defines the board in a chess game.
 *
 * @param <F> interface or class that extends the FieldInformation Interface.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public interface Board<F extends FieldInformation> {
    /**
     * Returns the pieces currently present on the board.
     *
     * @return An unmodifiable map of the fields and the occupying pieces on that field (if applicable) on the board. The returned
     *       map will contain at least 2 and at most 32 entries.
     *
     * @since 1.0.0
     */
    Map<F, Piece> pieceMapping();
}
