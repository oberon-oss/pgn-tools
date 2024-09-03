package org.oberon.oss.chess.data;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.oberon.oss.chess.data.element.ElementSequence;
import org.oberon.oss.chess.data.tags.AbstractTag;

import java.util.Set;


@Builder
@Getter
@ToString
public class Game {
    private final Set<AbstractTag<?>> tagSection;
    private final ElementSequence     elementSequence;
    private final GameTermination gameTermination;
}
