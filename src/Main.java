import data.DataLoader;
import data.Dataset;
import fitness.FitnessEvaluator;
import tree.Node;
import tree.TreeGenerator;
import gp.Individual;
import gp.Population;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        Dataset trainData = DataLoader.loadCSV("Breast_train.csv");

        Random random = new Random(1234);
        TreeGenerator generator = new TreeGenerator(random);
        FitnessEvaluator evaluator = new FitnessEvaluator();

        Node tree = generator.generateRampedHalfAndHalf(3);

        double accuracy = evaluator.calculateAccuracy(tree, trainData);

        System.out.println("Generated tree:");
        System.out.println(tree);
        System.out.println("Accuracy: " + (accuracy * 100) + "%");

        Population population = new Population(200, generator, 3);
        population.evaluate(evaluator, trainData);

        Individual best = population.getBestIndividual();

        System.out.println("Best individual in population:");
        System.out.println(best);
    }
}