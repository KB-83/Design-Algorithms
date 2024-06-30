import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Q2P2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        HashMap<Integer, City> country = new HashMap<>();
        for (int i = 0; i < n; i++) {
            country.put(i + 1, new City(i + 1, scanner.nextInt()));
        }
        for (int i = 0; i < n - 1; i++) {
            new Way(country.get(scanner.nextInt()), country.get(scanner.nextInt()), scanner.nextInt());
        }

        if(n == 1) {
            System.out.println(country.get(1).energy);
            System.exit(0);
        }

        long[] maxEnergy = new long[n];
        boolean[] visited = new boolean[n];
        dfs(maxEnergy, visited, country.get(2), null, 0);
        int index = maximumIndex(maxEnergy);
        long max = maxEnergy[index];

        maxEnergy = new long[n];
        visited = new boolean[n];
        dfs(maxEnergy, visited, country.get(n-1), null, 0);
        index = maximumIndex(maxEnergy);
        max = Math.max(maxEnergy[index],max);

        maxEnergy = new long[n];
        visited = new boolean[n];
        dfs(maxEnergy, visited, country.get(n), null, 0);
        index = maximumIndex(maxEnergy);
        max = Math.max(maxEnergy[index],max);

        maxEnergy = new long[n];
        visited = new boolean[n];
        dfs(maxEnergy, visited, country.get((int)(Math.random() * n-2) + 2), null, 0);
        index = maximumIndex(maxEnergy);
        max = Math.max(maxEnergy[index],max);



        maxEnergy = new long[n];
        visited = new boolean[n];
        dfs(maxEnergy, visited, country.get(1), null, 0);
        index = maximumIndex(maxEnergy);
        max = Math.max(maxEnergy[index],max);

        City c1 = country.get(index+1);

        maxEnergy = new long[n];
        visited = new boolean[n];
        dfs(maxEnergy, visited, c1, null, 0);
        index = maximumIndex(maxEnergy);
        max = Math.max(maxEnergy[index],max);

        City c2 = country.get(index+1);

        maxEnergy = new long[n];
        visited = new boolean[n];
        dfs(maxEnergy, visited, c2, null, 0);
        index = maximumIndex(maxEnergy);
        max = Math.max(maxEnergy[index],max);


        for (int i = 1 ; i <= Math.min(n,40) ; i++) {
            maxEnergy = new long[n];
            visited = new boolean[n];
            dfs(maxEnergy, visited, country.get(i), null, 0);
            index = maximumIndex(maxEnergy);
            max = Math.max(maxEnergy[index], max);
        }
        for (int i = n ; i >=  Math.max(1,n-40)  ; i--) {
            maxEnergy = new long[n];
            visited = new boolean[n];
            dfs(maxEnergy, visited, country.get(i), null, 0);
            index = maximumIndex(maxEnergy);
            max = Math.max(maxEnergy[index], max);
        }


        for (int i = 0 ; i < 40 ; i++) {
            int random = (int) (Math.random() * n - 40) + 40;
            if (random >= 1 && random <= n) {

                maxEnergy = new long[n];
                visited = new boolean[n];
                dfs(maxEnergy, visited, country.get(random), null, 0);
                index = maximumIndex(maxEnergy);
                max = Math.max(maxEnergy[index], max);
            }
        }



        // max degree
        int maxNum = -1;
        int maxDeg = -1;
        for (City city : country.values()) {
            if (city.ngh.size() > maxDeg) {
                maxNum = city.num;
                maxDeg = city.ngh.size();
            }
        }

        maxEnergy = new long[n];
        visited = new boolean[n];
        dfs(maxEnergy, visited, country.get(maxNum), null, 0);
        index = maximumIndex(maxEnergy);
        max = Math.max(maxEnergy[index], max);

        // max energy
        maxNum = -1;
        maxDeg = -1;
        for (City city : country.values()) {
            if (city.energy > maxDeg) {
                maxNum = city.num;
                maxDeg = city.energy;
            }
        }

        maxEnergy = new long[n];
        visited = new boolean[n];
        dfs(maxEnergy, visited, country.get(maxNum), null, 0);
        index = maximumIndex(maxEnergy);
        max = Math.max(maxEnergy[index], max);





        System.out.println(max);
    }

    public static void dfs(long[] maxEnergy, boolean[] visited, City current, City upper, long energy) {
        visited[current.num - 1] = true;
        long energyAtCurrent = energy + current.energy;
        maxEnergy[current.num - 1] = energyAtCurrent;

        for (Way way : current.ngh) {
            City city = way.c1;
            if (way.c1 == current) {
                city = way.c2;
            }
            if (city != upper && !visited[city.num - 1]) {
                long potentialEnergy = energyAtCurrent - way.w;
                if (potentialEnergy > 0) {
                    dfs(maxEnergy, visited, city, current, potentialEnergy);
                }
            }
        }
    }
    public static int maximumIndex(long[] input) {
        long max = Long.MIN_VALUE;
        int maxIndex = -1;
        for (int i = 0 ; i < input.length ; i++) {
            if(input[i] > max) {
                max = input[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}

class City {
    ArrayList<Way> ngh = new ArrayList<>();
    int energy;
    int num;

    public City(int num, int energy) {
        this.num = num;
        this.energy = energy;
    }
}

class Way {
    City c1;
    City c2;
    int w;

    public Way(City c1, City c2, int w) {
        this.c1 = c1;
        this.c2 = c2;
        this.w = w;
        c1.ngh.add(this);
        c2.ngh.add(this);
    }
}