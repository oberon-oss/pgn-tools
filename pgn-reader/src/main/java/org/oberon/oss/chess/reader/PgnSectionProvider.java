package org.oberon.oss.chess.reader;

import java.util.Iterator;

/**
 * Defines a contract for implementing classes on how to provide a single section of a PGN source while iterating over the source.
 * <p>
 * A 'single' section refers to a complete PGN game - consisting of
 * <ol>
 *     <li>A tag section</li>
 *     <li>The move section</li>
 * </ol>
 *
 * @author fhdumay
 * @since 1.0.0
 */
public interface PgnSectionProvider extends Iterator<PgnSection> {

}
