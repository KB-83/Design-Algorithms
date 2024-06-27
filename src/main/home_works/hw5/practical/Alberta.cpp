#include <iostream>
#include <vector>
#include <queue>
#include <unordered_map>
#include <algorithm>

using namespace std;

class Vertex {
public:
    int num;
    int type;
    Vertex(int num, int type) : num(num), type(type) {}
};

class DEdge {
public:
    int capacity;
    Vertex* start;
    Vertex* end;
    DEdge(int capacity, Vertex* start, Vertex* end) : capacity(capacity), start(start), end(end) {}
};

class FlowGraph;

class MaxFlowFinder {
public:
    static vector<DEdge*> findPath(FlowGraph* graph);
};

class ModelGraph {
public:
    vector<vector<int> > edges;
    vector<Vertex*> vertices;
};

class FlowGraph {
public:
    unordered_map<Vertex*, vector<DEdge*> > adjList;
    unordered_map<int, Vertex*> vertices;
    Vertex* source;
    Vertex* sink;
    int sVertices = 0;
    int pVertices = 0;

    FlowGraph(ModelGraph& in) {
        createGraph(in);
    }

    void createGraph(ModelGraph& in) {
        auto& edges = in.edges;
        auto& verticess = in.vertices;
        source = new Vertex(0, 5);
        sink = new Vertex(verticess.size() + 1, 4);
        vertices[0] = source;
        vertices[sink->num] = sink;

        for (auto vertex : verticess) {
            vertices[vertex->num] = vertex;
            if (vertex->type == 0) {
                sVertices++;
            } else if (vertex->type == 1) {
                pVertices++;
            }
        }

        for (auto vertex : vertices) {
            adjList[vertex.second] = vector<DEdge*>();
        }

        for (size_t i = 0; i < edges.size(); i++) {
            Vertex* start = vertices[edges[i][0]];
            Vertex* end = vertices[edges[i][1]];
            addEdge(start, end, 1);
            addEdge(end, start, 1);

            if (start->type == 0) {
                addEdge(source, start, pVertices + sVertices);
            }
            if (start->type == 1) {
                addEdge(start, sink, pVertices + sVertices);
            }
            if (end->type == 0) {
                addEdge(source, end, pVertices + sVertices);
            }
            if (end->type == 1) {
                addEdge(end, sink, pVertices + sVertices);
            }
        }
    }

    void addEdge(Vertex* start, Vertex* end, int capacity) {
        DEdge* edge = new DEdge(capacity, start, end);
        adjList[start].push_back(edge);
    }

    void update(vector<DEdge*>& path) {
        for (auto edge : path) {
            edge->capacity--;
            if (edge->capacity == 0) {
                adjList[edge->start].erase(remove(adjList[edge->start].begin(), adjList[edge->start].end(), edge), adjList[edge->start].end());
            }
        }
    }

    int maxFlow() {
        int maxFlow = 0;
        while (true) {
            vector<DEdge*> path = MaxFlowFinder::findPath(this);
            if (path.empty()) {
                return maxFlow;
            }
            maxFlow++;
            update(path);
        }
    }
};

vector<DEdge*> MaxFlowFinder::findPath(FlowGraph* graph) {
    queue<Vertex*> queue;
    unordered_map<Vertex*, DEdge*> parentMap;
    queue.push(graph->source);
    parentMap[graph->source] = nullptr;

    while (!queue.empty()) {
        Vertex* current = queue.front();
        queue.pop();

        if (current == graph->sink) {
            vector<DEdge*> path;
            for (Vertex* v = graph->sink; v != graph->source; v = parentMap[v]->start) {
                path.push_back(parentMap[v]);
            }
            reverse(path.begin(), path.end());
            return path;
        }

        auto& edges = graph->adjList[current];
        for (auto edge : edges) {
            if (parentMap.find(edge->end) == parentMap.end() && edge->capacity > 0) {
                parentMap[edge->end] = edge;
                queue.push(edge->end);
            }
        }
    }
    return vector<DEdge*>();
}

int main() {
    int n, m, c;
    cin >> n >> m >> c;
    string types;
    cin >> types;

    vector<vector<int> > edges(m, vector<int>(2));
    for (int i = 0; i < m; i++) {
        cin >> edges[i][0] >> edges[i][1];
    }

    vector<Vertex*> vertices;
    for (int i = 0; i < n; i++) {
        char type = types[i];
        int vertexType = (type == 'S' ? 0 : (type == 'C' ? 2 : 1));
        vertices.push_back(new Vertex(i + 1, vertexType));
    }

    ModelGraph modelGraph;
    modelGraph.edges = edges;
    modelGraph.vertices = vertices;
    FlowGraph graph(modelGraph);
    cout << graph.maxFlow() * c << endl;

    return 0;
}