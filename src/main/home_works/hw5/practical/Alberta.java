import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Alberta {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] firstLine = reader.readLine().split(" ");
        int n = Integer.parseInt(firstLine[0]);
        int m = Integer.parseInt(firstLine[1]);
        int c = Integer.parseInt(firstLine[2]);

        String types = reader.readLine();

        int[][] edges = new int[m][2];
        for (int i = 0; i < m; i++) {
            String[] edgeLine = reader.readLine().split(" ");
            edges[i][0] = Integer.parseInt(edgeLine[0]);
            edges[i][1] = Integer.parseInt(edgeLine[1]);
        }

        ArrayList<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            char type = types.charAt(i);
            int vertexType = type == 'S' ? 0 : 1;
            if (type == 'C' ){
                vertexType = 2;
            }
            vertices.add(new Vertex(i + 1, vertexType));
        }

        ModelGraph modelGraph = new ModelGraph();
        modelGraph.edges = edges;
        modelGraph.vertices = vertices;
        FlowGraph graph = new FlowGraph(modelGraph);
        System.out.println(graph.maxFlow() * c);
    }
}

class MaxFlowFinder {
    public static List<DEdge> findPath(FlowGraph graph) {
        Map<Vertex, DEdge> parentMap = new HashMap<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(graph.source);
        parentMap.put(graph.source, null);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();

            if (current.equals(graph.sink)) {
                List<DEdge> path = new ArrayList<>();
                for (Vertex v = graph.sink; !v.equals(graph.source); v = parentMap.get(v).start) {
                    path.add(parentMap.get(v));
                }
                Collections.reverse(path);
                return path;
            }

            for (DEdge edge : graph.adjList.get(current)) {
                if (!parentMap.containsKey(edge.end) && edge.capacity > 0) {
                    parentMap.put(edge.end, edge);
                    queue.add(edge.end);
                }
            }
        }
        return Collections.emptyList();
    }
}

class FlowGraph {
    Map<Vertex, List<DEdge>> adjList = new HashMap<>();
    Map<Integer, Vertex> vertices = new HashMap<>();
    Vertex source;
    Vertex sink;
    int sVertices = 0;
    int pVertices = 0;

    public FlowGraph(ModelGraph in) {
        createGraph(in);
    }

    public void createGraph(ModelGraph in) {
        int[][] edges = in.edges;
        ArrayList<Vertex> verticess = in.vertices;
        this.source = new Vertex(0, 5);
        this.sink = new Vertex(verticess.size() + 1, 4);
        this.vertices.put(0, source);
        this.vertices.put(sink.num, sink);

        for (Vertex vertex : verticess) {
            this.vertices.put(vertex.num, vertex);
            if (vertex.type == 0) {
                sVertices++;
            } else if (vertex.type == 1) {
                pVertices++;
            }
        }

        for (Vertex vertex : vertices.values()) {
            adjList.put(vertex, new ArrayList<>());
        }

        for (int i = 0; i < edges.length; i++) {
            Vertex start = this.vertices.get(edges[i][0]);
            Vertex end = this.vertices.get(edges[i][1]);
            addEdge(start, end, 1);
            addEdge(end, start, 1);

            if (start.type == 0) {
                addEdge(source, start, pVertices + sVertices);
            }
            if (start.type == 1) {
                addEdge(start, sink, pVertices + sVertices);
            }
            if (end.type == 0) {
                addEdge(source, end, pVertices + sVertices);
            }
            if (end.type == 1) {
                addEdge(end, sink, pVertices + sVertices);
            }
        }
    }

    private void addEdge(Vertex start, Vertex end, int capacity) {
        DEdge edge = new DEdge(capacity, start, end);
        adjList.get(start).add(edge);
    }

    public void update(List<DEdge> path) {
        for (DEdge edge : path) {
            edge.capacity--;
            if (edge.capacity == 0) {
                adjList.get(edge.start).remove(edge);
            }
        }
    }

    public int maxFlow() {
        int maxFlow = 0;
        while (true) {
            List<DEdge> path = MaxFlowFinder.findPath(this);
            if (path.isEmpty()) {
                return maxFlow;
            }
            maxFlow++;
            update(path);
        }
    }
}

class ModelGraph {
    int[][] edges;
    ArrayList<Vertex> vertices;
}

class DEdge {
    int capacity;
    Vertex start;
    Vertex end;

    public DEdge(int capacity, Vertex start, Vertex end) {
        this.capacity = capacity;
        this.start = start;
        this.end = end;
    }
}

class Vertex {
    int num;
    int type;

    public Vertex(int num, int type) {
        this.num = num;
        this.type = type;
    }
}