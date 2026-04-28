package gp;

import symbolicTree.SymbolicNode;

public class SymbolicIndividual {
    private SymbolicNode tree;
    private double fitness;

    public SymbolicIndividual(SymbolicNode tree) {
        this.tree = tree;
        this.fitness = 0.0;
    }

    public SymbolicNode getTree() {
        return tree;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public SymbolicIndividual copy() {
        SymbolicIndividual copy = new SymbolicIndividual(tree.copy());
        copy.setFitness(fitness);
        return copy;
    }

    @Override
    public String toString() {
        return "Fitness: " + (fitness * 100) + "%\nTree: " + tree.toString();
    }
}
