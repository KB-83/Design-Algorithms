#include <iostream>
#include <vector>
#include <list>
#include <string>
#include <cmath>

using namespace std;


static list<vector<int> > connectedComponentsList;
static vector<pair<pair<int,int> , string> > lines;

static int k;
static int n;
static long long mod = pow(10, 9) + 7;

static long long uglyBlue ;
static long long uglyRed ;


void dfs(int v, vector<vector<int> >& adj, vector<bool>& visited, vector<int>& connectedNodes) {
    visited[v] = true;
    connectedNodes.push_back(v);
    for (int nextNode : adj[v]) {
        if (!visited[nextNode]) {
            dfs(nextNode, adj, visited, connectedNodes);
        }
    }
}

void findConnectedComponents(int n, vector<vector<int> >& adj) {
    vector<bool> visited(n + 1, false);
    connectedComponentsList.clear();
    for (int i = 1; i <= n; i++) {
        if (!visited[i]) {
            vector<int> connectedNodes;
            dfs(i, adj, visited, connectedNodes);
            connectedComponentsList.push_back(connectedNodes);
        }
    }
}

void fillADJ(int n, vector<pair<int, int> >& input, vector<vector<int> >& adj) {
    adj.empty();
    for (pair<int, int> line : input) {
        int u = line.first;
        int v = line.second;
        adj[u].push_back(v);
        adj[v].push_back(u);
    }
}

long long findOutput() {
    long long output = static_cast<long long>(pow(n, k)) - uglyRed - uglyBlue;
    output = (output + mod) % mod;
    return output;
}

long long fillUgly(string color){
    vector<pair<int, int> > edges;
    for(pair< pair<int,int> ,string>  edge : lines){
        int u,v;
        u = edge.first.first;
        v = edge.first.second;
        string colorr = edge.second;
        if (colorr == color) {
            edges.push_back(make_pair(u,v));
        }
    }
    vector<vector<int> > adj(n + 1);
    fillADJ(n, edges, adj);
    findConnectedComponents(n, adj);
    // cout << "size" << color << connectedComponentsList.size() << endl;

    long long ugly = 0;
    for (const vector<int>& connectedComponent : connectedComponentsList) {
        int componentSize = connectedComponent.size();
        ugly = (ugly + static_cast<long long>(pow(componentSize, k))) % mod;
        ugly = ugly - componentSize;
    }
    // cout << color << ugly << endl;
    return ugly;

}
int main() {
    cin >> n >> k;

    for (int i = 0; i < n - 1; i++) {
        int u, v;
        string color;
        cin >> u >> v >> color;
        lines.push_back(make_pair(make_pair(u,v),color));
    }

    uglyBlue = fillUgly("BLUE");
    uglyRed = fillUgly("RED");

    cout << findOutput() << endl;

    return 0;
}
