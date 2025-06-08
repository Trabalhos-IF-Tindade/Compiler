package br.edu.ifgoiano;

import java_cup.runtime.Symbol;
import br.edu.ifgoiano.except.ListError;

%%
%cup
%unicode
%line
%column
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
    
    // registra erro com linha, coluna e texto
    public void defineError(int line, int column, String text) {
        this.listError.defineError(line, column, text);
    }
    
    public void defineError(int linha, int coluna) {
        this.listError.defineError(linha, coluna);
    }
    
    public void defineError(String texto) {
        this.listError.defineError(texto);
    }
    
    // cria Symbol preservando posição
    public Symbol createSymbol(int token, Object value) {
        return new Symbol(token, yyline + 1, yycolumn + 1, value);
    }
    
    public Symbol createSymbol(int token) {
        return this.createSymbol(token, null);
    }
%}

digit      = [0-9]
letter     = [a-zA-Z]
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

[\t\r\n ]+  {/* Ignorar espaços, quebras de linha, etc */}

<<EOF>>         { return createSymbol(Sym.EOF); }

. {
    String texto = yytext();
    defineError(yyline + 1, yycolumn + 1,
        "Erro léxico: símbolo desconhecido '" + texto + "'");
    /* devolve token de erro para parser poder sincronizar */
    return createSymbol(Sym.ERROR, texto);
}