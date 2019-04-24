package com.ramimartin.ga.fitness;

import com.ramimartin.ga.chromosome.BitChromosome;

public class BitFitness extends Fitness {

    private BitChromosome chromosome;

    public BitFitness(BitChromosome chromosome) {
        this.chromosome = chromosome;
    }

    @Override
    public float caclFitness() {
        int correctGenes = 0;
        for (int geneIndex = 0; geneIndex < chromosome.getListGene().size; geneIndex++) {
            if (chromosome.getGene(geneIndex).getValue() == 1) {
                correctGenes += 1;
            }
        }
        fitness =  (float) correctGenes / chromosome.getChromosomeLength();
        return fitness;
    }
}
