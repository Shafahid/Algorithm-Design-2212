


import java.io.*;
import java.util.*;
 
public class Problem_4 {
    static int vertices;
    static int edges;
    static ArrayList<Integer>[] adj;
    static boolean[] visited;
    static ArrayList<Integer> TopOrder;
    static int SCC = 0;
    static int[] kingdoms;
    static ArrayList<Integer>[] Transpose;
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        vertices = Integer.parseInt(st.nextToken());
        edges = Integer.parseInt(st.nextToken());
        adj = new ArrayList[vertices + 1];
        Transpose = new ArrayList[vertices + 1];
        visited = new boolean[vertices + 1];
        kingdoms = new int[vertices + 1];
        TopOrder = new ArrayList<>();
 
        for (int i = 1; i <= vertices; i++) {
            adj[i] = new ArrayList<>();
            visited[i] = false;
            Transpose[i] = new ArrayList<>();
        }
 
        for (int i = 0; i < edges; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            adj[a].add(b);
        }
 
        for (int i = 1; i <= vertices; i++) {
            if (!visited[i]) {
                dfs1(i);
            }
        }
 
        for (int i = 1; i <= vertices; i++) {
            for (int c : adj[i]) {
                Transpose[c].add(i);
            }
        }
 
        Arrays.fill(visited, false);
        Collections.reverse(TopOrder);
 
        for (int u : TopOrder) {
            if (!visited[u]) {
                SCC++;
                dfs2(u);
            }
        }
        System.out.println(SCC);
        for (int i = 1; i <= vertices; i++) {
            bw.write(kingdoms[i] + " ");
        }
        bw.flush();
    }
 
    static void dfs1(int node) {
        visited[node] = true;
        kingdoms[node] = SCC;
        for (int child : adj[node]) {
            if (!visited[child]) {
                dfs1(child);
            }
        }
        TopOrder.add(node);
    }
 
    static void dfs2(int node) {
        visited[node] = true;
        kingdoms[node] = SCC;
        for (int child : Transpose[node]) {
            if (!visited[child]) {
                dfs2(child);
            }
        }
    }
}
