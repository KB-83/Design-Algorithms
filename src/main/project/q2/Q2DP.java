package q2;
import java.util.*;
import java.io.*;

public class Q2DP {

    static Node[] nodes;
    static long maxEnergy = Long.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        nodes = new Node[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            long energy = Long.parseLong(st.nextToken());
            nodes[i] = new Node(i, energy);
        }

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            long c = Long.parseLong(st.nextToken());

            nodes[a].edges.add(new Edge(nodes[b], c));
            nodes[b].edges.add(new Edge(nodes[a], c));
        }

        dfs(nodes[0], null);

        System.out.println(maxEnergy);
    }

    private static long dfs(Node current, Node parent) {
        long maxSinglePath = current.energy;
        long maxPath1 = 0, maxPath2 = 0;

        for (Edge edge : current.edges) {
            if (edge.to != parent) {
                long childPath = dfs(edge.to, current) - edge.cost;
                if (childPath > maxPath1) {
                    maxPath2 = maxPath1;
                    maxPath1 = childPath;
                } else if (childPath > maxPath2) {
                    maxPath2 = childPath;
                }
            }
        }

        maxEnergy = Math.max(maxEnergy, maxPath1 + maxPath2 + current.energy);

        return maxSinglePath + maxPath1;
    }
}
class Node {
    int num;
    long energy;
    List<Edge> edges;

    Node(int num, long energy) {
        this.num = num;
        this.energy = energy;
        this.edges = new ArrayList<>();
    }
}

class Edge {
    Node to;
    long cost;

    Edge(Node to, long cost) {
        this.to = to;
        this.cost = cost;
    }
}