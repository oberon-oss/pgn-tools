package org.oberon.oss.chess.data.tags;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@Getter
@ToString
public class StandardTag extends AbstractTag<String> {
    protected StandardTag(final @NotNull TagType tagType, final @NotNull String tagValue) {
        super(tagType, tagValue);
    }
}
