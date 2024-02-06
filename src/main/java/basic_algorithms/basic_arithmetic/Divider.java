package basic_algorithms.basic_arithmetic;

public class Divider {
    // divide x by y: target of this class is to find
    // q and r in such a way that x = p.y + r where 0 <= r < y


    public static void main(String[] args) {
        long x = 298745299;
        long y = 987039;
        System.out.println(x/y +" --- "+ String.valueOf(x-y*(x/y)));
        System.out.println(divide(x,y).q+" --- "+divide(x,y).r);
    }

    public static DivisionPair divide(long x, long y) {
        if(x == 0) {
            return new DivisionPair(0,0);
        }
        DivisionPair dp = divide(x/2,y);
        long q = dp.q;
        long r = dp.r;
        q = 2 * q;
        r = 2 * r;
        if(x % 2 == 1) {
            r = r+1;
        }
        if (r >= y) {
            r = r-y;
            q++;
        }
        return new DivisionPair(q,r);

    }
    static class DivisionPair{
        long q;
        long r;

        public DivisionPair(long q, long r) {
            this.q = q;
            this.r = r;
        }
    }
}
