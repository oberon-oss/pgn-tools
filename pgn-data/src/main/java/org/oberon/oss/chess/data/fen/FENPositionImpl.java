package org.oberon.oss.chess.data.fen;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.oberon.oss.chess.data.Piece;
import org.oberon.oss.chess.data.enums.ChessField;
import org.oberon.oss.chess.data.ChessFieldInformation;
import org.oberon.oss.chess.data.enums.Color;

import java.util.Map;

/**
 * Default implementation of the {@link FENPosition} interface.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@Builder(builderClassName = "FENPositionBuilder")
@Accessors(fluent = true)
@Getter
@EqualsAndHashCode
public class FENPositionImpl implements FENPosition<ChessFieldInformation> {
    private final Map<ChessFieldInformation, Piece> board;
    private final Color                             sideToMove;
    private final int                               halveMoveClock;
    private final int                               fullMoveNumber;
    private final ChessField                        enPassantField;
    private final boolean                           whiteCanCastleKingSide;
    private final boolean                           whiteCanCastleQueenSide;
    private final boolean                           blackCanCastleKingSide;
    private final boolean                           blackCanCastleQueenSide;
}
