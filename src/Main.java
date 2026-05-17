import data.*;
import fitness.*;
import gp.*;
import utils.*;

import java.util.Random;
import java.util.Scanner;

public class Main {

    static final int RUNS = 30;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        while (true) {

            System.out.println("\n=================================");
            System.out.println(" Genetic Programming Assignment ");
            System.out.println("=================================");
            System.out.println("1. Train Logical GP");
            System.out.println("2. Train Symbolic GP");
            System.out.println("3. Test Logical GP");
            System.out.println("4. Test Symbolic GP");
            System.out.println("5. Run 30-Run Comparison");
            System.out.println("6. Exit");
            System.out.print("Choose option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    trainLogical();
                    break;
                case 2:
                    trainSymbolic();
                    break;
                case 3:
                    testLogical();
                    break;
                case 4:
                    testSymbolic();
                    break;
                case 5:
                    runComparison();
                    break;
                case 6:
                    System.out.println("Goodbye.");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ==================================================
    // OPTION 1 - TRAIN LOGICAL GP
    // ==================================================
    public static void trainLogical() throws Exception {

        System.out.print("Enter seed: ");
        long seed = Long.parseLong(scanner.nextLine());

        Dataset trainData = DataLoader.loadCSV("../Breast_train.csv");

        Random random = new Random(seed);

        long start = System.nanoTime();

        LogicalGPAlgorithm gp = new LogicalGPAlgorithm(random);
        Individual best = gp.train(trainData);

        long end = System.nanoTime();

        FitnessEvaluator eval = new FitnessEvaluator();

        double acc = eval.getRawAccuracy(best.getTree(), trainData);
        double f = eval.calculateFMeasure(best.getTree(), trainData);
        double runtime = (end - start) / 1e6;

        System.out.println("\n=================================");
        System.out.println(" FINAL LOGICAL GP RESULT");
        System.out.println("=================================");
        System.out.println("Best Individual:");
        System.out.println(best.getTree());
        System.out.println("Training Accuracy: " + acc);
        System.out.println("F-measure:         " + f);
        System.out.println("Runtime (ms):      " + runtime);
    }

    // ==================================================
    // OPTION 2 - TRAIN SYMBOLIC GP
    // ==================================================
    public static void trainSymbolic() throws Exception {

        System.out.print("Enter seed: ");
        long seed = Long.parseLong(scanner.nextLine());

        Dataset trainData = DataLoader.loadCSV("../Breast_train.csv");

        Random random = new Random(seed);

        long start = System.nanoTime();

        SymbolicGPAlgorithm gp = new SymbolicGPAlgorithm(random);
        SymbolicIndividual best = gp.train(trainData);

        long end = System.nanoTime();

        SymbolicFitnessEvaluator eval = new SymbolicFitnessEvaluator();

        double acc = eval.getRawAccuracy(best.getTree(), trainData);
        double f = eval.calculateFMeasure(best.getTree(), trainData);
        double runtime = (end - start) / 1e6;

        System.out.println("\n=================================");
        System.out.println(" FINAL SYMBOLIC GP RESULT");
        System.out.println("=================================");
        System.out.println("Best Individual:");
        System.out.println(best.getTree());
        System.out.println("Training Accuracy: " + acc);
        System.out.println("F-measure:         " + f);
        System.out.println("Runtime (ms):      " + runtime);
    }

    // ==================================================
    // OPTION 3 - TEST LOGICAL GP
    // ==================================================
    public static void testLogical() throws Exception {

        System.out.print("Enter training seed: ");
        long seed = Long.parseLong(scanner.nextLine());

        Dataset trainData = DataLoader.loadCSV("../Breast_train.csv");
        Dataset testData = DataLoader.loadCSV("../Breast_test.csv");

        Random random = new Random(seed);

        LogicalGPAlgorithm gp = new LogicalGPAlgorithm(random);
        Individual best = gp.train(trainData);

        FitnessEvaluator eval = new FitnessEvaluator();

        double testAcc = eval.getRawAccuracy(best.getTree(), testData);
        double testF = eval.calculateFMeasure(best.getTree(), testData);

        System.out.println("\n=================================");
        System.out.println(" LOGICAL GP TEST RESULTS");
        System.out.println("=================================");
        System.out.println("Model:");
        System.out.println(best.getTree());
        System.out.println("Test Accuracy: " + testAcc);
        System.out.println("F-measure:    " + testF);
    }

    // ==================================================
    // OPTION 4 - TEST SYMBOLIC GP
    // ==================================================
    public static void testSymbolic() throws Exception {

        System.out.print("Enter training seed: ");
        long seed = Long.parseLong(scanner.nextLine());

        Dataset trainData = DataLoader.loadCSV("../Breast_train.csv");
        Dataset testData = DataLoader.loadCSV("../Breast_test.csv");

        Random random = new Random(seed);

        SymbolicGPAlgorithm gp = new SymbolicGPAlgorithm(random);
        SymbolicIndividual best = gp.train(trainData);

        SymbolicFitnessEvaluator eval = new SymbolicFitnessEvaluator();

        double testAcc = eval.getRawAccuracy(best.getTree(), testData);
        double testF = eval.calculateFMeasure(best.getTree(), testData);

        System.out.println("\n=================================");
        System.out.println(" SYMBOLIC GP TEST RESULTS");
        System.out.println("=================================");
        System.out.println("Model:");
        System.out.println(best.getTree());
        System.out.println("Test Accuracy: " + testAcc);
        System.out.println("F-measure:    " + testF);
    }

    // ==================================================
    // OPTION 5 - FULL 30 RUN COMPARISON
    // ==================================================
    public static void runComparison() throws Exception {

        Dataset trainData = DataLoader.loadCSV("../Breast_train.csv");
        Dataset testData = DataLoader.loadCSV("../Breast_test.csv");

        double[] logicalAcc = new double[RUNS];
        double[] logicalF = new double[RUNS];

        double[] symbolicAcc = new double[RUNS];
        double[] symbolicF = new double[RUNS];

        double logicalRuntimeTotal = 0;
        double symbolicRuntimeTotal = 0;

        double bestLogical = -1;
        double bestSymbolic = -1;

        int bestLogicalRun = 0;
        int bestSymbolicRun = 0;

        String bestLogicalTree = "";
        String bestSymbolicTree = "";

        System.out.println("\n--------------------------");
        System.out.println(" Running 30 Comparisons");
        System.out.println("--------------------------");

        for (int run = 1; run <= RUNS; run++) {

            long seed = 1234 + run;

            // Logical
            long start1 = System.nanoTime();

            LogicalGPAlgorithm gp1 = new LogicalGPAlgorithm(new Random(seed));
            Individual best1 = gp1.train(trainData);

            long end1 = System.nanoTime();

            FitnessEvaluator eval1 = new FitnessEvaluator();

            double acc1 = eval1.getRawAccuracy(best1.getTree(), testData);
            double f1 = eval1.calculateFMeasure(best1.getTree(), testData);

            logicalAcc[run - 1] = acc1;
            logicalF[run - 1] = f1;

            logicalRuntimeTotal += (end1 - start1) / 1e6;

            if (acc1 > bestLogical) {
                bestLogical = acc1;
                bestLogicalRun = run;
                bestLogicalTree = best1.getTree().toString();
            }

            // Symbolic
            long start2 = System.nanoTime();

            SymbolicGPAlgorithm gp2 = new SymbolicGPAlgorithm(new Random(seed));
            SymbolicIndividual best2 = gp2.train(trainData);

            long end2 = System.nanoTime();

            SymbolicFitnessEvaluator eval2 = new SymbolicFitnessEvaluator();

            double acc2 = eval2.getRawAccuracy(best2.getTree(), testData);
            double f2 = eval2.calculateFMeasure(best2.getTree(), testData);

            symbolicAcc[run - 1] = acc2;
            symbolicF[run - 1] = f2;

            symbolicRuntimeTotal += (end2 - start2) / 1e6;

            if (acc2 > bestSymbolic) {
                bestSymbolic = acc2;
                bestSymbolicRun = run;
                bestSymbolicTree = best2.getTree().toString();
            }

            System.out.println("Run " + run + " complete.");
        }

        System.out.println("\n--------------------------------");
        System.out.println("        FINAL SUMMARY");
        System.out.println("--------------------------------");

        System.out.println("Logical Mean Test Accuracy: " + mean(logicalAcc));
        System.out.println("Logical Mean F-measure:     " + mean(logicalF));
        System.out.println("Logical Mean Runtime (ms):  " + (logicalRuntimeTotal / RUNS));
        System.out.println("Best Logical Run:           " + bestLogicalRun);
        System.out.println("Best Logical Accuracy:      " + bestLogical);
        System.out.println("Best Logical Individual:");
        System.out.println(bestLogicalTree);

        System.out.println();

        System.out.println("Symbolic Mean Test Accuracy: " + mean(symbolicAcc));
        System.out.println("Symbolic Mean F-measure:     " + mean(symbolicF));
        System.out.println("Symbolic Mean Runtime (ms):  " + (symbolicRuntimeTotal / RUNS));
        System.out.println("Best Symbolic Run:           " + bestSymbolicRun);
        System.out.println("Best Symbolic Accuracy:      " + bestSymbolic);
        System.out.println("Best Symbolic Individual:");
        System.out.println(bestSymbolicTree);

        System.out.println();

        WilcoxonTest.run(logicalAcc, symbolicAcc, "Wilcoxon_Test_Accuracy.csv");
        WilcoxonTest.run(logicalF, symbolicF, "Wilcoxon_Test_FMeasure.csv");
        System.out.println("Results saved in 'Logical_GP_results.csv' & 'Symbolic_GP_results.csv' respectively.");
        System.out.println("Wilcoxon test files created.");
    }

    public static double mean(double[] values) {
        double sum = 0;
        for (double v : values) sum += v;
        return sum / values.length;
    }
}