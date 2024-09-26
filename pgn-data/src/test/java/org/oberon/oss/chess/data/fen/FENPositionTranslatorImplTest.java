package org.oberon.oss.chess.data.fen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.oberon.oss.chess.data.field.FieldInformation;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Fabien H. Dumay
 */
class FENPositionTranslatorImplTest {

    private FENPositionTranslator<FieldInformation> translator;

    @BeforeEach
    void init() {
        translator = new FENPositionTranslatorImpl();
    }

    private static final String INVALID_SETUP_STRING              = "rnbqkbnr/pppppppx/9/8/8/8/PPPPPPPP/RNBQKBNR w QKqk - 0 0";
    private static final String INVALID_SETUP_STRING_INVALID_RANK = "rnbqkbnr/ppppppp222/9/8/8/8/PPPPPPPP/RNBQKBNR w QKqk - 0 0";
    private static final String INITIAL_POSITION_SETUP_STRING     = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w QKqk - 0 0";
    private static final String POSTION_AFTER_1_WHITE_MOVE        = "rnbqkbnr/pppppppp/8/8/4P3/8/PPPP1PPP/RNBQKBNR b KQkq - 0 0";
    private static final String POSITION_SETUP_STRING_NO_CASTLING = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w - - 0 0";

    private static final String EN_PASSANT_WITH_EMPTY_FIELD      = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w QKqk E6 0 0";
    private static final String EN_PASSANT_WHITE_WITH_WHITE_PAWN = "rnbqkbnr/pppppppp/4P3/8/8/8/PPPPPPPP/RNBQKBNR w QKqk E6 0 0";
    private static final String EN_PASSANT_WHITE_WITH_BLACK_PAWN = "rnbqkbnr/pppppppp/4p3/8/8/8/PPPPPPPP/RNBQKBNR w QKqk E6 0 0";
    private static final String EN_PASSANT_BLACK_WITH_WHITE_PAWN = "rnbqkbnr/pppppppp/8/8/8/4P3/PPPPPPPP/RNBQKBNR b QKqk E3 0 0";
    private static final String EN_PASSANT_BLACK_WITH_BLACK_PAWN = "rnbqkbnr/pppppppp/8/8/8/4p3/PPPPPPPP/RNBQKBNR b QKqk E3 0 0";

    private static final String CASTLING_BLACK_KING_SIDE_KING_MOVED  = "rnbq1bnr/kppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w k - 0 0";
    private static final String CASTLING_BLACK_KING_SIDE_ROOK_MOVED  = "rnbqkbnq/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w k - 0 0";
    private static final String CASTLING_BLACK_QUEEN_SIDE_KING_MOVED = "rnbq1bnr/pppkpppp/8/8/8/8/PPPPPPPP/RNBQKBNR w q - 0 0";
    private static final String CASTLING_BLACK_QUEEN_SIDE_ROOK_MOVED = "1nbqkbnr/pprppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w q - 0 0";
    private static final String CASTLING_WHITE_KING_SIDE_KING_MOVED  = "rnbqkbnr/pppppppp/8/8/8/8/PKPPPPPP/RNBQ1BNR w K - 0 0";
    private static final String CASTLING_WHITE_KING_SIDE_ROOK_MOVED  = "rnbqkbnr/pppppppp/8/8/8/8/PPPRPPPP/RNBQKBN1 w K - 0 0";
    private static final String CASTLING_WHITE_QUEEN_SIDE_KING_MOVED = "rnbqkbnr/pppppppp/8/8/8/8/PPPPKPPP/RNBQ1BNR w Q - 0 0";
    private static final String CASTLING_WHITE_QUEEN_SIDE_ROOK_MOVED = "rnbqkbnr/pppppppp/8/8/8/8/PPRPPPPP/1NBQKBNR w Q - 0 0";

    @Test
    void toFENPosition() {
        FENPosition<FieldInformation> position1 = translator.toFENPosition(INITIAL_POSITION_SETUP_STRING);
        FENPosition<FieldInformation> position2 = translator.toFENPosition(translator.toFENString(position1));
        assertEquals(position1, position2);
        assertEquals(translator.toFENString(position1), translator.toFENString(position2));

        assertTrue(position1.chessPosition().blackCanCastleKingSide());
        assertTrue(position1.chessPosition().blackCanCastleQueenSide());
        assertTrue(position2.chessPosition().whiteCanCastleKingSide());
        assertTrue(position2.chessPosition().whiteCanCastleQueenSide());
    }

