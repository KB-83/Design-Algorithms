//import javafx.util.Pair;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//class Q5{
//
//    public static HashMap<Edge,Integer> iterativeRounding(Graph graph, LPSolver solver, List<List<Double>> A, List<Double> b, List<Double> c, HashMap<HashMap<Integer,Integer>,Integer> f) {
//        HashMap<Integer,Integer> J = new HashMap<>();
//        HashMap<HashMap<Integer,Integer>,Integer> fPrime = new HashMap<>();
//        for (int i = 0 ; i < f.size() ; i++) {
//            HashMap<Integer,Integer> S = (HashMap<Integer, Integer>) f.keySet().toArray()[i];
//            fPrime.put(S,f.get(S));
//        }
//        int i= 0;
//
//        while (!checkSolution()) {
//            i++;
//
//
//            List<Double> x = new ArrayList<>();
//            // Solve the LP
//            double result = solver.solve(x);
//
//            // find A b and c from given data
//
//
//            // Identify edges to be removed or fixed
//            for (int e = 0; e < x.size(); e++) {
//                if (x.get(e) == 0.0) {
//                    graph.edges.remove(e);
//                } else if (x.get(e) >= 0.5) {
//                    J.put(e,e);
//                    graph.edges.remove(e);
//                }
//            }
//
//            for (int i = 0 ; i < f.size() ; i++) {
//                HashMap<Integer,Integer> S = (HashMap<Integer, Integer>) f.keySet().toArray()[i];
//                fPrime.put(S,f.get(S)-deltaSize(S,graph));
//            }
//
//
//            // Update solver with new constraints and cost function
//            solver = new LPSolver(A, b, fPrime);
//        }
//
//        return J;
//    }
//    public static void solveLP_SND(Graph graph,HashMap<HashMap<Integer,Integer>,Integer> f,List<Double> x,LPSolver lpSolver,List<Double> c) {
//
//        //Create A
//
//
//        //Create b
//
//
//    }
//    public static int deltaSize(HashMap<Integer,Integer> nodes,Graph graph) {
//        return 0;
//    }
//
//    public static double countEdges(List<List<Double>> A, int e) {
//        double count = 0;
//        for (List<Double> row : A) {
//            count += row.get(e);
//        }
//        return count;
//    }
//    public static boolean checkSolution() {
//        return true;
//    }
//
//}
//
//class LPSolver {
//    public static void main(String[] args) {
//        // Example (DPV page 202)
//        List<List<Double>> A = new ArrayList<>();
//        A.add(Arrays.asList(1.0, 0.0));
//        A.add(Arrays.asList(0.0, 1.0));
//        A.add(Arrays.asList(1.0, 1.0));
//
//        List<Double> b = Arrays.asList(200.0, 300.0, 400.0);
//
//        List<Double> c = Arrays.asList(1.0, 6.0);
//
//        LPSolver solver = new LPSolver(A, b, c);
//
//        List<Double> x = new ArrayList<>();
//
//
//        double result = solver.solve(x);
//
//        System.out.println("Optimal value: " + result);
//        System.out.println("Optimal solution: " + x);
//    }
//    private static final double EPS = 1e-8;
//    private static final double INF = 1 / 0.0;
//    private int m, n;
//    private List<Integer> N, B;
//    private List<List<Double>> D;
//
//    public LPSolver(List<List<Double>> A, List<Double> b, List<Double> c) {
//        m = b.size();
//        n = c.size();
//        N = new ArrayList<>();
//        B = new ArrayList<>();
//        D = new ArrayList<>();
//
//        for (int i = 0; i < m + 2; i++) {
//            D.add(new ArrayList<>());
//            for (int j = 0; j < n + 2; j++) {
//                D.get(i).add(0.0);
//            }
//        }
//
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                D.get(i).set(j, A.get(i).get(j));
//            }
//        }
//
//        for (int i = 0; i < m; i++) {
//            B.add(n + i);
//            D.get(i).set(n, -1.0);
//            D.get(i).set(n + 1, b.get(i));
//        }
//
//        for (int j = 0; j < n; j++) {
//            N.add(j);
//            D.get(m).set(j, -c.get(j));
//        }
//
//        N.add(-1);
//        D.get(m + 1).set(n, 1.0);
//    }
//
//    private void pivot(int r, int s) {
//        double[] a = D.get(r).stream().mapToDouble(Double::doubleValue).toArray();
//        double inv = 1 / a[s];
//
//        for (int i = 0; i < m + 2; i++) {
//            if (i != r && Math.abs(D.get(i).get(s)) > EPS) {
//                double[] b = D.get(i).stream().mapToDouble(Double::doubleValue).toArray();
//                double inv2 = b[s] * inv;
//                for (int j = 0; j < n + 2; j++) {
//                    b[j] -= a[j] * inv2;
//                }
//                b[s] = a[s] * inv2;
//                for (int j = 0; j < n + 2; j++) {
//                    D.get(i).set(j, b[j]);
//                }
//            }
//        }
//
//        for (int j = 0; j < n + 2; j++) {
//            if (j != s) {
//                D.get(r).set(j, D.get(r).get(j) * inv);
//            }
//        }
//
//        for (int i = 0; i < m + 2; i++) {
//            if (i != r) {
//                D.get(i).set(s, D.get(i).get(s) * -inv);
//            }
//        }
//
//        D.get(r).set(s, inv);
//        int temp = B.get(r);
//        B.set(r, N.get(s));
//        N.set(s, temp);
//    }
//
//    private boolean simplex(int phase) {
//        int x = m + (phase - 1);
//
//        while (true) {
//            int s = -1;
//            for (int j = 0; j < n + 1; j++) {
//                if (N.get(j) != -phase && (s == -1 || D.get(x).get(j) < D.get(x).get(s))) {
//                    s = j;
//                }
//            }
//
//            if (D.get(x).get(s) > -EPS) {
//                return true;
//            }
//
//            int r = -1;
//            for (int i = 0; i < m; i++) {
//                if (D.get(i).get(s) > EPS && (r == -1 || D.get(i).get(n + 1) / D.get(i).get(s) < D.get(r).get(n + 1) / D.get(r).get(s))) {
//                    r = i;
//                }
//            }
//
//            if (r == -1) {
//                return false;
//            }
//
//            pivot(r, s);
//        }
//    }
//
//    public double solve(List<Double> x) {
//        int r = 0;
//        for (int i = 1; i < m; i++) {
//            if (D.get(i).get(n + 1) < D.get(r).get(n + 1)) {
//                r = i;
//            }
//        }
//
//        if (D.get(r).get(n + 1) < -EPS) {
//            pivot(r, n);
//            if (!simplex(2) || D.get(m + 1).get(n + 1) < -EPS) {
//                return -INF;
//            }
//
//            for (int i = 0; i < m; i++) {
//                if (B.get(i) == -1) {
//                    int s = -1;
//                    for (int j = 0; j < n + 1; j++) {
//                        if (s == -1 || D.get(i).get(j) < D.get(i).get(s)) {
//                            s = j;
//                        }
//                    }
//                    pivot(i, s);
//                }
//            }
//        }
//
//        if (!simplex(1)) {
//            return INF;
//        }
//
//        if (x != null) {
//            x.clear();
//            for (int i = 0; i < n; i++) {
//                x.add(0.0);
//            }
//
//            for (int i = 0; i < m; i++) {
//                if (B.get(i) < n) {
//                    x.set(B.get(i), D.get(i).get(n + 1));
//                }
//            }
//        }
//
//        return D.get(m).get(n + 1);
//    }
//}
//class Edge{}
//class Graph{
//    ArrayList<Edge> edges;
//    HashMap<Integer,Integer> nodes;
//}