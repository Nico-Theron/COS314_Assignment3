package symbolicTree;

public abstract class SymbolicNode {
    public abstract double evaluate(int[] row);

    // Checks what class it belongs to
    public int classify(int[] row) {
        return evaluate(row) >= 0.5 ? 1 : 0;
    }

    public abstract SymbolicNode copy();
    public abstract String toString();

    public abstract int countNodes();
    public abstract SymbolicNode getNodeAt(int index);
    public abstract SymbolicNode replaceNodeAt(int index, SymbolicNode replacement);
}
