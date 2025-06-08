package br.edu.ifgoiano.graph;

public class GeraGrafo {
    public static void main(String[] args) {
        Grafo grafoOrigem = new Grafo(false, true);
        grafoOrigem.adicionarAresta("A", "C");
        grafoOrigem.adicionarAresta("A", "B");
        grafoOrigem.adicionarAresta("A", "E");

        grafoOrigem.gerar_entrada();
        
        System.out.println("Arquivo de entrada gerado com sucesso.");
    }
}
