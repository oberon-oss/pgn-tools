package org.oberon.oss.chess.reader;

import java.net.URL;

public interface PgnSource {
    PgnSourceType getSourceType();

    URL getSourceLocation();
}
