package com.ramimartin.ga.population;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.ramimartin.ga.individual.Individual;

import java.util.Comparator;
import java.util.Random;

public abstract class Population<I extends Individual, P extends PopulationComponent> extends Entity implements Comparator<I> {

    public float populationFitness;
    public float bestFitnessEver;
    public int generation;

    public int populationSize;
    protected float crossoverRate;
    protected float mutationRate;
    protected int elitismCount;

    protected Array<I> population;

    public Population(int populationSize, float mutationRate, float crossoverRate, int elitismCount){

        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
        this.generation = 1;

        population = new Array<I>(populationSize);
    }

    public abstract void generateNewPopulation();

    public void addIndividual(I individual){
        if(individual.hasFitness()) {
            population.add(individual);
        }else{
            Gdx.app.log("", "NO FITNESS FOUND !!");
        }
    }

    public Array<I> getListIndividues(){
        return population;
    }

    /**
     * Find an individual in the population by its fitness
     *
     * This method lets you select an individual in order of its fitness. This
     * can be used to find the single strongest individual (eg, if you're
     * testing for a solution), but it can also be used to find weak individuals
     * (if you're looking to cull the population) or some of the strongest
     * individuals (if you're using "elitism").
     *
     * @param index
     *            The index of the individual you want, sorted by fitness. 0 is
     *            the strongest, population.length - 1 is the weakest.
     * @return individual Individual at offset
     */
    public I getFittest(int index) {
        population.sort(this);
        return population.get(index);
    }

    @Override
    public int compare(I i1, I i2) {
        if(i1 == null || i2 == null || i1.getFitness() == null || i2.getFitness() == null ) return 0;
        return Float.compare(i2.getFitness().getValue(), i1.getFitness().getValue());
    }

    public void sort(){
        population.sort(this);
    }

    public void setPopulationFitness(float fitness) {
        this.populationFitness = fitness;
    }

    public double getPopulationFitness() {
        return this.populationFitness;
    }

    public int size() {
        return population.size;
    }

    public I setIndividual(int index, I individual) {
        population.set(index, individual);
        return population.get(index);
    }

    public I getIndividual(int index) {
        return population.get(index);
    }

    /**
     * Shuffles the population in-place
     */
    public void shuffle() {
        Random rnd = new Random();
        for (int i = population.size - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            I a = population.get(index);
            population.set(index, population.get(i));
            population.set(i, a);
        }
    }

    public abstract void crossover();
    public abstract void mutate();

    public abstract P getPopulationComponent();

    public Population startGeneticSelection(Engine engine){
        add(getPopulationComponent());
        engine.addEntity(this);
        return this;
    }
}
