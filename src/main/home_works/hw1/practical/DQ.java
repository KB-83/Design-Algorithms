import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class DQ {

    static long m;
    static long M;
    static int n;
    static long[] input;
    public static void main(String[] args) throws IOException {
        fillInput();
        System.out.println(numOfNiceArrays(input));
    }

    public static void fillInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] line1 = reader.readLine().split(" ");
        String[] line2 = reader.readLine().split(" ");
        n = Integer.valueOf(line1[0]);
        m = Integer.valueOf(line1[1]);
        M = Integer.valueOf(line1[2]);
        input = new long[n];
        for (int i = 0 ; i < n ; i++) {
            input[i] = Long.parseLong(line2[i]);
        }
    }


    public static long numOfNiceArrays(long[] input){
        //base case
        if(input.length == 0) {
            return 0;
        }
        if (input.length == 1) {
            return  input[0]>=m && input[0] <=M  ? 1:0;
//            check if it is in interval
        }
        int n = input.length;
        long[] L = subArray(input,0,n/2 - 1);
        long[] R = subArray(input,n/2,n -1);
        long num = numOfNiceArrays(L)+ numOfNiceArrays(R) + merge(L,R);
        return num;


    }

    public static long merge(long[] A,long[] B) {
        //A is the left array
        //B is the right array
        //base case
        if(B==null || A==null
        ||A.length ==0 || B.length == 0) {return 0;}
        long[] A2 = inverseCumulative(A); //inverse cumulative
        long[] B2  = cumulative(B);//cumulative  kony //cumulative
        sort(A2);
        sort(B2);

        int bDownPointer = 0;//is i
        int bUpPointer = B2.length - 1;
        long[] upperBounds = new long[A2.length];
        long[] lowerBounds = new long[A2.length];

        //find upper bounds
        for(int i = 0 ; i < A2.length;i++) {

            while (bUpPointer >= 0 && A2[i] + B2[bUpPointer] > M) {
                bUpPointer--;
            }
            upperBounds[i] = bUpPointer;

        }
        //find lowe bounds
        for(int i = A2.length - 1 ; i >-1;i-- ) {

            while (bDownPointer < B2.length && A2[i] + B2[bDownPointer] < m) {
                bDownPointer ++;
            }
            lowerBounds[i] = bDownPointer;
        }

        //find sum
        long sum = 0 ;
        for (int i = 0 ; i < lowerBounds.length ; i++) {
            if(upperBounds[i] >= lowerBounds[i]) { //double check if condition is true
                sum += upperBounds[i] - lowerBounds[i] + 1;
            }
        }
        return sum;


    }
    public static void sort(long[] input) {
        Arrays.sort(input);
    }
    public static long[] cumulative(long[] input) {
        long[] output = new long[input.length];
        output[0] = input[0];
        for (int i = 1 ; i < input.length ; i++) {
            output[i] = input[i] + output[i-1];
        }
        return output;
    }

    public static long[] inverseCumulative(long[] input) {
        long[] output = new long[input.length];
        output[output.length - 1] = input[input.length - 1];
        for (int i = output.length - 2 ; i >= 0 ; i--) {
            output[i] = input[i] + output[i+1];
        }
        return output;
    }

    public static long[] subArray(long[] input, int from, int to) {
        int n = to - from + 1;
        long[] output = new long[n];
        int j = 0;
        for (int i = from ; i <= to ; i++) {
            output[j] = input[i];
            j++;
        }
        return output;
    }
}
