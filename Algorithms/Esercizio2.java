package Algorithms;


import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Esercizio2 {
    private static int n; // size of the vertex array
    private static int[] nodes; // array of nodes
    private static int m; // number of edges
    private static ArrayList<Edge> edges; // array of edges
    private static int q; // number of pairs to check
    private static ArrayList<Edge> pairs; // connections to check
    private static int[] rank; // rank array
    private static int[] parent; // parent array

    // Edge class representing an edge from source to destination
    public static class Edge{
        private int x;
        private int y;
        public Edge(int x, int y){        
            this.x = x;
            this.y = y;    
        }
    }

    /*
     * readInput method takes two files as input, the first containing the number n
     * of nodes
     * and the number m of edges needed to compose the initial composition
     */

    public static void readInput(File input, File input2) throws NumberFormatException{
        Scanner scan;
        try {
            scan = new Scanner(new FileReader(input));
            n = scan.nextInt();
            if (n < 2) { // check n is not less than 2 (at least 2 nodes)
                throw new NumberFormatException("n è inferiore a 2");
            }
            nodes = new int[n];
            m = scan.nextInt();
            if (m < 1){ // check m is not less than 1 (at least one edge)
                throw new NumberFormatException("m è inferiore a 1");
            }
            edges = new ArrayList<Edge>(m); 
            while(scan.hasNext()){ 
                int x = scan.nextInt();
                int y = scan.nextInt();
                Edge edge = new Edge(x, y);
                edges.add(edge);
            }
            scan = new Scanner(new FileReader(input2)); 
            q = scan.nextInt(); 
            if (q < 1){ // check there is at least one pair
                throw new NumberFormatException("q è minore di 1");
            }
            pairs = new ArrayList<Edge>(q); // creation of the array of pairs to verify
            while (scan.hasNext()) {
                int x = scan.nextInt();
                int y = scan.nextInt();
                Edge edge = new Edge(x, y); // reuse of the edge object, for other purposes
                pairs.add(edge);
            }     
        scan.close();
    } catch (Exception e) { 
        System.err.println("Error. Please check the input file. Stack trace below.\n");
         e.printStackTrace();
         System.exit(0);
        }
    }

    public static void DJS(){ 
        for (int i = 0; i < n; i++) { // assign each node's parent to itself
            parent[i] = i;
        }
    }
    public static int find(int x){ // find the root node by traversing the parent array
        if (parent[x] != x) { // if the node is not its own root, call the find method
            parent[x] = find(parent[x]); // find method is a recursive method to find the root
        }
        return parent[x];
    }

    // union method is used to merge the two sets in question
    public static void union(int x, int y){
        int px = find(x); // find the root nodes
        int py = find(y);
        if (px == py) // the elements are already connected, no need for operations
            return;
        // check elements based on their ranks; if an element has a lower rank, it will
        // be placed under the tree of the higher-rank element
        if (rank[px] < rank[py]) 
            parent[px] = py;
        else if (rank[py] < rank[px])
            parent[py] = px;
        else {
            parent[px] = py; // case of equal ranks, add to one of the elements' tree and increment
            rank[py] = rank[py]++;
        }
    }
    /*
     * QuickUnion with path compression using ranks, as ranks can become too high,
     * we make the root of the shorter tree
     * a child of the taller one, all elements maintain information about their rank
     * the calculation of the cost in the first part is summarized when we call DJS,
     * i.e., cost O(n) number of vertices
     * then we call the union, a merge to create the initial structure O(1) best or
     * O(log n) worst x Number of Edges
     * finally, we check the presence of the pairs, the cost is O(log n) x Number of
     * pairs
     */
    
    public static void main(String[] args) {
        File input = new File("Algorithms/input2.txt");
        File input2 = new File("Algorithms/input2_part2.txt");
        readInput(input, input2); 
        rank = new int[n]; // creation of the rank array
        parent = new int[n]; // creation of the parent array
        DJS(); 
        // extracting each edge and building the "sets"
        for (Edge e : edges){ 
            union(e.x, e.y); // (O(1)) x (m)
        }
        // extracting the pairs to check and verifying if they are connected through find
        for (Edge e : pairs){
            if (find(e.x) == find(e.y)){ // O(log*(n)) x (q)
                System.out.println(e.x +  " " + e.y + " " + "C"); 
            } else {
                System.out.println(e.x +  " " + e.y + " " + "NC"); 
            }
        }
    }
}
