package symbolicTree;

public class SymbolicOperatorNode extends SymbolicNode {
    private char operator;
    private SymbolicNode left;
    private SymbolicNode right;

    public SymbolicOperatorNode(char operator, SymbolicNode left, SymbolicNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public double evaluate(int[] row) {
        double leftVal = left.evaluate(row);
        double rightVal = right.evaluate(row);

        switch (operator) {
            case '+': return leftVal + rightVal;
            case '-': return leftVal - rightVal;
            case '*': return leftVal * rightVal;
            case '/': return (rightVal == 0.0) ? 1.0 : leftVal / rightVal; // Protected division
            default: throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }

    @Override
    public SymbolicNode copy() {
        return new SymbolicOperatorNode(operator, left.copy(), right.copy());
    }

    @Override
    public int countNodes() {
        return 1 + left.countNodes() + right.countNodes();
    }

    @Override
    public SymbolicNode getNodeAt(int index) {
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
    public SymbolicNode replaceNodeAt(int index, SymbolicNode replacement) {
        if (index == 0) {
            return replacement.copy();
        }

        int leftCount = left.countNodes();

        if (index <= leftCount) {
            SymbolicNode newLeft = left.replaceNodeAt(index - 1, replacement);
            return new SymbolicOperatorNode(operator, newLeft, right.copy());
        } else {
            SymbolicNode newRight = right.replaceNodeAt(index - 1 - leftCount, replacement);
            return new SymbolicOperatorNode(operator, left.copy(), newRight);
        }
    }

    @Override
    public String toString() {
        return "(" + left.toString() + " " + operator + " " + right.toString() + ")";
    }
}
