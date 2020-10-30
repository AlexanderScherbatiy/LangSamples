grammar Func;

@header {
    package func;
}


root: stmt+ ;

stmt
    : ID EQ expr ';'             # Assign
    | expr ';'                   # ExprStmt
    ;

expr
    : expr op=(MUL|DIV) expr     # MulDiv
    | expr op=(PLUS|MINUS) expr  # AddSub
    | NUMBER                     # number
    | '(' expr ')'               # parentheses
    ;


ID
    : (LETTER)+
    ;

NUMBER
    : (DIGIT)+
    ;

LETTER
    : [a-zA-Z']
    ;

DIGIT
    : '0'..'9'
    ;


PLUS : '+' ;
MINUS: '-' ;
MUL  : '*' ;
DIV  : '/' ;
EQ   : '=' ;

WS
    : [ \t\r\n]+ -> skip
    ;
