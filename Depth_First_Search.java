package DFS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Graph{
    int vertices;
    int edges;
    ArrayList<Integer>[] adj;
    ArrayList<Integer> Traversal;
    boolean[] visited;
    int source;

    public Graph(String filename) throws FileNotFoundException {
        File fl = new File(filename);
        Scanner sc = new Scanner(fl);
        this.vertices = sc.nextInt();
        this.edges = sc.nextInt();

        adj = new ArrayList[vertices+1];
        visited = new boolean[vertices+1];
        Traversal = new ArrayList<>();

        for(int i=1;i<=vertices;i++) {
            adj[i] = new ArrayList<>();
            visited[i] = false;
        }

        for(int i=0;i<edges;i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            addEdge(u,v);
        }
        this.source = sc.nextInt();

    }

    public void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }


    public int getNumberOfVertices() {
        return vertices;
    }



    public void displayGraph(){
        for(int i=1;i<=vertices;i++){
            System.out.println(i);
            for(int j:adj[i]){
                System.out.println("-->"+j);
            }
        }
    }

    public void DFS(int u){
        visited[u] = true;
        Traversal.add(u);
        for(int v:adj[u]){
            if(!visited[v]){
                DFS(v);
            }
        }
    }
    public void printTraversal() {
        for(int i=0;i<Traversal.size();i++){
            System.out.print(Traversal.get(i)+" ");
        }
    }
}




    public class Depth_First_Search {
    public static void main(String[] args) throws FileNotFoundException {
        Graph gg = new Graph("C:\\\\Users\\\\ASUS\\\\Desktop\\\\CSE 2-2\\\\Java Algo\\\\DFS\\\\input.txt");
        gg.DFS(gg.source);
        gg.printTraversal();
    }
}
