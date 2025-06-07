package br.edu.ifgoiano.grath;


public class GeraGrafo {
    public static void main(String[] args) {
        // 1) instancia o grafo e adiciona as arestas
        Grafo grafoOrigem = new Grafo(false, true);
        grafoOrigem.adicionarAresta("A", "C", 1);
        grafoOrigem.adicionarAresta("A", "B", 1);
        grafoOrigem.adicionarAresta("A", "E", 1);

        // 2) gera o arquivo de entrada (grafo_entrada.txt)
        grafoOrigem.gerar_entrada();
        
        System.out.println("Arquivo de entrada gerado com sucesso.");
    }
}
