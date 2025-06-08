package br.edu.ifgoiano.graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Grafo {
    private Map<String, List<Aresta>> listaAdj;
    private boolean direcionado;
    private boolean adjacency;

    private static class Aresta {
    String vertice;

    Aresta(String vertice) {
        this.vertice = vertice;
    }
    }

    public Grafo(boolean direcionado, boolean adjacencia) {
        this.listaAdj = new HashMap<>();
        this.direcionado = direcionado;
        this.adjacency = adjacencia;
    }

    public void adicionarVertice(String v) {
        listaAdj.putIfAbsent(v, new ArrayList<>());
    }

    public void adicionarAresta(String v, String w) {
        adicionarVertice(v);
        adicionarVertice(w);
        listaAdj.get(v).add(new Aresta(w));
        if (!direcionado && !v.equals(w)) {
            listaAdj.get(w).add(new Aresta(v));
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
                matriz[i][j] = 1;
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

    public boolean isDirecionado() {
        return direcionado;
    }
}
