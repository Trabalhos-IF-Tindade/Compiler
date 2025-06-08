package br.edu.ifgoiano;

import java.io.FileReader;
import java.nio.file.Paths;
import br.edu.ifgoiano.except.ListError;

public class App {
    public static void main(String[] args) throws Exception {
        String rootPath = Paths.get("").toAbsolutePath().toString();
        String inputFilePath = rootPath + "\\src\\br\\edu\\ifgoiano\\input\\grafo_errado.txt";
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
        }
    }
}
