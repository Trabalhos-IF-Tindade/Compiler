package br.edu.ifgoiano.except;

public class Error {

    private int line, column;
    private String text;

    public Error(int line, int column, String text) {
        this.line = line;
        this.column = column;
        this.text = text;
    }

    public void print() {
        String aux = "Erro na linha " + line + " e coluna " + column + ": ";
        if(this.text == null)
            aux += "Erro desconhecido";
        else
            aux += this.text;
        System.out.println(aux);
    }

    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
