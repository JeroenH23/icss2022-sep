grammar ICSS;

//--- LEXER: ---

// IF support:
IF: 'if';
ELSE: 'else';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;


//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';

//--- PARSER: ---

// Level 0 + 1 + 2 + 3
stylesheet: (variabeleassignment | stylerule)+;
variabeleassignment: variabelreference ASSIGNMENT_OPERATOR value SEMICOLON;
variabelreference: CAPITAL_IDENT;

stylerule: selector OPEN_BRACE (declaration | ifstatement)+ CLOSE_BRACE;
selector: (LOWER_IDENT | ID_IDENT | CLASS_IDENT)+;
declaration: property COLON value SEMICOLON;
property: LOWER_IDENT;
value: (COLOR | PIXELSIZE | CAPITAL_IDENT | TRUE | FALSE | expression);

ifstatement: IF BOX_BRACKET_OPEN variabelreference BOX_BRACKET_CLOSE OPEN_BRACE declaration nestedifstatement;
nestedifstatement: IF BOX_BRACKET_OPEN variabelreference BOX_BRACKET_CLOSE OPEN_BRACE declaration CLOSE_BRACE elsestatement;
elsestatement: ELSE OPEN_BRACE declaration CLOSE_BRACE CLOSE_BRACE declaration;
expression: add;
add: variabelreference PLUS multiply;
multiply: (scalar | MUL | value)*;
scalar: SCALAR;

// mogelijk een andere klasse toevoegen voor de if statements