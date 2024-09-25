package org.oberon.oss.chess.data;

import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.enums.Color;

/**
 * Interface describing the contract for classes that provide information on fields on a chess board.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
public interface ChessFieldInformation {
    /**
     * Returns the color of the field.
     *
     * @return a value from the {@link Color} enumeration specifying the color of the field
     */
    Color getFieldColor();

    /**
     * Returns the file of the field.
     *
     * @return a String in the range "A"..."H" indicating the file the pieces belongs to
     *
     * @since 1.0.0
     */
    @NotNull String getFile();

    /**
     * Returns the rank of the field.
     *
     * @return a number in the range 1...8 indicating the rank the pieces belongs to
     *
     * @since 1.0.0
     */
    @NotNull Integer getRank();
}
