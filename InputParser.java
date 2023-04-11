


// A parser for reading test case data from the input and creating TestCase object from it.

/**
Insert(68,40,51)
GetNextRide()
Print(1,100)
UpdateTrip(53,15)
 */
public class InputParser {
    public static TestCase getParsedTestCase(final String testCaseStr) {
        final TestCase testCase = new TestCase();
        testCase.setTestCommand(getTestCommand(testCaseStr));
        
        switch (testCase.getTestCommand()) {
            case INSERT:
                testCase.setRideNumber(getNumber(testCaseStr, "(", ","));
                String rideCostStr = testCaseStr.substring(testCaseStr.indexOf(","));
                testCase.setrideCost(Integer.parseInt(rideCostStr.substring(rideCostStr.indexOf(",") + 1, rideCostStr.substring(1).indexOf(",")+1)));
                String tripTimeStr = rideCostStr.substring(1);
                testCase.setTripDuration(getNumber(tripTimeStr, ",", ")"));
                break;
            case PRINT:
                testCase.setStartRideNumber(getNumber(testCaseStr, "(", ")"));
                break;
            case GETNEXTRIDE:
                break;
            case PRINTBTW:
                testCase.setStartRideNumber(getNumber(testCaseStr, "(", ","));
                testCase.setEndRideNumber(getNumber(testCaseStr, ",", ")"));
                break;  
            case CANCEL:
                testCase.setRideNumber(getNumber(testCaseStr, "(", ")"));
                break;
            case UPDATE:    
                testCase.setRideNumber(getNumber(testCaseStr, "(", ","));
                testCase.setTripDuration(getNumber(testCaseStr, ",", ")")); 
                break;
        }
        return testCase;
    }


    private static TestCommand getTestCommand(final String testCaseStr) {
        return testCaseStr.startsWith("I") ? TestCommand.INSERT : 
            (testCaseStr.startsWith("G") ? TestCommand.GETNEXTRIDE :
            (testCaseStr.startsWith("U") ? TestCommand.UPDATE : 
            (testCaseStr.startsWith("C") ? TestCommand.CANCEL : (testCaseStr.contains(",") ? TestCommand.PRINTBTW :TestCommand.PRINT))));
    }

    private static int getNumber(final String testCaseStr, final String separator1, final String separator2) {
        return Integer.parseInt(testCaseStr.substring(testCaseStr.indexOf(separator1) + 1, testCaseStr.indexOf(separator2)));
    }
}