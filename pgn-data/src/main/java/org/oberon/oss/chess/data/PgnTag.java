package org.oberon.oss.chess.data;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class PgnTag  {
    private final String tagName;
    private final String tagValue;
}
