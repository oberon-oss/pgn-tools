package org.oberon.oss.chess.data.board;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import org.oberon.oss.chess.data.enums.ChessBoardField;

import java.util.Map;

/**
 * @author Fabien H. Dumay
 */
@Value
@Builder
public class ChessPosition {
    @Singular("position")
    Map<ChessBoardField, Piece> position;
}
