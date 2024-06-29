import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Q1FinalOptimized {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        int t = Integer.parseInt(reader.readLine().trim());

        for (int testcase = 0; testcase < t; testcase++) {
            StringTokenizer st = new StringTokenizer(reader.readLine());
            int n = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            int[][] beauty = new int[n + 1][n + 1];
            for (int i = 1; i <= n; i++) {
                st = new StringTokenizer(reader.readLine());
                for (int j = i; j <= n; j++) {
                    beauty[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            List<Integer> beauties = new ArrayList<>();
            beauties.add(beauty[1][1]);

            for (int j = 2; j <= n; j++) {
                List<Integer> newBeauties = new ArrayList<>();
                newBeauties.add(beauty[1][j]);

                for (int s = j - 1; s >= 1; s--) {
                    for (int val : beauties) {
                        newBeauties.add(val + beauty[s + 1][j]);
                    }
                }

                Collections.sort(newBeauties, Collections.reverseOrder());
                if (newBeauties.size() > k) {
                    newBeauties = newBeauties.subList(0, k);
                }
                beauties = newBeauties;
            }

            for (int i = 0; i < k && i < beauties.size(); i++) {
                writer.write(beauties.get(i) + " ");
            }
            writer.newLine();
        }
        writer.flush();
    }
}
