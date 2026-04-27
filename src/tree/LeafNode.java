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
    public String toString() {
        return String.valueOf(predictedClass);
    }
}