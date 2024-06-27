package q1;

import java.io.*;
import java.util.*;

public class TestCaseRunner {
    public static void main(String[] args) throws Exception {
//        if (args.length != 3) {
//            System.out.println("Usage: java q1.TestCaseRunner testcases.txt q1.SearchQ1GPT q1.Q1");
//            return;
//        }

        String testCaseFile = "testcases.txt";
        String solution1ClassName = "q1.SearchQ1GPT";
        String solution2ClassName = "q1.Q1P";

        // Load the solution classes dynamically
        Class<?> solution1Class = Class.forName(solution1ClassName);
        Solution solution1 = (Solution) solution1Class.getDeclaredConstructor().newInstance();

        Class<?> solution2Class = Class.forName(solution2ClassName);
        Solution solution2 = (Solution) solution2Class.getDeclaredConstructor().newInstance();

        // Read the test cases
        List<String> testCases = readTestCases(testCaseFile);

        // Execute and compare outputs
        compareSolutions(testCases, solution1, solution2);
    }

    private static List<String> readTestCases(String filePath) throws IOException {
        List<String> testCases = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                testCases.add(line);
            }
        }
        return testCases;
    }

    private static void compareSolutions(List<String> testCases, Solution solution1, Solution solution2) {
        List<String> currentTestCase = new ArrayList<>();
        boolean testCaseStart = true;

        for (String line : testCases) {
            if (testCaseStart) {
                currentTestCase = new ArrayList<>();
                testCaseStart = false;
            }
            currentTestCase.add(line);

            if (line.trim().isEmpty() || line.equals(testCases.get(testCases.size() - 1))) {
                // Process the current test case when we reach an empty line or the last line
                List<String> output1 = solution1.solve(currentTestCase);
                List<String> output2 = solution2.solve(currentTestCase);

                if (!output1.equals(output2)) {
                    System.out.println("Test case failed:");
                    for (String testCaseLine : currentTestCase) {
                        System.out.println(testCaseLine);
                    }
                    System.out.println("Output1: " + output1);
                    System.out.println("Output2: " + output2);
                }

                testCaseStart = true;
            }
        }
    }
}
