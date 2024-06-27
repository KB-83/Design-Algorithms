package q1;

import java.util.*;

public class Q1P implements Solution {

    @Override
    public List<String> solve(List<String> input) {
        List<String> result = new ArrayList<>();
        Iterator<String> iterator = input.iterator();
        int t = Integer.parseInt(iterator.next().trim());

        for (int i = 0; i < t; i++) {
            StringTokenizer st = new StringTokenizer(iterator.next());
            int n = Integer.parseInt(st.nextToken());
            if (n == 0) {
                continue;
            }
            int k = Integer.parseInt(st.nextToken());

            int[][] beauty = new int[n][n];
            for (int j = 0; j < n; j++) {
                st = new StringTokenizer(iterator.next());
                for (int q = j; q < n; q++) {
                    beauty[j][q] = Integer.parseInt(st.nextToken());
                }
            }

            int[][] dp = new int[n][k];
            ArrayList<Integer> data = new ArrayList<>();
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

            for (int j = 1; j < n; j++) {
                for (int g = j; g > 0; g--) {
                    for (int r = 0; r < Math.min(k, pointers[g - 1]); r++) {
                        if (g == j) {
                            data.add(dp[g - 1][r]);
                        } else {
                            data.add(dp[g - 1][r] + beauty[g + 1][j]);
                        }
                    }
                }

                data.add(beauty[0][j]);
                if (n >= 1) {
                    data.add(beauty[1][j]);
                }

                Collections.sort(data, Comparator.reverseOrder());

                for (int q = 0; q < Math.min(k, data.size()); q++) {
                    dp[j][q] = data.get(q);
                }
                pointers[j] = data.size();
                data.clear();
            }

            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < k; j++) {
                sb.append(dp[n - 1][j]).append(" ");
            }
            result.add(sb.toString().trim());
        }

        return result;
    }

//    public static void main(String[] args) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        List<String> input = new ArrayList<>();
//
//        String line;
//        while ((line = reader.readLine()) != null && !line.isEmpty()) {
//            input.add(line);
//        }
//
//        q1.Q1 solution = new q1.Q1();
//        List<String> output = solution.solve(input);
//
//        for (String lineOutput : output) {
//            System.out.println(lineOutput);
//        }
//    }
}
