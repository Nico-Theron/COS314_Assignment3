package symbolicOperators;

import gp.SymbolicIndividual;
import gp.SymbolicPopulation;

import java.util.Random;

public class SymbolicSelection {
    private Random random;
    private int tournamentSize;

    public SymbolicSelection(Random random, int tournamentSize) {
        this.random = random;
        this.tournamentSize = tournamentSize;
    }

    public SymbolicIndividual tournamentSelection(SymbolicPopulation population) {
        SymbolicIndividual best = null;

        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(population.size());
            SymbolicIndividual candidate = population.getIndividual(randomIndex);

            if (best == null || candidate.getFitness() > best.getFitness()) {
                best = candidate;
            }
        }

        return best.copy(); //return a copy so we don’t modify original trees later

    }
}
