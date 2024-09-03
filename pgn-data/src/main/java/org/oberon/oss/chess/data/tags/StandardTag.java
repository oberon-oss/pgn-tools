package org.oberon.oss.chess.data.tags;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StandardTag extends AbstractTag<String> {
    private final String tagValue;

    public StandardTag(String tagName, String tagValue) {
        super(tagName);
        this.tagValue = tagValue;
    }
}
