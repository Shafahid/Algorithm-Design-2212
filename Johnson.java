import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class Graph {
    int vertices;
    int edges;
    ArrayList<Edge>[] adj;
    int[][] dist;  
    int[] h;       
    int inf = Integer.MAX_VALUE;
    public Graph(String filename) throws FileNotFoundException {
        File fl = new File(filename);
        Scanner sc = new Scanner(fl);
        this.vertices = sc.nextInt();
        this.edges = sc.nextInt();

        adj = new ArrayList[vertices + 2];  // +1 for 1-based indexing, +1 for new vertex 's'
        for (int i = 0; i <= vertices + 1; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            addEdge(u, v, w);
        }
        sc.close();

        dist = new int[vertices + 1][vertices + 1];  // dist[u][v] = shortest path u → v
        h = new int[vertices + 2];                   // h[v] = potential of vertex v
    }

    public void addEdge(int u, int v, int w) {
        adj[u].add(new Edge(v, w));
    }

    // Add a new vertex 's' (vertex 0) connected to all others with weight 0
    public void addJohnsonSourceVertex() {
        for (int v = 1; v <= vertices; v++) {
            adj[0].add(new Edge(v, 0));
        }
    }

    // Run Bellman-Ford to compute h[v] (vertex potentials)
    public boolean computeVertexPotentials() {
        Arrays.fill(h, inf);
        h[0] = 0;  // Source vertex 's' (0)

        // Relax all edges V times
        for (int i = 0; i < vertices; i++) {
            for (int u = 0; u <= vertices; u++) {
                for (Edge e : adj[u]) {
                    int v = e.node;
                    int w = e.weight;
                    if (h[u] != inf && h[v] > h[u] + w) {
                        h[v] = h[u] + w;
                    }
                }
            }
        }
        System.out.println("h values: ");
        for(int i=1; i<=vertices; i++){
            System.out.print(h[i]+" ");
        }
        System.out.println();
        // Check for negative cycles
        for (int u = 0; u <= vertices; u++) {
            for (Edge e : adj[u]) {
                int v = e.node;
                int w = e.weight;
                if (h[u] != inf && h[v] > h[u] + w) {
                    return false;  // Negative cycle detected
                }
            }
        }
        return true;
    }

    // Reweight edges and run Dijkstra from each vertex
    public void johnsonsAlgorithm() {
        addJohnsonSourceVertex();
        if (!computeVertexPotentials()) {
            System.out.println("Graph contains a negative cycle!");
            return;
        }

        System.out.println("Reweighted edge values: ");
        // Reweight edges: w'(u, v) = w(u, v) + h[u] - h[v]
        for (int u = 1; u <= vertices; u++) {
            for (Edge e : adj[u]) {
                e.weight = e.weight + h[u] - h[e.node];
                System.out.print(e.weight+" ");
            }
        }
        System.out.println();
        // Run Dijkstra from each vertex
        for (int u = 1; u <= vertices; u++) {
            dijkstra(u);
        }

        for(int u=1;u<=vertices;u++){
            for(int v=1;v<=vertices;v++){
                System.out.print((dist[u][v] == inf ? "INF" : dist[u][v]) + " ");
            }
            System.out.println();
        }

        // Restore original distances: δ(u, v) = d(u, v) - h[u] + h[v]
        for (int u = 1; u <= vertices; u++) {
            for (int v = 1; v <= vertices; v++) {
                if (dist[u][v] != inf) {
                    dist[u][v] = dist[u][v] - h[u] + h[v];
                }
            }
        }
    }

    // Dijkstra's algorithm from source 'u'
    private void dijkstra(int u) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Arrays.fill(dist[u], inf);
        dist[u][u] = 0;
        pq.add(new Node(u, 0));

        while (!pq.isEmpty()) {
            Node curr = pq.poll();
            if (curr.distance > dist[u][curr.vertex]) continue;

            for (Edge e : adj[curr.vertex]) {
                int v = e.node;
                int w = e.weight;
                if (dist[u][v] > dist[u][curr.vertex] + w) {
                    dist[u][v] = dist[u][curr.vertex] + w;
                    pq.add(new Node(v, dist[u][v]));
                }
            }
        }
    }

    // Print all-pairs shortest paths
    public void printAllPairsShortestPaths() {
        System.out.println("All-pairs shortest paths:");
        for (int u = 1; u <= vertices; u++) {
            for (int v = 1; v <= vertices; v++) {
                System.out.print((dist[u][v] == inf ? "INF" : dist[u][v]) + " ");
            }
            System.out.println();
        }
    }
}

public class Johnson {
    public static void main(String[] args) throws FileNotFoundException {
        Graph g = new Graph("inputttt.txt");  
        g.johnsonsAlgorithm();
        g.printAllPairsShortestPaths();
    }
}