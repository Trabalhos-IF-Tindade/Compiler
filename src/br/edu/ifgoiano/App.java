package br.edu.ifgoiano;

import java.io.FileReader;
import java.nio.file.Paths;
import br.edu.ifgoiano.except.ListError;
import br.edu.ifgoiano.graph.Grafo;
import java_cup.runtime.Symbol;

public class App {
  public static void main(String[] args) throws Exception {
    try {
      String inputPath = "src\\br\\edu\\ifgoiano\\input\\grafo_entrada.txt";

      System.out.println("=== Lista de tokens lidos pelo scanner ===");
      ListError lexErrors = new ListError();
      try (FileReader fr1 = new FileReader(inputPath)) {
        Yylex lexPrinter = new Yylex(fr1, lexErrors);
        Symbol tok;
        do {
          tok = lexPrinter.next_token();
          String name = Sym.terminalNames[tok.sym];
          String lex = tok.value != null ? tok.value.toString() : "<vazio>";
          System.out.printf("Token: %-12s | Lexema: %s%n", name, lex);

          if (tok.sym == Sym.ERROR) {
            System.err.println(">> Erro léxico encontrado.");
            lexErrors.logErrors();
            return;  
        }
        } while (tok.sym != Sym.EOF);
      }

      ListError parseErrors = new ListError();
      try (FileReader fr2 = new FileReader(inputPath)) {
        Yylex scanner = new Yylex(fr2, parseErrors);
        Parser parser = new Parser(scanner);
        parser.parse();
        if (parseErrors.hasErrors()) {
          System.err.println("Erros durante o parse:");
          parseErrors.logErrors();
        } else {
          System.out.println("Processamento concluído com sucesso.");
          System.out.println("  • Entrada: " + inputPath);
          System.out.println("  • Matriz:  src/br/edu/ifgoiano/output/matriz_adjacencia.txt");
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
