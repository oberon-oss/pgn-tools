package org.oberon.oss.chess.reader;

import java.net.URL;

/**
 * Describes the source of a processed PGN game(s).
 *
 * @author fhdumay
 * @since 1.0.0
 */
public interface PgnSource {
    /**
     * Returns the source type a PGN game was read from.
     *
     * @return A value from the {@link PgnSourceType } enumeration
     *
     * @since 1.0.0
     */
    PgnSourceType getSourceType();

    URL getSourceURL();
}
