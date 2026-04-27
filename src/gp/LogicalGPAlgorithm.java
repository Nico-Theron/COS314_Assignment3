package gp;

import data.Dataset;
import fitness.FitnessEvaluator;
import operators.Crossover;
import operators.Mutation;
import operators.Selection;
import tree.TreeGenerator;

import java.util.Random;

public class LogicalGPAlgorithm {
    private int populationSize;
    private int maxGenerations;
    private int initialMaxDepth;
    private double crossoverRate;
    private double mutationRate;

    private TreeGenerator generator;
    private FitnessEvaluator evaluator;
    private Selection selection;
    private Crossover crossover;
    private Mutation mutation;
    private Random random;

    public LogicalGPAlgorithm(Random random) {
        this.random = random;

        this.populationSize = 200;
        this.maxGenerations = 100;
        this.initialMaxDepth = 3;
        this.crossoverRate = 0.80;
        this.mutationRate = 0.10;

        this.generator = new TreeGenerator(random);
        this.evaluator = new FitnessEvaluator();
        this.selection = new Selection(random, 5);
        this.crossover = new Crossover(random);
        this.mutation = new Mutation(random, generator, 2);
    }

    public Individual train(Dataset trainData) {
        Population population = new Population(populationSize, generator, initialMaxDepth);
        population.evaluate(evaluator, trainData);

        Individual bestOverall = population.getBestIndividual().copy();

        for (int generation = 0; generation <= maxGenerations; generation++) {
            population.evaluate(evaluator, trainData);

            Individual bestThisGeneration = population.getBestIndividual();

            if (bestThisGeneration.getFitness() > bestOverall.getFitness()) {
                bestOverall = bestThisGeneration.copy();
            }

            System.out.println("Generation " + generation);
            System.out.println("Best accuracy: " + (bestThisGeneration.getFitness() * 100) + "%");
            System.out.println("Best tree: " + bestThisGeneration.getTree());
            System.out.println("----------------------------------------");

            if (generation == maxGenerations) {
                break;
            }

            Population nextPopulation = new Population(populationSize, generator, initialMaxDepth);

            // Elitism: keep the best tree unchanged
            nextPopulation.setIndividual(0, bestOverall.copy());

            for (int i = 1; i < populationSize; i++) {
                Individual parent1 = selection.tournamentSelection(population);
                Individual parent2 = selection.tournamentSelection(population);

                Individual child;

                if (random.nextDouble() < crossoverRate) {
                    child = crossover.crossover(parent1, parent2);
                } else {
                    child = parent1.copy();
                }

                if (random.nextDouble() < mutationRate) {
                    child = mutation.mutate(child);
                }

                double fitness = evaluator.calculateAccuracy(child.getTree(), trainData);
                child.setFitness(fitness);

                nextPopulation.setIndividual(i, child);
            }

            population = nextPopulation;
        }

        return bestOverall;
    }
}