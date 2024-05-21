import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Dijekstra {
    static long INFINITY = -1;
    static long mod = (long)(1e9+7);
    Graph g;
    long[] dist;
    long[] path;
    int n;

    private long extendedDijekstra(Node s) {
        for (int i = 0 ; i < n;i++) {
            dist[i] = INFINITY;
        }
        path[s.nodeNumber] = 1;
        dist[s.nodeNumber] = 0;
        s.dist = 0;
        PriorityQueue<Node> unvisitedHeap = new PriorityQueue<>(Comparator.comparingLong(node -> node.dist));
        unvisitedHeap.add(s);

        while (!unvisitedHeap.isEmpty()){
            Node target = unvisitedHeap.poll();
            for (Node ngh : target.neighbors) {
                long newPath = dist[target.nodeNumber] + g.weightFunction[target.nodeNumber][ngh.nodeNumber];
                    if (dist[ngh.nodeNumber] >
                            newPath || dist[ngh.nodeNumber] == INFINITY) {
                        dist[ngh.nodeNumber] = newPath;
                        unvisitedHeap.remove(ngh);
                        ngh.dist = newPath;
                        path[ngh.nodeNumber] = (path[target.nodeNumber])%mod ;
                        unvisitedHeap.add(ngh);

                    } else if (dist[ngh.nodeNumber] == newPath) {
                        path[ngh.nodeNumber] = (path[ngh.nodeNumber] % mod + path[target.nodeNumber] % mod) % mod;
                    }
                }
        }
        return path[n-1];
    }

    public void getInput() throws IOException {
        int m;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] line = reader.readLine().split(" ");
        n = Integer.valueOf(line[0]);
        path = new long[n];
        dist = new long[n];
        m = Integer.valueOf(line[1]);
        g = new Graph(n);
        for (int i = 0 ; i < m ; i++) {
            String[] line1 = reader.readLine().split(" ");
            int u = Integer.valueOf(line1[0]);
            int v = Integer.valueOf(line1[1]);
            long w = Long.valueOf(line1[2]);

            g.nodes.get(u).neighbors.add(g.nodes.get(v));
            g.nodes.get(v).neighbors.add(g.nodes.get(u));
            g.weightFunction[u][v] = w;
            g.weightFunction[v][u] = w;

        }

    }
    public static void main(String[] args) throws IOException {
        Dijekstra dijekstra = new Dijekstra();
        dijekstra.getInput();
        System.out.println(dijekstra.extendedDijekstra(dijekstra.g.nodes.get(0)));
    }


}
class Graph{
    int n;
    long[][] weightFunction;
    HashMap <Integer,Node> nodes = new HashMap<>();

    public Graph(int n) {
        this.n = n;
        weightFunction = new long[n][n];
        for (int i = 0;i < n; i++) {
            nodes.put(i,new Node(i));
        }
    }
}
class Node{
    int nodeNumber;
    long dist = Dijekstra.INFINITY;
    ArrayList<Node> neighbors = new ArrayList<>();

    public Node(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }
}