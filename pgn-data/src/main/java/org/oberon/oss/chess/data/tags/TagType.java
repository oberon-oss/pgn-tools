package org.oberon.oss.chess.data.tags;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fabien H. Dumay
 */
public enum TagType {
    // Seven tag roster tags
    EVENT(true),
    SITE(true),
    DATE(true),
    ROUND(true),
    WHITE(true),
    BLACK(true),
    RESULT(true),
    // well known or defined tag
    EVENT_DATE("EventDate"),
    EVENT_SPONSOR("EventSponsor"),
    SECTION("Section"),
    STAGE("Stage"),
    BOARD("Board"),
    TIME("Time"),
    UTC_DATE("UTCDate"),
    UTC_TIME("UTCTime"),
    WHITE_ELO("WhiteElo"),
    WHITE_USCF("WhiteUSCF"),
    WHITE_NA("WhiteNA"),
    WHITE_TYPE("WhiteType"),
    BLACK_ELO("BlackElo"),
    WHITE_TITLE("WhiteTitle"),
    BLACK_TITLE("BlackTitle"),
    BLACK_USCF("BlackUSCF"),
    BLACK_NA("BlackNA"),
    BLACK_TYPE("BlackType"),
    TIME_CONTROL("TimeControl"),
    TERMINATION("Termination"),
    SETUP("SetUp"),
    FEN,
    OPENING("Opening"),
    VARIATION("Variation"),
    SUB_VARIATION("SubVariation"),
    ECO,
    NIC,
    ANNOTATOR("Annotator"),
    MODE("Mode"),
    PLY_COUNT("PlyCount"),
    // Tag definition for tags currently unknown or not yet supported6
    USER_DEFINED_TAG("*");

    private final String lookupName;

    @Getter
    private final boolean required;

    TagType() {
        this(false);
    }

    TagType(String lookupName) {
        this(lookupName, false);
    }

    TagType(boolean required) {
        this.required   = required;
        this.lookupName = name().toUpperCase();
    }

    TagType(String lookupName, boolean required) {
        this.required   = required;
        this.lookupName = lookupName.toUpperCase();
    }

    private static final Map<String, TagType> TAG_TYPE_MAP;

    static {
        Map<String, TagType> lookup = new HashMap<>();
        for (TagType tagType : TagType.values()) {
            lookup.put(tagType.lookupName, tagType);
        }
        TAG_TYPE_MAP = Map.copyOf(lookup);
    }

    /**
     * Returns the {@link TagType} instance for the specified lookup name.
     *
     * @param lookupName The tag type to lookup.
     *
     * @return The tag type matching the provided 'lookupName'. If no enumeration instances matched the specified string, the value
     *       {@link TagType#USER_DEFINED_TAG}
     *
     * @since 1.0.0
     */
    public static @NotNull TagType getTagType(final String lookupName) {
        TagType type = TAG_TYPE_MAP.get(lookupName.toUpperCase());
        if (type == null) {
            type = USER_DEFINED_TAG;
        }
        return type;
    }
}
