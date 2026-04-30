
package utils;

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
        CSVExporter csv2 = new CSVExporter("Symbolic_GP_results.csv");

        double[] logicalTestAcc = new double[RUNS];
        double[] symbolicTestAcc = new double[RUNS];
        double[] logicalF = new double[RUNS];
        double[] symbolicF = new double[RUNS];

        System.out.println("--------------------------");
        System.out.println("        Logical GP:       ");
        System.out.println("--------------------------");

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

            logicalTestAcc[run - 1] = testAcc;
            logicalF[run - 1] = testF;

            csv.write(run, seed, trainAcc, trainF, testAcc, testF, runtime);

            System.out.println("Run " + run + " complete for logical GP");
        }
        csv.close();

        System.out.println("--------------------------");
        System.out.println("        Symbolic GP:      ");
        System.out.println("--------------------------");

        for (int run = 1; run <= RUNS; run++) {

            long seed = 1234 + run;
            Random random = new Random(seed);

            long startTime = System.nanoTime();

            SymbolicGPAlgorithm gp = new SymbolicGPAlgorithm(random);
            SymbolicIndividual best = gp.train(trainData);

            long endTime = System.nanoTime();
            double runtime = (endTime - startTime) / 1e6;

            SymbolicFitnessEvaluator evaluator = new SymbolicFitnessEvaluator();

            double trainAcc = evaluator.calculateAccuracy(best.getTree(), trainData);
            double trainF = evaluator.calculateFMeasure(best.getTree(), trainData);

            double testAcc = evaluator.calculateAccuracy(best.getTree(), testData);
            double testF = evaluator.calculateFMeasure(best.getTree(), testData);

            symbolicTestAcc[run - 1] = testAcc;
            symbolicF[run - 1] = testF;

            csv2.write(run, seed, trainAcc, trainF, testAcc, testF, runtime);

            System.out.println("Run " + run + " complete for symbolic GP");
        }
        csv2.close();
        WilcoxonTest.run(logicalTestAcc, symbolicTestAcc, "Wilcoxon_Test_Accuracy.csv");
        WilcoxonTest.run(logicalF, symbolicF, "Wilcoxon_Test_FMeasure.csv");
        System.out.println("All runs complete.");
        System.out.println("Results saved in 'Logical_GP_results.csv' & 'Symbolic_GP_results.csv' respectively.");
    }
}