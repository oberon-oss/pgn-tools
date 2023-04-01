package org.oberon.oss.chess.reader;

import java.time.LocalDateTime;

public interface PgnReaderLog {
    PgnSource getPgnSource();

    LocalDateTime dateTimeRead();

    String loadedBy();

}
