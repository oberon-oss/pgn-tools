package org.oberon.oss.chess.data.fen;

import org.oberon.oss.chess.data.field.FieldInformation;
import org.oberon.oss.chess.data.position.Position;
import org.oberon.oss.chess.data.ChessColor;

/**
 * Allows the querying of the information extracted from a FEN setup string.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public interface FENPosition<F extends FieldInformation> {
    /**
     * Returns the chess specific information for the FENPosition.
     *
     * @return The chess position for this FEN position.
     *
     * @since 1.0.0
     */
    Position<F> chessPosition();

    /**
     * Returns the number of half-moves played that lead op to the position captured in the FEN setup string.
     *
     * @return the number of half moves (or plies) that have been played so far
     *
     * @since 1.0.0
     */
    int halveMoveClock();

    /**
     * Returns the number of full moves (1 half move for {@link ChessColor#WHITE WHITE} and 1 half move for {@link ChessColor#BLACK BLACK}).
     *
     * @return The number of full moves played so far
     *
     * @since 1.0.0
     */
    int fullMoveNumber();
}
