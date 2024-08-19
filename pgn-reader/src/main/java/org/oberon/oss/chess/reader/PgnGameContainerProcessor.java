package org.oberon.oss.chess.reader;

/**
 * Contract for classes that want to process the contents of a {@link PgnGameContainer}
 *
 * @author fhdumay
 * @since 1.0.0
 */
public interface PgnGameContainerProcessor {
    /**
     * Processes the information collected into the container
     *
     * @param container The container to process
     *
     * @since 1.0.0
     */
    void processGameContainer(PgnGameContainer container);
}
