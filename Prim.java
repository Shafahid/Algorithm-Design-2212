import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


class Edge {
    int dest, weight;

    public Edge(int dest, int weight) {
        this.dest = dest;
        this.weight = weight;
    }
}
class Graph {
    int vertices;
    int edges;
    ArrayList<Edge>[] adj;

    boolean[] inMST;
    int[] key;
    int[] parent;

    public Graph(String filename) throws FileNotFoundException {
        File fl = new File(filename);
        Scanner sc = new Scanner(fl);
        this.vertices = sc.nextInt();
        this.edges = sc.nextInt();

        adj = new ArrayList[vertices + 1];
        inMST = new boolean[vertices + 1];// Track nodes in MST
        key = new int[vertices + 1]; // Store min weight edge for each vertex
        parent = new int[vertices + 1]; // Store MST structure


        for (int i = 1; i <= vertices; i++) {
            adj[i] = new ArrayList<>();
            inMST[i] = false;
            key[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }

        for (int i = 0; i < edges; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            addEdge(u, v, w);
        }

    }
    public void addEdge(int u, int v, int weight) {
        adj[u].add(new Edge(v,weight));
        adj[v].add(new Edge(u,weight));
    }


    public int getNumberOfVertices() {
        return vertices;
    }


    public void MST() {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        key[1] = 0;  // Start from vertex 1 (assuming 1-based index)
        pq.add(new int[]{0, 1});
        while (!pq.isEmpty()) {
            int u = pq.poll()[1];

            if (inMST[u]) continue;
            inMST[u] = true;

            for (Edge edge : adj[u]) {
                int v = edge.dest, weight = edge.weight;
                if (!inMST[v] && weight < key[v]) {
                    key[v] = weight;
                    parent[v] = u;
                    pq.add(new int[]{weight, v});
                }
            }
        }
        System.out.println("Edges in the Minimum Spanning Tree:");
        int totalWeight = 0;
        for (int i = 2; i <= vertices; i++) {
            System.out.println(parent[i] + " - " + i + " (Weight: " + key[i] + ")");
            totalWeight += key[i];
        }
        System.out.println("Total Weight of MST: " + totalWeight);
    }
    }
public class Prim {
    public static void main(String[] args) throws FileNotFoundException {
        Graph gg = new Graph("MST/input.txt");
        gg.MST();
    }
}
