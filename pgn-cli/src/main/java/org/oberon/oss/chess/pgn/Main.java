package org.oberon.oss.chess.pgn;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.oberon.oss.chess.reader.*;
import picocli.CommandLine;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

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
                PGNSectionProvider provider   = new FilePGNSectionProvider(canonicalFile);
                PGNDataReader      dataReader = new PGNDataReader();

                while (provider.hasNext()) {
                    LOGGER.info(dataReader.processInputData(provider.next()));
                }
            }
        }
        return 0;
    }

    public static class FilePGNSectionProvider implements PGNSectionProvider {
        private static final Pattern TAG_LINE_PATTERN = Pattern.compile(
              "^\\[\\s*([a-zA-Z0-9][a-zA-Z0-9_+#=:-]*)\\s+\"([^\"]*)\"]"
        );

        private boolean eof          = false;
        private String  bufferedLine = null;


        private final LineNumberReader reader;
        private       int              gameIndex = 0;

        public FilePGNSectionProvider(@NotNull File canonicalFile) throws FileNotFoundException {
            reader = new LineNumberReader(new FileReader(canonicalFile));
        }

        @Override
        public PgnSourceType getSourceType() {
            return PgnSourceType.TEXT_FILE;
        }

        @Override
        public boolean hasNext() {
            return !eof;
        }

        @Override
        public PGNSection next() {
            StringBuilder currentSection;
            if (eof) {
                throw new NoSuchElementException();
            }

            int lineNumber;

            try {
                currentSection = new StringBuilder("\n");
                lineNumber     = syncToTagSectionStart(currentSection);
                readTagSection(currentSection);
                readRemainingGameData(currentSection);
            }
            catch (IOException e) {
                throw new NoSuchElementException(e);
            }
            return new PGNSectionImpl(++gameIndex, lineNumber, currentSection.toString());
        }

        private void readRemainingGameData(StringBuilder currentSection) throws IOException {
            int     lineNumber = reader.getLineNumber();
            boolean done       = false;
            while (!done && !eof) {
                LOGGER.trace("SyncMode 2 {}", lineNumber);
                String line = getNextLine();
                if (line != null) {
                    if (!TAG_LINE_PATTERN.matcher(line).matches()) {
                        currentSection.append(line).append("\n");
                    } else {
                        bufferedLine = line;
                        done         = true;
                    }
                }
            }
        }

        private void readTagSection(StringBuilder currentSection) throws IOException {
            int     lineNumber = reader.getLineNumber();
            boolean done       = false;
            while (!done) {
                LOGGER.trace("SyncMode 1 {}", lineNumber);
                String line = getNextLine();
                if (line != null) {
                    currentSection.append(line).append("\n");
                    if (!TAG_LINE_PATTERN.matcher(line).matches()) {
                        done = true;
                    }
                }
            }
        }

        private int syncToTagSectionStart(StringBuilder currentSection) throws IOException {
            int     lineNumber = reader.getLineNumber();
            boolean done       = false;
            while (!done) {
                LOGGER.trace("SyncMode 0 {}", lineNumber);
                String line = getNextLine();
                if (line != null && TAG_LINE_PATTERN.matcher(line).matches()) {
                    currentSection.append(line).append("\n");
                    lineNumber = reader.getLineNumber();
                    done       = true;
                }
            }
            return lineNumber;
        }

        private String getNextLine() throws IOException {
            String line;
            if (bufferedLine != null) {
                line         = bufferedLine;
                bufferedLine = null;
            } else {
                line = reader.readLine();
                eof  = line == null;
            }
            return line;
        }
    }

    @ToString
    @Getter
    private static class PGNSectionImpl implements PGNSection {
        private final int     index;
        private final int     startingLine;
        private final int     lines;
        private final Charset charset = StandardCharsets.UTF_8;

        @ToString.Exclude
        private final String sectionData;

        private PGNSectionImpl(int index, int startingLine, String sectionData) {
            this.startingLine = startingLine;
            this.sectionData  = sectionData;
            this.index        = index;
            this.lines        = this.sectionData.split("\\n").length;
        }
    }
}
