package BFS;

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
    int[] dist;
    int source;

    public Graph(String filename) throws FileNotFoundException {
        File fl = new File(filename);
        Scanner sc = new Scanner(fl);
        this.vertices = sc.nextInt();
        this.edges = sc.nextInt();

        adj = new ArrayList[vertices+1];

        for(int i=1;i<=vertices;i++) {
            adj[i] = new ArrayList<>();
        }

        for(int i=0;i<edges;i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            addEdge(u,v);
        }
        this.source = sc.nextInt();

        dist = new int[vertices+1];
        for(int i=1;i<=vertices;i++) {
                dist[i] = Integer.MAX_VALUE;
            }
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

    public void printDistance() {
       for(int i=1;i<=vertices;i++) {
           System.out.print(dist[i]+" ");
       }
    }

}

public class Breadth_First_Search {
    public static void BFS(Graph g){
        boolean[] visited = new boolean[g.getNumberOfVertices()+1];
        visited[g.source] = true;
        Queue<Integer> q = new LinkedList<>();
        q.add(g.source);
        g.dist[g.source] = 0;
        while(!q.isEmpty()){
            int u = q.poll();
            for(int v:g.adj[u]){
                if(!visited[v]){
                    visited[v] = true;
                    g.dist[v] = g.dist[u]+1;
                    q.add(v);
                }
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
            Graph gg = new Graph("C:\\\\Users\\\\ASUS\\\\Desktop\\\\CSE 2-2\\\\Java Algo\\\\BFS\\\\input.txt");
            BFS(gg);
            gg.printDistance();

    }
    }

