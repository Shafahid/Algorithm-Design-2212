import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;

class Graph {
    int vertices;
    int edges;
    ArrayList<Edge>[] adj;  
    int[] dist;
    int source;

    public Graph(String filename) throws FileNotFoundException {
        File fl = new File(filename);
        Scanner sc = new Scanner(fl);
        this.vertices = sc.nextInt();
        this.edges = sc.nextInt();

        adj = new ArrayList[vertices + 1];

        for (int i = 1; i <= vertices; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();  
            addEdge(u, v, w);
        }
        this.source = sc.nextInt();

        dist = new int[vertices + 1];
        for (int i = 1; i <= vertices; i++) {
            dist[i] = Integer.MAX_VALUE;
        }
        sc.close();
    }

    public void addEdge(int u, int v, int w) {
        adj[u].add(new Edge(v, w));
        adj[v].add(new Edge(u, w));  
    }

    public int getNumberOfVertices() {
        return vertices;
    }

    public void displayGraph() {
        for (int i = 1; i <= vertices; i++) {
            System.out.print(i + " -> ");
            for (Edge e : adj[i]) {
                System.out.print(e.node + "(" + e.weight + ") ");
            }
            System.out.println();
        }
    }

    public void printDistance() {
        System.out.println("Shortest distances from source " + source + ":");
        for (int i = 1; i <= vertices; i++) {
            System.out.println("To " + i + ": " + (dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]));
        }
    }
}

class Edge {
    int node;
    int weight;

    public Edge(int node, int weight) {
        this.node = node;
        this.weight = weight;
    }
}

class Node implements Comparable<Node> {
    int vertex;
    int distance;

    public Node(int vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}

public class Dijkstra {
    public static void DijkstraAlgorithm(Graph g) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        g.dist[g.source] = 0;
        pq.add(new Node(g.source, 0));
    
        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            int u = curr.vertex;
    
            // Skip if this path is outdated
            if (curr.distance > g.dist[u]) continue;
    
            for (Edge edge : g.adj[u]) {
                int v = edge.node;
                int w = edge.weight;
    
                if (g.dist[u] + w < g.dist[v]) {
                    g.dist[v] = g.dist[u] + w;
                    pq.add(new Node(v, g.dist[v]));
                }
            }
        }
    }
    

    public static void main(String[] args) throws FileNotFoundException {
        Graph gg = new Graph("inputt.txt");  
        DijkstraAlgorithm(gg);
        gg.printDistance();
    }
}
