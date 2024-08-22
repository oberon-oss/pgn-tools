package org.oberon.oss.chess.data;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.oberon.oss.chess.data.element.ElementSequence;

import java.util.Set;


@Builder
@Getter
@ToString
public class Game {
    private final Set<Tag>        tagSection;
    private final ElementSequence elementSequence;
    private final GameTermination gameTermination;
}
