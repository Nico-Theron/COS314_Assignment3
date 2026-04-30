package gp;

import data.Dataset;
import fitness.SymbolicFitnessEvaluator;
import symbolicOperators.SymbolicCrossover;
import symbolicOperators.SymbolicMutation;
import symbolicOperators.SymbolicSelection;
import symbolicTree.SymbolicTreeGenerator;

import java.util.Random;

public class SymbolicGPAlgorithm {
    private int populationSize;
    private int maxGenerations;
    private int initialMaxDepth;
    private double crossoverRate;
    private double mutationRate;

    private SymbolicTreeGenerator generator;
    private SymbolicFitnessEvaluator evaluator;
    private SymbolicSelection selection;
    private SymbolicCrossover crossover;
    private SymbolicMutation mutation;
    private Random random;

    public SymbolicGPAlgorithm(Random random) {
        this.random = random;

        this.populationSize = 200;
        this.maxGenerations = 100;
        this.initialMaxDepth = 5;
        this.crossoverRate = 0.80;
        this.mutationRate = 0.15;

        this.generator = new SymbolicTreeGenerator(random);
        this.evaluator = new SymbolicFitnessEvaluator();
        this.selection = new SymbolicSelection(random, 5);
        this.crossover = new SymbolicCrossover(random);
        this.mutation = new SymbolicMutation(random, generator, 2);
    }

    public SymbolicIndividual train(Dataset trainData) {
        SymbolicPopulation population = new SymbolicPopulation(populationSize, generator, initialMaxDepth);
        population.evaluate(evaluator, trainData);

        SymbolicIndividual bestOverall = population.getBestIndividual().copy();

        for (int generation = 0; generation <= maxGenerations; generation++) {
            population.evaluate(evaluator, trainData);

            SymbolicIndividual bestThisGeneration = population.getBestIndividual();

            if (bestThisGeneration.getFitness() > bestOverall.getFitness()) {
                bestOverall = bestThisGeneration.copy();
            }

            double accuracy = bestThisGeneration.getFitness();

            double fMeasure = evaluator.calculateFMeasure(bestThisGeneration.getTree(), trainData);

            System.out.println("Generation " + generation);
            System.out.println("Accuracy: " + (accuracy * 100) + "%");
            System.out.println("F-measure: " + fMeasure);
            System.out.println("Tree: " + bestThisGeneration.getTree());
            System.out.println("----------------------------------------");

            if (generation == maxGenerations) {
                break;
            }

            SymbolicPopulation nextPopulation = new SymbolicPopulation(populationSize, generator, initialMaxDepth);

            nextPopulation.setIndividual(0, bestOverall.copy());

            for (int i = 1; i < populationSize; i++) {
                SymbolicIndividual parent1 = selection.tournamentSelection(population);
                SymbolicIndividual parent2 = selection.tournamentSelection(population);

                SymbolicIndividual child;

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