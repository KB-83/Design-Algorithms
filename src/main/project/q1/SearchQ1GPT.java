import java.util.*;

public class SearchQ1GPT {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int t = scanner.nextInt();  // number of test cases

        for (int testCase = 0; testCase < t; testCase++) {
            int n = scanner.nextInt();
            int k = scanner.nextInt();
            int[][] a = new int[n][];

            // Read the array a
            for (int i = 0; i < n; i++) {
                a[i] = new int[n - i];
                for (int j = 0; j < n - i; j++) {
                    a[i][j] = scanner.nextInt();
                }
            }

            List<String> sequences = new ArrayList<>();
            generateSequences(sequences, "", n);

            List<Integer> beauties = new ArrayList<>();

            // Iterate through all sequences and compute their beauty
            for (String sequence : sequences) {
                List<int[]> intervals = new ArrayList<>();
                int start = -1;
                for (int i = 0; i < n; i++) {
                    if (sequence.charAt(i) == '1') {
                        if (start == -1) {
                            start = i;
                        }
                    } else {
                        if (start != -1) {
                            intervals.add(new int[]{start, i - 1});
                            start = -1;
                        }
                    }
                }
                if (start != -1) {
                    intervals.add(new int[]{start, n - 1});
                }

                int beauty = 0;
                for (int[] interval : intervals) {
                    beauty += a[interval[0]][interval[1] - interval[0]];
                }
                beauties.add(beauty);
            }

            // Sort the beauties in descending order
            beauties.sort(Collections.reverseOrder());

            // Output the first k beauties
            for (int i = 0; i < k; i++) {
                if (i > 0) System.out.print(" ");
                System.out.print(beauties.get(i));
            }
            System.out.println();
        }

        scanner.close();
    }

    // Helper method to generate all binary sequences of length n
    private static void generateSequences(List<String> sequences, String current, int n) {
        if (current.length() == n) {
            sequences.add(current);
            return;
        }
        generateSequences(sequences, current + "0", n);
        generateSequences(sequences, current + "1", n);
    }
}
