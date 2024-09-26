package org.oberon.oss.chess.data.fen;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.oberon.oss.chess.data.ChessColor;
import org.oberon.oss.chess.data.ChessPiece;
import org.oberon.oss.chess.data.Piece;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Fabien H. Dumay
 */
class FENChessPieceTest {

    public static Stream<Arguments> getFENCharacterForPiece() {
        return Stream.of(
              Arguments.of("k", new Piece(ChessPiece.KING, ChessColor.BLACK)),
              Arguments.of("q", new Piece(ChessPiece.QUEEN, ChessColor.BLACK)),
              Arguments.of("r", new Piece(ChessPiece.ROOK, ChessColor.BLACK)),
              Arguments.of("b", new Piece(ChessPiece.BISHOP, ChessColor.BLACK)),
              Arguments.of("n", new Piece(ChessPiece.KNIGHT, ChessColor.BLACK)),
              Arguments.of("p", new Piece(ChessPiece.PAWN, ChessColor.BLACK)),

              Arguments.of("K", new Piece(ChessPiece.KING, ChessColor.WHITE)),
              Arguments.of("Q", new Piece(ChessPiece.QUEEN, ChessColor.WHITE)),
              Arguments.of("R", new Piece(ChessPiece.ROOK, ChessColor.WHITE)),
              Arguments.of("B", new Piece(ChessPiece.BISHOP, ChessColor.WHITE)),
              Arguments.of("N", new Piece(ChessPiece.KNIGHT, ChessColor.WHITE)),
              Arguments.of("P", new Piece(ChessPiece.PAWN, ChessColor.WHITE))
        );
    }

    @ParameterizedTest
    @MethodSource
    void getFENCharacterForPiece(String expected, Piece piece) {
        assertEquals(expected, FENChessPiece.getFENCharacterForPiece(piece));
    }

    @Test
    void getFENCharacterForPieceWithNullValue() {
        assertThrows(NullPointerException.class, () -> FENChessPiece.getFENCharacterForPiece(null));
    }

    public static Stream<Arguments> lookupFENChessPiece() {
        return Stream.of(
              Arguments.of("K","k", FENChessPiece.KING),
              Arguments.of("Q","q", FENChessPiece.QUEEN),
              Arguments.of("R","r", FENChessPiece.ROOK),
              Arguments.of("B","b", FENChessPiece.BISHOP),
              Arguments.of("N","n", FENChessPiece.KNIGHT),
              Arguments.of("P","p", FENChessPiece.PAWN)
        );
    }

    @ParameterizedTest
    @MethodSource
    void lookupFENChessPiece(String white,String black, FENChessPiece piece) {
        assertEquals(FENChessPiece.lookupFENChessPiece(white),piece);
        assertEquals(FENChessPiece.lookupFENChessPiece(black),piece);
    }

    @Test
    void lookupFENChessPieceWithNull() {
        assertThrows(NullPointerException.class, () -> FENChessPiece.lookupFENChessPiece(null));
    }

    @Test
    void lookupFENChessPieceWithUnknownFENCharacter() {
        assertThrows(IllegalArgumentException.class, () -> FENChessPiece.lookupFENChessPiece("X"));
    }
}