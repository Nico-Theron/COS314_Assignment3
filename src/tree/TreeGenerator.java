package tree;

import java.util.Random;

public class TreeGenerator {
    private Random random;
    // There are nine features if we exclude the class col
    private static final int FEATURE_COUNT = 9;

    public TreeGenerator(Random random) {
        this.random = random;
    }

    public Node generateRandomTree(int maxDepth) {
        return generateGrowTree(maxDepth);
    }

    public Node generateFullTree(int maxDepth) {
        if (maxDepth == 0) {
            return randomLeaf();
        }

        return randomDecisionNode(generateFullTree(maxDepth - 1), generateFullTree(maxDepth - 1));
    }

    public Node generateGrowTree(int maxDepth) {
        if (maxDepth == 0) {
            return randomLeaf();
        }

        // Sometimes stop early and create a leaf
        if (random.nextDouble() < 0.30) {
            return randomLeaf();
        }

        return randomDecisionNode(generateGrowTree(maxDepth - 1), generateGrowTree(maxDepth - 1));
    }

    public Node generateRampedHalfAndHalf(int maxDepth) {
        if (random.nextBoolean()) {
            return generateFullTree(maxDepth);
        } else {
            return generateGrowTree(maxDepth);
        }
    }

    private Node randomDecisionNode(Node left, Node right) {
        int featureIndex = random.nextInt(FEATURE_COUNT);
        int threshold = randomThreshold(featureIndex);

        return new DecisionNode(featureIndex, threshold, left, right);
    }

    private Node randomLeaf() {
        int predictedClass = random.nextInt(2); // 0 or 1
        return new LeafNode(predictedClass);
    }

    private int randomThreshold(int featureIndex) {
        // Feature indexes:
        // 0 age: 0-5
        // 1 menopause: 0-2
        // 2 tumor_size: binned values, use 0-11 safely
        // 3 inv_nodes: binned values, use 0-12 safely
        // 4 node_caps: 0-2
        // 5 deg_malig: usually 1-3
        // 6 breast: 0-1
        // 7 breast_quad: 0-5
        // 8 irradiat: 0-1

        switch (featureIndex) {
            case 0:
                return random.nextInt(6);
            case 1:
                return random.nextInt(3);
            case 2:
                return random.nextInt(12);
            case 3:
                return random.nextInt(13);
            case 4:
                return random.nextInt(3);
            case 5:
                return 1 + random.nextInt(3);
            case 6:
                return random.nextInt(2);
            case 7:
                return random.nextInt(6);
            case 8:
                return random.nextInt(2);
            default:
                return 0;
        }
    }
}