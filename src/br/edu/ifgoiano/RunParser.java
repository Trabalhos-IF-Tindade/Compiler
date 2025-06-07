package br.edu.ifgoiano;

import java.io.FileReader;
import java.nio.file.Paths;

import br.edu.ifgoiano.except.ListError;
import br.edu.ifgoiano.grath.Grafo;

public class RunParser {
  public static void main(String[] args) {/* 
    String rootPath = Paths.get("").toAbsolutePath().toString();
    String subPath = "/src/br/edu/ifgoiano/";
    try {
      String cupFile[] = { "-destdir", rootPath + subPath,
          "-parser", "Parser", "-symbols", "Sym", rootPath +
              subPath + "glc.cup" };

      java_cup.Main.main(cupFile);
    } catch (Exception ex) {
      ex.printStackTrace();
    } */
   /* try {
            ListError listError = new ListError();
            FileReader in = new FileReader("src\\br\\edu\\\\ifgoiano\\input\\grafo_entrada.txt");
            Yylex scanner = new Yylex(in, listError);
            Parser parser = new Parser(scanner);

            parser.parse();  
            Grafo grafoParse = parser.getGrafo();

            if (listError.hasErrors()) {
                System.err.println("Erros léxicos encontrados:");
                listError.logErrors();
            } else {
                System.out.println("Parsing concluído sem erros léxicos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } */
  }
}