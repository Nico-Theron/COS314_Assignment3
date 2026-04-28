package operators;

import gp.Individual;
import tree.Node;
import tree.TreeGenerator;

import java.util.Random;

public class Mutation {
    private Random random;
    private TreeGenerator generator;
    private int mutationDepth;

    public Mutation(Random random, TreeGenerator generator, int mutationDepth) {
        this.random = random;
        this.generator = generator;
        this.mutationDepth = mutationDepth;
    }

    public Individual mutate(Individual individual) {
        Node originalTree = individual.getTree().copy();

        int mutationPoint = random.nextInt(originalTree.countNodes());
        Node newSubtree = generator.generateGrowTree(mutationDepth);

        Node mutatedTree = originalTree.replaceNodeAt(mutationPoint, newSubtree);

        return new Individual(mutatedTree);
    }
}