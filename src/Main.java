import data.*;
import fitness.*;
import gp.*;

import java.util.Random;
import utils.CSVExporter;

public class Main {

    static final int RUNS = 10;

    public static void main(String[] args) throws Exception {

        Dataset trainData = DataLoader.loadCSV("Breast_train.csv");
        Dataset testData = DataLoader.loadCSV("Breast_test.csv");

        CSVExporter csv = new CSVExporter("Logical_GP_results.csv");
        CSVExporter csv2 = new CSVExporter("Symbolic_GP_results.csv");

        for (int run = 1; run <= RUNS; run++) {

            long seed = 1234 + run;
            Random random = new Random(seed);

            long startTime = System.nanoTime();

            LogicalGPAlgorithm gp = new LogicalGPAlgorithm(random);
            Individual best = gp.train(trainData);

            long endTime = System.nanoTime();
            double runtime = (endTime - startTime) / 1e6; // ms

            FitnessEvaluator evaluator = new FitnessEvaluator();
            SymbolicFitnessEvaluator evaluator2 = new SymbolicFitnessEvaluator();

            double trainAcc = evaluator.calculateAccuracy(best.getTree(), trainData);
            double trainF = evaluator.calculateFMeasure(best.getTree(), trainData);

            double testAcc = evaluator.calculateAccuracy(best.getTree(), testData);
            double testF = evaluator.calculateFMeasure(best.getTree(), testData);

            csv.write(run, seed, trainAcc, trainF, testAcc, testF, runtime);

            System.out.println("Run " + run + " complete");

            System.out.println("----------------------------------------");
            System.out.println("----------------------------------------");
            System.out.println("----------------------------------------");
            System.out.println("----------------------------------------");
            System.out.println("----------------------------------------");
            System.out.println("----------------------------------------");
            System.out.println("----------------------------------------");
            System.out.println("----------------------------------------");


            SymbolicGPAlgorithm sgp = new SymbolicGPAlgorithm(random);
            SymbolicIndividual best2 = sgp.train(trainData);

            double trainAcc2 = evaluator2.calculateAccuracy(best2.getTree(), trainData);
            double trainF2 = evaluator2.calculateFMeasure(best2.getTree(), trainData);

            double testAcc2 = evaluator2.calculateAccuracy(best2.getTree(), testData);
            double testF2 = evaluator2.calculateFMeasure(best2.getTree(), testData);

            csv2.write(run, seed, trainAcc2, trainF2, testAcc2, testF2, runtime);

            System.out.println("Run " + run + " complete");
        }

        csv.close();
        csv2.close();
        System.out.println("All runs complete.");
    }
}