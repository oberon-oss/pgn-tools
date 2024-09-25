package org.oberon.oss.chess.data.fen;

import org.jetbrains.annotations.NotNull;

/**
 * Provides services to create a {@link FENPosition} object from a setup string, or vice versa.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public interface FENPositionTranslator {
    /**
     * Constructs a {@link FENPosition} object from the provided setup string.
     *
     * @param fenPositionSetupString The string to create the FENPosition object from.
     *
     * @return The FENPosition
     *
     * @throws FENTranslatorException if an error is detected while processing the setup string.
     * @since 1.0.0
     */
    FENPosition toFENPosition(@NotNull String fenPositionSetupString);

    /**
     * Converts the provided position into the equivalent FEN string representation.
     *
     * @param position The position to create the setup string for
     *
     * @return The resulting string object.
     *
     * @since 1.0.0
     */
    String toFENString(@NotNull FENPosition position);
}
