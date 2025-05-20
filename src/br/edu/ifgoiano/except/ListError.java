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

  public void logErrors() {
    for (Error e : this.errors) {
      e.print();
    }
  }

  public boolean hasErrors() {
    return this.errors.size() > 0;
  }

}