package org.oberon.oss.chess.reader;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorLogRecord {
    private final int line;
    private final int offset;
    private final String source;
    private final String message;
    private final Exception exception;
}
