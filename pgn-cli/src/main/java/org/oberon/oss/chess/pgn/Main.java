package org.oberon.oss.chess.pgn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.File;
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
        LOGGER.info("PWD: {}",System.getProperty("user.dir"));
        for (File file : files) {
            LOGGER.info("Processing file: {}; file {}",file.getCanonicalFile(), (file.exists() ? "exists":"not found"));

        }
        return 0;
    }
}
