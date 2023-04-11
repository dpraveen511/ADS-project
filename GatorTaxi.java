import java.io.*;
import java.util.*;

public class gatorTaxi {
    // Read all the test data from file at once, and store them in a Queue.
    private static Queue<TestCase> testCases;

    public static void main(String[] args) throws IOException {
        // Input filename will be provided as the first argument of the command-line arguments array. i.e args[0]
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(args[0])))) {
            testCases = new LinkedList<>();
            
            String testCaseStr;
            while((testCaseStr = bufferedReader.readLine()) != null) {
                // Parse the input and add the Test case into Queue.
                testCases.add(InputParser.getParsedTestCase(testCaseStr));
            }
        }

        // Starts executing the test cases
        RidesExecutor cityBuilder = new RidesExecutor(testCases);
        cityBuilder.build();
    }
}