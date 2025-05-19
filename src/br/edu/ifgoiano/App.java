package br.edu.ifgoiano;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) throws IOException {
        String rootPath = Paths.get("").toAbsolutePath().toString();
        BufferedReader text = new BufferedReader(new FileReader(rootPath + "\\input.txt"));
        String line;
        while ((line = text.readLine()) != null) {
            try {
                Yylex scanner = new Yylex(new StringReader(line));
                Parser parser = new Parser(scanner);
                // Retorna null se não houver erro
                Object result = parser.parse().value;
                System.out.println("Sintaxe correta: " + line);
            } catch (Exception ex) {
                System.out.println("Erro de sintaxe: " + line);
            }
        }
    }
}