package tree;

public abstract class Node {
    public abstract int classify(int[] row);
    public abstract Node copy();
    public abstract String toString();

    public abstract int countNodes();
    public abstract Node getNodeAt(int index);
    public abstract Node replaceNodeAt(int index, Node replacement);
}