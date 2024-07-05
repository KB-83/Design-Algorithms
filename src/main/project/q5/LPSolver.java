package q5;
import java.util.*;

class Q5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        scanner.nextLine();
        int[][] rIJ = new int[n][n];
        int[][] cHelper = new int[n][n];
        int edgeNum = 1;
        LPGraph graph = new LPGraph(n);
        HashMap<Integer, LPEdge> edges = new HashMap<>();
        List<Double> c = new ArrayList<>();
        for (int a = 0; a < m; a++) {
            int i = scanner.nextInt();
            int j = scanner.nextInt();
            int rij = scanner.nextInt();
            int eij = scanner.nextInt();
            rIJ[i-1][j-1] = rij;
            rIJ[j-1][i-1] = rij;
            if (eij == 1) {
                double cij = scanner.nextDouble();
                graph.edges.put(edgeNum, new LPEdge(i-1, j-1, edgeNum));
                edges.put(edgeNum, new LPEdge(i-1, j-1, edgeNum));
                edgeNum++;
                c.add(cij);
                cHelper[i-1][j-1] = (int) cij;
                cHelper[j-1][i-1] = (int) cij;
            }
            scanner.nextLine();
        }
        SurvivableNetworkDesign snd = new SurvivableNetworkDesign(graph, n, m, c, rIJ, cHelper);
        List<Double> ans = snd.iterativeRounding();
        int out = 0;
        for (double d : ans) {
            if (d == 1.0) {
                out++;
            }
        }
        System.out.println(out);
        for (int i = 0; i < ans.size(); i++) {
            if (ans.get(i) == 1.0) {
                System.out.println((edges.get(i + 1).node1 + 1) + " " + (edges.get(i + 1).node2 + 1));
            }
        }
    }
}

class LPGraph {
    int n;
    HashMap<Integer, LPEdge> edges;

    public LPGraph(int n) {
        this.n = n;
        this.edges = new HashMap<>();
    }
}

class LPEdge {
    int node1, node2, index;

    public LPEdge(int node1, int node2, int index) {
        this.node1 = node1;
        this.node2 = node2;
        this.index = index;
    }
}

class SurvivableNetworkDesign {
    int[] f;
    int n;
    int m;
    int[][] S;
    LPGraph graph;
    List<List<Double>> A;
    List<Double> b;
    List<Double> c;
    int[][] cHelper;
    int[][] rIJ;
    private static final double INF = Double.POSITIVE_INFINITY;

    public SurvivableNetworkDesign(LPGraph graph, int n, int m, List<Double> c, int[][] rIJ, int[][] cHelper) {
        this.graph = graph;
        this.rIJ = rIJ;
        this.n = n;
        this.m = m;
        this.cHelper = cHelper;
        fillSAndA(n);
        fillC(c);
        fillF(rIJ);
        fillB(f);
    }

    private void fillA() {
        A = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            ArrayList<Double> hih = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                hih.add(0.0);
            }
            hih.set(i, 1.0);
            A.add(hih);
        }
        for (int i = 1; i < S.length - 1; i++) {
            A.add(adjDelta(S[i]));
        }
    }

    private void fillSAndA(int n) {
        int num = (int) Math.pow(2, n);
        S = new int[num][n];
        for (int i = 0; i < num; i++) {
            String binaryString = String.format("%" + n + "s", Integer.toBinaryString(i)).replace(' ', '0');
            for (int j = 0; j < n; j++) {
                S[i][j] = binaryString.charAt(j) == '0' ? 0 : 1;
            }
        }
        fillA();
    }

    private void fillF(int[][] rIJ) {
        f = new int[S.length - 2];
        for (int i = 1; i < S.length - 1; i++) {
            int max = Integer.MIN_VALUE;
            for (LPEdge e : delta(S[i])) {
                if (max < rIJ[e.node1][e.node2]) {
                    max = rIJ[e.node1][e.node2];
                }
            }
            f[i - 1] = max;
        }
    }

    private void fillC(List<Double> c) {
        this.c = new ArrayList<>();
        for (double d : c) {
            this.c.add(-d); // Negative for maximization
        }
    }

    private List<Double> adjDelta(int[] set) {
        List<Double> adj = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            adj.add(0.0);
        }
        for (LPEdge e : delta(set)) {
            adj.set(e.index - 1, 1.0);
        }
        return adj;
    }

    private List<LPEdge> delta(int[] set) {
        List<LPEdge> edgesList = new ArrayList<>();
        for (LPEdge edge : graph.edges.values()) {
            if (set[edge.node1] != set[edge.node2]) {
                edgesList.add(edge);
            }
        }
        return edgesList;
    }

    private void fillB(int[] fP) {
        b = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            b.add(1.0);
        }
        for (int value : fP) {
            b.add((double) value);
        }
    }

    public List<Double> iterativeRounding() {
        int[] fPrime = Arrays.copyOf(f, f.length);
        List<Double> J = new ArrayList<>(Collections.nCopies(graph.edges.size(), 0.0));
        List<Double> x = new ArrayList<>(Collections.nCopies(graph.edges.size(), 0.0));
        while (true) {
            double result = solveLP_SND(fPrime, x, c);
            if (result == INF || result == -INF) {
                break;
            }
            List<Double> Jp = new ArrayList<>();
            for (int e = 1; e <= x.size(); e++) {
                if (x.get(e - 1) <= 2 * LPSolver.EPS) {
                    graph.edges.remove(e);
                } else if (x.get(e - 1) >= 0.5 - 2 * LPSolver.EPS) {
                    Jp.add((double) e);
                    J.set(e - 1, 1.0);
                    graph.edges.remove(e);
                }
            }
            for (double edge : Jp) {
                if (!J.contains(edge)) {
                    J.add(edge);
                }
            }
            fPrime = calculateFPrime(J);
            if (!check(J, x)) {
                break;
            }
        }
        return J;
    }

    private int[] calculateFPrime(List<Double> J) {
        int[] fPrime = new int[f.length];
        Arrays.fill(fPrime, Integer.MAX_VALUE);
        for (int i = 0; i < fPrime.length; i++) {
            if (J.get(i) == 1.0) {
                fPrime[i] = f[i];
            }
        }
        return fPrime;
    }

    private boolean check(List<Double> J, List<Double> x) {
        for (int i = 0; i < J.size(); i++) {
            if (J.get(i) == 1.0 && x.get(i) < 0.5) {
                return false;
            }
        }
        return true;
    }

    private double solveLP_SND(int[] fPrime, List<Double> x, List<Double> c) {
        fillA();
        fillB(fPrime);
        LPSolver solver = new LPSolver(A, b, c);
        return solver.solve(x);
    }
}


