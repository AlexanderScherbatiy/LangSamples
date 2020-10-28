grammar Expression;

expr: term ((PLUS|MINUS) term)* ;
term: factor ((MULT|DIV) factor)* ;
factor: NUMBER;

PLUS : '+' ;
MINUS: '-' ;
MULT:  '*' ;
DIV:   '/' ;

NUMBER: (DIGIT)+;

DIGIT: '0'..'9';

WS: [ \t\r\n]+ -> skip ;

