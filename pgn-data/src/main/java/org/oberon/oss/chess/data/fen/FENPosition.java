package org.oberon.oss.chess.data.fen;

import org.jetbrains.annotations.Nullable;
import org.oberon.oss.chess.data.Piece;
import org.oberon.oss.chess.data.enums.ChessField;
import org.oberon.oss.chess.data.enums.Color;

import java.util.Map;

/**
 * Allows the querying of the information extracted from a FEN setup string.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public interface FENPosition {
    /**
     * Returns a map of the fields occupied by pieces defined in the FEN position.
     *
     * @return a read-only map of the chess position.
     *
     * @since 1.0.0
     */
    Map<ChessField, Piece> board();

    /**
     * Returns the color that is to make the next moved.
     *
     * @return a value from the {@link Color} enumeration specifying the color to move next.
     *
     * @since 1.0.0
     */
    Color sideToMove();

    /**
     * Returns the number of half-moves played that lead op to the position captured in the FEN setup string.
     *
     * @return the number of half moves (or plies) that have been played so far
     *
     * @since 1.0.0
     */
    int halveMoveClock();

    /**
     * Returns the number of full moves (1 half move for {@link Color#WHITE WHITE} and 1 half move for {@link Color#BLACK BLACK}).
     *
     * @return The number of full moves played so far
     *
     * @since 1.0.0
     */
    int fullMoveNumber();

    /**
     * Returns the {@link ChessField} that would allow an en-passant capture.
     *
     * @return The en-passant field, which is a chess field in the range
     *       <ul>
     *          <li> {@link ChessField#A3 A3}-{@link ChessField#H3 H3} (if {@link #sideToMove()} returns {@link Color#BLACK BLACK})</li>
     *          <li> {@link ChessField#A6 A6}-{@link ChessField#H6 H6} (if {@link #sideToMove()} returns {@link Color#WHITE WHITE})</li>
     *       </ul>
     *
     * @since 1.0.0
     */
    @Nullable ChessField enPassantField();

    /**
     * Returns a flag indicating if {@link Color#WHITE WHITE}) is allowed to castle King side (O-O).
     *
     * @return <b>true</b> if allowed, or <b>false</b> if not allowed.
     *
     * @since 1.0.0
     */
    boolean whiteCanCastleKingSide();

    /**
     * Returns a flag indicating if {@link Color#WHITE WHITE}) is allowed to castle Queen side (O-O-O).
     *
     * @return <b>true</b> if allowed, or <b>false</b> if not allowed.
     *
     * @since 1.0.0
     */
    boolean whiteCanCastleQueenSide();

    /**
     * Returns a flag indicating if {@link Color#BLACK BLACK}) is allowed to castle King side (O-O).
     *
     * @return <b>true</b> if allowed, or <b>false</b> if not allowed.
     *
     * @since 1.0.0
     */
    boolean blackCanCastleKingSide();

    /**
     * Returns a flag indicating if {@link Color#BLACK BLACK}) is allowed to castle Queen side (O-O-O).
     *
     * @return <b>true</b> if allowed, or <b>false</b> if not allowed.
     *
     * @since 1.0.0
     */
    boolean blackCanCastleQueenSide();
}
