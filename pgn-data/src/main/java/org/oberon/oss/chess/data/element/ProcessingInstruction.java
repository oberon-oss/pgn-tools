package org.oberon.oss.chess.data.element;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProcessingInstruction implements Element {

    private final String text;

    public ProcessingInstruction(String text) {
        this.text = text;
    }
}
