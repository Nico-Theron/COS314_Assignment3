package symbolicOperators;

import gp.Individual;
import gp.SymbolicIndividual;
import symbolicTree.SymbolicNode;

import java.util.Random;

public class SymbolicCrossover {
    private Random random;

    public SymbolicCrossover(Random random) {
        this.random = random;
    }

    public SymbolicIndividual crossover(SymbolicIndividual parent1, SymbolicIndividual parent2) {
        SymbolicNode tree1 = parent1.getTree().copy();
        SymbolicNode tree2 = parent2.getTree().copy();

        int point1 = random.nextInt(tree1.countNodes());
        int point2 = random.nextInt(tree2.countNodes());

        SymbolicNode subtreeFromParent2 = tree2.getNodeAt(point2).copy();

        SymbolicNode childTree = tree1.replaceNodeAt(point1, subtreeFromParent2);

        return new SymbolicIndividual(childTree);
    }
}
