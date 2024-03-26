package Algorithms;


import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Vector;

public class Esercizio4 {

    private static int n; // number of rows
    private static int m; // number of columns
    private static Node[][] h; // node matrix
    private static double fixCost; // fixed cost of 50
    private static double heightCost; // height fixed cost
    public static int ind; // node index
    public static Vector<LinkedList<Edge>> adjList; // adjacency list of nodes
    public static Node nd; // temporary node
    public static Node ndUp; // temporary upper node
    public static Node ndDown; // temporary lower node
    public static Node ndLeft; // temporary left node
    public static Node ndRight; // temporary right node
    public static int counterEdge; // temporary edge counter
    public static int[] p; // array of parent nodes
    public static double[] d; // path weight
    public static int source; // source node 0

    // main method where it takes the file as input, starts the read file method,
    // creates adjlist for each node, starts the dikstra method
    public static void main(String[] args) {
        File input = new File("Algorithms/input4.txt");
        readInput(input);
        adjListCreation();
        dijkstraAlgo(0, adjList); // method takes the starting node and the adjlist list as input
        print();
    }

    // read input method

    public static void readInput(File input) throws NumberFormatException {
        Scanner scan;
        ind = 0;
        try {
            scan = new Scanner(new FileReader(input));
            fixCost = scan.nextDouble(); 
            heightCost = scan.nextDouble();
            n = scan.nextInt();
            m = scan.nextInt();
            h = new Node[n][m];
            for (int i = 0; i < n; i++) {
                for (int y = 0; y < m; y++) {
                    double weight = scan.nextDouble();
                    Node node = new Node(ind, i, y, weight);
                    h[i][y] = node;
                    ind++;
                }
            }
            scan.close();
        } catch (Exception e) {
            System.err.println("Error. Please check the input file. Stack trace below.\n");
            e.printStackTrace();
            System.exit(0);
        }
    }

    // method to create adjlist
    public static void adjListCreation() {
        counterEdge = 0; 
        adjList = new Vector<LinkedList<Edge>>(); // creation of a list of adjlist for each node
        for (int i = 0; i < (n * m); i++) {
            adjList.add(i, new LinkedList<Edge>());
        }

        // for each single node of the h matrix we assign the values to their adjlist
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                nd = h[i][j]; // node under consideration
                // creation of every possible edge to which the node can connect "using the
                // limits imposed by the matrix"
                if ((i - 1) >= 0) {
                    ndUp = h[i - 1][j];
                    adjList.get(nd.getIndex()).add(new Edge(nd.getIndex(), ndUp.getIndex(), edgeCalculation(nd, ndUp)));
                    counterEdge++;
                }
                if ((i + 1) < n) {
                    ndDown = h[i + 1][j];
                    adjList.get(nd.getIndex())
                            .add(new Edge(nd.getIndex(), ndDown.getIndex(), edgeCalculation(nd, ndDown)));
                    counterEdge++;
                }
                if ((j - 1) >= 0) {
                    ndLeft = h[i][j - 1];
                    adjList.get(nd.getIndex())
                            .add(new Edge(nd.getIndex(), ndLeft.getIndex(), edgeCalculation(nd, ndLeft)));
                    counterEdge++;
                }
                if ((j + 1) < m) {
                    ndRight = h[i][j + 1];
                    adjList.get(nd.getIndex())
                            .add(new Edge(nd.getIndex(), ndRight.getIndex(), edgeCalculation(nd, ndRight)));
                    counterEdge++;
                }
            }
        }
    }

    // method for calculating the weight of each edge considering two nodes
    private static double edgeCalculation(Node nodeWA, Node nodeWB) {
        double edgeCost = fixCost + (heightCost * Math.pow((nodeWA.weight - nodeWB.weight), 2));
        return edgeCost;
    }

    /*
     * Search data structure using the Dijkstra algorithm and a priority queue The
     * method calculates the minimum path necessary to reach the last vertex of the
     * node matrix previously created by taking int s, source point and the list of
     * adjlist as input
     */
    public static void dijkstraAlgo(int s, Vector<LinkedList<Edge>> adjList) {
        boolean[] visited = new boolean[n * m];

        // creation of the priority queue
        MH q = new MH(n * m);

        d = new double[n * m];
        p = new int[n * m];

        // initialization of all distances with the maximum available value, of all
        // negative nodes, and of all unvisited nodes
        Arrays.fill(d, Double.POSITIVE_INFINITY);
        Arrays.fill(p, -1);
        Arrays.fill(visited, false);

        // assign 0 distance from the origin to itself
        d[s] = 0.0;

        // insertion in the priority queue of the node and dist pair
        for (int v = 0; v < n * m; v++) {
            q.insert(v, d[v]);
        }
     
        while (!q.isEmpty()) {
            // extraction of the node and dist pair
            final int u = q.min();
            q.deleteMin();
            visited[u] = true; // assign the node as visited
            for (Edge e : adjList.get(u)) { // extraction of the adjlist of the current node
                final int v = e.dst; // node to reach
                if (!visited[v] && (d[u] + e.w < d[v])) { // check if the sum of the path is less than what is already present
                    d[v] = d[u] + e.w; // assign the new dst
                    q.changePrio(v, d[v]); // insert the new values in the priority queue (automated ordering)
                    p[v] = u; // assignment necessary for backtracking
                }
            }
        }
    }

    // print method necessary to print the traversed nodes
    public static void print() {
        int tmp = 0;
        for (int nd = 0; nd < n * m; nd++) {
            tmp = nd;
        }
        printPath(tmp);
        System.out.print(d[tmp] + 50); // print of the minimum distance (addition of 50 due to the value of the first
                                       // node not taken into account)
    }

    // printpath method which loops the parent array where it will do the minimum
    // path backtracking
    protected static void printPath(int nd) {
        if (nd == source) {
            System.out.println(0 + " " + 0); 
        } else if (p[nd] == -1){
            System.out.println("Irraggiungibile"); 
        }
        else {
            printPath(p[nd]); // chiamata ricorsiva 
            for (int i = 0; i < n; i++) { 
                for (int j = 0; j < m; j++) {
                    if (h[i][j].index == nd){
                        System.out.println(h[i][j].x + " " + h[i][j].y); // stampa delle cordinate del nodo in questione 
                    }
                }
            }
        }
    }
}

