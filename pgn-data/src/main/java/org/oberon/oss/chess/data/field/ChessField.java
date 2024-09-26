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
    A8(WHITE), B8(BLACK), C8(WHITE), D8(BLACK), E8(WHITE), F8(BLACK), G8(WHITE), H8(BLACK),
    A7(BLACK), B7(WHITE), C7(BLACK), D7(WHITE), E7(BLACK), F7(WHITE), G7(BLACK), H7(WHITE),
    A6(WHITE), B6(BLACK), C6(WHITE), D6(BLACK), E6(WHITE), F6(BLACK), G6(WHITE), H6(BLACK),
    A5(BLACK), B5(WHITE), C5(BLACK), D5(WHITE), E5(BLACK), F5(WHITE), G5(BLACK), H5(WHITE),
    A4(WHITE), B4(BLACK), C4(WHITE), D4(BLACK), E4(WHITE), F4(BLACK), G4(WHITE), H4(BLACK),
    A3(BLACK), B3(WHITE), C3(BLACK), D3(WHITE), E3(BLACK), F3(WHITE), G3(BLACK), H3(WHITE),
    A2(WHITE), B2(BLACK), C2(WHITE), D2(BLACK), E2(WHITE), F2(BLACK), G2(WHITE), H2(BLACK),
    A1(BLACK), B1(WHITE), C1(BLACK), D1(WHITE), E1(BLACK), F1(WHITE), G1(BLACK), H1(WHITE);

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

    public static List<ChessField> getRankFields(@NotNull Integer rank) {
        if (rank < 1 || rank > 8) {
            throw new IllegalArgumentException("Rank out of bounds (1..8): " + rank);
        }
        return RANK_LOOKUP.get(rank);
    }

    public static List<ChessField> getFileFields(@NotNull String file) {
        if (file.length() != 1) {
            throw new IllegalArgumentException("Invalid file specified: " + file);
        }
        if (!"ABCDEFGH".contains(file.substring(0, 1))) {
            throw new IllegalArgumentException("File out of range A..H: " + file);
        }
        return FILE_LOOKUP.get(file);
    }

    @SuppressWarnings("unused")
    public static ChessField getFieldByCoordinates(String file, int rank) {
        return ChessField.valueOf(file.toUpperCase() + rank);
    }

}
