package com.ramimartin.ga.chromosome;

import com.ramimartin.ga.gene.BitGene;

public class BitChromosome extends Chromosome<BitGene> {

    public BitChromosome() {
        super(50);
    }

    @Override
    public void generateGenes() {
        for(int i = 0; i < lenghGene; i++){
            getListGene().add(new BitGene());
        }
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < getChromosomeLength(); i++) {
            output += getGene(i).getValue();
        }
        return output;
    }
}
