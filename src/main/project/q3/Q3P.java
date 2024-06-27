package q3;

import java.util.*;

public class Q3P {

    public static void main(String[] args)  {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int[][] input = new int[n][n];

        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                input[i][j] = scanner.nextInt();
            }
            scanner.nextLine();
        }
        List<Map<Integer, Integer>> rows= new ArrayList<>();
        List<Map<Integer, Integer>> columns = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            rows.add(new HashMap<>());
            columns.add(new HashMap<>());
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rows.get(i).put(input[i][j], rows.get(i).getOrDefault(input[i][j], 0) + 1);
                columns.get(j).put(input[i][j], columns.get(j).getOrDefault(input[i][j], 0) + 1);
            }
        }

        int output = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int v = input[i][j];
                if (rows.get(i).get(v) > 1 && columns.get(j).get(v) > 1) {
                    rows.get(i).put(v, rows.get(i).get(v) - 1);
                    columns.get(j).put(v, columns.get(j).get(v) - 1);
                    output++;
                }
            }
        }

        int rowSum = 0;
        for (Map<Integer, Integer> row : rows) {
            for (int value : row.values()) {
                rowSum += Math.max(0, value - 1);
            }
        }
        int colSum = 0;
        for (Map<Integer, Integer> col : columns) {
            for (int value : col.values()) {
                colSum += Math.max(0, value - 1);
            }
        }

        System.out.println(output + rowSum + colSum);
    }
}
