package com.ramimartin.ga.individual;

import com.ramimartin.ga.chromosome.BitChromosome;
import com.ramimartin.ga.fitness.BitFitness;

public class BitIndividual extends Individual<BitChromosome> {

    public BitIndividual(){
    }

    public void generate(){
        addChromosome(new BitChromosome());
        addFitness(new BitFitness(chromosome));
        chromosome.generateGenes();
    }

    public String toString() {
        return chromosome.toString();
    }
}
