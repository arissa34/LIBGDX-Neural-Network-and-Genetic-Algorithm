package com.ramimartin.ga.individual;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.ramimartin.ga.chromosome.Chromosome;
import com.ramimartin.ga.fitness.Fitness;

public abstract class Individual <T extends Chromosome> extends Entity {

    protected Fitness fitness;
    protected T chromosome;

    public Individual(){
    }

    public void addChromosome(T chromosome){
        this.chromosome = chromosome;
        add(this.chromosome);
    }

    public  T getChromosome(){
        return chromosome;
    }

    public Fitness getFitness() {
        return fitness;
    }

    public void addFitness(Fitness fitness) {
        this.fitness = fitness;
        add(fitness);
    }

    public void addToPopulation(Engine engine){
        if(!engine.getEntities().contains(this, true))
            engine.addEntity(this);
    }

    public void removeToPopulation(Engine engine){
        engine.removeEntity(this);
    }

    public boolean hasFitness(){
        return fitness != null;
    }
}
