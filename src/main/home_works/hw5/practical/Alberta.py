import sys
from collections import deque, defaultdict

class q5.Vertex:
    def __init__(self, num, vtype):
        self.num = num
        self.type = vtype

class DEdge:
    def __init__(self, capacity, start, end):
        self.capacity = capacity
        self.start = start
        self.end = end

class ModelGraph:
    def __init__(self, edges, vertices):
        self.edges = edges
        self.vertices = vertices

class FlowGraph:
    def __init__(self, model_graph):
        self.adj_list = defaultdict(list)
        self.vertices = {}
        self.source = q5.Vertex(0, 5)
        self.sink = q5.Vertex(len(model_graph.vertices) + 1, 4)
        self.s_vertices = 0
        self.p_vertices = 0
        self.vertices[self.source.num] = self.source
        self.vertices[self.sink.num] = self.sink
        self.create_graph(model_graph)

    def create_graph(self, model_graph):
        for vertex in model_graph.vertices:
            self.vertices[vertex.num] = vertex
            if vertex.type == 0:
                self.s_vertices += 1
            elif vertex.type == 1:
                self.p_vertices += 1

        for vertex in self.vertices.values():
            self.adj_list[vertex] = []

        for edge in model_graph.edges:
            start = self.vertices[edge[0]]
            end = self.vertices[edge[1]]
            self.add_edge(start, end, 1)
            self.add_edge(end, start, 0)

            if start.type == 0:
                self.add_edge(self.source, start, self.p_vertices + self.s_vertices)
            if start.type == 1:
                self.add_edge(start, self.sink, self.p_vertices + self.s_vertices)
            if end.type == 0:
                self.add_edge(self.source, end, self.p_vertices + self.s_vertices)
            if end.type == 1:
                self.add_edge(end, self.sink, self.p_vertices + self.s_vertices)

    def add_edge(self, start, end, capacity):
        edge = DEdge(capacity, start, end)
        self.adj_list[start].append(edge)
        reverse_edge = DEdge(0, end, start)
        self.adj_list[end].append(reverse_edge)

    def update(self, path):
        for edge in path:
            edge.capacity -= 1
            for rev_edge in self.adj_list[edge.end]:
                if rev_edge.end == edge.start:
                    rev_edge.capacity += 1
                    break

    def max_flow(self):
        max_flow = 0
        while True:
            path = self.find_path()
            if not path:
                return max_flow
            max_flow += 1
            self.update(path)

    def find_path(self):
        parent_map = {}
        queue = deque([self.source])
        parent_map[self.source] = None

        while queue:
            current = queue.popleft()
            if current == self.sink:
                path = []
                v = self.sink
                while v != self.source:
                    edge = parent_map[v]
                    path.append(edge)
                    v = edge.start
                path.reverse()
                return path

            for edge in self.adj_list[current]:
                if edge.end not in parent_map and edge.capacity > 0:
                    parent_map[edge.end] = edge
                    queue.append(edge.end)

        return []

def main():
    input = sys.stdin.read
    data = input().split()
    
    n = int(data[0])
    m = int(data[1])
    c = int(data[2])
    
    types = data[3]
    
    edges = []
    index = 4
    for i in range(m):
        u = int(data[index])
        v = int(data[index + 1])
        edges.append([u, v])
        index += 2
    
    vertices = []
    for i in range(n):
        type_char = types[i]
        vertex_type = 0 if type_char == 'S' else 1 if type_char == 'P' else 2
        vertices.append(q5.Vertex(i + 1, vertex_type))
    
    model_graph = ModelGraph(edges, vertices)
    graph = FlowGraph(model_graph)
    
    print(graph.max_flow() * c)

if __name__ == "__main__":
    main()