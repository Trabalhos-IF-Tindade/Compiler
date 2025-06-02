package br.edu.ifgoiano;

import java.io.FileReader;
import java.nio.file.Paths;
import br.edu.ifgoiano.except.ListError;
import java_cup.runtime.Symbol;


public class App {
  public static void main(String[] args) throws Exception {
    /* String rootPath = Paths.get("").toAbsolutePath().toString();
    String inputFilePath = rootPath + "\\input.txt";
    ListError listError = new ListError();
    FileReader in = new FileReader(inputFilePath);
    Yylex scanner = new Yylex(in, listError);
    Parser parser = new Parser(scanner);
    try {
      parser.parse();
    } catch (Exception e) {
      System.out.print("");
    }
    if (listError.hasErrors()) {
      listError.logErrors();
    } else {
      System.out.println("Sintaxe correta!");
    } */

    try {
            ListError listError = new ListError();
            FileReader file = new FileReader("input.txt");
            Yylex scanner = new Yylex(file, listError);

            Symbol token;
            do {
                token = scanner.next_token();
                String tokenName = Sym.terminalNames[token.sym];
                String lexema = token.value != null ? token.value.toString() : "<vazio>";
                System.out.println("Token: " + tokenName + " | Lexema: " + lexema);
            } while (token.sym != Sym.EOF);

            if (listError.hasErrors()) {
                System.out.println("Erros léxicos encontrados:");
                listError.logErrors();
            } else {
                System.out.println("Análise léxica finalizada sem erros.");
            }

        } catch (Exception e) {
            System.err.println("Erro ao executar scanner: " + e.getMessage());
        }
  }
}