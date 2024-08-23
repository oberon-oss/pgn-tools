package org.oberon.oss.chess.reader;

import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Log4j2
public class ErrorLogEntry {

    private final PgnSection  pgnSection;
    private final int         line;
    private final int         offset;
    private final Set<String> antlrNameSet = new HashSet<>();
    private final String      additionalInformation;
    private final Exception   exception;

    ErrorLogEntry(PgnSection pgnSection, int line, int offset, String antlrName, @Nullable String additionalInformation, @Nullable Exception exception) {
        this.pgnSection = pgnSection;
        this.line       = line;
        this.offset     = offset;
        if (antlrName != null) {
            antlrNameSet.add(antlrName);
        }
        this.additionalInformation = additionalInformation;
        this.exception             = exception;
    }

    ErrorLogEntry(ErrorLogEntry errorLogEntry, @NotNull String antlrName) {
        pgnSection            = errorLogEntry.pgnSection;
        line                  = errorLogEntry.line;
        offset                = errorLogEntry.offset;
        additionalInformation = errorLogEntry.additionalInformation;
        exception             = errorLogEntry.exception;
        antlrNameSet.addAll(errorLogEntry.antlrNameSet);
        antlrNameSet.add(antlrName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorLogEntry entry = (ErrorLogEntry) o;
        return line == entry.line && offset == entry.offset && Objects.equals(pgnSection, entry.pgnSection);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(pgnSection);
        result = 31 * result + line;
        result = 31 * result + offset;
        return result;
    }

    public String getFormattedLogRecord() {
        StringBuilder stringBuilder = new StringBuilder("\n\t===========================================");
        if (!antlrNameSet.isEmpty()) {
            stringBuilder.append("\n\tAntlr nam(s)    : ");
            Iterator<String> iterator = antlrNameSet.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append(iterator.next());
                if (iterator.hasNext()) {
                    stringBuilder.append(", ");
                }
            }
        }

        if (additionalInformation != null && !additionalInformation.isEmpty()) {
            stringBuilder.append(" (").append(additionalInformation).append(")");
        }

        stringBuilder.append("\n\tPgn source      : ").append(pgnSection.getPgnSource().getSourceURL())
                     .append("\n\tGame Index      : ").append(pgnSection.getIndex())
                     .append("\n\tstarting line   : ").append(pgnSection.getStartingLine())
                     .append("\n\t# of lines      : ").append(pgnSection.getLines())
                     .append("\n\t   Error Line   : ").append(pgnSection.getStartingLine() + line - 1)
                     .append("\n\t         Offset : ").append(offset)
                     .append("\n\t---------------- Game text ----------------");

        int lineCounter = pgnSection.getStartingLine();
        for (String string : pgnSection.getSectionData().split("\n")) {
            stringBuilder.append("\n\t").append(String.format("%-8d", lineCounter++)).append(": ").append(string);
        }
        stringBuilder.append("\n\t===========================================");
        if (exception != null) {
            stringBuilder.append("\n*** Exception Information ***").append(exception.getMessage());
        }
        return stringBuilder.toString();
    }
}
