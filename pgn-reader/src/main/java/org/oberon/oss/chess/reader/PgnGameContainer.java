package org.oberon.oss.chess.reader;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.oberon.oss.chess.data.Game;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Log4j2
@Getter
@ToString
public class PgnGameContainer {
    private final int                 parseTime;
    private final LocalDateTime       dateTimeRead;
    private final PgnSection          pgnSection;
    private final Game                game;
    private final Set<ErrorLogRecord> recordErrors;

    private PgnGameContainer(final int parseTime,
                             final LocalDateTime dateTimeRead,
                             final PgnSection pgnSection,
                             Game game,
                             Set<ErrorLogRecord> recordErrors
    ) {
        this.parseTime    = parseTime;
        this.dateTimeRead = dateTimeRead;
        this.pgnSection   = pgnSection;
        this.game         = game;
        this.recordErrors = Set.copyOf(recordErrors);
    }

    public boolean hasErrors() {
        return !recordErrors.isEmpty();
    }

    public String getFormattedLogRecord() {
        if (recordErrors.isEmpty()) {
            return "** No errors detected **";
        }

        StringBuilder sb = new StringBuilder("\n\t===========================================");

        sb.append("\n\tPgn source      : ").append(pgnSection.getPgnSource().getSourceURL())
          .append("\n\tGame Index      : ").append(pgnSection.getIndex())
          .append("\n\tstarting line   : ").append(pgnSection.getStartingLine())
          .append("\n\t# of lines      : ").append(pgnSection.getLines())
          .append("\n\t---------------- Game text ----------------");
        int lineCounter = pgnSection.getStartingLine();
        for (String string : pgnSection.getSectionData().split("\n")) {
            sb.append("\n\t").append(String.format("%-8d", lineCounter++)).append(": ").append(string);
        }

        sb.append("\n\t-------------------------------------------");

        int errorNo = 1;
        for (ErrorLogRecord recordError : recordErrors) {
            boolean exceptionPresent = recordError.getException() != null;

            sb.append(String.format("%n\tError %-2d of %-2d: ", errorNo++, recordErrors.size()))
              .append(", source=").append(recordError.getSource());

            if (!exceptionPresent) {
                sb.append(" line #").append(recordError.getLine() + pgnSection.getStartingLine() - 1)
                  .append(", @offset=").append(recordError.getOffset());
            }
            if (recordError.getMessage() != null) {
                sb.append(", msg=").append(recordError.getMessage());
            }
            if (exceptionPresent) {
                writeStackTrace(sb, recordError.getException());
            }
        }

        return sb.append("\n\t===========================================\n").toString();
    }

    private void writeStackTrace(StringBuilder sb, Exception exception) {
        sb.append("\n\t***** Stack trace *****");

        StringWriter stringWriter     = new StringWriter();
        PrintWriter  stackTraceWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(stackTraceWriter);
        stackTraceWriter.flush();
        String[] split = stringWriter.toString().split("\n");

        for (String line : split) {
            sb.append("\n\t").append(line.replace("\r", ""));
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    @Setter
    @Getter(AccessLevel.PACKAGE)
    public static class Builder {
        private       int                 parseTime;
        private       LocalDateTime       dateTimeRead;
        private       PgnSection          pgnSection;
        private       Game                game;
        @Singular
        private final Set<ErrorLogRecord> recordErrors = new HashSet<>();

        private Builder() {
        }

        public PgnGameContainer build() {
            return new PgnGameContainer(parseTime, dateTimeRead, pgnSection, game, recordErrors);
        }
    }
}
