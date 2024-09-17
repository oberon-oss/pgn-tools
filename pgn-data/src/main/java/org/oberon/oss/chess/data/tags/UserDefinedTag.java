package org.oberon.oss.chess.data.tags;

import org.oberon.oss.chess.data.tags.defs.AbstractTag;

public class UserDefinedTag extends AbstractTag<String> {

    public UserDefinedTag(String tagName, String tagValue) {
        super(tagName, tagValue);
    }

    @Override
    public String fromString(String tagValue) {
        return tagValue;
    }
}
