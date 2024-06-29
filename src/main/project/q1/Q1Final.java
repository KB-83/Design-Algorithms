import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

public class Q1Final {

    public static void main(String[] args) throws IOException, InterruptedException {
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

            int[][] whiteDP = new int[n+1][k];
            int[] blackDP = new int[k];
            blackDP[0] = beauty[1][1];
            whiteDP[1][0] = 0;

            int[] pre = new int[n+1];
            pre[0] = 1;
            boolean reach = false;
            for (int i = 1 ; i < n+1 ; i++) {
                if (!reach) {
                    pre[i] = Math.min(k, pre[i - 1] * 2);
                    if (pre[i] == k) {
                        reach = true;
                    }
                }
                else {
                    pre[i] = k;
                }


            }

            MyHeap wData = new MyHeap(k);
            wData.addToHeap(Integer.MIN_VALUE);
            for (int j = 2; j < n + 1; j++) {

                MyHeap bData = new MyHeap(k);
                int till = pre[j-2];
                int min = (int) wData.peek();
                for (int i = till - 1 ; i >= 0 ; i-- ) {
                    if (whiteDP[j-1][i] > min || wData.size() < k) {
                        wData.addToHeap(whiteDP[j - 1][i]);
                        min = (int) wData.peek();
                    }
                    else {
                        break;
                    }
                }
                for (int i = till - 1 ; i >= 0 ; i-- ) {
                    if (blackDP[i] > min || wData.size() < k) {
                        wData.addToHeap(blackDP[i]);
                        min = (int) wData.peek();
                    }
                    else {
                        break;
                    }
                }

                bData.addToHeap(beauty[1][j]);
                a : for (int s = j - 1; s >= 1; s--) {
                    min = (int) bData.peek();
                    till = pre[s-1];
                    for (int z = 0 ; z < till ; z++) {
                        if (whiteDP[s][z] + beauty[s + 1][j] > min || bData.size() < k) {
                            bData.addToHeap(whiteDP[s][z] + beauty[s + 1][j]);
                            min = (int)bData.peek();
                        }
                        else {
                            continue a;
                        }
                    }
                }

                List<Integer> wDataList = new ArrayList<>(wData);
                Collections.sort(wDataList);
                till = Math.min(k, wDataList.size());
                for (int q = till - 1; q >= 0; q--) {
                    whiteDP[j][q] = wDataList.get(q);
                }
                till = Math.min(k, bData.size()) - 1;
                for (int q = till; q>= 0; q--) {
                    blackDP[q] = (int)bData.poll();
                }

            }

            int bTill = Math.min(pre[n-1],blackDP.length);
            int wTill = Math.min(pre[n-1],whiteDP[n].length);
            int w = 0 ;
            int b = 0 ;
            for (int num = 0 ; num < k ; num++) {

                if (w < wTill && b < bTill) {
                    if (whiteDP[n][w] >= blackDP[b]) {
                        writer.write(whiteDP[n][w] + " ");
                        w++;
                    } else {
                        writer.write(blackDP[b] + " ");
                        b++;
                    }
                } else if (w < wTill) {
                    writer.write(whiteDP[n][w] + " ");
                    w++;
                } else if (b < bTill) {
                    writer.write(blackDP[b] + " ");
                    b++;
                }

            }

            writer.newLine();
        }
        writer.flush();
    }
}

class MyHeap extends PriorityQueue {
    int k ;

    public MyHeap(int initialCapacity) {
        super(initialCapacity);
        this.k = initialCapacity;
    }
    void addToHeap(int value) {
        if (size() < k) {
            offer(value);
        } else if (value > (int)peek()) {
            poll();
            offer(value);
        }
    }
}
