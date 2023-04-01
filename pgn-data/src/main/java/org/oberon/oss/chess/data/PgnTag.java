package org.oberon.oss.chess.data;

import org.oberon.oss.chess.data.builders.PgnTagBuilder;
import org.oberon.oss.chess.data.util.BaseClass;
import org.oberon.oss.chess.data.util.PgnClassBuilder;

public class PgnTag implements BaseClass<PgnTag> {

    private final String tagName;

    private final String tagValue;

    public PgnTag(PgnTagBuilder builder) {
        tagName = builder.getTagName();
        tagValue = builder.getTagValue();
    }

    public static PgnTag createFromBuilder(PgnTagBuilder builder) {
        return new PgnTag(builder);
    }

    @Override
    public PgnClassBuilder<PgnTag> getBuilder() {
        return new PgnTagBuilder();
    }
}
