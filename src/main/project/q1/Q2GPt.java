package q1;

import java.util.*;

public class Q2GPt {

    static class Edge {
        int to, cost;
        Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    private static int[] dp;
    private static boolean[] visited;
    private static List<List<Edge>> graph;

    public static int maxEnergy(int n, int[] energies, int[][] edges) {
        // Initialize graph
        graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // Add edges to the graph
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1], c = edge[2];
            graph.get(a).add(new Edge(b, c));
            graph.get(b).add(new Edge(a, c));
        }

        // Initialize DP array and visited array
        dp = new int[n + 1];
        visited = new boolean[n + 1];
        Arrays.fill(dp, Integer.MIN_VALUE);

        // Start DFS from an arbitrary node, let's choose node 1
        dfs(1, energies);

        // Find the maximum value in the dp array
        int maxEnergy = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            maxEnergy = Math.max(maxEnergy, dp[i]);
        }

        return maxEnergy;
    }

    private static void dfs(int node, int[] energies) {
        visited[node] = true;
        dp[node] = energies[node - 1];
        int maxGain = 0;

        for (Edge edge : graph.get(node)) {
            if (!visited[edge.to]) {
                dfs(edge.to, energies);
                dp[node] = Math.max(dp[node], energies[node - 1] + dp[edge.to] - edge.cost);
                maxGain = Math.max(maxGain, dp[edge.to] - edge.cost);
            }
        }

        dp[node] = Math.max(dp[node], energies[node - 1] + maxGain);
    }

    public static void main(String[] args) {
        // Example 1
        int n1 = 4;
        int[] energies1 = {4, 2, 3, 3};
        int[][] edges1 = {
                {1, 2, 1},
                {3, 4, 5},
                {3, 2, 3}
        };
        System.out.println(maxEnergy(n1, energies1, edges1));  // Output: 5

        // Example 2
        int n2 = 3;
        int[] energies2 = {6, 6, 1};
        int[][] edges2 = {
                {1, 3, 3},
                {2, 3, 3}
        };
        System.out.println(maxEnergy(n2, energies2, edges2));  // Output: 7
    }
}

