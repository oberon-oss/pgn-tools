package org.oberon.oss.chess.reader;

import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@ToString
@Getter
public class PgnSectionImpl implements PgnSection {
    private final int       index;
    private final int       startingLine;
    private final int       lines;
    private final Charset   charset;
    private final PgnSource pgnSource;

    @ToString.Exclude
    private final String sectionData;

    PgnSectionImpl(int index, int startingLine, String sectionData, PgnSource pgnSource) {
        this(index, startingLine, sectionData, pgnSource, null);
    }

    PgnSectionImpl(int index, int startingLine, String sectionData, PgnSource pgnSource, @Nullable Charset charset) {
        this.startingLine = startingLine;
        this.sectionData  = sectionData;
        this.index        = index;
        this.pgnSource    = pgnSource;
        this.charset = charset == null ? StandardCharsets.UTF_8 : charset;
        this.lines        = this.sectionData.split("\\n").length;
    }
}
