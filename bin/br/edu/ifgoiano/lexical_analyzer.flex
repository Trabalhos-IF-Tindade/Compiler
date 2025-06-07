package br.edu.ifgoiano;

import java_cup.runtime.Symbol;
import br.edu.ifgoiano.except.ListError;

%%
%cup
%unicode
%line
%column
%class Yylex
%public
%caseless

%{
    private ListError listError;
    
    public Yylex(java.io.FileReader in, ListError listError) {
        this(in);
        this.listError = listError;
    }
    
    public ListError getListError() {
        return listError;
    }
    
    public void defineError(int line, int column, String text) {
        this.listError.defineError(line, column, text);
    }
    
    public void defineError(int linha, int coluna) {
        this.listError.defineError(linha, coluna);
    }
    
    public void defineError(String texto) {
        this.listError.defineError(texto);
    }
    
    public Symbol createSymbol(int token, Object value) {
        return new Symbol(token, yyline, yycolumn, value);
    }
    
    public Symbol createSymbol(int token) {
        return this.createSymbol(token, null);
    }
%}

digit = [0-9]
letter = [a-zA-Z]
identifier = {letter}({letter}|{digit}|_)*

%%

"graph"         { return createSymbol(Sym.GRAPH, yytext()); }
"vertex"        { return createSymbol(Sym.VERTEX, yytext()); }
"edge"          { return createSymbol(Sym.EDGE, yytext()); }
"print"         { return createSymbol(Sym.PRINT, yytext()); }
"adjacency"     { return createSymbol(Sym.ADJACENCY, yytext()); }
"directed"      { return createSymbol(Sym.DIRECTED, yytext()); }
"undirected"    { return createSymbol(Sym.UNDIRECTED, yytext()); }

"->"            { return createSymbol(Sym.ARROW, yytext()); }
"--"            { return createSymbol(Sym.DASH, yytext()); }
":"             { return createSymbol(Sym.COLON, yytext()); }  

{identifier}    { return createSymbol(Sym.ID, yytext()); }

[\r\n]+         { yyline++; yycolumn = 1; } // incrementa linha e reseta coluna
[\t]            { yycolumn += 4; } // tabulação assume 4 espaços

[\r\n\t ]+      { /* ignora espaços */ }

. {
    String texto = yytext();
   defineError(yyline + 1, yycolumn + 1,"Erro léxico: símbolo desconhecido '" + texto + "'");
   // devolve Sym.ERROR carregando o lexema
   return createSymbol(Sym.ERROR, texto);
}

<<EOF>>         { return createSymbol(Sym.EOF); }