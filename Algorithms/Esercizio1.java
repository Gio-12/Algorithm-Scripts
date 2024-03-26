package Algorithms;


import java.util.NoSuchElementException;
import java.util.Random;

public class Esercizio1 {   
    private static Random random = new Random();
    private static int TSI; 
    private static int TSE; 
    private static Tripla triplaTmp;
    private static int tsiTmp; // temporary value of tsi;
    private static int bound;
    public static void main(String[] args) {
        long seed = 938306; 
        random.setSeed(seed);
        int k = random.nextInt(27) + 16; // generate the required value 15 < k < 27
        MinHeap minHeap = new MinHeap(k); 
        bound = 2; // initial bound to avoid overhead problems
        for (int i = 1; i <= k; i++){ 
            if (i == 1){
                do { // do-while loop to ensure conditions are met
                    TSI = random.nextInt(bound) + 0; // generate tsi > 0 using lower bound 0
                    TSE = random.nextInt(bound) + 0; // generate tse > 0 using lower bound 0
                    if ((TSE > TSI)){ 
                        triplaTmp = new Tripla(TSI, TSE, i); 
                        tsiTmp = triplaTmp.getTSI(); // assign the value tsi(i-1), needed later for checking further conditions
                        minHeap.insert(triplaTmp); // O(logN) insert
                        break;
                    }        
                } while (true); 
                bound *= 1.5;  // increase limit           
        } else {
            do {
                TSI = random.nextInt(bound) + 0; 
                TSE = random.nextInt(bound) + 0;
                if ((TSE > TSI) && (TSI > tsiTmp)) { // check tse tsi conditions
                    triplaTmp = new Tripla(TSI, TSE, i);
                    tsiTmp = triplaTmp.getTSI();
                    minHeap.insert(triplaTmp);
                    break;
                }
            } while (true);
            bound *= 1.5;
        }
    }
    minHeap.printTriple();

    /*
     * The requirements are met through consistency checks. The approach uses a
     * minheap where generated elements are inserted.
     * These elements are given a priority based on the comparison tse - tsi, and
     * all conditions are satisfied due to the properties
     * of the minheap; the triplet with the lowest priority is at the base of the
     * heap.
     */

    System.out.println("\n Separazione \n");
    int test = k * 2; 
    bound = 2; // reset the bound
    for (int i = 1; i <= test; i++) {
        triplaTmp = minHeap.peek(); // O(1) peek cost
        minHeap.pop(); // O(logn) pop cost
        if(i == 1){
            do {
                TSI = random.nextInt(bound) + 0;
                TSE = random.nextInt(bound) + 0;
                if (TSI < TSE){
                    triplaTmp = new Tripla(TSI, TSE, k + 1);
                    tsiTmp = triplaTmp.getTSI();
                    k++;
                    minHeap.insert(triplaTmp);
                    bound *= 1.5;
                    System.out.println(triplaTmp);
                    break;
                }
            } while (true);
        } else {           
            TSI = tsiTmp + random.nextInt(11) + 4;
            TSE = TSI + random.nextInt(8) + 2;
            triplaTmp = new Tripla(TSI, TSE, k + 1);
            k++;
            tsiTmp = triplaTmp.getTSI();
            minHeap.insert(triplaTmp);
            System.out.println(triplaTmp);
        }  
    }
    System.out.println("\n Separazione \n");
    minHeap.printTriple();
}
}

// Triplet class

class Tripla implements Comparable<Tripla> {
    private int priority; 
    private int TSI;
    private int TSE;
    private int i;

    public Tripla(int TSI, int TSE, int i) {
        this.TSI = TSI;
        this.TSE = TSE;
        this.i = i;
        this.priority = TSE - TSI; // calculate priority level based on tse - tsi
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getTSI() {
        return TSI;
    }

    public void setTSI(int TSI) {
            this.TSI = TSI;
    }

    public int getTSE() {
        return TSE;
    }

    public void setTSE(int TSE) {
            this.TSE = TSE;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    //
    @Override
    public int compareTo(Tripla o) { // compare method to check priority between two elements
        Tripla b = null;
        if (o instanceof Tripla) {
            b = (Tripla) o;
        }
        if (this.priority == b.priority) {
            return this.i > b.i ? 1 : -1;
        } else if (this.priority < b.priority) {
            return -1;
        } else {
            return 1;
        }
    }

    public String toString() {
        return "{P: " + priority + ", i: " + i + ", TSI: " + TSI + ", TSE: " + TSE + "}";
    }
}
// Minheap structure 

class MinHeap {
    private int capacity;
    private int size;
    private Tripla[] array;

    public MinHeap(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        array = new Tripla[capacity];
    }

    public Tripla pop() {
        Tripla value = array[0];
        array[0] = array[--size];
        restoreTopToBottom();
        return value;
    }

    public Tripla peek() {
        if (!isEmpty()) {
            return array[0];
        } else {
            throw new NoSuchElementException();
        }
    }

    public void insert(Tripla element) {
        if (size < capacity) {
            array[size++] = element;
            restoreBottomUp();
        }
    }

    private void restoreTopToBottom() {
        int index = 0;
        while (hasLeftChild(index)) {
            int child = getIndexLeftChild(index);
            if (hasRightChild(index) && getRightChild(index).compareTo(array[child]) < 0) {
                child = getIndexRightChild(index);
            }
            if (array[index].compareTo(array[child]) > 0) {
                swap(index, child);
            } else {
                break;
            }
            index = child;
        }
    }

    private void restoreBottomUp() {
        int index = size - 1;
        while (hasParent(index) && getParent(index).compareTo(array[index]) > 0) {
            swap(index, getIndexParent(index));
            index = getIndexParent(index);
        }
    }

    private void swap(int index, int indexParent) {
        Tripla tmp = array[index];
        array[index] = array[indexParent];
        array[indexParent] = tmp;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    private int getIndexLeftChild(int pos) {
        return pos * 2 + 1;
    }

    private int getIndexRightChild(int pos) {
        return pos * 2 + 2;
    }

    private int getIndexParent(int pos) {
        return (pos - 1) / 2;
    }

    private boolean isLeaf(int pos) {
        if (getIndexLeftChild(pos) >= size || getIndexRightChild(pos) >= size) {
            return true;
        }
        return false;
    }

    private boolean hasRightChild(int pos) {
        return getIndexRightChild(pos) < size;
    }

    private boolean hasLeftChild(int pos) {
        return getIndexLeftChild(pos) < size;
    }

    private boolean hasParent(int pos) {
        return getIndexParent(pos) >= 0;
    }

    private Tripla getLeftChild(int pos) {
        return array[getIndexLeftChild(pos)];
    }

    private Tripla getRightChild(int pos) {
        return array[getIndexRightChild(pos)];
    }

    private Tripla getParent(int pos) {
        return array[getIndexParent(pos)];
    }

    public void printTriple() {
        for (int i = 0; i < size; i++) {
            System.out.println(array[i]);
        }
    }
}

