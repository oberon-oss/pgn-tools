package org.oberon.oss.chess.data.board;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.Accessors;
import org.oberon.oss.chess.data.Piece;
import org.oberon.oss.chess.data.field.FieldInformation;

import java.util.Map;

/**
 * @author Fabien H. Dumay
 */
@Builder(builderClassName = "ChessBoardBuilder")
@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
public class BoardImpl implements Board<FieldInformation> {
    @Singular("addToPieceMapping")
    private final Map<FieldInformation, Piece> pieceMapping;
}
