import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
class Q5{

    int[] f;
    int n;
    int[][] S;
    LPGraph graph;
    HashMap<Integer,Edge> firstProvidedEdges;
    List<List<Double>> A;//fixed
    List<Double> c;//fixed
    int[][] rIJ;

    public Q5(LPGraph graph, int n, List<Double> c, int[][] rIJ) {
        this.graph = graph;
        this.c = c;
        this.rIJ = rIJ;
        this.n = n;
        fillEdges(graph);
        fillF(rIJ);
        fillSAndA(n);
    }
    private void fillSAndA(int n) {
        int num = (int) Math.pow(2, n);
        A = new ArrayList<>();
        S = new int[num][n];

        for (int i = 0; i < num; i++) {
            List<Double> sequence = new ArrayList<>();
            String binaryString = String.format("%" + n + "s", Integer.toBinaryString(i)).replace(' ', '0');
            for (int j = 0; j < n; j++) {
                S[i][j] = binaryString.charAt(j) == '0' ? 0 : 1;
                sequence.add((double) S[i][j]);
            }
            A.add(sequence);
        }
    }
    private void fillF(int[][] rIJ) {
        f = new int[S.length];
        for (int i = 0 ; i < S.length ; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = 0 ; j < S[i].length ; j++) {
                if (S[i][j] == 1) {
                    for (int k = 0 ; k <S[i].length ; k++) {
                        if (S[i][k] == 0) {
                            if (rIJ[j][k] > max) {
                                max = rIJ[j][k];
                            }
                        }
                    }
                }
            }
            f[i] = max;

        }

    }
    private void fillEdges(LPGraph g) {
        // Deep Copy
        firstProvidedEdges = new HashMap<>();
        for (int i= 1 ; i<= g.edges.size() ; i++) {
            firstProvidedEdges.put(i,new Edge(g.edges.get(i).node1,g.edges.get(i).node2));
        }

    }


    public static void main(String[] args) {
//        Q5 q5 = new Q5(3);
//        for (List<Double> list : A) {
//            System.out.println(list);
//        }
//        for (int i = 0 ; i < S.length;i++) {
//            for (int j = 0 ; j < S[0].length ; j++) {
//                System.out.print(S[i][j]+" ");
//            }
//            System.out.println();
//
//        }
    }

    public List<Double> iterativeRounding() {
//        J <- {}

//        fPrime <- f
        int[] fPrime = f;//?

//        i <- 0
        int index= 0;

        List<Double> J = new ArrayList<>();
        double ans = solveLP_SND(graph,fPrime,J,c);

        while (ans == -Long.MIN_VALUE) {
            index++;

//             Solve LP_SND
            solveLP_SND(graph,fPrime,J,c);


//             Identify edges to be removed or fixed
//            J <- J union Jp
            for (int e = 1; e <= J.size(); e++) {
                if (J.get(e) == 0.0) {
                    graph.edges.remove(e);
                } else if (J.get(e) >= 0.5) {
                    J.add(e,1.0);
                    graph.edges.remove(e);
                }
            }


            // We do not need to calculate it?
            fPrime = calculateFPrime(J);

//          i <- i+1
            index++;
        }

        return J;
    }
    private int[] calculateFPrime(List<Double> J) {
        int[] fPrime = new int[f.length];
        for (int i = 0 ; i < f.length ; i++) {
            int fp = 0;
            List<Edge> delta = delta(S[i]);
            for (Edge e : delta) {
                if(J.get(e.number) == 1.0) {
                    fp++;
                }
            }
            fPrime[i] = fp;
        }
        return fPrime;
    }
    public double solveLP_SND(LPGraph graph,int[] f,List<Double> J,List<Double> c) {

        //A : fix
        //C : fix
        //Create b
        List<Double> b = new ArrayList<>();
        for (int i : f) {
            b.add((double) i);
        }
        LPSolver solver = new LPSolver(A,b,c);
        return solver.solve(J);

    }
    public List<Edge> delta(int[] S) {
        List<Edge> deltaS = new ArrayList<>();
        for (int i = 0 ; i < S.length ; i++) {
            if (S[i] == 1) {
                for (Edge e : graph.edges.values()) {
                    if (S[e.node1] != S[e.node2]) {
                        deltaS.add(e);
                    }
                }
            }
        }
        return deltaS;
    }

//    public static double countEdges(List<List<Double>> A, int e) {
//        double count = 0;
//        for (List<Double> row : A) {
//            count += row.get(e);
//        }
//        return count;
//    }
    public static boolean checkSolution(List<Double> J) {
        return true;
    }

}

