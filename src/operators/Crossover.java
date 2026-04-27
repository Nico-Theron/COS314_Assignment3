package operators;

import gp.Individual;
import tree.Node;

import java.util.Random;

public class Crossover {
    private Random random;

    public Crossover(Random random) {
        this.random = random;
    }

    public Individual crossover(Individual parent1, Individual parent2) {
        Node tree1 = parent1.getTree().copy();
        Node tree2 = parent2.getTree().copy();

        int point1 = random.nextInt(tree1.countNodes());
        int point2 = random.nextInt(tree2.countNodes());

        Node subtreeFromParent2 = tree2.getNodeAt(point2).copy();

        Node childTree = tree1.replaceNodeAt(point1, subtreeFromParent2);

        return new Individual(childTree);
    }
}