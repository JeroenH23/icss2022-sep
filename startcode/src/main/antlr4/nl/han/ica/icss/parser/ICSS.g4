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

// Level 0 + 1 + 2
stylesheet: (variabeleassignment | stylerule)+;
variabeleassignment: variabelreference ASSIGNMENT_OPERATOR value SEMICOLON;
variabelreference: CAPITAL_IDENT;
stylerule: selector OPEN_BRACE declaration+ CLOSE_BRACE;
selector: (LOWER_IDENT | ID_IDENT | CLASS_IDENT)+;
declaration: property COLON value SEMICOLON;
property: LOWER_IDENT;
value: (COLOR | PIXELSIZE | CAPITAL_IDENT | TRUE | FALSE | expression);
expression: add PLUS SCALAR multiply;
add: variabelreference;
multiply: SCALAR MUL value;

//expression:
//    expression PLUS expression #addExpression |
//    PIXELSIZE #pixelLiteral;

//add: variabelreference PLUS multiply;
//multiply: SCALAR MUL PIXELSIZE;W

//
//expression:
//    expression PLUS expression #addExpression |
//    PIXELSIZE #pixelLiteral

// mogelijk een andere klasse toevoegen voor de if statements
//(add PLUS multiply)?

//stylesheet: variabeleassignment+ stylerule+;
//variabeleassignment: variabelreference;
//variabelreference: CAPITAL_IDENT ASSIGNMENT_OPERATOR value SEMICOLON;
//stylerule: selector OPEN_BRACE declaration+ CLOSE_BRACE;
//selector: (LOWER_IDENT)? (ID_IDENT)? (CLASS_IDENT)?;
//declaration: property+ COLON value SEMICOLON;
//property: LOWER_IDENT;
//value: (COLOR)? (PIXELSIZE)? (CAPITAL_IDENT?) (TRUE)? (FALSE)?;
