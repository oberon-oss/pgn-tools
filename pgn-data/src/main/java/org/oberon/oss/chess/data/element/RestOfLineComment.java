package org.oberon.oss.chess.data.element;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RestOfLineComment implements Element{
    private final String text;

    public RestOfLineComment(String text) {
        this.text = text;
    }
}
