package org.oberon.oss.chess.data.element;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RestOfLineComment implements Element<String>{
    private final String text;

    public RestOfLineComment(String text) {
        this.text = text;
    }

    @Override
    public String getElementData() {
        return "";
    }
}
