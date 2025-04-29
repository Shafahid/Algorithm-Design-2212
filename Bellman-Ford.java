import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

class Graph {
    int vertices;
    int edges;
    ArrayList<Edge> edgesList;  
    int[] dist;
    int source;

    public Graph(String filename) throws FileNotFoundException {
        File fl = new File(filename);
        Scanner sc = new Scanner(fl);
        this.vertices = sc.nextInt();
        this.edges = sc.nextInt();

        edgesList = new ArrayList<>();

        for (int i = 0; i < edges; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            edgesList.add(new Edge(u, v, w));
        }
        this.source = sc.nextInt();

        dist = new int[vertices + 1];
        for (int i = 1; i <= vertices; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        sc.close();
    }

    public int getNumberOfVertices() {
        return vertices;
    }

    public void printDistance() {
        System.out.println("Shortest distances from source " + source + ":");
        for (int i = 1; i <= vertices; i++) {
            System.out.println("To " + i + ": " + (dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]));
        }
    }
}

class Edge {
    int source;
    int destination;
    int weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

public class BellmanFord {
    public static void BellmanFordAlgorithm(Graph g) {
        
        g.dist[g.source] = 0;

        // Relax all edges |V| - 1 times
        for (int i = 1; i < g.vertices; i++) {
            for (Edge edge : g.edgesList) {
                int u = edge.source;
                int v = edge.destination;
                int weight = edge.weight;

                if (g.dist[u] != Integer.MAX_VALUE && g.dist[u] + weight < g.dist[v]) {
                    g.dist[v] = g.dist[u] + weight;
                }
            }
        }

        // Check for negative-weight cycles
        for (Edge edge : g.edgesList) {
            int u = edge.source;
            int v = edge.destination;
            int weight = edge.weight;

            if (g.dist[u] != Integer.MAX_VALUE && g.dist[u] + weight < g.dist[v]) {
                System.out.println("Graph contains negative weight cycle");
                return;
            }
        }
        
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph gg = new Graph("input.txt");  // Same input format as Dijkstra
        BellmanFordAlgorithm(gg);
        gg.printDistance();
    }
}
