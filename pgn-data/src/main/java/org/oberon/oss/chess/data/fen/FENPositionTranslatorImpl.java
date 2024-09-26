package org.oberon.oss.chess.data.fen;

import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.*;
import org.oberon.oss.chess.data.board.BoardImpl;
import org.oberon.oss.chess.data.board.BoardImpl.ChessBoardBuilder;
import org.oberon.oss.chess.data.position.PositionImpl;
import org.oberon.oss.chess.data.position.PositionImpl.ChessPositionBuilder;
import org.oberon.oss.chess.data.board.Board;
import org.oberon.oss.chess.data.position.Position;
import org.oberon.oss.chess.data.field.ChessField;
import org.oberon.oss.chess.data.field.FieldIteratorImpl;
import org.oberon.oss.chess.data.ChessColor;
import org.oberon.oss.chess.data.fen.FENPositionImpl.FENPositionBuilder;
import org.oberon.oss.chess.data.field.FieldInformation;
import org.oberon.oss.chess.data.field.FieldIterator;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.oberon.oss.chess.data.fen.FENChessPiece.lookupFENChessPiece;
import static org.oberon.oss.chess.data.field.ChessField.*;
import static org.oberon.oss.chess.data.ChessPiece.*;
import static org.oberon.oss.chess.data.ChessColor.BLACK;
import static org.oberon.oss.chess.data.ChessColor.WHITE;

