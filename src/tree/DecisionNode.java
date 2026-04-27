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
    public int countNodes() {
        return 1 + left.countNodes() + right.countNodes();
    }

    @Override
    public Node getNodeAt(int index) {
        if (index == 0) {
            return this;
        }

        int leftCount = left.countNodes();

        if (index <= leftCount) {
            return left.getNodeAt(index - 1);
        } else {
            return right.getNodeAt(index - 1 - leftCount);
        }
    }

    @Override
    public Node replaceNodeAt(int index, Node replacement) {
        if (index == 0) {
            return replacement.copy();
        }

        int leftCount = left.countNodes();

        if (index <= leftCount) {
            Node newLeft = left.replaceNodeAt(index - 1, replacement);
            return new DecisionNode(featureIndex, threshold, newLeft, right.copy());
        } else {
            Node newRight = right.replaceNodeAt(index - 1 - leftCount, replacement);
            return new DecisionNode(featureIndex, threshold, left.copy(), newRight);
        }
    }

    @Override
    public String toString() {
        return "(if feature[" + featureIndex + "] <= " + threshold +
                " then " + left.toString() +
                " else " + right.toString() + ")";
    }
}