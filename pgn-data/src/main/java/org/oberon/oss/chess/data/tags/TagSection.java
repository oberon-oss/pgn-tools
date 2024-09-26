package org.oberon.oss.chess.data.tags;

import lombok.Builder;
import lombok.Singular;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.oberon.oss.chess.data.tags.defs.AbstractTag;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.oberon.oss.chess.data.tags.TagType.*;

/**
 * @author Fabien H. Dumay
 * @since 1.0.0
 */
@Builder
public class TagSection implements Iterable<AbstractTag<?>> {
    @Singular
    private final Set<AbstractTag<?>> tags;

    private static final Set<TagType> SEVEN_TAG_ROSTER = Set.of(EVENT, SITE, DATE, ROUND, WHITE, BLACK, RESULT);

    public boolean isSevenTagRosterCompliant() {
        Set<TagType> found = new HashSet<>();
        for (AbstractTag<?> tag : tags) {
            found.add(tag.getTagType());
        }
        return found.containsAll(SEVEN_TAG_ROSTER);
    }

    public @Nullable AbstractTag<Object> lookupTag(@NotNull TagType type) {
        for (AbstractTag<?> tag : tags) {
            if (tag.getTagType().equals(type)) {
                //noinspection unchecked
                return (AbstractTag<Object>) tag;
            }
        }
        return null;
    }

    @Override
    public @NotNull Iterator<AbstractTag<?>> iterator() {
        return tags.iterator();
    }
}