/**
 * Default implementation for the {@link FENPositionTranslator} interface
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@Log4j2
@EqualsAndHashCode
public class FENPositionTranslatorImpl implements FENPositionTranslator<FieldInformation> {
    protected static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");
    protected static final Pattern FEN_PATTERN   = Pattern.compile(
          "((?:[kqrbnpKQRBNP1-8]{1,8}/?){8})" // group 1: position
          + "\\s([bw])"                       // group 2: sideToMove
          + "\\s(-|[KQkq]{1,4})"              // group 3: castling rights
          + "\\s(-|[a-e][36])"                // group 4: en-passant field
          + "\\s(100|\\d{1,2})"               // group 5: half-move (ply) count
          + "\\s(\\d{1,3})"                   // group 6: move count
    );

    @Override
    public FENPosition<FieldInformation> toFENPosition(@NotNull String fenPositionSetupString) {
        FENPositionBuilder   fenPositionBuilder   = FENPositionImpl.builder();
        ChessPositionBuilder chessPositionBuilder = PositionImpl.builder();

        Matcher wrk = FEN_PATTERN.matcher(fenPositionSetupString);
        if (!wrk.matches()) {
            throw new FENTranslatorException("Invalid setup string: " + fenPositionSetupString);
        }

        Board<FieldInformation> board = extract(wrk.group(1));
        chessPositionBuilder
              .board(board)
              .sideToMove(wrk.group(2).contentEquals("b") ? ChessColor.BLACK : WHITE)
              .enPassantField(setEnPassantField(board, wrk.group(4)));
        checkCastlingRights(board, wrk.group(3), chessPositionBuilder);

        fenPositionBuilder
              .chessPosition(chessPositionBuilder.build())
              .halveMoveClock(Integer.parseInt(wrk.group(5)))
              .fullMoveNumber(Integer.parseInt(wrk.group(6)));

        return fenPositionBuilder.build();
    }

    @Override
    public String toFENString(@NotNull FENPosition<FieldInformation> fenPosition) {
        StringBuilder              sb       = new StringBuilder();
        Position<FieldInformation> position = fenPosition.chessPosition();
        convertPositionToString(position.board().pieceMapping(), sb);

        sb.append(" ").append(position.sideToMove() == BLACK ? "b" : "w");
        sb.append(" ");
        int length = sb.length();
        sb.append(position.whiteCanCastleKingSide() ? "K" : "");
        sb.append(position.whiteCanCastleQueenSide() ? "Q" : "");
        sb.append(position.blackCanCastleKingSide() ? "k" : "");
        sb.append(position.blackCanCastleQueenSide() ? "q" : "");

        if (sb.length() == length) {
            sb.append("-");
        }

        sb.append(" ").append(position.enPassantField() == null ? "-" : position.enPassantField());
        sb.append(" ").append(fenPosition.halveMoveClock());
        sb.append(" ").append(fenPosition.fullMoveNumber());
        return sb.toString();
    }

    private void checkCastlingRights(Board<FieldInformation> board, String castlingRights, ChessPositionBuilder positionBuilder) {
        if (!castlingRights.contentEquals("-")) {
            for (int i = 0; i < castlingRights.length(); i++) {
                char c = castlingRights.charAt(i);
                switch (c) {
                    case 'K':
                        verifyIfFieldContains(board, E1, KING, WHITE);
                        verifyIfFieldContains(board, H1, ROOK, WHITE);
                        positionBuilder.whiteCanCastleKingSide(true);
                        break;
                    case 'Q':
                        verifyIfFieldContains(board, E1, KING, WHITE);
                        verifyIfFieldContains(board, A1, ROOK, WHITE);
                        positionBuilder.whiteCanCastleQueenSide(true);
                        break;
                    case 'k':
                        verifyIfFieldContains(board, E8, KING, BLACK);
                        verifyIfFieldContains(board, H8, ROOK, BLACK);
                        positionBuilder.blackCanCastleKingSide(true);
                        break;
                    case 'q':
                        verifyIfFieldContains(board, E8, KING, BLACK);
                        verifyIfFieldContains(board, A8, ROOK, BLACK);
                        positionBuilder.blackCanCastleQueenSide(true);
                        break;
                    default:
                        throw new FENTranslatorException("Invalid castlingRights: " + castlingRights);
                }
            }
        }
    }

    private void verifyIfFieldContains(Board<FieldInformation> board, ChessField field, ChessPiece piece, ChessColor color) {
        ChessPiece chessPiece = board.pieceMapping().get(field).getChessPiece();
        ChessColor    pieceColor    = board.pieceMapping().get(field).getPieceColor();
        if (chessPiece != piece && pieceColor != color) {
            throw new FENTranslatorException(String.format("Field %s does not contain a %s %s", field, chessPiece, pieceColor));
        }
    }

    private ChessField setEnPassantField(Board<FieldInformation> position, String enPassantField) {
        if (enPassantField.contentEquals("-")) {
            return null;
        }
        ChessField field = ChessField.valueOf(enPassantField);
        verifyIfFieldContains(position, field, PAWN, field.getRank() == 6 ? ChessColor.BLACK : WHITE);
        return field;
    }

    private Board<FieldInformation> extract(String positionString) {
        FieldIterator<FieldInformation> iterator = FieldIteratorImpl.chessBoardFieldIterator();
        ChessBoardBuilder               builder  = BoardImpl.builder();

        FieldInformation currentField;
        for (String rank : positionString.split("/")) {
            for (int i = 0; i < rank.length(); i++) {
                currentField = iterator.next();
                String  content = rank.substring(i, i + 1);
                Matcher m       = DIGIT_PATTERN.matcher(content);
                if (m.matches()) {
                    int parseInt = Integer.parseInt(m.group(0));
                    iterator.skipFields(parseInt);
                    LOGGER.debug("Skipping {} fields", parseInt);
                } else {
                    FENChessPiece fenChessPiece = lookupFENChessPiece(content);
                    ChessColor    color;
                    if ("kqrbnp".contains(content)) {
                        color = ChessColor.BLACK;
                    } else {
                        color = WHITE;
                    }
                    builder.addToPieceMapping(currentField, new Piece(fenChessPiece.getChessPiece(), color));
                    LOGGER.debug("Processing piece {} @ {}", content, currentField);
                }
            }
        }
        return builder.build();
    }

    private void convertPositionToString(Map<FieldInformation, Piece> position, StringBuilder sb) {
        for (int rank = 8; rank >= 1; rank--) {
            int                             emptyFieldsCount = 0;
            FieldIterator<FieldInformation> iterator         = FieldIteratorImpl.rankIterator(rank);
            while (iterator.hasNext()) {
                FieldInformation field = iterator.next();
                Piece            piece = position.get(field);
                emptyFieldsCount = appendData(sb, piece, emptyFieldsCount);
            }
            if (emptyFieldsCount > 0) {
                sb.append(emptyFieldsCount);
            }
            if (rank > 1) {
                sb.append("/");
            }
        }
    }

    private int appendData(StringBuilder sb, Piece piece, int fieldsToSkip) {
        if (piece != null) {
            if (fieldsToSkip > 0) {
                sb.append(fieldsToSkip);
                fieldsToSkip = 0;
            }
            sb.append(FENChessPiece.getFENCharacterForPiece(piece));
        } else {
            ++fieldsToSkip;
        }
        return fieldsToSkip;
    }

}
