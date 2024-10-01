package org.oberon.oss.chess.data.element;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;

import java.util.List;

/**
 * Defines a sequence of 0 or more elements.
 *
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@Getter
@Builder
@ToString
public class ElementSequence implements Element<List<Element<?>>> {
    @Singular
    private final List<Element<?>> elements;

    @Override
    public List<Element<?>> getElementData() {
        return elements;
    }
}
