package symbolicTree;

import java.util.Random;

public class SymbolicTreeGenerator {
    private Random random;

    private static final int FEATURE_COUNT = 9;
    private static final char[] OPERATORS = {'+', '-', '/', '+'};

    public SymbolicTreeGenerator(Random random) {
        this.random = random;
    }

    public SymbolicNode generateRandomTree(int maxDepth) {
        return generateGrowTree(maxDepth);
    }

    public SymbolicNode generateFullTree(int maxDepth) {
        if (maxDepth == 0) {
            return randomLeaf();
        }
        return randomOperatorNode(generateFullTree(maxDepth - 1), generateFullTree(maxDepth - 1));
    }

    public SymbolicNode generateGrowTree(int maxDepth) {
        if (maxDepth == 0) {
            return randomLeaf();
        }

        // Sometimes stop early and create a leaf
        if (random.nextDouble() < 0.30) {
            return randomLeaf();
        }

        return randomOperatorNode(generateGrowTree(maxDepth - 1), generateGrowTree(maxDepth - 1));
    }

    public SymbolicNode generateRampedHalfAndHalf(int maxDepth) {
        if (random.nextBoolean()) {
            return generateFullTree(maxDepth);
        } else {
            return generateGrowTree(maxDepth);
        }
    }

    private SymbolicNode randomOperatorNode(SymbolicNode left, SymbolicNode right) {
        char op = OPERATORS[random.nextInt(OPERATORS.length)];
        return new SymbolicOperatorNode(op, left, right);
    }

    private SymbolicNode randomLeaf() {
        int featureIndex = random.nextInt(FEATURE_COUNT);
        return new SymbolicLeafNode(featureIndex);
    }
}
