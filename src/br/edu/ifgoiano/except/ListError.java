package br.edu.ifgoiano.except;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

    public void logErrors() {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream("errors.log", false),
                        "UTF-8"
                )
        )) {
            for (Error e : this.errors) {
                String msg;
                if (e.getLine() > 0) {
                    msg = "Linha " + e.getLine()
                            + ", Coluna " + e.getColumn()
                            + ": " + e.getText();
                } else {
                    msg = "Erro: " + e.getText();
                }
                writer.write(msg);
                writer.newLine();
            }
            System.out.println("Erro(s) de sintaxe. Confira os logs salvos em: /errors.log");
        } catch (IOException ex) {
            System.err.println("Falha ao gravar log de erros: " + ex.getMessage());
        }
    }

    public boolean hasErrors() {
        return !this.errors.isEmpty();
    }
}
