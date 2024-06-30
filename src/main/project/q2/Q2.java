package q2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Q2 {

    static class Edge {
        int v1;
        int v2;
        int v1E;
        int v2E;
        int w;

        public Edge(int v1, int v2, int v1E, int v2E, int w) {
            this.v1 = v1;
            this.v2 = v2;
            this.v1E = v1E;
            this.v2E = v2E;
            this.w = w;
        }
    }


    static List<Edge> graph;
    static int[] energy;

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        energy = new int[n + 1];
        graph = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            energy[i] = scanner.nextInt();
        }

        for (int i = 0; i < n - 1; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int c = scanner.nextInt();
            graph.add(new Edge(a,b,energy[a],energy[b], c));
        }
//        try {
            System.out.println(dq(1,n));
//        }catch (ArrayIndexOutOfBoundsException e){
//            System.out.println(0);
//        }
//        catch (StackOverflowError e){
//            Thread.sleep(4000);
//        }

    }

    private static long dq(int start, int end) {
        int mid = (end - start) / 2;
        if (mid == end) {
            return 0;
        }
        if (end == start) {
            return 0;
        }
        if (end == start+1) {
            return 0;// Change it
        }
        if (end == start+2) {
            return 0;// Change it
        }

        long leftResult = dq(start, mid);
        long rightResult = dq(mid + 1, end);

        long maxEnergy = Math.max(leftResult, rightResult);



            for (Edge edge : graph) {
                if(edge.v1 > mid && edge.v2 <= mid) {
                    long leftBFS = bfs(edge.v2,1,mid);
                    long rightBFS = bfs(edge.v1,mid+1,end);
                    maxEnergy = Math.max(maxEnergy, leftBFS + rightBFS - edge.w);

                }
                else if(edge.v2 > mid && edge.v1 <= mid) {
                    long leftBFS = bfs(edge.v1,1,mid);
                    long rightBFS = bfs(edge.v2,mid+1,end);
                    maxEnergy = Math.max(maxEnergy, leftBFS + rightBFS - edge.w);
                }
            }
        return maxEnergy;
    }

    private static long bfs(int start,int min,int max) {
        // Implement the BFS function
        return 0;
    }
}
