package org.oberon.oss.chess.data.position;

import org.jetbrains.annotations.Nullable;
import org.oberon.oss.chess.data.board.Board;
import org.oberon.oss.chess.data.ChessColor;
import org.oberon.oss.chess.data.field.ChessField;
import org.oberon.oss.chess.data.field.FieldInformation;

/**
 * Allows the querying of the information extracted from a FEN setup string.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public interface Position<F extends FieldInformation> {
    /**
     * Returns a map of the fields occupied by pieces defined in the FEN position.
     *
     * @return a read-only map of the chess position.
     *
     * @since 1.0.0
     */
    Board<F> board();

    /**
     * Returns the color that is to make the next moved.
     *
     * @return a value from the {@link ChessColor} enumeration specifying the color to move next.
     *
     * @since 1.0.0
     */
    ChessColor sideToMove();

    /**
     * Returns the {@link F} that would allow an en-passant capture.
     *
     * @return The en-passant field, which is a chess field in the range
     *             <ul>
     *                <li> {@link ChessField#A3 A3}-{@link ChessField#H3 H3} (if {@link #sideToMove()} returns {@link ChessColor#BLACK BLACK})</li>
     *                <li> {@link ChessField#A6 A6}-{@link ChessField#H6 H6} (if {@link #sideToMove()} returns {@link ChessColor#WHITE WHITE})</li>
     *             </ul>
     *       or {@literal <null>} if there is no en-passant move to be made.
     *
     * @since 1.0.0
     */
    @Nullable F enPassantField();

    /**
     * Returns a flag indicating if {@link ChessColor#WHITE WHITE}) is allowed to castle King side (O-O).
     *
     * @return <b>true</b> if allowed, or <b>false</b> if not allowed.
     *
     * @since 1.0.0
     */
    boolean whiteCanCastleKingSide();

    /**
     * Returns a flag indicating if {@link ChessColor#WHITE WHITE}) is allowed to castle Queen side (O-O-O).
     *
     * @return <b>true</b> if allowed, or <b>false</b> if not allowed.
     *
     * @since 1.0.0
     */
    boolean whiteCanCastleQueenSide();

    /**
     * Returns a flag indicating if {@link ChessColor#BLACK BLACK}) is allowed to castle King side (O-O).
     *
     * @return <b>true</b> if allowed, or <b>false</b> if not allowed.
     *
     * @since 1.0.0
     */
    boolean blackCanCastleKingSide();

    /**
     * Returns a flag indicating if {@link ChessColor#BLACK BLACK}) is allowed to castle Queen side (O-O-O).
     *
     * @return <b>true</b> if allowed, or <b>false</b> if not allowed.
     *
     * @since 1.0.0
     */
    boolean blackCanCastleQueenSide();
}
