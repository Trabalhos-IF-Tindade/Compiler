package br.edu.ifgoiano.except;

import java.util.List;

public class ListError {

    private List<Error> errors;

    public ListError() {
        this.errors = new java.util.ArrayList<Error>();
    }

    public void defineError(int line, int column, String text) {
        this.errors.add(new Error(line, column, text));
    }

    public void defineError(int line, int column) {
        this.errors.add(new Error(line, column, null));
    }

    public void defineError(String text) {
        for (Error e : this.errors) {
            if (e.getText() == null) {
                e.setText(text);
                return;
            }
        }
    }

    private void writeToLogFile(int line, int column, String text) {
        try (java.io.FileWriter writer = new java.io.FileWriter("errors.log", true)) {
            writer.write("Erro" + (line > 0 ? " na linha " + line + ", coluna " + column : "") + ": " + text + "\n");
        } catch (java.io.IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }

    public void logErrors() {
        for (Error e : this.errors) {
            e.print();
        }
    }

    public boolean hasErrors() {
        return this.errors.size() > 0;
    }

}
