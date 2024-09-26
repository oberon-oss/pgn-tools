package org.oberon.oss.chess.data.position;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.oberon.oss.chess.data.board.Board;
import org.oberon.oss.chess.data.ChessColor;
import org.oberon.oss.chess.data.field.FieldInformation;

/**
 * @author Fabien H. Dumay
 */
@Getter
@Builder(builderClassName = "ChessPositionBuilder")
@Accessors(fluent = true)
@EqualsAndHashCode
public class PositionImpl implements Position<FieldInformation> {
    private final Board<FieldInformation> board;
    private final ChessColor              sideToMove;
    private final FieldInformation        enPassantField;
    private final boolean                 whiteCanCastleKingSide;
    private final boolean                 whiteCanCastleQueenSide;
    private final boolean                 blackCanCastleKingSide;
    private final boolean                 blackCanCastleQueenSide;
}
