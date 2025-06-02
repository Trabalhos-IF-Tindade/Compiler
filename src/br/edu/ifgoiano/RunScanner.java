/* package br.edu.ifgoiano;

import java.nio.file.Paths;

public class RunScanner {
  public static void main(String[] args) {
    String rootPath = Paths.get("").toAbsolutePath().toString();
    String subPath = "/src/br/edu/ifgoiano/";

    String flexFile[] = { rootPath + subPath + "lexical_analyzer.flex" };
    jflex.Main.main(flexFile);
  }
} */

package br.edu.ifgoiano;

import java.io.FileReader;
import java_cup.runtime.Symbol;
import br.edu.ifgoiano.except.ListError;

public class RunScanner {
    public static void main(String[] args) {
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
