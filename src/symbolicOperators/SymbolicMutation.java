package symbolicOperators;

import gp.Individual;
import gp.SymbolicIndividual;
import symbolicTree.SymbolicNode;
import symbolicTree.SymbolicTreeGenerator;

import java.util.Random;

public class SymbolicMutation {
    private Random random;
    private SymbolicTreeGenerator generator;
    private int mutationDepth;

    public SymbolicMutation(Random random, SymbolicTreeGenerator generator, int mutationDepth) {
        this.random = random;
        this.generator = generator;
        this.mutationDepth = mutationDepth;
    }

    public SymbolicIndividual mutate(SymbolicIndividual individual) {
        SymbolicNode originalTree = individual.getTree().copy();

        int mutationPoint = random.nextInt(originalTree.countNodes());
        SymbolicNode newSubtree = generator.generateGrowTree(mutationDepth);

        SymbolicNode mutatedTree = originalTree.replaceNodeAt(mutationPoint, newSubtree);

        return new SymbolicIndividual(mutatedTree);
    }
}
