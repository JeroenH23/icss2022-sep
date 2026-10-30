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

stylerule: selector OPEN_BRACE (declaration | statement)+ CLOSE_BRACE;
selector: (LOWER_IDENT | ID_IDENT | CLASS_IDENT)+;
declaration: property (ASSIGNMENT_OPERATOR | COLON)  value SEMICOLON;
property: LOWER_IDENT | CAPITAL_IDENT;
value: (valuestypes | addoperation | multiplyoperation);
valuestypes: (COLOR | PIXELSIZE | CAPITAL_IDENT | TRUE | FALSE);

statement: (ifstatement | elsestatement)+;
ifstatement: IF BOX_BRACKET_OPEN variabelreference BOX_BRACKET_CLOSE OPEN_BRACE declaration (CLOSE_BRACE | ifstatement)+;
elsestatement: ELSE OPEN_BRACE (declaration | CLOSE_BRACE)+;

addoperation: leftside (PLUS rightside)+;
leftside: variabelreference;
rightside: (multiplyoperation | nomultiply | valuestypes | variabelreference);
nomultiply: value;
multiplyoperation: (MUL | scalar)+ value;
scalar: SCALAR;


//addoperation: (variabelreference | operator | multiplyoperation)+;


//multiplyoperation: (MUL);
//operator: (PLUS | MIN | scalar | valuestypes);

// mogelijk een andere klasse toevoegen voor de if statements