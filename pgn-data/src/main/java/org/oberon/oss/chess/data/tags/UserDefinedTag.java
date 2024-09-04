package org.oberon.oss.chess.data.tags;

public class UserDefinedTag extends AbstractTag<String>{

    public UserDefinedTag(String tagName,String tagValue) {
        super(tagName,tagValue);
    }

    @Override
    public String getTagValue() {
        return null;
    }
}
