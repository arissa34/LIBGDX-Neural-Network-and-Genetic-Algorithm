package com.ramimartin.ga.utils;

import com.ramimartin.ga.individual.Individual;

public class CrossoverUtils {

    /******************
     *
     * Select gene randomly
     *
     */
    public static Individual uniform(Individual parent1, Individual parent2, Individual offspring) {

        for (int geneIndex = 0; geneIndex < parent1.getChromosome().getChromosomeLength(); geneIndex++) {
            // Use half of parent1's genes and half of parent2's genes
            if (0.5 > Math.random()) {
                offspring.getChromosome().setGene(geneIndex, parent1.getChromosome().getGene(geneIndex).clone());
            } else {
                offspring.getChromosome().setGene(geneIndex, parent2.getChromosome().getGene(geneIndex).clone());
            }
        }

        return offspring;
    }

    /*******************
     *
     * parent 1     AA|AAA
     * parent 2     BB|BBB
     * offspring    AA|BBB
     *   swapPoint--->|
     *
     */
    public static Individual singlePoint(Individual parent1, Individual parent2, Individual offspring, int swapPoint) {

        for (int geneIndex = 0; geneIndex < parent1.getChromosome().getChromosomeLength(); geneIndex++) {
            if (geneIndex < swapPoint) {
                offspring.getChromosome().setGene(geneIndex, parent1.getChromosome().getGene(geneIndex).clone());
            } else {
                offspring.getChromosome().setGene(geneIndex, parent2.getChromosome().getGene(geneIndex).clone());
            }
        }

        return offspring;
    }


    /*******************
     *
     * parent 1     AA|AA|A
     * parent 2     BB|BB|B
     * offspring    AA|BB|A
     *      endPos--->|  |<----startPos
     *
     */
    public static Individual twoPoint(Individual parent1, Individual parent2, Individual offspring, int endPosition, int startPosition) {

        for (int geneIndex = 0; geneIndex < parent1.getChromosome().getChromosomeLength(); geneIndex++) {
            if (geneIndex < endPosition || geneIndex > startPosition){
                offspring.getChromosome().setGene(geneIndex, parent2.getChromosome().getGene(geneIndex).clone());
            } else{
                offspring.getChromosome().setGene(geneIndex, parent1.getChromosome().getGene(geneIndex).clone());
            }
        }

        return offspring;
    }
}
