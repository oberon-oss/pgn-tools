package org.oberon.oss.chess.data.fen;

/**
 * Exception intended for implementations of the {@link FENPositionTranslator} interface.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public class FENTranslatorException extends IllegalArgumentException {
    public FENTranslatorException(String message) {
        super(message);
    }
}
