package gp;

import data.Dataset;
import fitness.FitnessEvaluator;
import tree.TreeGenerator;

public class Population {
    private Individual[] individuals;

    public Population(int size, TreeGenerator generator, int maxDepth) {
        individuals = new Individual[size];

        for (int i = 0; i < size; i++) {
            individuals[i] = new Individual(generator.generateRampedHalfAndHalf(maxDepth));
        }
    }

    public Individual[] getIndividuals() {
        return individuals;
    }

    public int size() {
        return individuals.length;
    }

    public Individual getIndividual(int index) {
        return individuals[index];
    }

    public void setIndividual(int index, Individual individual) {
        individuals[index] = individual;
    }

    public void evaluate(FitnessEvaluator evaluator, Dataset dataset) {
        for (int i = 0; i < individuals.length; i++) {
            double fitness = evaluator.calculateAccuracy(individuals[i].getTree(), dataset);
            individuals[i].setFitness(fitness);
        }
    }

    public Individual getBestIndividual() {
        Individual best = individuals[0];

        for (int i = 1; i < individuals.length; i++) {
            if (individuals[i].getFitness() > best.getFitness()) {
                best = individuals[i];
            }
        }

        return best;
    }
}