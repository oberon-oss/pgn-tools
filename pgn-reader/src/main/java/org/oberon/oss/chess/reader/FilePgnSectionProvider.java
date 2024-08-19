package org.oberon.oss.chess.reader;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

/**
 * Implementation of the {@link PgnSectionProvider} interface that reads from a plain text file.
 *
 * @author fhdumay
 * @since 1.0.0
 */
@Log4j2
public class FilePgnSectionProvider implements PgnSectionProvider {
    private static final Pattern TAG_LINE_PATTERN = Pattern.compile(
          "^\\[\\s*([a-zA-Z0-9][a-zA-Z0-9_+#=:-]*)\\s+\"([^\"]*)\"]"
    );

    private final LineNumberReader reader;
    private final PgnSource        pgnSource;

    private boolean eof          = false;
    private String  bufferedLine = null;
    private int     gameIndex    = 0;

    /**
     * Constructs a new section provider, using a {@link File} object as its input source.
     *
     * @param pgnFile The file to read from
     *
     * @throws FileNotFoundException If the file specified by 'pgnFile' does not exists.
     * @since 1.0.0
     */
    public FilePgnSectionProvider(@NotNull File pgnFile) throws FileNotFoundException {
        reader    = new LineNumberReader(new FileReader(pgnFile));
        pgnSource = new PgnSource() {
            @Override
            public PgnSourceType getSourceType() {
                return PgnSourceType.TEXT_FILE;
            }

            @Override
            public URL getSourceURL() throws MalformedURLException {
                return pgnFile.toURI().toURL();
            }
        };
    }

    @Override
    public boolean hasNext() {
        return !eof;
    }

    @Override
    public PgnSection next() {
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
        return new PgnSectionImpl(++gameIndex, lineNumber, currentSection.toString(), pgnSource);
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
