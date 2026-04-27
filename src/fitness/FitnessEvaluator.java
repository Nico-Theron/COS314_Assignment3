package fitness;

import data.Dataset;
import tree.Node;

public class FitnessEvaluator {

    public double calculateAccuracy(Node tree, Dataset dataset) {
        int correct = 0;

        for (int i = 0; i < dataset.size(); i++) {
            int prediction = tree.classify(dataset.features[i]);

            if (prediction == dataset.labels[i]) {
                correct++;
            }
            
        }

        return (double) correct / dataset.size();
    }
}