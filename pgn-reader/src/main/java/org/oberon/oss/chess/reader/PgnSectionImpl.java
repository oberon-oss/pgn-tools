package org.oberon.oss.chess.reader;

import lombok.Getter;
import lombok.ToString;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@ToString
@Getter
public class PgnSectionImpl implements PgnSection {
    private final int       index;
    private final int       startingLine;
    private final int       lines;
    private final Charset   charset = StandardCharsets.UTF_8;
    private final PgnSource pgnSource;

    @ToString.Exclude
    private final String sectionData;

    PgnSectionImpl(int index, int startingLine, String sectionData, PgnSource pgnSource) {
        this.startingLine = startingLine;
        this.sectionData  = sectionData;
        this.index        = index;
        this.pgnSource    = pgnSource;
        this.lines        = this.sectionData.split("\\n").length;
    }
}
