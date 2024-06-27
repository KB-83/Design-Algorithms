package q3;

import java.util.*;

//accepted

public class TableQ3 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[][] table = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                table[i][j] = scanner.nextInt();
            }
        }
        List<HashMap<Integer,Integer>> graph = new ArrayList<>();
        for (int i = 0; i < n * n; i++) {
            graph.add(new HashMap<>());
        }
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                for (int k = j+1 ; k < n; k++) {
                    if(table[i][j] == table[i][k]) {
                        graph.get(i * n + j).put(i*n +k,i*n +k);
                        graph.get(i * n + k).put(i*n +j,i*n +j);
                    }
                }
                for (int k = i+1 ; k < n; k++) {
                    if(table[i][j] == table[k][j]) {
                        graph.get(i * n + j).put(k*n +j,k*n +j);
                        graph.get(k*n +j).put(i * n + j,i * n + j);
                    }
                }
            }
        }

        boolean[] visited = new boolean[n * n];
        List<List<Integer>> components = new ArrayList<>();

        for (int i = 0; i < n * n; i++) {
            if (!visited[i]) {
                List<Integer> component = new ArrayList<>();
                Queue<Integer> queue = new LinkedList<>();
                queue.add(i);
                visited[i] = true;

                while (!queue.isEmpty()) {
                    int node = queue.poll();
                    component.add(node);
                    for (int neighbor : graph.get(node).values()) {
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            queue.add(neighbor);
                        }
                    }
                }

                components.add(component);
            }
        }

        int totalChanges = 0;

        for (List<Integer> component : components) {
            if (component.size() > 1) {
                totalChanges += getMinimumChanges(graph, component);
            }
        }

        System.out.println(totalChanges);
    }

    private static int getMinimumChanges(List<HashMap<Integer,Integer>> graph, List<Integer> component) {
        int changes = 0;

        while (!component.isEmpty()) {
            int maxDegree = -1;
            int maxDegreeVertex = -1;
            boolean findNgh1 = false;
            for (int vertex : component) {
                int degree = graph.get(vertex).size();
                if(degree == 1) {
                    maxDegreeVertex = (int) graph.get(vertex).values().toArray()[0];
                    maxDegree = graph.get(maxDegreeVertex).size();;
                    break;
                }
                if (degree > maxDegree) {
                    maxDegree = degree;
                    maxDegreeVertex = vertex;
                }
            }

            if (maxDegreeVertex == -1 || maxDegree<= 0 ) {
                break;
            }


            for (int neighbor : graph.get(maxDegreeVertex).values()) {
                graph.get(neighbor).remove(maxDegreeVertex);

            }
            graph.get(maxDegreeVertex).clear();
            component.remove((Integer) maxDegreeVertex);
            changes++;
        }

        return changes;
    }}
