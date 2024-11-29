package com.ramimartin.ga.utils;

import com.badlogic.gdx.math.MathUtils;
import com.ramimartin.Config;
import com.ramimartin.doodlejump.ga.AxonGene;
import com.ramimartin.ga.chromosome.Chromosome;
import com.ramimartin.ga.gene.Gene;
import com.ramimartin.ga.individual.Individual;

public class MutationUtils {

    public static boolean swapGene(Individual individual) {
        boolean hasChanged = false;
        Chromosome chromosome = individual.getChromosome();
        for (int geneIndex = 0; geneIndex < chromosome.getChromosomeLength(); geneIndex++) {
            int newGenePos = (int) (Math.random() * chromosome.getChromosomeLength());
            Gene gene1 = chromosome.getGene(newGenePos);
            Gene gene2 = chromosome.getGene(geneIndex);
            chromosome.setGene(geneIndex, gene1);
            chromosome.setGene(newGenePos, gene2);
            hasChanged = true;
        }
        return hasChanged;
    }

    public static boolean randomMutation(Individual individual) {
        boolean hasChanged = false;
        Chromosome chromosome = individual.getChromosome();
        for (int g = 0; g < chromosome.getChromosomeLength(); g++) {
            if (Math.random() < Config.mutationRate) {
                chromosome.getGene(g).mutate();
                hasChanged = true;
            }

        }
        return hasChanged;
    }

    public static boolean perturbWeight(Individual individual) {
        boolean hasChanged = false;
        float maxPerturbation = 0.3f;
        Chromosome chromosome = individual.getChromosome();
        for (int g = 0; g < chromosome.getChromosomeLength(); g++) {
            if (Math.random() < Config.mutationRate) {
                float weight = ((AxonGene) chromosome.getGene(g)).getAxon().getWeight();
                float perturbation = (float) (Math.random() * maxPerturbation - maxPerturbation / 2);
                ((AxonGene) chromosome.getGene(g)).getAxon().setWeight(weight + perturbation);
                hasChanged = true;
            }
        }
        return hasChanged;
    }

    public static boolean invertWeight(Individual individual) {
        boolean hasChanged = false;
        Chromosome chromosome = individual.getChromosome();
        for (int g = 0; g < chromosome.getChromosomeLength(); g++) {
            if (Math.random() < Config.mutationRate) {
                float weight = ((AxonGene) chromosome.getGene(g)).getAxon().getWeight();
                ((AxonGene) chromosome.getGene(g)).getAxon().setWeight(-weight);
                hasChanged = true;
            }
        }
        return hasChanged;
    }
}
