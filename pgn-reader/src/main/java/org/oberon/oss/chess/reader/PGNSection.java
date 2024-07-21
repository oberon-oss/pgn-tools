package org.oberon.oss.chess.reader;

import java.nio.charset.Charset;

public interface PGNSection {
    int getIndex();

    int getStartingLine();

    int getLines();

    String getSectionData();

    Charset getCharset();
}
