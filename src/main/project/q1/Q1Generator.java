package q1;

import java.util.*;
import java.io.*;

public class Q1Generator {
    public static void main(String[] args) {
        Random random = new Random();
        int t = 1;  // Number of test cases to generate

        try (PrintWriter writer = new PrintWriter(new File("testcases.txt"))) {
            writer.println(t);

            for (int test = 0; test < t; test++) {
                int n = 9;  // n between 1 and 10 for manageable test cases
//                int n = 22;
//                int k = Math.min(1 << n, 5000);  // k is the minimum of 2^n and 5000
                int k = 1<<n;

                writer.println(n + " " + k);

                int[][] a = new int[n][];
                for (int i = 0; i < n; i++) {
                    a[i] = new int[n - i];
                    for (int j = 0; j < n - i; j++) {
                        a[i][j] = 1000000;  // Random values between -10^6 and 10^6
                    }
                }

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n - i; j++) {
                        writer.print(a[i][j] + " ");
                    }
                    writer.println();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

