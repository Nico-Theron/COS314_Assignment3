package gp;

import data.Dataset;
import fitness.SymbolicFitnessEvaluator;
import symbolicTree.SymbolicTreeGenerator;

public class SymbolicPopulation {
    private SymbolicIndividual[] individuals;

    public SymbolicPopulation(int size, SymbolicTreeGenerator generator, int maxDepth) {
        individuals = new SymbolicIndividual[size];

        for (int i = 0; i < size; i++) {
            individuals[i] = new SymbolicIndividual(generator.generateRampedHalfAndHalf(maxDepth));
        }
    }

    public SymbolicIndividual[] getIndividuals() {
        return individuals;
    }

    public int size() {
        return individuals.length;
    }

    public SymbolicIndividual getIndividual(int index) {
        return individuals[index];
    }

    public void setIndividual(int index, SymbolicIndividual individual) {
        individuals[index] = individual;
    }

    public void evaluate(SymbolicFitnessEvaluator evaluator, Dataset dataset) {
        for (int i = 0; i < individuals.length; i++) {
            double fitness = evaluator.calculateAccuracy(individuals[i].getTree(), dataset);
            individuals[i].setFitness(fitness);
        }
    }

    public SymbolicIndividual getBestIndividual() {
        SymbolicIndividual best = individuals[0];

        for (int i = 1; i < individuals.length; i++) {
            if (individuals[i].getFitness() > best.getFitness()) {
                best = individuals[i];
            }
        }

        return best;
    }
}
