package org.oberon.oss.chess.reader;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Log4j2
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PgnDataReaderTest {
    private static final String PATH = "src/test/resources/data/";

    private static @NotNull Stream<Arguments> testReadingFile() {
        return Stream.of(
              Arguments.of(PATH + "365chess_games.pgn"),
              Arguments.of(PATH + "365chess_games_1.pgn"),
              Arguments.of(PATH + "365chess_games_2.pgn"),
              Arguments.of(PATH + "ChessAdmin-Class C.pgn")
        );
    }

    @ParameterizedTest
    @MethodSource
    void testReadingFile(String filename) {
        assertDoesNotThrow(
              () -> PgnDataReader.processPgnData(
                    new FilePgnSectionProvider(new File(filename)),
                    new PgnGameContainerProcessorImpl()
              )
        );
    }

    private static class PgnGameContainerProcessorImpl implements PgnGameContainerProcessor {
        @Override
        public void processGameContainer(PgnGameContainer container) {
            // Dummy method.
        }
    }
}