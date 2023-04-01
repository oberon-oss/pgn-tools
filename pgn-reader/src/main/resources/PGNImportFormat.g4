/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
grammar PGNImportFormat;

@header {
package net.oberon.oss.chess.pgn.reader;
}
parse
 : pgn_database EOF
 ;

pgn_database
 : pgn_game*
 ;

pgn_game
 : tag_section movetext_section game_termination
 ;

tag_section
 : tag_pair*
 ;

tag_pair
 : LEFT_BRACKET tag_name tag_value RIGHT_BRACKET
 ;

tag_name
 : SYMBOL
 ;

tag_value
 : STRING
 ;

movetext_section
 : element_sequence
 ;

element_sequence
 : (element | recursive_variation)*
 ;

element
 : processing_instruction
 | rest_of_line_comment
 | move_number_indication
 | san_move
 | move_comment
 | nag
 ;

move_number_indication
 : INTEGER (PERIOD|ELLIPSIS)?
 ;

san_move
 : SYMBOL SUFFIX_ANNOTATION?
 ;

recursive_variation
 : LEFT_PARENTHESIS element_sequence RIGHT_PARENTHESIS
 ;
move_comment
 : BRACE_COMMENT
 ;

nag
 : NUMERIC_ANNOTATION_GLYPH
 ;

rest_of_line_comment
: REST_OF_LINE_COMMENT
;

processing_instruction
: ESCAPE
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

game_termination
 : WHITE_WINS
 | BLACK_WINS
 | DRAWN_GAME
 | UNDECIDED_OR_UNKNOWN
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