import data.*;
import fitness.*;
import gp.*;

import java.util.Random;
import utils.CSVExporter;

public class Main {

    static final int RUNS = 30;

    public static void main(String[] args) throws Exception {

        Dataset trainData = DataLoader.loadCSV("Breast_train.csv");
        Dataset testData = DataLoader.loadCSV("Breast_test.csv");

        CSVExporter csv = new CSVExporter("Logical_GP_results.csv");

        for (int run = 1; run <= RUNS; run++) {

            long seed = 1234 + run;
            Random random = new Random(seed);

            long startTime = System.nanoTime();

            LogicalGPAlgorithm gp = new LogicalGPAlgorithm(random);
            Individual best = gp.train(trainData);

            long endTime = System.nanoTime();
            double runtime = (endTime - startTime) / 1e6; // ms

            FitnessEvaluator evaluator = new FitnessEvaluator();

            double trainAcc = evaluator.calculateAccuracy(best.getTree(), trainData);
            double trainF = evaluator.calculateFMeasure(best.getTree(), trainData);

            double testAcc = evaluator.calculateAccuracy(best.getTree(), testData);
            double testF = evaluator.calculateFMeasure(best.getTree(), testData);

            csv.write(run, seed, trainAcc, trainF, testAcc, testF, runtime);

            System.out.println("Run " + run + " complete");
        }

        csv.close();
        System.out.println("All runs complete.");
    }
}