package com.ramimartin.ga.system;

import com.badlogic.gdx.Gdx;
import com.ramimartin.ga.fitness.Fitness;
import com.ramimartin.ga.population.BitPopulationComponent;
import com.ramimartin.ga.population.Population;

public class BitSystem extends SearchSpaceSystem<BitPopulationComponent> {

    public BitSystem() {
        super(BitPopulationComponent.class);
    }

    @Override
    public boolean isTerminationConditionMet(Population population) {
        for (int i = 0; i < population.size(); ++i) {
            Fitness fitness = population.getFittest(i).getFitness();
            if (fitness.getValue() == 1) return true;
        }
        return false;
    }

    @Override
    public void onTerminateConditionMet(Population population) {
        Gdx.app.log("", "IS TERMINATED generation : " + population.generation + " gene : " + population.getFittest(0));
        setProcessing(false);
    }

    @Override
    public boolean isGenerationTerminate(Population population) {
        return true;
    }
}
