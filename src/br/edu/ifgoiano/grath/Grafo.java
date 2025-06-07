package br.edu.ifgoiano.grath;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Grafo {
    private Map<String, List<Aresta>> listaAdj;
    private boolean direcionado;
    private boolean adjacency;

    public Grafo(boolean direcionado, boolean adjacencia) {
        this.listaAdj = new HashMap<>();
        this.direcionado = direcionado;
        this.adjacency = adjacencia;
    }

    public void adicionarVertice(String v) {
        listaAdj.putIfAbsent(v, new ArrayList<>());
    }

    public void adicionarAresta(String v, String w, int peso) {
        if (peso < 0) {
            throw new IllegalArgumentException("Peso da aresta não pode ser negativo");
        }
        adicionarVertice(v);
        adicionarVertice(w);
        listaAdj.get(v).add(new Aresta(w, peso));
        if (!direcionado && !v.equals(w)) {
            listaAdj.get(w).add(new Aresta(v, peso));
        }
    }

    public int contarLacos() {
        int count = 0;
        for (String v : listaAdj.keySet()) {
            for (Aresta a : listaAdj.get(v)) {
                if (v.equals(a.vertice)) {
                    count++;
                    if (!direcionado)
                        break;
                }
            }
        }
        return count;
    }

    public boolean completo() {
        int n = listaAdj.size();
        for (String v : listaAdj.keySet()) {
            Set<String> distintos = new HashSet<>();
            for (Aresta a : listaAdj.get(v)) {
                if (!a.vertice.equals(v))
                    distintos.add(a.vertice);
            }
            if (distintos.size() != n - 1)
                return false;
        }
        return true;
    }

    public int grauVertice(String v) {
        if (!listaAdj.containsKey(v)) {
            throw new IllegalArgumentException("Vértice \"" + v + "\" não existe");
        }
        return listaAdj.get(v).size();
    }

    public List<String> encontrarCaminho(String inicio, String fim) {
        if (!listaAdj.containsKey(inicio) || !listaAdj.containsKey(fim))
            return null;

        Queue<String> fila = new LinkedList<>();
        Set<String> visitado = new HashSet<>();
        Map<String, String> pai = new HashMap<>();

        fila.add(inicio);
        visitado.add(inicio);

        while (!fila.isEmpty()) {
            String v = fila.poll();
            if (v.equals(fim))
                break;
            for (Aresta a : listaAdj.get(v)) {
                String w = a.vertice;
                if (!visitado.contains(w)) {
                    visitado.add(w);
                    pai.put(w, v);
                    fila.add(w);
                }
            }
        }

        if (!visitado.contains(fim))
            return null;

        List<String> caminho = new ArrayList<>();
        for (String atual = fim; atual != null; atual = pai.get(atual)) {
            caminho.add(atual);
        }
        Collections.reverse(caminho);
        return caminho;
    }

    public ResultadoDijkstra dijkstra(String inicio) {
        if (!listaAdj.containsKey(inicio)) {
            throw new IllegalArgumentException("Vértice \"" + inicio + "\" não existe");
        }

        Map<String, Integer> dist = new HashMap<>();
        Map<String, String> prev = new HashMap<>();
        Set<String> visitado = new HashSet<>();

        for (String v : listaAdj.keySet()) {
            dist.put(v, Integer.MAX_VALUE);
            prev.put(v, null);
        }
        dist.put(inicio, 0);

        while (visitado.size() < listaAdj.size()) {
            String u = null;
            int minDist = Integer.MAX_VALUE;
            for (String v : dist.keySet()) {
                if (!visitado.contains(v) && dist.get(v) < minDist) {
                    minDist = dist.get(v);
                    u = v;
                }
            }

            if (u == null)
                break;

            visitado.add(u);

            for (Aresta a : listaAdj.get(u)) {
                String w = a.vertice;
                int peso = a.peso;
                if (!visitado.contains(w)) {
                    int alt = dist.get(u) + peso;
                    if (alt < dist.get(w)) {
                        dist.put(w, alt);
                        prev.put(w, u);
                    }
                }
            }
        }

        return new ResultadoDijkstra(dist, prev);
    }

    public ResultadoCaminho menorCaminho(String inicio, String fim) {
        ResultadoDijkstra resultado = dijkstra(inicio);
        int distancia = resultado.distancias.getOrDefault(fim, Integer.MAX_VALUE);
        if (distancia == Integer.MAX_VALUE) {
            return new ResultadoCaminho(null, Integer.MAX_VALUE);
        }

        List<String> caminho = new ArrayList<>();
        for (String v = fim; v != null; v = resultado.antecessores.get(v)) {
            caminho.add(v);
        }
        Collections.reverse(caminho);
        return new ResultadoCaminho(caminho, distancia);
    }

    public void imprimirMenorCaminho(String inicio, String fim) {
        ResultadoCaminho resultado = menorCaminho(inicio, fim);
        if (resultado.caminho == null) {
            System.out.println("Não há caminho de " + inicio + " até " + fim);
        } else {
            System.out.println("Menor caminho de " + inicio + " até " + fim + ": " +
                    String.join(" -> ", resultado.caminho) +
                    " (distância: " + resultado.distancia + ")");
        }
    }

    public String paraDot() {
        StringBuilder sb = new StringBuilder();
        String op = direcionado ? "->" : "--";
        // sb.append(direcionado ? "digraph G {\n" : "graph G {\n");
        String type = direcionado ? "directed" : "undirected";

        sb.append("GRAPH:\n");
        sb.append("\t" + type + "\n");
        for (String v : listaAdj.keySet()) {
            sb.append("\tvertex " + v + "\n");
            // sb.append(" \"" + v + "\"\n");
        }

        Set<String> vistos = new HashSet<>();

        for (Map.Entry<String, List<Aresta>> entry : listaAdj.entrySet()) {
            String v = entry.getKey();
            for (Aresta a : entry.getValue()) {
                String w = a.vertice;
                // int peso = a.peso;

                if (v.equals(w)) {
                    sb.append("\tedge  " + v + " " + op + " " + w + " \n");
                } else {
                    String chave = direcionado ? v + "->" + w : v.compareTo(w) < 0 ? v + "|" + w : w + "|" + v;
                    if (!vistos.contains(chave)) {
                        sb.append("\tedge  " + v + " " + op + " " + w + " \n");
                        vistos.add(chave);
                    }
                }
            }
        }
        if (adjacency) {
            sb.append("\tprint adjacency");
        }

        return sb.toString();
    }

    /* -------------- */
    public void gerar_entrada() {
        String conteudo = paraDot();

        File arquivo = new File("src\\br\\edu\\ifgoiano\\input\\grafo_entrada.txt");
        File pastaPai = arquivo.getParentFile();
        if (pastaPai != null && !pastaPai.exists()) {
            pastaPai.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
            writer.write(conteudo);
            System.out.println("Entrada DOT salva em: " + arquivo.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* ----- */
    public String gerarMatrizAdjacencia() {
        List<String> vertices = new ArrayList<>(listaAdj.keySet());
        Collections.sort(vertices);

        int n = vertices.size();
        Map<String, Integer> indice = new HashMap<>();
        for (int i = 0; i < n; i++) {
            indice.put(vertices.get(i), i);
        }

        int[][] matriz = new int[n][n];

        for (int i = 0; i < n; i++) {
            String v = vertices.get(i);
            for (Aresta a : listaAdj.get(v)) {
                int j = indice.get(a.vertice);
                matriz[i][j] = a.peso;
            }
        }

        StringBuilder sb = new StringBuilder();

        sb.append("\t");
        for (String v : vertices) {
            sb.append(v).append("\t");
        }
        sb.append("\n");

        for (int i = 0; i < n; i++) {
            sb.append(vertices.get(i)).append("\t");
            for (int j = 0; j < n; j++) {
                sb.append(matriz[i][j]).append("\t");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public void salvarMatrizAdjacenciaEmArquivo(String caminhoArquivo) {
        String conteudo = gerarMatrizAdjacencia();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write(conteudo);
            System.out.println("Matriz de adjacência salva em: " + caminhoArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Aresta {
        String vertice;
        int peso;

        Aresta(String vertice, int peso) {
            this.vertice = vertice;
            this.peso = peso;
        }
    }

    public static class ResultadoDijkstra {
        Map<String, Integer> distancias;
        Map<String, String> antecessores;

        ResultadoDijkstra(Map<String, Integer> distancias, Map<String, String> antecessores) {
            this.distancias = distancias;
            this.antecessores = antecessores;
        }
    }

    public static class ResultadoCaminho {
        List<String> caminho;
        int distancia;

        ResultadoCaminho(List<String> caminho, int distancia) {
            this.caminho = caminho;
            this.distancia = distancia;
        }
    }
}
