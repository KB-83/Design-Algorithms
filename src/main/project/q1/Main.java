package q1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(reader.readLine().trim());

        try {
            for (int i = 0; i < t; i++) {
                StringTokenizer st = new StringTokenizer(reader.readLine());
                int n = Integer.parseInt(st.nextToken());
                int k = Integer.parseInt(st.nextToken());

                int[][] beauty = new int[n + 1][n + 1];
                for (int j = 1; j < n + 1; j++) {
                    st = new StringTokenizer(reader.readLine());
                    for (int q = j; q < n + 1; q++) {
                        beauty[j][q] = Integer.parseInt(st.nextToken());
                    }
                }

                ArrayList<ArrayList<Long>> whiteDP = new ArrayList<>();
                ArrayList<ArrayList<Long>> blackDP = new ArrayList<>();

                for (int s = 0; s < n + 1; s++) {
                    whiteDP.add(new ArrayList<>());
                    blackDP.add(new ArrayList<>());
                }
                blackDP.get(1).add((long) beauty[1][1]);
                whiteDP.get(1).add(0L);


                for (int j = 2; j < n + 1; j++) {
                    ArrayList<Long> wData = new ArrayList<>();
                    ArrayList<Long> bData = new ArrayList<>();

                    wData.addAll(blackDP.get(j - 1));
                    wData.addAll(whiteDP.get(j - 1));

                    bData.add((long) beauty[1][j]);
                    for (int s = j - 1; s >= 1; s--) {
                        for (Long l : whiteDP.get(s)) {
                            bData.add(l + beauty[s + 1][j]);
                        }
                    }

                    bData.sort(Collections.reverseOrder());
                    wData.sort(Collections.reverseOrder());

                    for (int q = 0; q < Math.min(k, wData.size()); q++) {
                        whiteDP.get(j).add(wData.get(q));
                    }

                    for (int q = 0; q < Math.min(k, bData.size()); q++) {
                        blackDP.get(j).add(bData.get(q));
                    }

                }


                int blackIndex = 0;
                int witheIndex = 0;
                for (int num = 0; num < k; num++) {
                    if (witheIndex < whiteDP.get(n).size() && blackIndex < blackDP.get(n).size()) {
                        if (whiteDP.get(n).get(witheIndex) >= blackDP.get(n).get(blackIndex)) {
                            System.out.print(whiteDP.get(n).get(witheIndex) + " ");
                            witheIndex++;
                        } else {
                            System.out.print(blackDP.get(n).get(blackIndex) + " ");
                            blackIndex++;
                        }
                    } else if (witheIndex < whiteDP.get(n).size()) {
                        System.out.print(whiteDP.get(n).get(witheIndex) + " ");
                        witheIndex++;
                    } else if (blackIndex < blackDP.get(n).size()) {
                        System.out.print(blackDP.get(n).get(blackIndex) + " ");
                        blackIndex++;
                    }
                }

                System.out.println();

            }
        }catch (IndexOutOfBoundsException e) {
            Thread.sleep(13000);
        }
    }
}
