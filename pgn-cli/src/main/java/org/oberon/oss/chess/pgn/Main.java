package org.oberon.oss.chess.pgn;

import picocli.CommandLine;

import java.util.concurrent.Callable;

import static org.oberon.oss.chess.pgn.LogProperties.loadLoggingProperties;

@CommandLine.Command(
        version = "v1.0.0",
        description = "Allows processing of PGN data in files and/or archives."
)
public class Main implements Callable<Integer> {
    public static void main(String[] args) {
        new CommandLine(Main.class).execute(args);
    }


    @Override
    public Integer call() throws Exception {
        loadLoggingProperties();

        return 0;
    }
}
