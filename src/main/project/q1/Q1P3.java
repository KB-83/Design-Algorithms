import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Q1P3 {
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine().trim());

        for (int i = 0; i < t; i++) {
            StringTokenizer st = new StringTokenizer(reader.readLine());
            int n = Integer.parseInt(st.nextToken());
            if (n == 0) {
                continue;
            }
            int k = Integer.parseInt(st.nextToken());

            int[][] beauty = new int[n][n];
            for (int j = 0; j < n; j++) {
                st = new StringTokenizer(reader.readLine());
                for (int q = j; q < n; q++) {
                    beauty[j][q] = Integer.parseInt(st.nextToken());
                }
            }

            long[][] dp = new long[n][k];
            dp[0][0] = beauty[0][0];
            int[] pointers = new int[n];
            pointers[0] = 1;

            if (k > 1) {
                dp[0][1] = 0;
                pointers[0] = 2;
                if (dp[0][0] < dp[0][1]) {
                    dp[0][1] = dp[0][0];
                    dp[0][0] = 0;
                }
            }
            PriorityQueue<Long> minHeap = new PriorityQueue<>(k);
            for (int j = 1; j < n; j++) {
                for (int g = j; g > 0; g--) {
                    for (int r = 0; r < Math.min(k, pointers[g - 1]); r++) {
                        long value;
                        if (g == j) {
                            value = dp[g - 1][r];
                        } else {
                            value = dp[g - 1][r] + beauty[g + 1][j];
                        }
                        addToHeap(minHeap, value, k);
                    }
                }

                addToHeap(minHeap, beauty[0][j], k);
                addToHeap(minHeap, beauty[1][j], k);

                int index = 0;
                while (!minHeap.isEmpty() && index < k) {
                    dp[j][index++] = minHeap.poll();
                }
                pointers[j] = index;
            }

            for (int j = k-1; j >= 0; j--) {
                System.out.print(dp[n - 1][j] + " ");
            }
            System.out.println();
        }
    }

    private static void addToHeap(PriorityQueue<Long> minHeap, long value, int k) {
        if (minHeap.size() < k) {
            minHeap.offer(value);
        } else if (value > minHeap.peek()) {
            minHeap.poll();
            minHeap.offer(value);
        }
    }
}



