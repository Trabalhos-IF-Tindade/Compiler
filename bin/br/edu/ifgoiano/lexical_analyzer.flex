package br.edu.ifgoiano;

import java_cup.runtime.Symbol;

%%

%cup
%unicode
%line
%column

espaco = [/n/s/t/r]

%%

"a"                 {return new Symbol(Sym.a);}
"b"                 {return new Symbol(Sym.b);}
{espaco}            {/*VAZIO*/}
.                   {return new Symbol(Sym.error);}
<<EOF>>             {return new Symbol(Sym.EOF);} 