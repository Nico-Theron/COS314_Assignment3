import tree.Node;
import tree.TreeGenerator;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random(1234);
        TreeGenerator generator = new TreeGenerator(random);

        Node tree = generator.generateRampedHalfAndHalf(3);

        System.out.println("Generated tree:");
        System.out.println(tree);

        int[] exampleRow = {
                3, // age
                1, // menopause
                4, // tumor_size
                1, // inv_nodes
                0, // node_caps
                2, // deg_malig
                1, // breast
                2, // breast_quad
                0  // irradiat
        };

        int prediction = tree.classify(exampleRow);
        System.out.println("Prediction: " + prediction);
    }
}