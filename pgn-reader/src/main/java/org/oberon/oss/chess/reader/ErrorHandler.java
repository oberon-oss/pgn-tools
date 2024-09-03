package org.oberon.oss.chess.reader;

import lombok.extern.log4j.Log4j2;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;

import java.util.BitSet;

@Log4j2
public class ErrorHandler implements ANTLRErrorListener {
    private final PgnGameContainer.Builder builder;

    public ErrorHandler(PgnGameContainer.Builder builder) {
        this.builder = builder;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object o, int line, int offset, String additionalInformation, RecognitionException e) {
        builder.getRecordErrors()
               .add(ErrorLogRecord.builder()
                                  .line(line)
                                  .offset(offset)
                                  .source("syntax error")
                                  .message(additionalInformation)
                                  .build()
               );
    }

    @Override
    public void reportAmbiguity(Parser parser, DFA dfa, int line, int offset, boolean b, BitSet bitSet, ATNConfigSet atnConfigSet) {
        Token ct = parser.getCurrentToken();
        builder.getRecordErrors()
               .add(ErrorLogRecord.builder()
                                  .line(ct.getLine())
                                  .offset(ct.getCharPositionInLine())
                                  .source("report ambiguity")
                                  .build()
               );
    }

    @Override
    public void reportAttemptingFullContext(Parser parser, DFA dfa, int line, int offset, BitSet bitSet, ATNConfigSet atnConfigSet) {
        Token ct = parser.getCurrentToken();
        builder.getRecordErrors()
               .add(ErrorLogRecord.builder()
                                  .line(ct.getLine())
                                  .offset(ct.getCharPositionInLine())
                                  .source("report attempting full context")
                                  .build()
               );
    }

    @Override
    public void reportContextSensitivity(Parser parser, DFA dfa, int line, int offset, int i2, ATNConfigSet atnConfigSet) {
        builder.getRecordErrors()
               .add(ErrorLogRecord.builder()
                                  .line(line)
                                  .offset(offset)
                                  .source("report context sensitivity")
                                  .build()
               );
    }

    public void applicationError(final Exception e) {
        builder.getRecordErrors()
               .add(ErrorLogRecord
                          .builder()
                          .source("application error")
                          .message(e.getMessage())
                          .exception(e)
                          .build()
               );
    }

    public void unknownSuffix(String text) {
        builder.getRecordErrors()
               .add(ErrorLogRecord
                          .builder()
                          .source("unknown suffix")
                          .message(text)
                          .build()
               );
    }
}
