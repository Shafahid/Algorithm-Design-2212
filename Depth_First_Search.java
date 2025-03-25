package DFS;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Graph{

    int white = 0;
    int grey = 1;
    int black = 2;
    int vertices;
    int edges;
    int time;
    ArrayList<Integer>[] adj;
    ArrayList<Integer> Traversal;
    int[] discoveryTime;
    int[] finshingTime;

    int[] color;

    int[] parent;
    int source;

    public Graph(String filename) throws FileNotFoundException {
        File fl = new File(filename);
        Scanner sc = new Scanner(fl);
        this.vertices = sc.nextInt();
        this.edges = sc.nextInt();
        this.time = 0;

        adj = new ArrayList[vertices+1];
        discoveryTime = new int[vertices+1];
        finshingTime = new int[vertices+1];
        color = new int[vertices+1];
        parent = new int[vertices+1];
        Traversal = new ArrayList<>();


        for(int i=1;i<=vertices;i++) {
            adj[i] = new ArrayList<>();
            discoveryTime[i] = 0;
            finshingTime[i] = 0;
            color[i] = white;
            parent[i] = -1;
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
        time = time + 1;
        discoveryTime[u] = time;
        color[u] = grey;
        Traversal.add(u);
        for(int v:adj[u]){
            if(color[v]==white){
                parent[v] = u;
                DFS(v);
            }
        }
        time = time + 1;
        finshingTime[u] = time;
        color[u] = black;
    }
    public void printTraversal() {
        for(int i=0;i<Traversal.size();i++){
            System.out.print(Traversal.get(i)+" ");
        }
        System.out.println();
    }
    public void printTime(){
        for (int i = 1; i <= vertices; i++) {
            System.out.println(i+"-> "+discoveryTime[i]+" "+finshingTime[i]);
        }
    }
}




    public class Depth_First_Search {
    public static void main(String[] args) throws FileNotFoundException {
        Graph gg = new Graph("input.txt");
        gg.DFS(gg.source);
        gg.printTraversal();
        gg.printTime();
    }
}
