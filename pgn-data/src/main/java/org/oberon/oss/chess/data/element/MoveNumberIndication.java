package org.oberon.oss.chess.data.element;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MoveNumberIndication implements Element{
    private final String text;

    public MoveNumberIndication(String text) {
        this.text = text;
    }
}