class LPSolver {
    public static void main(String[] args) {
        // Example (DPV page 202)
        List<List<Double>> A = new ArrayList<>();
        A.add(Arrays.asList(1.0, 0.0));
        A.add(Arrays.asList(0.0, 1.0));
        A.add(Arrays.asList(1.0, 1.0));

        List<Double> b = Arrays.asList(200.0, 300.0, 400.0);

        List<Double> c = Arrays.asList(1.0, 6.0);

        LPSolver solver = new LPSolver(A, b, c);

        List<Double> x = new ArrayList<>();


        double result = solver.solve(x);

        System.out.println("Optimal value: " + result);
        System.out.println("Optimal solution: " + x);
    }
    private static final double EPS = 1e-8;
    private static final double INF = 1 / 0.0;
    private int m, n;
    private List<Integer> N, B;
    private List<List<Double>> D;

    public LPSolver(List<List<Double>> A, List<Double> b, List<Double> c) {
        m = b.size();
        n = c.size();
        N = new ArrayList<>();
        B = new ArrayList<>();
        D = new ArrayList<>();

        for (int i = 0; i < m + 2; i++) {
            D.add(new ArrayList<>());
            for (int j = 0; j < n + 2; j++) {
                D.get(i).add(0.0);
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                D.get(i).set(j, A.get(i).get(j));
            }
        }

        for (int i = 0; i < m; i++) {
            B.add(n + i);
            D.get(i).set(n, -1.0);
            D.get(i).set(n + 1, b.get(i));
        }

        for (int j = 0; j < n; j++) {
            N.add(j);
            D.get(m).set(j, -c.get(j));
        }

        N.add(-1);
        D.get(m + 1).set(n, 1.0);
    }

    private void pivot(int r, int s) {
        double[] a = D.get(r).stream().mapToDouble(Double::doubleValue).toArray();
        double inv = 1 / a[s];

        for (int i = 0; i < m + 2; i++) {
            if (i != r && Math.abs(D.get(i).get(s)) > EPS) {
                double[] b = D.get(i).stream().mapToDouble(Double::doubleValue).toArray();
                double inv2 = b[s] * inv;
                for (int j = 0; j < n + 2; j++) {
                    b[j] -= a[j] * inv2;
                }
                b[s] = a[s] * inv2;
                for (int j = 0; j < n + 2; j++) {
                    D.get(i).set(j, b[j]);
                }
            }
        }

        for (int j = 0; j < n + 2; j++) {
            if (j != s) {
                D.get(r).set(j, D.get(r).get(j) * inv);
            }
        }

        for (int i = 0; i < m + 2; i++) {
            if (i != r) {
                D.get(i).set(s, D.get(i).get(s) * -inv);
            }
        }

        D.get(r).set(s, inv);
        int temp = B.get(r);
        B.set(r, N.get(s));
        N.set(s, temp);
    }

    private boolean simplex(int phase) {
        int x = m + (phase - 1);

        while (true) {
            int s = -1;
            for (int j = 0; j < n + 1; j++) {
                if (N.get(j) != -phase && (s == -1 || D.get(x).get(j) < D.get(x).get(s))) {
                    s = j;
                }
            }

            if (D.get(x).get(s) > -EPS) {
                return true;
            }

            int r = -1;
            for (int i = 0; i < m; i++) {
                if (D.get(i).get(s) > EPS && (r == -1 || D.get(i).get(n + 1) / D.get(i).get(s) < D.get(r).get(n + 1) / D.get(r).get(s))) {
                    r = i;
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
            if (D.get(i).get(n + 1) < D.get(r).get(n + 1)) {
                r = i;
            }
        }

        if (D.get(r).get(n + 1) < -EPS) {
            pivot(r, n);
            if (!simplex(2) || D.get(m + 1).get(n + 1) < -EPS) {
                return -INF;
            }

            for (int i = 0; i < m; i++) {
                if (B.get(i) == -1) {
                    int s = -1;
                    for (int j = 0; j < n + 1; j++) {
                        if (s == -1 || D.get(i).get(j) < D.get(i).get(s)) {
                            s = j;
                        }
                    }
                    pivot(i, s);
                }
            }
        }

        if (!simplex(1)) {
            return INF;
        }

        if (x != null) {
            x.clear();
            for (int i = 0; i < n; i++) {
                x.add(0.0);
            }

            for (int i = 0; i < m; i++) {
                if (B.get(i) < n) {
                    x.set(B.get(i), D.get(i).get(n + 1));
                }
            }
        }

        return D.get(m).get(n + 1);
    }
}
class Edge{
    int node1,node2;
    int number;

    public Edge(int node1, int node2) {
        this.node1 = node1;
        this.node2 = node2;
    }
}
class LPGraph {
    HashMap<Integer , Edge> edges;
    HashMap<Integer,Integer> nodes;
}