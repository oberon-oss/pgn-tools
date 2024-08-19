package org.oberon.oss.chess.reader;

import java.nio.charset.Charset;

/**
 * Describes a section from a PGN file that contains 1 complete PGN game.
 *
 * @author fhdumay
 * @since 1.0.0
 */
public interface PgnSection {

    /**
     * Returns the source the section was read from.
     *
     * @return The source of the PGN section.
     *
     * @since 1.0.0
     */
    PgnSource getPgnSource();

    /**
     * Returns the relative position of the PGN section relative to the start of the source of the PGN section.
     *
     * @return The relative index of the section
     *
     * @since 1.0.0
     */
    int getIndex();

    /**
     * Returns the starting line number of the PGN Section, relative to the start of the source of the PGN section.
     *
     * @return The starting line number of the section.
     *
     * @since 1.0.0
     */
    int getStartingLine();

    /**
     * Returns the number of lines of the PGN section.
     *
     * @return The number of lines contained in the section.
     *
     * @since 1.0.0
     */
    int getLines();

    /**
     * Returns the actual data contained in the PGN section.
     *
     * @return The data in the section, consisting of the tag section and the move section.
     *
     * @since 1.0.0
     */
    String getSectionData();

    /**
     * Returns the character set of the data returned by the {@link #getSectionData()} method
     *
     * @return the {@link Charset} of the data in the section
     *
     * @since 1.0.0
     */
    Charset getCharset();
}
