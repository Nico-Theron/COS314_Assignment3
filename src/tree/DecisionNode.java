package tree;

public class DecisionNode extends Node {
    private int featureIndex;
    private int threshold;
    private Node left;
    private Node right;

    public DecisionNode(int featureIndex, int threshold, Node left, Node right) {
        this.featureIndex = featureIndex;
        this.threshold = threshold;
        this.left = left;
        this.right = right;
    }

    @Override
    public int classify(int[] row) {
        if (row[featureIndex] <= threshold) {
            return left.classify(row);
        } else {
            return right.classify(row);
        }
    }

    @Override
    public Node copy() {
        return new DecisionNode(featureIndex, threshold, left.copy(), right.copy());
    }

    @Override
    public String toString() {
        return "(if feature[" + featureIndex + "] <= " + threshold +
                " then " + left.toString() +
                " else " + right.toString() + ")";
    }
}