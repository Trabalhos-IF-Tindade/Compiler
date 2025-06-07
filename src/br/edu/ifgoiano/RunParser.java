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
   try {
            // 1) monta o grafo em memória
            Grafo grafoOrigem = new Grafo(false, true);
            grafoOrigem.adicionarAresta("A", "C", 1);
            grafoOrigem.adicionarAresta("B", "C", 1);
            grafoOrigem.adicionarAresta("C", "D", 1);
            grafoOrigem.adicionarAresta("C", "E", 1);
            grafoOrigem.adicionarAresta("D", "E", 1);

            // 2) gera o arquivo de entrada que o parser vai ler
            grafoOrigem.gerar_entrada();  // escreve output/grafo_entrada.txt

            // 3) roda a análise JFlex+CUP sobre esse arquivo
            ListError listError = new ListError();
            FileReader in = new FileReader("output/grafo_entrada.txt");
            Yylex scanner = new Yylex(in, listError);
            Parser parser = new Parser(scanner);

            parser.parse();  // aqui são chamadas as semantic actions do CUP

            // 4) opcional: se quiser usar o grafo do parser após o parse
            Grafo grafoParse = parser.getGrafo();
            // ... mas a matriz já foi salva por dentro da action PRINT ADJACENCY

            if (listError.hasErrors()) {
                System.err.println("Erros léxicos encontrados:");
                listError.logErrors();
            } else {
                System.out.println("Parsing concluído sem erros léxicos.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
  }
}