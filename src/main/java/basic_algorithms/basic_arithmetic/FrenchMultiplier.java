package basic_algorithms.basic_arithmetic;

public class FrenchMultiplier {
    public static void main(String[] args) {
        long x = 329017972;
        long y = 98701;

        System.out.println(multiply(x,y));
        System.out.println(x * y);
    }
    public static long multiply(long x, long y) {
        if (y == 0) {
            return 0;
        }
        long z = multiply(x,y/2);
        if (y % 2 == 0) {
            return 2 * z;
        }
        return x + 2 * z;
    }
}
