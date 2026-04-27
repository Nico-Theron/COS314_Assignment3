package tree;

public abstract class Node {
    public abstract int classify(int[] row);
    public abstract Node copy();
    public abstract String toString();
}