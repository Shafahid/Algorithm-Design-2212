
import java.util.*;

class DSU {
    int[] parent, rank;

    DSU(int n) {
        parent = new int[n]; // 0-based indexing
        rank = new int[n];

        // Initially, every node is its own parent
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }


    int find(int node) {
        if (parent[node] != node) {
            parent[node] = find(parent[node]); // Path compression
        }
        return parent[node];
    }

    // Union by rank
    void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rootU != rootV) {
            if (rank[rootU] > rank[rootV]) {
                parent[rootV] = rootU;
            } else if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }
        }
    }
}

public class Kruskal {
    int[][] edges; // Stores (u, v, weight)
    int n, e; // Number of nodes and edges

    // Constructor
    Kruskal(int n, int e) {
        this.n = n;
        this.e = e;
        edges = new int[e][3]; // Each edge has (src, dest, weight)
    }

    // Add an edge to the list
    void addEdge(int index, int u, int v, int w) {
        edges[index][0] = u;
        edges[index][1] = v;
        edges[index][2] = w;
    }

    // Kruskal's Algorithm to find the Minimum Spanning Tree
    void kruskal() {
        Arrays.sort(edges, Comparator.comparingInt(a -> a[2])); // Sort edges by weight

        DSU dsu = new DSU(n); // Initialize DSU for union-find

        int mstWeight = 0;
        List<int[]> mstEdges = new ArrayList<>();

        for (int i = 0; i < e; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int w = edges[i][2];

            if (dsu.find(u) != dsu.find(v)) { // Check if u and v belong to different sets
                dsu.union(u, v);
                mstWeight += w;
                mstEdges.add(new int[]{u, v, w});
            }
        }

        // Print the MST
        System.out.println("Edges in Minimum Spanning Tree:");

        for (int[] edge : mstEdges) {
            System.out.println(edge[0] + " -- " + edge[1] + " == " + edge[2]);
        }

        System.out.println("Total MST Weight: " + mstWeight);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read number of nodes and edges
        System.out.print("Enter number of vertices and edges: ");
        int n = sc.nextInt();
        int e = sc.nextInt();

        // Create an instance of KruskalMST
        Kruskal graph = new Kruskal(n, e);

        // Read edges (source, destination, weight)
        System.out.println("Enter edges (u, v, weight):");
        for (int i = 0; i < e; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int w = sc.nextInt();
            graph.addEdge(i, u, v, w);
        }

        // Run Kruskal's Algorithm
        graph.kruskal();

        sc.close();
    }
}
