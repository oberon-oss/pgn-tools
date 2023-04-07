package org.oberon.oss.chess.data;

import org.oberon.oss.chess.data.builders.PgnTagBuilder;

public class PgnTag  {

    private final String tagName;

    private final String tagValue;

    public PgnTag(PgnTagBuilder builder) {
        tagName = builder.getTagName();
        tagValue = builder.getTagValue();
    }

    public static PgnTag createFromBuilder(PgnTagBuilder builder) {
        return new PgnTag(builder);
    }


    public static PgnTagBuilder getBuilder() {
        return new PgnTagBuilder();
    }
}
