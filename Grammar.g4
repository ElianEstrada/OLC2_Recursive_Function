grammar Grammar;

options { caseInsensitive = true; }

/*-----------------------Reserved Words-----------------------*/
RVOID       : 'void';
RMAIN       : 'main';
RINT        : 'int';
RIF         : 'if';
RELSE       : 'else';
RPRINT      : 'print';
RRETURN     : 'return';

/*-------------Regular Expression---------------*/
INT         : [0-9]+;
IDEN        : LETTER (LETTER | [_0-9])*;
STRING      : ["'] (~['"\r\n] | '""' | '\'\'')+ ["'];
COMMENT     : '!' (~[\r\n])* ('\r'? '\n')? -> skip;
WS          : [ \t\r\n]+ -> skip;

/*-------------Productions---------------*/
start : listInstructions
      ;

listInstructions : instruction*
                 ;

instruction : declaration       #insDeclar
            | assignment        #insAssign
            | if                #insIf
            | print             #insPrint
            | function          #insFunc
            | main              #insMain
            | callFunction      #insCall
            | return            #insRet
            ;

declaration : type IDEN listDeclarations*
            ;

listDeclarations : ('=' expression | ',' IDEN)
                 ;

assignment : id=IDEN '=' expression
           ;

type : RINT
     ;

if : RIF '(' expression ')' '{' listInstructions '}' ifOptions?
   ;

ifOptions : RELSE (if | '{' listInstructions '}' )
          ;

print : RPRINT '(' expression ')'
      ;

return : RRETURN expression
       ;

function : type IDEN '(' parameters? ')' '{' listInstructions '}'
         ;

callFunction : IDEN '(' values? ')'
             ;

parameters : IDEN listID*
           ;

values : expression listExpression*
       ;

listID : ',' IDEN
       ;

listExpression : ',' expression
               ;

main : RVOID RMAIN '(' ')' '{' listInstructions '}'
     ;

expression : op = '-' right=expression                                                              #arithExp
           | left = expression op = ('*' | '/') right = expression                                  #arithExp
           | left = expression op = ('+' | '-') right = expression                                  #arithExp
           | left = expression op = ('<' | '<=' | '>' | '>=' | '==' | '!=' ) right = expression     #relaExp
           | '(' expression ')'                                                                     #agroupExp
           | callFunction                                                                           #funcExp
           | INT                                                                                    #intExp
           | STRING                                                                                 #strExp
           | IDEN                                                                                   #idExp
           ;

fragment LETTER: [a-z];