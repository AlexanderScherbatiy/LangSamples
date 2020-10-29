grammar Expression;

@header {
    package expression;
}

root: expr ;

expr
    : expr op=(MUL|DIV) expr     # MulDiv
    | expr op=(PLUS|MINUS) expr  # AddSub
    | NUMBER                     # number
    | '(' expr ')'               # parentheses
    ;

NUMBER
    : (DIGIT)+
    ;

DIGIT
    : '0'..'9'
    ;

PLUS : '+' ;
MINUS: '-' ;
MUL : '*' ;
DIV  : '/' ;

WS
    : [ \t\r\n]+ -> skip
    ;