public class LPSolver {
    public static final double EPS = 1e-8;
    public static final double INF = 1 / 0.0;
    private int m, n;
    private List<Integer> N, B;
    private List<List<Double>> D;

    public LPSolver(List<List<Double>> A, List<Double> b, List<Double> c) {
        m = b.size();
        n = c.size();
        N = new ArrayList<>();
        B = new ArrayList<>();
        D = new ArrayList<>();
        for (int i = 0; i < A.size(); i++) {
            D.add(new ArrayList<>(A.get(i)));
        }
        for (int i = 0; i < b.size(); i++) {
            D.get(i).add(b.get(i));
        }
        D.add(new ArrayList<>(c));
        D.get(m).add(0.0);
        for (int i = 0; i < n; i++) {
            N.add(i);
        }
        for (int i = 0; i < m; i++) {
            B.add(n + i);
        }
    }

    private void pivot(int r, int c) {
        List<Double> tab = new ArrayList<>();
        tab.addAll(D.get(r));
        double inv = 1.0 / tab.get(c);
        tab.set(c, inv);
        for (int i = 0; i <= n; i++) {
            if (i != c) {
                tab.set(i, tab.get(i) * inv);
            }
        }
        for (int i = 0; i <= m; i++) {
            if (i != r) {
                List<Double> currRow = new ArrayList<>();
                currRow.addAll(D.get(i));
                double coeff = currRow.get(c);
                currRow.set(c, coeff * inv);
                for (int j = 0; j <= n; j++) {
                    if (j != c) {
                        currRow.set(j, currRow.get(j) - coeff * tab.get(j));
                    }
                }
                D.set(i, currRow);
            }
        }
        D.set(r, tab);
        B.set(r, c);
    }

    private boolean simplex(int phase) {
        int x = phase == 1 ? n + m : n;
        while (true) {
            int s = -1;
            for (int j = 0; j < n; j++) {
                if (N.get(j) != x && (s == -1 || D.get(m).get(j) < D.get(m).get(s))) {
                    s = j;
                }
            }
            if (D.get(m).get(s) > -EPS) {
                return true;
            }
            int r = -1;
            for (int i = 0; i < m; i++) {
                if (D.get(i).get(s) > EPS) {
                    if (r == -1 || D.get(i).get(n) / D.get(i).get(s) < D.get(r).get(n) / D.get(r).get(s)) {
                        r = i;
                    }
                }
            }
            if (r == -1) {
                return false;
            }
            pivot(r, s);
        }
    }

    public double solve(List<Double> x) {
        int r = 0;
        for (int i = 1; i < m; i++) {
            if (D.get(i).get(n) < D.get(r).get(n)) {
                r = i;
            }
        }
        if (D.get(r).get(n) < -EPS) {
            List<Double> c = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                c.add(D.get(m).get(j));
            }
            D.get(m).add(INF);
            for (int i = 0; i < m; i++) {
                D.get(i).add(-1.0);
            }
            N.add(n + m);
            pivot(r, n + m);
            if (!simplex(1) || D.get(m).get(n + m) < -EPS) {
                return -INF;
            }
            for (int i = 0; i < m; i++) {
                if (B.get(i) == n + m) {
                    int s = -1;
                    for (int j = 0; j < n; j++) {
                        if (s == -1 || D.get(i).get(j) < D.get(i).get(s)) {
                            s = j;
                        }
                    }
                    pivot(i, s);
                }
            }
            D.remove(D.size() - 1);
            for (int i = 0; i < m; i++) {
                D.get(i).remove(D.get(i).size() - 1);
            }
            N.remove(N.size() - 1);
            for (int j = 0; j < n; j++) {
                D.get(m).set(j, c.get(j));
            }
        }
        if (!simplex(2)) {
            return INF;
        }
        x.clear();
        for (int i = 0; i < n; i++) {
            x.add(0.0);
        }
        for (int i = 0; i < m; i++) {
            if (B.get(i) < n) {
                x.set(B.get(i), D.get(i).get(n));
            }
        }
        return D.get(m).get(n);
    }
}
