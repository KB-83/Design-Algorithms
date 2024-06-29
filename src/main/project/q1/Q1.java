import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.file.AccessDeniedException;
import java.util.*;

public class Q1 {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        scanner.nextLine();

        //test 2 has got n = 9

        ArrayList<ArrayList<Long>> out = new ArrayList<>();


        for (int i = 0; i < t; i++) {
            int n = scanner.nextInt();
//            if (n > 9) {
//                throw new RuntimeException();
//            }
            int k = scanner.nextInt();
            scanner.nextLine();
            ArrayList ans = new ArrayList<>();
            out.add(ans);

            long[][] beauty = new long[n + 1][n + 1];
            for (int j = 1; j < n + 1; j++) {
                for (int q = j; q < n + 1; q++) {
                    beauty[j][q] = scanner.nextInt();
//                    if (beauty[i][j] > 10000){
//                        throw new RuntimeException();
//                    }
                }
//                if(j == n-1) {
//                    scanner.nextLine();
//                }
            }

            long[][] dp = new long[n + 1][k];
            dp[0][0] = 0;
            dp[1][0] = beauty[1][1];
            for (int j = 2; j < n + 1; j++) {
                ArrayList<Long> data = new ArrayList<>();
                for (int p = j ; p >= 2; p--) {
                    for (int takoja = p - 2; takoja >= 0; takoja--) {
                        for (int s = 0; s < Math.max(Math.min(k, Math.pow(2, takoja-1)),1); s++) {
                            data.add(dp[takoja][s] + beauty[p][j]);
                        }
                    }
                }
                data.add(beauty[1][j]);
                Collections.sort(data, new Comparator<Long>() {
                    @Override
                    public int compare(Long o1, Long o2) {
                        return o2.compareTo(o1);
                    }
                });
                for (int q = 0; q < Math.min(k, data.size()); q++) {
                    dp[j][q] = data.get(q);
                }
            }
                int[] start = new int[n + 1];
                long max = Long.MIN_VALUE;
                int maxIndex = 0;
                for (int num = 0; num < k; num++) {
                    for (int s = 0; s < n + 1; s++) {
                        if (Math.pow(2, s - 1) > start[s] && dp[s][start[s]] > max) {
                            max = dp[s][start[s]];
                            maxIndex = s;
                        }
                    }
                    start[maxIndex]++;
                    ans.add(max);
//                    System.out.print(max + " ");
                    max = Long.MIN_VALUE;
                }
//                System.out.println();

            }

        for (int i = 0 ; i < out.size() ; i++) {
            for (int j = 0 ; j < out.get(i).size() ; j++) {
                System.out.print(out.get(i).get(j));
                if(j != out.get(i).size() -1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        }


}

