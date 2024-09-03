package org.oberon.oss.chess.data.tags;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fabien H. Dumay
 */
public enum TagType {
    EVENT,
    EVENT_DATE("EventDate"),
    EVENT_SPONSOR("EventSponsor"),
    SECTION("Section"),
    STAGE("Stage"),
    BOARD("Board"),
    SITE,
    DATE,
    TIME("Time"),
    UTC_DATE("UTCDate"),
    UTC_TIME("UTCTime"),
    ROUND,
    WHITE,
    WHITE_ELO("WhiteElo"),
    WHITE_USCF("WhiteUSCF"),
    WHITE_NA("WhiteNA"),
    WHITE_TYPE("WhiteType"),
    BLACK,
    BLACK_ELO("BlackElo"),
    WHITE_TITLE("WhiteTitle"),
    BLACK_TITLE("BlackTitle"),
    BLACK_USCF("BlackUSCF"),
    BLACK_NA("BlackNA"),
    BLACK_TYPE("BlackType"),
    TIME_CONTROL("TimeControl"),
    RESULT,
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
    USER_DEFINED_TAG("*");

    private final String lookupName;

    TagType() {
        lookupName = name().toUpperCase();
    }

    TagType(String lookupName) {
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
