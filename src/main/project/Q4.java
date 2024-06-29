import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Q4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0 ; i < t ; i++) {
            int n = scanner.nextInt();
            MyTwoSAT twoSat = new MyTwoSAT(n);
            scanner.nextLine();
            int[][] table = new int[3][n];
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < n; k++) {
                    table[j][k] = scanner.nextInt();
                }
                scanner.nextLine();
            }


            for (int col = 0; col < n; col++) {
                int[] variables = new int[3];
                boolean[] isTrue = new boolean[3];
                for (int row = 0; row < 3; row++) {
                    int value = table[row][col];
                    variables[row] = Math.abs(value) - 1;
                    isTrue[row] = value > 0;
                }
//                NotA -> b and ...
                twoSat.addClause(variables[0], isTrue[0], variables[1], isTrue[1]);
                twoSat.addClause(variables[1], isTrue[1], variables[2], isTrue[2]);
                twoSat.addClause(variables[2], isTrue[2], variables[0], isTrue[0]);
            }

            if (twoSat.solve()) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }

        }


    }
}
class MyTwoSAT{
    int n;
    ArrayList<Integer>[] graph;
    ArrayList<Integer>[] transposeGraph;
    boolean[] visited;
    ArrayList<Integer> postOrder;
    int[] component;
    boolean[] output;
    public MyTwoSAT(int n) {
        this.n = n;
        graph = new ArrayList[2 * n];
        transposeGraph = new ArrayList[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            graph[i] = new ArrayList<>();
            transposeGraph[i] = new ArrayList<>();
        }

    }
    void addClause(int a_i, boolean a_iB, int a_j, boolean a_jB) {
        int a = 2 * a_i + (a_iB ? 0 : 1);
        int notA = 2 * a_i + (a_iB ? 1 : 0);
        int b = 2 * a_j + (a_jB ? 0 : 1);
        int notB = 2 * a_j + (a_jB ? 1 : 0);

        graph[notA].add(b);
        graph[notB].add(a);
        transposeGraph[b].add(notA);
        transposeGraph[a].add(notB);
    }

    boolean solve() {
        findComponents();

//        Check if a_i and -a_i are in same component
        output = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (component[2 * i] == component[2 * i + 1]) {
                return false;
            }
            output[i] = component[2 * i] > component[2 * i + 1];
        }
        return true;
    }
    void findComponents(){
        visited = new boolean[2 * n];
        postOrder = new ArrayList<>();
        for (int i = 0; i < 2 * n; i++) {
            if (!visited[i]) {
                DFS1(i);
            }
        }
        component = new int[2 * n];
        for (int i = 0 ; i < component.length ; i++) {
            component[i] = -1;
        }
        int index = 0;
        for (int i = postOrder.size() - 1; i >= 0; i--) {
            int node = postOrder.get(i);
            if (component[node] == -1) {
                DFS2(node, index++);
            }
        }
    }
    private void DFS1(int node) {
        visited[node] = true;
        for (int neighbor : graph[node]) {
            if (!visited[neighbor]) {
                DFS1(neighbor);
            }
        }
        postOrder.add(node);
    }

    private void DFS2(int node, int componentIndex) {
        component[node] = componentIndex;
        for (int neighbor : transposeGraph[node]) {
            if (component[neighbor] == -1) {
                DFS2(neighbor, componentIndex);
            }
        }
    }
}