package br.edu.ifgoiano;

import java_cup.runtime.Symbol;
import br.edu.ifgoiano.except.ListError;

%%
%cup
%unicode
%line
%column

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
number = {digit}+

%%
{number}    { return createSymbol(Sym.NUMBER, Integer.valueOf(yytext())); }
"{"         { return createSymbol(Sym.LBRACE); }
"}"         { return createSymbol(Sym.RBRACE); }
":"         { return createSymbol(Sym.COLON); }
"idade"     { return createSymbol(Sym.AGE); }
"\""        { return createSymbol(Sym.QUOTE); }

[\t\r\n ]+  { /* Ignorar espaços, quebras de linha, etc */ }

.           { this.defineError(yyline, yycolumn, "Caractere desconhecido: " + yytext()); }

<<EOF>>     { return createSymbol(Sym.EOF); }