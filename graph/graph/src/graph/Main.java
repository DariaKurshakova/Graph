package graph;
// Проверка функциональности графа
import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        Graph<String> g = new Graph<>(false);
        g.addVertex("A");
        g.addVertex("B");
        g.addVertex("C");
        g.addVertex("D");
        g.addEdge("A", "B", 1);
        g.addEdge("B", "C", 2);
        g.addEdge("A", "C", 4);
        g.addEdge("C", "D", 1);
        System.out.println("Граф:");
        System.out.println(g);
        System.out.println("\nDFS из A:");
        g.dfs("A");
        System.out.println("\nBFS из A:");
        g.bfs("A");
        System.out.println("\nЕсть цикл? " + g.hasCycle());
        System.out.println("\nКратчайшие пути из A:");
        System.out.println(g.shortestPath("A"));
        try {
            g.saveToFile("graph.txt");
            System.out.println("\nСохранено в graph.txt");
            Graph<String> g2 = new Graph<>(false);
            g2.loadFromFile("graph.txt");
            System.out.println("\nЗагружено:");
            System.out.println(g2);
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}