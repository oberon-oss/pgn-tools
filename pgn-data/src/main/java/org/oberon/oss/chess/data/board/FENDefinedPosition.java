package org.oberon.oss.chess.data.board;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.data.enums.ChessBoardField;
import org.oberon.oss.chess.data.enums.ChessBoardField.ChessBoardFieldIterator;

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

    public FENDefinedPosition(@NotNull String setupString) {
        Matcher matcher = FEN_PATTERN.matcher(setupString);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid setup string: " + setupString);
        }

        extractPosition(matcher.group(1));
    }

    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");

    private void extractPosition(String group) {
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
                    LOGGER.info("Skipping {} fields", parseInt);
                } else {
                    LOGGER.info("Processing piece {} @ {}", content, currentField);
                }
                if (iterator.hasNext()) {
                    currentField = iterator.next();
                }
            }
        }
    }

    public ChessPosition getPosition() {
        return null;
    }
}
