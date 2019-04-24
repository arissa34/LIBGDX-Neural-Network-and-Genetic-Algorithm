package com.ramimartin.ga.utils;

import com.ramimartin.ga.chromosome.Chromosome;
import com.ramimartin.ga.gene.Gene;
import com.ramimartin.ga.individual.Individual;

public class MutationUtils {

    public static void swapGene(Individual individual){
        Chromosome chromosome = individual.getChromosome();
        for (int geneIndex = 0; geneIndex < chromosome.getChromosomeLength(); geneIndex++) {
            int newGenePos = (int) (Math.random() * chromosome.getChromosomeLength());
            Gene gene1 = chromosome.getGene(newGenePos);
            Gene gene2 = chromosome.getGene(geneIndex);
            chromosome.setGene(geneIndex, gene1);
            chromosome.setGene(newGenePos, gene2);
        }
    }
}
