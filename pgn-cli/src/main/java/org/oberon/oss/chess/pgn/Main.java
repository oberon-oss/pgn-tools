package org.oberon.oss.chess.pgn;

import lombok.extern.log4j.Log4j2;
import org.oberon.oss.chess.reader.FilePgnSectionProvider;
import org.oberon.oss.chess.reader.PgnDataReader;
import org.oberon.oss.chess.reader.PgnGameContainer;
import org.oberon.oss.chess.reader.PgnGameContainerProcessor;
import picocli.CommandLine;

import java.io.File;
import java.util.List;
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
    private List<File> files;

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
                PgnDataReader.processPgnData(new FilePgnSectionProvider(canonicalFile), new Processor());
            }
        }
        return 0;
    }

    private static class Processor implements PgnGameContainerProcessor {

        @Override
        public void processGameContainer(PgnGameContainer container) {
            LOGGER.info(container);
        }
    }
}
