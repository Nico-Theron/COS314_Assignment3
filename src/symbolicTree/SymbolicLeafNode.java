package symbolicTree;

public class SymbolicLeafNode extends SymbolicNode {
    private int featureIndex;

    public SymbolicLeafNode(int featureIndex) {
        this.featureIndex = featureIndex;
    }

    @Override
    public double evaluate(int[] row) {
        return row[featureIndex];
    }

    @Override
    public SymbolicNode copy() {
        return new SymbolicLeafNode(featureIndex);
    }

    @Override
    public int countNodes() {
        return 1;
    }

    @Override
    public SymbolicNode getNodeAt(int index) {
        if (index == 0) {
            return this;
        }
        return null;
    }

    @Override
    public SymbolicNode replaceNodeAt(int index, SymbolicNode replacement) {
        if (index == 0) {
            return replacement.copy();
        }
        return this.copy();
    }

    @Override
    public String toString() {
        return "feature[" + featureIndex + "]";
    }
}
