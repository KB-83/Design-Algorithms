import java.util.ArrayList;
import java.util.Scanner;

public class Colors {
    static int[] colors;
    public static void main(String[] args) {

        getInput();
        int n = colors.length;
        int[][] DP = new int[n+2][n+2];
        for(int k = 0;k<= n; k++) {
            DP[0][k] = n-k+1;
            DP[k][n+1] = k;
        }
        for(int i = 1 ; i < n;i++) {
            for(int j = n; j > i ; j--) {
                int delta =0;
                if(i>=1 && i<=n && j >=1 &&j<=n){
                    delta = colors[i-1] == colors[j-1] ? 1 : 0;
                }
                DP[i][j] = Math.min(DP[i-1][j]+1 , DP[i][j+1]+1) ;
                if(delta == 1){
                    DP[i][j] = Math.min(delta*(1+DP[i-1][j+1]),DP[i][j]);
                }
            }
        }
        int min = DP[0][2];
        for (int i = 0;i<n;i++) {
            if(DP[i][i+2] < min) {
                min = DP[i][i+2];
            }
        }
        System.out.println(min);
    }
    public static void getInput(){
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int[] input = new int[n];
        for(int i = 0 ; i<n ; i++) {
            input[i] = scanner.nextInt();
        }
        createColors(input);
    }
    private static void createColors(int[] input){
        int last = input[0];
        ArrayList<Integer> help = new ArrayList<>();
        help.add(last);
        for(int i = 1;i<input.length;i++) {
            if(input[i] != last) {
                last = input[i];
                help.add(last);
            }
        }
        colors = new int[help.size()];
        for (int i = 0 ; i < colors.length ; i++) {
            colors[i] = help.get(i);
        }
    }
}
