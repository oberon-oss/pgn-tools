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

    /**
     * Placeholder for the builder class as constructed by the @Builder annotation of the FENPositionImpl class. Its main purpose is
     * to prevent javadoc generators from reporting errors about missing builders.
     *
     * @since 1.0.0
     */
    @SuppressWarnings("java:S2094") // Prevent javadoc complaining about missing builders
    public static class FENPositionBuilder<B extends FENPositionBuilder<B>> {
    }
}
