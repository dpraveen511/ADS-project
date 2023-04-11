import java.io.IOException;
import java.util.*;

public class RidesExecutor {
    private Queue<TestCase> testCases;

    // MinHeap instance.
    private MinHeap minHeap;

    // RedBlackTree instance.
    private RedBlackTree redBlackTree;

    // Represents the maximum number of buildings allowed to be inserted.
    private static final int MAX_RIDES = 2000;

    // Constructor.
    public RidesExecutor(Queue<TestCase> testCases) {
        this.testCases = testCases;
        this.minHeap = new MinHeap(MAX_RIDES);
        this.redBlackTree = new RedBlackTree();
    }

    // Starts rides.
    public void build() {
        // Work until all the testcases are executed
        while(!testCases.isEmpty()) {
            // Check if there are test case operations left to be performed.
                TestCase testCase = testCases.peek();
                    switch(testCase.getTestCommand()) {
                        case INSERT:
                            try {
                                insertBuilding(testCase.getRideNumber(), testCase.getrideCost(), testCase.getTripDuration());
                                //OutputParser.addBuilding(redBlackTree.search(testCase.getBuildingId()));
                            } catch(Exception exception) {
                                OutputParser.addErrorMessage(exception.getMessage());
                                finish();
                            }
                            break;
                        case PRINT:
                            OutputParser.addRideTuple(redBlackTree.search(testCase.getStartRideNumber()));
                            break;
                        case GETNEXTRIDE:
                            getNextNode();
                            break;
                        case PRINTBTW:
                            OutputParser.addBetweenRides(redBlackTree.searchInRange(testCase.getStartRideNumber(),testCase.getEndRideNumber()));
                            break; 
                        case CANCEL:
                            cancelRide(testCase.getRideNumber());
                            break;
                        case UPDATE:
                            updateRide(testCase);
                            break;           
                    }

                    // Remove from queue once executed.
                    testCases.remove();        
        }
        finish();        
    }

    // Finish execution. Print the output to the file.
    private void finish() {
        try {
            OutputParser.print();
            System.exit(0);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Inserts new building to be worked in Min heap and Red black tree.
    private void insertBuilding(int rideNumber, int rideCost, int tripDuration) {
        //checks if duplicate ride exists and then inserts
        RedBlackNode rbNode = new RedBlackNode(rideNumber, rideCost, tripDuration);
        RedBlackNode exist = redBlackTree.search(rideNumber);
        //OutputParser.addBuilding(exist);
        if(Objects.isNull(exist)) {
        HeapNode heapNode = new HeapNode(rideNumber, rideCost, tripDuration, rbNode);
        rbNode.setHeapNodeReference(heapNode);
        redBlackTree.insert(rbNode);
        minHeap.insert(heapNode);
        } else{ 
        //OutputParser.appendString("Duplicate RideNumber");
        throw new IllegalArgumentException("Duplicate RideNumber");
        }
    }

    private void getNextNode(){
        //checks if next Node exists and then removes 
        HeapNode min = minHeap.extractMin();
        if(min != null){
        OutputParser.addRideTuple(min.getRbtReference());
        redBlackTree.delete(min.getRbtReference());
        } else{
        OutputParser.appendString("No active ride requests");
        }
    } 

    private void cancelRide(int rideNumber){
        //Checks if a ride exists and then deletes
        RedBlackNode exist = redBlackTree.search(rideNumber);
        if(exist != null){
        RedBlackNode node = minHeap.delete(rideNumber);
        //OutputParser.addBuilding(node);
        redBlackTree.delete(node);
        } else
        return;
    }

    private void updateRide(TestCase testcase){
        RedBlackNode node = redBlackTree.search(testcase.getRideNumber());
        if(testcase.getTripDuration() <= node.gettripDuration()){
            HeapNode heapnode = node.getHeapNodeReference();
            node.settripDuration(testcase.getTripDuration());
            heapnode.settripDuration(testcase.getTripDuration());
        }
        else if(node.gettripDuration() < testcase.getTripDuration() && testcase.getTripDuration() <= 2*node.gettripDuration()) {
            redBlackTree.delete(node);
            minHeap.delete(node.getrideNumber());
            insertBuilding(node.getrideNumber(), node.getrideCost() + 10, testcase.getTripDuration());
        }
        else if( testcase.getTripDuration() > 2*node.gettripDuration()){
            redBlackTree.delete(node);
            minHeap.delete(node.getrideNumber());
        }   
    }
}