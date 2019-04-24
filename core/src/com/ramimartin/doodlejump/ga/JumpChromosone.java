package com.ramimartin.doodlejump.ga;

import com.badlogic.gdx.utils.Array;
import com.ramimartin.ga.chromosome.Chromosome;
import com.ramimartin.neural.Axon;

public class JumpChromosone extends Chromosome<AxonGene> {

    private Array<Axon> listAxon;

    public JumpChromosone(Array<Axon> listAxon) {
        super(listAxon.size);
        this.listAxon = listAxon;
    }

    @Override
    public void generateGenes() {
        for(int i = 0; i < lenghGene; i++){
            getListGene().add(new AxonGene(listAxon.get(i)));
        }
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < getChromosomeLength(); i++) {
            output += getGene(i).getAxon().getWeight()+",";
        }
        return output;
    }
}
