package org.oberon.oss.chess.data.element;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SanMove implements Element{
    private final String text;

    public SanMove(String text) {
        this.text = text;
    }
}
