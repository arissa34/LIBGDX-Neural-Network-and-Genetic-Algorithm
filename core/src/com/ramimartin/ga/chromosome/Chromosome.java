package com.ramimartin.ga.chromosome;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.ramimartin.ga.gene.Gene;

public abstract class Chromosome<T extends Gene> implements Component {

    protected int lenghGene;
    private Array<T> listGene;

    public Chromosome(int lenghGene){
        this.lenghGene = lenghGene;
        listGene = new Array<T>(lenghGene);
    }

    public Array<T> getListGene() {
        return listGene;
    }

    public void setListGene(Array<T> listGene) {
        this.listGene = listGene;
    }

    public T getGene(int i){
        return listGene.get(i);
    }
    public void setGene(int i, T gene){
        listGene.set(i, gene);
    }

    public int getChromosomeLength() {
        return lenghGene;
    }

    public abstract void generateGenes();

}
