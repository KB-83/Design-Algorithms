
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.file.AccessDeniedException;
import java.util.*;

public class Q1P2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine().trim());

        for (int i = 0; i < t; i++) {
            StringTokenizer st = new StringTokenizer(reader.readLine());
            int n = Integer.parseInt(st.nextToken());
            if(n == 0){
                continue;
            }
            int k = Integer.parseInt(st.nextToken());

            int[][] beauty = new int[n+1][n+1];
            for (int j = 1; j < n+1; j++) {
                st = new StringTokenizer(reader.readLine());
                for (int q = j; q < n+1; q++) {
                    beauty[j][q] = Integer.parseInt(st.nextToken());
                }
            }

            long[][] dp = new long[n+1][k];
            ArrayList<Long> data = new ArrayList<>();
            dp[0][0] = 0;
            dp[1][0] = beauty[1][1];
            //check the size
//            int[] pointers = new int[n+1];
//            pointers[0] = 1;
//            pointers[1] = 1;

            for(int j = 2 ; j < n+1 ; j++) {
                for(int last = j ; last > 0 ; last --) {
                    for(int prv = last - 2 ; prv >= 0 ; prv --){
                        for(int r = 0 ; r < Math.min(k, Math.pow(2,prv-1)); r++){
                            data.add((long) beauty[last][j] + dp[prv][r]);
                        }
                    }
                }
                data.add((long) beauty[1][j]);
                Collections.sort(data, new Comparator<Long>() {
                    @Override
                    public int compare(Long o1, Long o2) {
                        return o2.compareTo(o1);
                    }
                });

                for (int q = 0; q < Math.min(k, data.size()); q++) {
                    dp[j][q] = data.get(q);
                }
//                pointers[j] = Math.min(k,data.size());
                data.clear();
            }
            int[] start = new int[n+1];
            long max = Long.MIN_VALUE;
            int maxIndex = 0;
            for (int num = 0 ; num < k ; num++) {
                for (int s = 0; s < n + 1; s++) {
                    if (Math.pow(2,s-1)> start[s] && dp[s][start[s]] > max ) {
                        max = dp[s][start[s]];
                        maxIndex = s;
                    }
                }
                start[maxIndex]++;
                System.out.print(max+" ");
                max = Long.MIN_VALUE;
            }
            System.out.println();
        }
    }
}
