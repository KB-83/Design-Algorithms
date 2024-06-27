package q3;

import java.util.*;

public class Q3P3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[][] table = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                table[i][j] = scanner.nextInt();
            }
        }

        // Step 1: Construct the bipartite graph
        List<Set<Integer>> graph = new ArrayList<>();
        for (int i = 0; i < 2 * n * n; i++) {
            graph.add(new HashSet<>());
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (table[i][j] == table[i][k]) {
                        graph.get(i * n + j).add(i * n + k + n * n);
                        graph.get(i * n + k + n * n).add(i * n + j);
                    }
                }
                for (int k = i + 1; k < n; k++) {
                    if (table[i][j] == table[k][j]) {
                        graph.get(i * n + j).add(k * n + j + n * n);
                        graph.get(k * n + j + n * n).add(i * n + j);
                    }
                }
            }
        }

        // Step 2: Find the maximum matching in the bipartite graph
        int[] match = new int[2 * n * n];
        Arrays.fill(match, -1);
        boolean[] visited = new boolean[2 * n * n];

        int maxMatching = 0;
        for (int i = 0; i < n * n; i++) {
            Arrays.fill(visited, false);
            if (dfs(i, graph, match, visited)) {
                maxMatching++;
            }
        }

        // Step 3: Derive the minimum vertex cover from the maximum matching
        System.out.println(maxMatching);
    }

    private static boolean dfs(int u, List<Set<Integer>> graph, int[] match, boolean[] visited) {
        for (int v : graph.get(u)) {
            if (!visited[v]) {
                visited[v] = true;
                if (match[v] == -1 || dfs(match[v], graph, match, visited)) {
                    match[v] = u;
                    match[u] = v;
                    return true;
                }
            }
        }
        return false;
    }
}
