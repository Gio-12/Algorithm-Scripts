package Algorithms;

import java.io.*;
import java.util.*;

public class Esercizio3 {

    private static int n; // size of the coins array
    private static int[] Coins; // array of coins
    private static int Sum; // sum of coins present in the array

    /*
     * The readInput method reads the input file taking the values n = number of
     * coins
     * and then calculates the sum of the coins in the array
     */

    public static void readInput(File input) {
        Scanner scan;
        try {
            scan = new Scanner(new FileReader(input));
            n = scan.nextInt();
            Coins = new int[n];

            // legge il valore di ogni moneta nell'array e aggiunge alla somma totale
            for (int i = 0; i < n; i++) {
                Coins[i] = scan.nextInt();
                Sum += Coins[i];
            }
            scan.close();
        } catch (Exception e) {
            System.err.println("Error. Please check the input file. Stack trace below.\n");
            e.printStackTrace();
            System.exit(0);
        }
    }

    /*
     * The minNoChangeCoin method uses dynamic programming taking an array of coins
     * and the total sum as input
     * to return the minimum amount of change not dispensable, it uses a matrix
     * where n are respectively the coins
     * available to us and m corresponds to the sum of available coins + 1. which
     * acts as a limit for the non-dispensable change
     * we consider two different base cases j = 0 and i = 0, respectively change 0
     * to be dispensed or a single coin needed to dispense it
     */

    public static int minNoChangeCoin(int[] coins, int sum) {
        int minNoChange = sum + 1;

        // solution matrix
        int[][] Matrix = new int[n][sum + 1];

        // base case j = 0, where we dispense change 0
        for (int i = 0; i < n; i++) {
            Matrix[i][0] = 0;
        }

        // base case i = 0, in case we have a single coin
        for (int j = 0; j < sum + 1; j++) {
            if (coins[0] == j) {
                Matrix[0][j] = 1;
            } else {
                Matrix[0][j] = 0;
            }
        }
        // general case
        /*
         * for values equal to 1 the change is dispensable, otherwise it will not be
         * possible to dispense it
         */
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < sum + 1; j++) {
                if (coins[i] == j) {
                    Matrix[i][j] = 1;
                } else if (coins[i] != j && Matrix[i - 1][j] == 1) {
                    Matrix[i][j] = Matrix[i - 1][j];
                } else if (Matrix[i - 1][j] == 0) {
                    if (coins[i] > j) {
                        Matrix[i][j] = 0;
                    } else if (Matrix[i - 1][j - coins[i]] == 1) {
                        Matrix[i][j] = 1;
                    } else {
                        Matrix[i][j] = 0;
                    }
                }
            }
        }
        
        // By scanning the row, we look for the first index for which j is M[n-1][j] =
        // 0, the value will respect the impossibility of composing the change with the
        // available coins
        for (int j = 1; j < sum + 1; j++) {
            if (Matrix[n - 1][j] == 0) {
                minNoChange = j;
                break;
            }
        }
        return minNoChange;
    }

    // main method that takes the text file as input, starts the readInput method, and then assigns the minimum change
    public static void main(String[] args) {
        File inputFile = new File("Algorithms/input3.txt");
        readInput(inputFile);
        int minNoChange = minNoChangeCoin(Coins, Sum);
        System.out.println(minNoChange); 
    }
}
