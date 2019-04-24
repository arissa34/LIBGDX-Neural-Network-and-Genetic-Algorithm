package com.ramimartin.ga.fitness;

import com.badlogic.ashley.core.Component;

public abstract class Fitness implements Component {

    protected float fitness = 0;

    public Fitness() {
    }

    public float getValue() {
        return fitness;
    }

    public void reset(){
        fitness = 0;
    }

    public abstract float caclFitness();
}
