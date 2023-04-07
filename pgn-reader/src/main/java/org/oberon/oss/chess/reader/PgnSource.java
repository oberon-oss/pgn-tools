package org.oberon.oss.chess.reader;

import java.net.URI;
import java.net.URL;

public interface PgnSource {
    PgnSourceType getSourceType();

    URI getSourceLocation();
}
