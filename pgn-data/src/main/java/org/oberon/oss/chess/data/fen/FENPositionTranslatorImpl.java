package org.oberon.oss.chess.data.fen;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.ChessFieldInformation;
import org.oberon.oss.chess.data.ChessFieldIterator;
import org.oberon.oss.chess.data.FieldIterator;
import org.oberon.oss.chess.data.Piece;
import org.oberon.oss.chess.data.enums.*;
import org.oberon.oss.chess.data.fen.FENPositionImpl.FENPositionBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.oberon.oss.chess.data.enums.ChessField.*;
import static org.oberon.oss.chess.data.enums.ChessPiece.*;
import static org.oberon.oss.chess.data.enums.Color.BLACK;
import static org.oberon.oss.chess.data.enums.Color.WHITE;

/**
 * Default implementation for the {@link FENPositionTranslator} interface
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@Log4j2
public class FENPositionTranslatorImpl implements FENPositionTranslator<ChessFieldInformation> {
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
    public FENPosition<ChessFieldInformation> toFENPosition(@NotNull String fenPositionSetupString) {
        FENPositionBuilder positionBuilder = FENPositionImpl.builder();

        Matcher wrk = FEN_PATTERN.matcher(fenPositionSetupString);
        if (!wrk.matches()) {
            throw new FENTranslatorException("Invalid setup string: " + fenPositionSetupString);
        }

        Map<ChessFieldInformation, Piece> position = Map.copyOf(extract(wrk.group(1)));
        positionBuilder.board(position)
                       .sideToMove(wrk.group(2).contentEquals("b") ? Color.BLACK : WHITE)
                       .enPassantField(setEnPassantField(position, wrk.group(4)))
                       .halveMoveClock(Integer.parseInt(wrk.group(5)))
                       .fullMoveNumber(Integer.parseInt(wrk.group(6)));

        checkCastlingRights(position, wrk.group(3), positionBuilder);
        return positionBuilder.build();
    }

    @Override
    public String toFENString(@NotNull FENPosition<ChessFieldInformation> position) {
        StringBuilder sb = new StringBuilder();
        convertPositionToString(position.board(), sb);

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
        sb.append(" ").append(position.halveMoveClock());
        sb.append(" ").append(position.fullMoveNumber());
        return sb.toString();
    }

    private void checkCastlingRights(Map<ChessFieldInformation, Piece> position, String castlingRights, FENPositionBuilder positionBuilder) {
        if (!castlingRights.contentEquals("-")) {
            for (int i = 0; i < castlingRights.length(); i++) {
                char c = castlingRights.charAt(i);
                switch (c) {
                    case 'K':
                        verifyIfFieldContains(position, E1, KING, WHITE);
                        verifyIfFieldContains(position, H1, ROOK, WHITE);
                        positionBuilder.whiteCanCastleKingSide(true);
                        break;
                    case 'Q':
                        verifyIfFieldContains(position, E1, KING, WHITE);
                        verifyIfFieldContains(position, A1, ROOK, WHITE);
                        positionBuilder.whiteCanCastleQueenSide(true);
                        break;
                    case 'k':
                        verifyIfFieldContains(position, E8, KING, BLACK);
                        verifyIfFieldContains(position, H8, ROOK, BLACK);
                        positionBuilder.blackCanCastleKingSide(true);
                        break;
                    case 'q':
                        verifyIfFieldContains(position, E8, KING, BLACK);
                        verifyIfFieldContains(position, A8, ROOK, BLACK);
                        positionBuilder.blackCanCastleQueenSide(true);
                        break;
                    default:
                        throw new FENTranslatorException("Invalid castlingRights: " + castlingRights);
                }
            }
        }
    }

    private void verifyIfFieldContains(Map<ChessFieldInformation, Piece> position, ChessField field, ChessPiece piece, Color color) {
        ChessPiece chessPiece = position.get(field).getChessPiece();
        Color      pieceColor = position.get(field).getPieceColor();
        if (chessPiece != piece && pieceColor != color) {
            throw new FENTranslatorException(String.format("Field %s does not contain a %s %s", field, chessPiece, pieceColor));
        }
    }

    private ChessField setEnPassantField(Map<ChessFieldInformation, Piece> position, String enPassantField) {
        if (enPassantField.contentEquals("-")) {
            return null;
        }
        ChessField field = ChessField.valueOf(enPassantField);
        verifyIfFieldContains(position, field, PAWN, field.getRank() == 6 ? Color.BLACK : WHITE);
        return field;
    }

    private Map<ChessFieldInformation, Piece> extract(String positionString) {
        FieldIterator<ChessFieldInformation> iterator = ChessFieldIterator.chessBoardFieldIterator();
        Map<ChessFieldInformation, Piece>    wrk      = new HashMap<>();

        ChessFieldInformation currentField;
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
                    ChessPiece chessPiece = ChessPiece.lookup(content);
                    Color      color;
                    if ("kqrbnp".contains(content)) {
                        color = Color.BLACK;
                    } else {
                        color = WHITE;
                    }

                    wrk.put(currentField, new Piece(chessPiece, color));
                    LOGGER.debug("Processing piece {} @ {}", content, currentField);
                }
            }
        }
        return wrk;
    }

    private void convertPositionToString(Map<ChessFieldInformation, Piece> position, StringBuilder sb) {
        for (int rank = 8; rank >= 1; rank--) {
            int                                  emptyFieldsCount = 0;
            FieldIterator<ChessFieldInformation> iterator         = ChessFieldIterator.rankIterator(rank);
            while (iterator.hasNext()) {
                ChessFieldInformation field = iterator.next();
                Piece                 piece = position.get(field);
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
            ChessPiece chessPiece = piece.getChessPiece();
            sb.append(piece.getPieceColor() == BLACK ? chessPiece.getForBlack() : chessPiece.getForWhite());
        } else {
            ++fieldsToSkip;
        }
        return fieldsToSkip;
    }

}
