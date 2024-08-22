package org.oberon.oss.chess.data.element;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Nag implements Element{
    private final String text;

    public Nag(String text) {
        this.text = text;
    }
}
