package org.oberon.oss.chess.data.fen;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.oberon.oss.chess.data.field.FieldInformation;
import org.oberon.oss.chess.data.position.Position;

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
public class FENPositionImpl implements FENPosition<FieldInformation> {
    private final Position<FieldInformation> chessPosition;
    private final int                        halveMoveClock;
    private final int                        fullMoveNumber;
}
