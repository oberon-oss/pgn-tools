package org.oberon.oss.chess.data.board;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.enums.ChessBoardField;
import org.oberon.oss.chess.data.enums.ChessBoardField.ChessBoardFieldIterator;
import org.oberon.oss.chess.data.enums.ChessPiece;
import org.oberon.oss.chess.data.enums.Color;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Fabien H. Dumay
 */
@Log4j2
public class FENDefinedPosition {
    private static final Pattern FEN_PATTERN = Pattern.compile(
          "((?:[kqrbnpKQRBNP1-8]{1,8}/?){8})\\s([bw])\\s(-|[KQkq]{1,4})\\s(-|[a-e][36])\\s(100|\\d{1,2})\\s(\\d{1,3})"
    );

    private final ChessPosition position;

    public FENDefinedPosition(@NotNull String setupString) {
        Matcher matcher = FEN_PATTERN.matcher(setupString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid setup string: " + setupString);
        }

        position = extractPosition(matcher.group(1));
    }

    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");

    private ChessPosition extractPosition(String group) {
        ChessPosition.Builder   builder      = ChessPosition.builder();
        ChessBoardFieldIterator iterator     = ChessBoardField.iterator();
        ChessBoardField         currentField = ChessBoardField.A8;

        for (String rank : group.split("/")) {
            for (int i = 0; i < rank.length(); i++) {
                String  content = rank.substring(i, i + 1);
                Matcher m       = DIGIT_PATTERN.matcher(content);
                if (m.matches()) {
                    int parseInt = Integer.parseInt(m.group(0));
                    iterator.skipFields(parseInt);
                    LOGGER.debug("Skipping {} fields", parseInt);
                } else {
                    ChessPiece chessPiece = ChessPiece.lookup(content);
                    Color color;
                    if ("kqrbnp".contains(content)) {
                        color = Color.BLACK;
                    } else {
                        color = Color.WHITE;
                    }

                    builder.position(currentField, new Piece(color,chessPiece,currentField));
                    LOGGER.debug("Processing piece {} @ {}", content, currentField);
                }
                if (iterator.hasNext()) {
                    currentField = iterator.next();
                }
            }
        }
        return builder.build();
    }

    public ChessPosition getPosition() {
        return position;
    }
}
