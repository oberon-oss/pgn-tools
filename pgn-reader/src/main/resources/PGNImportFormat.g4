/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
grammar PGNImportFormat;

@header {
package org.oberon.oss.chess.reader;
}
parse
 : pgnGame EOF
 ;

pgnGame
 : tagSection moveComment? moveTextSection game_termination
 ;

tagSection
 : tagPair*
 ;

tagPair
 : LEFT_BRACKET tagName tagValue RIGHT_BRACKET
 ;

tagName
 : SYMBOL
 ;

tagValue
 : STRING
 ;

moveTextSection
 : elementSequence
 ;

elementSequence
 : (element | recursive_variation)*
 ;

element
 : processingInstruction
 | restOfLineComment
 | moveNumberIndication
 | sanMove
 | moveComment
 | nag
 ;

moveNumberIndication
 : INTEGER (PERIOD|ELLIPSIS)?
 ;

sanMove
 : SYMBOL SUFFIX_ANNOTATION?
 ;

recursive_variation
 : LEFT_PARENTHESIS elementSequence RIGHT_PARENTHESIS
 ;
moveComment
 : BRACE_COMMENT
 ;

nag
 : NUMERIC_ANNOTATION_GLYPH
 ;

restOfLineComment
: REST_OF_LINE_COMMENT
;

processingInstruction
: ESCAPE
;

game_termination
 : WHITE_WINS
 | BLACK_WINS
 | DRAWN_GAME
 | UNDECIDED_OR_UNKNOWN
 ;

BRACE_COMMENT
 : '{' ~'}'* '}'
 ;

REST_OF_LINE_COMMENT
 : ';' ~[\r\n]*
 ;

ESCAPE
 : {getCharPositionInLine() == 0}? '%' ~[\r\n]*
 ;

WHITE_WINS
 : '1-0'
 ;

BLACK_WINS
 : '0-1'
 ;

DRAWN_GAME
 : '1/2-1/2'
 ;

SPACES
 : [ \t\r\n]+ -> skip
 ;

STRING
 : '"' ('\\\\' | '\\"' | ~[\\"])* '"'
 ;

INTEGER
 : [0-9]+
 ;

PERIOD
 : '.'
 ;

ELLIPSIS
 : '...'
 ;

UNDECIDED_OR_UNKNOWN
 : '*'
 ;

LEFT_BRACKET
 : '['
 ;

RIGHT_BRACKET
 : ']'
 ;

LEFT_PARENTHESIS
 : '('
 ;

RIGHT_PARENTHESIS
 : ')'
 ;

LEFT_ANGLE_BRACKET
 : '<'
 ;

RIGHT_ANGLE_BRACKET
 : '>'
 ;

NUMERIC_ANNOTATION_GLYPH
 : '$' [0-9]+
 ;

SYMBOL
 : [a-zA-Z0-9] [a-zA-Z0-9_+#=:-]*
 ;

SUFFIX_ANNOTATION
 : [?!] [?!]?
 ;

UNEXPECTED_CHAR
 : .
 ;