package org.oberon.oss.chess.pgn;

import org.oberon.oss.chess.reader.PGNDataReader;
import org.oberon.oss.chess.reader.PgnGameContainer;
import org.oberon.oss.chess.reader.PgnSource;
import org.oberon.oss.chess.reader.PgnSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import static org.oberon.oss.chess.pgn.LogProperties.loadLoggingProperties;

@CommandLine.Command(
        version = "v1.0.0",
        description = "Allows processing of PGN data in files and/or archives."
)
public class Main implements Callable<Integer> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    @CommandLine.Option(
            names = {"-f", "--files"},
            required = true,
            arity = "1.."

    )
    private Set<File> files;

    public static void main(String[] args) {
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
                List<PgnGameContainer> pgnGameContainers = reader.processInputData();
                LOGGER.info("Games read: {}", pgnGameContainers.size());

            }
        }
        return 0;
    }
}
