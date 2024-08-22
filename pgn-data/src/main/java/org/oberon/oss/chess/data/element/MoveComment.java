package org.oberon.oss.chess.data.element;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MoveComment implements Element {
    private final String text;

    public MoveComment(String text) {
        this.text = text;
    }
}
