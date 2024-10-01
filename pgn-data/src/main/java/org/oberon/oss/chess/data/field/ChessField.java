package org.oberon.oss.chess.data.field;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.ChessColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.oberon.oss.chess.data.ChessColor.BLACK;
import static org.oberon.oss.chess.data.ChessColor.WHITE;

/**
 * Enumerates the fields present on a chess board.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@ToString
@Getter
@Log4j2
public enum ChessField implements FieldInformation {

    //@formatter:off
    /** A8 */ A8(WHITE), /** B8 */ B8(BLACK), /** C8 */ C8(WHITE), /** D8 */ D8(BLACK), /** E8 */ E8(WHITE), /** F8 */ F8(BLACK), /** G8 */G8(WHITE), /** H8 */ H8(BLACK),
    /** A7 */ A7(BLACK), /** B7 */ B7(WHITE), /** C7 */ C7(BLACK), /** D7 */ D7(WHITE), /** E7 */ E7(BLACK), /** F7 */ F7(WHITE), /** G7 */G7(BLACK), /** H7 */ H7(WHITE),
    /** A6 */ A6(WHITE), /** B6 */ B6(BLACK), /** C6 */ C6(WHITE), /** D6 */ D6(BLACK), /** E6 */ E6(WHITE), /** F6 */ F6(BLACK), /** G6 */G6(WHITE), /** H6 */ H6(BLACK),
    /** A5 */ A5(BLACK), /** B5 */ B5(WHITE), /** C5 */ C5(BLACK), /** D5 */ D5(WHITE), /** E5 */ E5(BLACK), /** F5 */ F5(WHITE), /** G5 */G5(BLACK), /** H5 */ H5(WHITE),
    /** A4 */ A4(WHITE), /** B4 */ B4(BLACK), /** C4 */ C4(WHITE), /** D4 */ D4(BLACK), /** E4 */ E4(WHITE), /** F4 */ F4(BLACK), /** G4 */G4(WHITE), /** H4 */ H4(BLACK),
    /** A3 */ A3(BLACK), /** B3 */ B3(WHITE), /** C3 */ C3(BLACK), /** D3 */ D3(WHITE), /** E3 */ E3(BLACK), /** F3 */ F3(WHITE), /** G3 */G3(BLACK), /** H3 */ H3(WHITE),
    /** A2 */ A2(WHITE), /** B2 */ B2(BLACK), /** C2 */ C2(WHITE), /** D2 */ D2(BLACK), /** E2 */ E2(WHITE), /** F2 */ F2(BLACK), /** G2 */G2(WHITE), /** H2 */ H2(BLACK),
    /** A1 */ A1(BLACK), /** B1 */ B1(WHITE), /** C1 */ C1(BLACK), /** D1 */ D1(WHITE), /** E1 */ E1(BLACK), /** F1 */ F1(WHITE), /** G1 */G1(BLACK), /** H1 */ H1(WHITE);
    //@formatter:on
    /**
     * Returns the chess field color
     *
     * @return the color of the chess field.
     * @since 1.0.0
     */
    private final ChessColor fieldColor;
    private final String     file;
    private final Integer    rank;

    ChessField(ChessColor fieldColor) {
        this.fieldColor = fieldColor;
        file            = name().charAt(0) + "";
        rank            = Integer.parseInt(name().charAt(1) + "");
    }

    private static final Map<Integer, List<ChessField>> RANK_LOOKUP;
    private static final Map<String, List<ChessField>>  FILE_LOOKUP;

    static {
        Map<Integer, List<ChessField>> rankMap = new HashMap<>();
        Map<String, List<ChessField>>  fileMap = new HashMap<>();
        for (ChessField chessField : ChessField.values()) {
            rankMap.computeIfAbsent(chessField.rank, r -> new ArrayList<>());
            fileMap.computeIfAbsent(chessField.file, f -> new ArrayList<>());

            rankMap.get(chessField.rank).add(chessField);
            fileMap.get(chessField.file).add(chessField);
        }
        RANK_LOOKUP = Map.copyOf(rankMap);
        FILE_LOOKUP = Map.copyOf(fileMap);
    }

    /**
     * Returns a list of fields in the specified rank.
     *
     * @param rank The rank to return the list of fields for. The value must be in the range 1...8 (inclusive)
     *
     * @return an immutable list of the fields in a rank, ordered A...H
     *
     * @throws IllegalArgumentException if the specified rank number is outside the range 1...8
     * @since 1.0.0
     */
    public static List<ChessField> getRankFields(@NotNull Integer rank) {
        if (rank < 1 || rank > 8) {
            throw new IllegalArgumentException("Rank out of bounds (1..8): " + rank);
        }
        return RANK_LOOKUP.get(rank);
    }

    /**
     * Returns a list of fields in the specified file.
     *
     * @param file The file to return the list of fields for. The value must be in the range A...H (inclusive)
     *
     * @return an immutable list of the fields in a rank, ordered 8...1
     *
     * @throws IllegalArgumentException if the specified file is outside the range A...H
     * @since 1.0.0
     */
    public static List<ChessField> getFileFields(@NotNull String file) {
        if (file.length() != 1) {
            throw new IllegalArgumentException("Invalid file specified: " + file);
        }
        if (!"ABCDEFGH".contains(file)) {
            throw new IllegalArgumentException("File out of range A..H: " + file);
        }
        return FILE_LOOKUP.get(file);
    }

    /**
     * Returns a field from the specified for the given coordinate parameters 'file' and 'rank'.
     *
     * @param file The file of the field, in the range A...H (inclusive)
     * @param rank The rank of the field, in the range 1...8 (inclusive)
     *
     * @return The field as specified by the file and rank
     *
     * @throws IllegalArgumentException in one of the following cases:
     *                                  <ul>
     *                                  <li>if the specified file is outside the range A...H</li>
     *                                  <li>if the specified rank number is outside the range 1...8</li>
     *                                  </ul>
     * @since 1.0.0
     */
    @SuppressWarnings("unused")
    public static ChessField getFieldByCoordinates(String file, int rank) {
        if (rank < 1 || rank > 8) {
            throw new IllegalArgumentException("Rank out of bounds (1..8): " + rank);
        }
        if (file.length() != 1) {
            throw new IllegalArgumentException("Invalid file specified: " + file);
        }
        return ChessField.valueOf(file.toUpperCase() + rank);
    }

}
