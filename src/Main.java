import data.DataLoader;
import data.Dataset;
import gp.Individual;
import gp.LogicalGPAlgorithm;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        Dataset trainData = DataLoader.loadCSV("Breast_train.csv");

        Random random = new Random(1234);

        LogicalGPAlgorithm gp = new LogicalGPAlgorithm(random);
        Individual best = gp.train(trainData);

        System.out.println("FINAL BEST TREE:");
        System.out.println(best);
    }
}