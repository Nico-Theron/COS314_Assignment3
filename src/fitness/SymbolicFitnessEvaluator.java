package fitness;

import data.Dataset;
import symbolicTree.SymbolicNode;

public class SymbolicFitnessEvaluator {
    public double calculateAccuracy(SymbolicNode tree, Dataset dataset) {
        int correct = 0;

        for (int i = 0; i < dataset.size(); i++) {
            int prediction = tree.classify(dataset.features[i]);

            if (prediction == dataset.labels[i]) {
                correct++;
            }

        }

        return (double) correct / dataset.size();
    }

    public double calculateFMeasure(SymbolicNode tree, Dataset dataset) {
        int tp = 0;
        int fp = 0;
        int fn = 0;

        for (int i = 0; i < dataset.size(); i++) {
            int prediction = tree.classify(dataset.features[i]);
            int actual = dataset.labels[i];

            if (prediction == 1 && actual == 1) tp++;
            if (prediction == 1 && actual == 0) fp++;
            if (prediction == 0 && actual == 1) fn++;
        }

        double precision = (tp + fp == 0) ? 0 : (double) tp / (tp + fp);
        double recall = (tp + fn == 0) ? 0 : (double) tp / (tp + fn);

        if (precision + recall == 0) return 0;

        return 2 * (precision * recall) / (precision + recall);
    }
}