    @Test
    void testInvalidSetupString() {
        assertThrows(FENTranslatorException.class, () -> translator.toFENPosition(INVALID_SETUP_STRING));
    }

    @Test
    void setupStringWithInvalidRankDataTest() {
        assertThrows(FENTranslatorException.class, () -> translator.toFENPosition(INVALID_SETUP_STRING_INVALID_RANK));
    }

    @Test
    void testNoCastlingRights() {
        FENPosition<FieldInformation> position = translator.toFENPosition(POSITION_SETUP_STRING_NO_CASTLING);
        assertFalse(position.chessPosition().blackCanCastleKingSide());
        assertFalse(position.chessPosition().blackCanCastleQueenSide());
        assertFalse(position.chessPosition().whiteCanCastleKingSide());
        assertFalse(position.chessPosition().whiteCanCastleQueenSide());

        assertEquals(POSITION_SETUP_STRING_NO_CASTLING, translator.toFENString(position));
    }

    @Test
    void testWithBlackToMoveNext() {
        FENPosition<FieldInformation> position = translator.toFENPosition(POSTION_AFTER_1_WHITE_MOVE);
        assertEquals(POSTION_AFTER_1_WHITE_MOVE, translator.toFENString(position));
    }

    public static Stream<Arguments> castlingTest() {
        return Stream.of(
              Arguments.of(CASTLING_BLACK_KING_SIDE_KING_MOVED, "Field E8 is empty"),
              Arguments.of(CASTLING_BLACK_KING_SIDE_ROOK_MOVED, "Field H8 does not contain a BLACK ROOK"),
              Arguments.of(CASTLING_BLACK_QUEEN_SIDE_KING_MOVED, "Field E8 is empty"),
              Arguments.of(CASTLING_BLACK_QUEEN_SIDE_ROOK_MOVED, "Field A8 is empty"),
              Arguments.of(CASTLING_WHITE_KING_SIDE_KING_MOVED, "Field E1 is empty"),
              Arguments.of(CASTLING_WHITE_KING_SIDE_ROOK_MOVED, "Field H1 is empty"),
              Arguments.of(CASTLING_WHITE_QUEEN_SIDE_KING_MOVED, "Field E1 is empty"),
              Arguments.of(CASTLING_WHITE_QUEEN_SIDE_ROOK_MOVED, "Field A1 is empty")
        );
    }

    @ParameterizedTest
    @MethodSource
    void castlingTest(String fenString, String expectedError) {
        if (expectedError != null) {
            FENTranslatorException exception =
                  assertThrows(FENTranslatorException.class, () -> translator.toFENPosition(fenString));
            assertEquals(expectedError, exception.getMessage());
        } else {
            assertDoesNotThrow(() -> translator.toFENPosition(fenString));
        }
    }

    public static Stream<Arguments> enPassantTest() {
        return Stream.of(
              Arguments.of(EN_PASSANT_WITH_EMPTY_FIELD, "Field E6 is empty"),
              Arguments.of(EN_PASSANT_WHITE_WITH_WHITE_PAWN, "Field E6 does not contain a BLACK PAWN"),
              Arguments.of(EN_PASSANT_WHITE_WITH_BLACK_PAWN, null),
              Arguments.of(EN_PASSANT_BLACK_WITH_BLACK_PAWN, "Field E3 does not contain a WHITE PAWN"),
              Arguments.of(EN_PASSANT_BLACK_WITH_WHITE_PAWN, null)
        );
    }

    @ParameterizedTest
    @MethodSource
    void enPassantTest(String fenString, String expectedError) {
        if (expectedError != null) {
            FENTranslatorException exception =
                  assertThrows(FENTranslatorException.class, () -> translator.toFENPosition(fenString));
            assertEquals(expectedError, exception.getMessage());
        } else {
            assertDoesNotThrow(() -> translator.toFENPosition(fenString));
        }
    }

}