// classe edge

class Edge {
    int src; // nodo di partenza 
    int dst; // nodo di arrivo
    double w; // peso dell'arco 

    public Edge(int src, int dst, double w) {
        this.src = src;
        this.dst = dst;
        this.w = w;
    }

    public int getDst() {
        return dst;
    }

    public int getSrc() {
        return src;
    }

    @Override
    public String toString() {
        return "\n" + " " + src + " " + dst;
    }
}

// classe nodo 
class Node {
    int index; // indice del nodo
    int x; // posizione x del nodo 
    int y; // posizione y del nodo
    double weight; // peso del nodo 

    public Node(int index, int x, int y, double weight) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public int getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return " " + x + " " + y;
    }
}

// classe heap appunti dalle lezioni
class MH{
    heapElement heap[];

    int pos[];
    int size;
    int maxSize;

    private class heapElement {
        public final int data;
        public double priority;

        public heapElement(int data, double priority) {
            this.data = data;
            this.priority = priority;
        }
    }

    public MH(int maxSize) {
        this.heap = new heapElement[maxSize];
        this.maxSize = maxSize;
        this.size = 0;
        this.pos = new int[maxSize];
        Arrays.fill(this.pos, -1);
    }

    private boolean valid(int i) {
        return ((i >= 0) && (i < size));
    }

    private void swap(int i, int j) {
        assert (pos[heap[i].data] == i);
        assert (pos[heap[j].data] == j);

        heapElement elemTmp = heap[i];
        heap[i] = heap[j];
        heap[j] = elemTmp;
        pos[heap[i].data] = i;
        pos[heap[j].data] = j;
    }

    private int parent(int i) {
        assert (valid(i));
        return (i + 1) / 2 - 1;
    }

    private int lchild(int i) {
        assert (valid(i));
        return (i + 1) * 2 - 1;
    }

    private int rchild(int i) {
        assert (valid(i));
        return lchild(i) + 1;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public boolean isFull() {
        return (size > maxSize);
    }

    public int min() {
        assert (!isEmpty());
        return heap[0].data;
    }

    private int minChild(int i) {
        assert (valid(i));

        final int l = lchild(i);
        final int r = rchild(i);
        int result = -1;
        if (valid(l)) {
            result = l;
            if (valid(r) && (heap[r].priority < heap[l].priority)) {
                result = r;
            }
        }
        return result;
    }

    private void moveUp(int i) {
        assert (valid(i));

        int p = parent(i);
        while ((p >= 0) && (heap[i].priority < heap[p].priority)) {
            swap(i, p);
            i = p;
            p = parent(i);
        }
    }

    private void moveDown(int i) {
        assert (valid(i));

        boolean done = false;
        do {
            int dst = minChild(i);
            if (valid(dst) && (heap[dst].priority < heap[i].priority)) {
                swap(i, dst);
                i = dst;
            } else {
                done = true;
            }
        } while (!done);
    }

    public void insert(int data, double prio) {
        assert ((data >= 0) && (data < maxSize));
        assert (pos[data] == -1);
        assert (!isFull());

        final int i = size++;
        pos[data] = i;
        heap[i] = new heapElement(data, prio);
        moveUp(i);
    }

    public void deleteMin() {
        assert (!isEmpty());
        swap(0, size - 1);
        pos[heap[size - 1].data] = -1;
        size--;
        if (size > 0)
            moveDown(0);
    }

    public void changePrio(int data, double newprio) {
        int j = pos[data];
        assert (valid(j));
        final double oldprio = heap[j].priority;
        heap[j].priority = newprio;
        if (newprio > oldprio) {
            moveDown(j);
        } else {
            moveUp(j);
        }
    }
}