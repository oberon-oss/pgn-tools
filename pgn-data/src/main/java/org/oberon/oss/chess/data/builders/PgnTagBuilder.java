package org.oberon.oss.chess.data.builders;

import org.oberon.oss.chess.data.PgnTag;
import org.oberon.oss.chess.data.util.PgnClassBuilder;

public class PgnTagBuilder implements PgnClassBuilder<PgnTag> {
    private String tagName;

    private String tagValue;

    @Override
    public PgnTag build() {
        return PgnTag.createFromBuilder(this);
    }

    public String getTagValue() {
        return tagValue;
    }

    public PgnTagBuilder setTagValue(String tagValue) {
        this.tagValue = tagValue;
        return this;
    }

    public String getTagName() {
        return tagName;
    }

    public PgnTagBuilder setTagName(String tagName) {
        this.tagName = tagName;
        return this;
    }
}
