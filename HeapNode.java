

public class HeapNode extends Ride {
    // Pointer to the corresponding red black node.
    private RedBlackNode rbtNodeReference;

    // Constructor.
    public HeapNode(int rideNumber, int rideCost, int tripDuration, RedBlackNode node) {
        super(rideNumber, rideCost, tripDuration);
        this.setRbtReference(node);
    }

    public RedBlackNode getRbtReference() {
        return rbtNodeReference;
    }

    private void setRbtReference(RedBlackNode node) {
        this.rbtNodeReference = node;
    }
}