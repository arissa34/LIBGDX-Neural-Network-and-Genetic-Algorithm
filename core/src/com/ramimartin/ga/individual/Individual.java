package com.ramimartin.ga.individual;

import com.ramimartin.ga.chromosome.Chromosome;
import com.ramimartin.ga.fitness.Fitness;

public abstract class Individual <T extends Chromosome> {

    protected Fitness fitness;
    protected T chromosome;

    public Individual(){
    }

    public void addChromosome(T chromosome){
        this.chromosome = chromosome;
    }

    public  T getChromosome(){
        return chromosome;
    }

    public Fitness getFitness() {
        return fitness;
    }

    public void addFitness(Fitness fitness) {
        this.fitness = fitness;
    }

    public boolean hasFitness(){
        return fitness != null;
    }
}
