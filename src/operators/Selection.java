package operators;

import gp.Individual;
import gp.Population;

import java.util.Random;

public class Selection {

    private Random random;
    private int tournamentSize;

    public Selection(Random random, int tournamentSize) {
        this.random = random;
        this.tournamentSize = tournamentSize;
    }

    public Individual tournamentSelection(Population population) {
        Individual best = null;

        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(population.size());
            Individual candidate = population.getIndividual(randomIndex);

            if (best == null || candidate.getFitness() > best.getFitness()) {
                best = candidate;
            }
        }

        return best.copy(); //return a copy so we don’t modify original trees later

    }
}