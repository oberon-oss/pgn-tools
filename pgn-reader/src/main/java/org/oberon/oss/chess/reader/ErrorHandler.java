package org.oberon.oss.chess.reader;

import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.jetbrains.annotations.Nullable;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Log4j2
public class ErrorHandler implements ANTLRErrorListener {
    private final PgnGameContainer.Builder builder;

    public ErrorHandler(PgnGameContainer.Builder builder) {
        this.builder = builder;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object o, int line, int offset, String additionalInformation, RecognitionException e) {
        constructAndAddErrorLogEntry(line, offset, "syntax error", additionalInformation);
    }

    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int line, int offset, boolean b, BitSet bitSet, ATNConfigSet atnConfigSet) {
        Token ct = parser.getCurrentToken();
        constructAndAddErrorLogEntry(ct.getLine(), ct.getCharPositionInLine(), "report ambiguity", null);
    }

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int line, int offset, BitSet bitSet, ATNConfigSet atnConfigSet) {
        Token ct = parser.getCurrentToken();
        constructAndAddErrorLogEntry(ct.getLine(), ct.getCharPositionInLine(), "report attempting full context", null);
    }

    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int line, int offset, int i2, ATNConfigSet atnConfigSet) {
        constructAndAddErrorLogEntry(line, offset, "report context sensitivity", null);
    }


    private void constructAndAddErrorLogEntry(int line, int offset, String antlrName, @Nullable String additionalInformation) {
        PgnSection                      pgnSection   = builder.getPgnSection();
        Map<ErrorLogEntry, Set<String>> recordErrors = builder.getRecordErrors();

        ErrorLogEntry entry = new ErrorLogEntry(pgnSection, line, offset, antlrName, additionalInformation, null);

        if (!recordErrors.containsKey(entry)) {
            recordErrors.put(entry, new HashSet<>());
        }
        recordErrors.get(entry).add(antlrName);
    }


}
