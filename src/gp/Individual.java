package gp;

import tree.Node;

public class Individual {
    private Node tree;
    private double fitness;

    public Individual(Node tree) {
        this.tree = tree;
        this.fitness = 0.0;
    }

    public Node getTree() {
        return tree;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public Individual copy() {
        Individual copy = new Individual(tree.copy());
        copy.setFitness(fitness);
        return copy;
    }

    @Override
    public String toString() {
        return "Fitness: " + (fitness * 100) + "%\nTree: " + tree.toString();
    }
}