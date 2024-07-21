package org.oberon.oss.chess.reader;

import java.util.Iterator;

public interface PGNSectionProvider extends Iterator<PGNSection> {
    PgnSourceType getSourceType();
}
