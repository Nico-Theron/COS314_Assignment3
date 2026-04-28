package tree;

public class LeafNode extends Node {
    private int predictedClass;

    public LeafNode(int predictedClass) {
        this.predictedClass = predictedClass;
    }

    @Override
    public int classify(int[] row) {
        return predictedClass;
    }

    @Override
    public Node copy() {
        return new LeafNode(predictedClass);
    }

    @Override
    public int countNodes() {
        return 1;
    }

    @Override
    public Node getNodeAt(int index) {
        if (index == 0) {
            return this;
        }

        return null;
    }

    @Override
    public Node replaceNodeAt(int index, Node replacement) {
        if (index == 0) {
            return replacement.copy();
        }

        return this.copy();
    }

    @Override
    public String toString() {
        return String.valueOf(predictedClass);
    }
}