package AlgoritmiDicembre;

import java.util.Random;

public class Launcher {
    private static Random random = new Random();

    public static void main(String[] args) {
        int k = random.nextInt(13)+15;
        test(k);
    }
    public static void test(int k){

        int TSI = random.nextInt(5)+1;
        int TSE = TSI + random.nextInt(4)+1;
        int i = 1;

        Tripla tripla = new Tripla(TSI,TSE,i);
        MinHeap minHeap = new MinHeap(k);
        minHeap.insert(tripla);
        System.out.println(tripla);

        for(int j = 2; j < k+1; j++){
            TSI = TSI + random.nextInt(7)+4;
            TSE = TSI + random.nextInt(6)+2;

            tripla = new Tripla(TSI,TSE,j);
            minHeap.insert(tripla);
            System.out.println(tripla);
        }

        System.out.println("\n\n");
        System.out.println(minHeap);
        System.out.println("\n\n");

        int bound = k*2;
        for(int z = 0; z < bound; z++){
            tripla = minHeap.peek(); //O(1)
            minHeap.pop();           //O(logn)

            TSI = TSI + random.nextInt(7)+4;
            TSE = TSI + random.nextInt(6)+2;
            Tripla tripla2 = new Tripla(TSI,TSE,k+1);
            k++;
            minHeap.insert(tripla2);  //O(logn)
        }

        System.out.println(minHeap);
        minHeap.printTriple();
    }
}
