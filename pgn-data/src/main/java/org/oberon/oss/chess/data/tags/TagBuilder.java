package org.oberon.oss.chess.data.tags;

import lombok.Setter;

public class TagBuilder<V> {
    private TagType tagType;
    private V value;

    public TagBuilder<V> setTagType(TagType tagType) {
        this.tagType = tagType;
        return this;
    }

    public TagBuilder<V> setValue(V value) {
        this.value = value;
        return this;
    }

    public AbstractTag<V> build() {
        switch (tagType) {
            default -> {
                return (AbstractTag<V>) new StandardTag(tagType, value.toString());
            }
        }
    }

}
