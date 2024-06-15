import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Alberta {


    public static void getInput(){}
    public static void printOutput(){}


}
class MaxFlowFinder{
    ModelGraph inputGraph;
    FlowGraph main;
    private static void findPath(){}
    private static void removePath(){}

}
class FlowGraph{
    ArrayList<DEdge> edges = new ArrayList<>();
    HashMap<Integer,Vertex> vertices = new HashMap<>();
    Vertex source;
    Vertex sink;
    ArrayList<Vertex> sVertices = new ArrayList<>();
    ArrayList<Vertex> pVertices = new ArrayList<>();
    FlowGraph pasmand;

    public FlowGraph(ModelGraph in) {
        createGraph(in);
        createPasmand();
    }
    private FlowGraph(){}

    public void createGraph(ModelGraph in){
        int[][] edges = in.edges;
        ArrayList<Vertex> vertices = in.vertices;
        this.sink = new Vertex(4,0);
        this.source = new Vertex(5,100);
        for (Vertex vertex:vertices) {
            this.vertices.put(vertex.num,vertex);
            if (vertex.type == 0){
                sVertices.add(vertex);
            }
            else if (vertex.type == 1){
                pVertices.add(vertex);
            }
        }
        for (int i = 0; i < edges.length ; i++) {
            for (int j = 0 ; j < edges[i].length; j++) {
                this.edges.add(new DEdge(1,this.vertices.get(i),this.vertices.get(j)));
                this.edges.add(new DEdge(1,this.vertices.get(j),this.vertices.get(i)));
            }
        }
        for (Vertex vertex:sVertices){
            this.edges.add(new DEdge(sVertices.size(),sink,vertex));
        }
        for (Vertex vertex:pVertices){
            this.edges.add(new DEdge(pVertices.size(),vertex,source));
        }

    }

    public void createPasmand(){
        this.pasmand = new FlowGraph();
        pasmand.vertices = this.vertices;
        pasmand.sink = this.sink;
        pasmand.source = this.source;
        pasmand.sVertices = this.sVertices;
        pasmand.pVertices = this.pVertices;
        for (DEdge dEdge:this.edges) {
            int mainNum = dEdge.capacity - dEdge.flow;
            int inverseNum = dEdge.flow;
            if(mainNum > 0) {
                pasmand.edges.add(new DEdge(mainNum,dEdge.start,dEdge.end));
            }
            if(inverseNum > 0) {
                pasmand.edges.add(new DEdge(inverseNum,dEdge.end,dEdge.start));
            }
        }
    }
    public  void updatePasmand(){}
    public static void maxFlow(){}

}
class ModelGraph{
//    undirected graph
    int[][] edges;
    ArrayList<Vertex> vertices;


}
class DEdge{
    int capacity;
    int flow = 0;
    Vertex start;
    Vertex end;

    public DEdge(int capacity, Vertex start, Vertex end) {
        this.capacity = capacity;
        this.start = start;
        this.end = end;
    }
}
class Vertex{
    int num;
    // 0 = s  1 = p  2 = t
    int type;

    public Vertex(int num,int type) {
        this.num = num;
        this.type = type;
    }
}

