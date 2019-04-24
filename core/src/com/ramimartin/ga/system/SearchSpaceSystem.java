package com.ramimartin.ga.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.ramimartin.ga.fitness.Fitness;
import com.ramimartin.ga.population.Population;
import com.ramimartin.ga.population.PopulationComponent;


public abstract class SearchSpaceSystem<P extends PopulationComponent> extends IteratingSystem {

    protected int maxGeneration = -1;
    protected float maxTime = -1;
    protected ComponentMapper<P> populationComponentMapper;

    public SearchSpaceSystem(Class<P> type) {
        super(Family.all(type).get());
        populationComponentMapper = ComponentMapper.getFor(type);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Population population = populationComponentMapper.get(entity).population;
        evalPopulation(population);
        if (isTerminationConditionMet(population)) {
            onTerminateConditionMet(population);
        } else if(isGenerationTerminate(population)){
            //Gdx.app.log("", "generation : " + population.generation + " best : " + population.getFittest(0).getChromosome().toString() + " fitness : " + population.getFittest(0).getFitness().getValue());
            population.crossover();
            population.mutate();
            population.generation++;
        }
    }

    public void evalPopulation(Population population) {
        population.populationFitness = 0;
        for (int i = 0; i < population.size(); ++i) {
            Fitness fitness = population.getIndividual(i).getFitness();
            population.populationFitness += fitness.caclFitness();
            //Gdx.app.log("", "fitness : "+fitness.getValue());
        }
    }

    public abstract boolean isTerminationConditionMet(Population population);
    public abstract void onTerminateConditionMet(Population population);
    public abstract boolean isGenerationTerminate(Population population);
}
