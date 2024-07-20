package org.oberon.oss.chess.pgn;

import lombok.extern.log4j.Log4j2;
import org.oberon.oss.chess.reader.PGNDataReader;
import org.oberon.oss.chess.reader.PgnGameContainer;
import org.oberon.oss.chess.reader.PgnSource;
import org.oberon.oss.chess.reader.PgnSourceType;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import static org.oberon.oss.chess.pgn.LogProperties.loadLoggingProperties;

@CommandLine.Command(
      version = "v1.0.0",
      description = "Allows processing of PGN data in files and/or archives."
)
@Log4j2
public class Main implements Callable<Integer> {

    @CommandLine.Option(
          names = {"-f", "--files"},
          required = true,
          arity = "1.."

    )
    private Set<File> files;

    public static void main(String[] args) {
        LOGGER.info("Files: {}", (Object) args);
        new CommandLine(Main.class).execute(args);
    }

    @Override
    public Integer call() throws Exception {
        loadLoggingProperties();
        LOGGER.info("PWD: {}", System.getProperty("user.dir"));
        for (File file : files) {
            File canonicalFile = file.getCanonicalFile();
            LOGGER.info("Processing file: {}; file {}", canonicalFile, (file.exists() ? "exists" : "not found"));
            if (file.exists()) {

                List<PgnGameContainer> pgnGameContainers = getPgnGameContainers(canonicalFile);
                LOGGER.info("Games read: {}", pgnGameContainers.size());
            }
        }
        return 0;
    }

    private static List<PgnGameContainer> getPgnGameContainers(File canonicalFile) throws IOException {
        PGNDataReader reader = new PGNDataReader(new PgnSource() {
            @Override
            public PgnSourceType getSourceType() {
                return PgnSourceType.TEXT_FILE;
            }

            @Override
            public URI getSourceLocation() {
                return canonicalFile.toURI();
            }
        }
        );
        return reader.processInputData();
    }
}
