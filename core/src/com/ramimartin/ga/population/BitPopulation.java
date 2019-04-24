package com.ramimartin.ga.population;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.ramimartin.ga.chromosome.Chromosome;
import com.ramimartin.ga.utils.CrossoverUtils;
import com.ramimartin.ga.utils.SelectionUtils;
import com.ramimartin.ga.individual.BitIndividual;

public class BitPopulation extends Population<BitIndividual, BitPopulationComponent> {

    public BitPopulation(int populationSize, float mutationRate, float crossoverRate, int elitismCount) {
        super(populationSize, mutationRate, crossoverRate, elitismCount);
    }

    @Override
    public void generateNewPopulation() {
        for (int i = 0; i < populationSize; i++) {
            BitIndividual individual = new BitIndividual();
            individual.generate();
            addIndividual(individual);
        }
    }

    @Override
    public void crossover() {
        Array<BitIndividual> newPopulation = new Array<BitIndividual>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            BitIndividual individual = new BitIndividual();
            newPopulation.add(individual);
        }

        for (int populationIndex = 0; populationIndex < size(); populationIndex++) {
            BitIndividual parent1 = getFittest(populationIndex);

            // Apply crossover to this individual?
            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {

                BitIndividual offspring = new BitIndividual();
                offspring.generate();

                // Find second parent
                BitIndividual parent2 = (BitIndividual) SelectionUtils.wheelRoulette(this);

                CrossoverUtils.uniform(parent1, parent2, offspring);

                // Add offspring to new population
                newPopulation.set(populationIndex, offspring);
            } else {
                // Add individual to new population without applying crossover
                newPopulation.set(populationIndex, parent1);
            }
        }

        population.clear();
        population.addAll(newPopulation);
    }

    @Override
    public void mutate() {
        for (int i = 0; i < population.size; ++i) {
            //MutationUtils.swapGene(population.get(i));
            Chromosome chromosome = population.get(i).getChromosome();
            for (int g = 0; g < chromosome.getChromosomeLength(); g++) {
                if (i > this.elitismCount) {
                    float r = MathUtils.random(0f, 1f);
                    if (mutationRate > r) {
                        chromosome.getGene(g).mutate();
                    }
                }
            }
        }
    }

    @Override
    public BitPopulationComponent getPopulationComponent() {
        return new BitPopulationComponent(this);
    }
}
