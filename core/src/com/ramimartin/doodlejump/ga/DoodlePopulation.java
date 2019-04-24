package com.ramimartin.doodlejump.ga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.ramimartin.doodlejump.camera.VerticalCamera;
import com.ramimartin.doodlejump.level.LevelManager;
import com.ramimartin.doodlejump.model.DoodleModel;
import com.ramimartin.ga.chromosome.Chromosome;
import com.ramimartin.ga.population.Population;
import com.ramimartin.ga.utils.CrossoverUtils;
import com.ramimartin.ga.utils.SelectionUtils;

public class DoodlePopulation extends Population<DoodleIndividual, DoodlePopulationComponent> {

    public DoodlePopulation(int populationSize, float mutationRate, float crossoverRate, int elitismCount) {
        super(populationSize, mutationRate, crossoverRate, elitismCount);
    }

    @Override
    public void generateNewPopulation() {
        for (int i = 0; i < populationSize; i++) {
            DoodleModel individual = new DoodleModel();
            addIndividual(individual.getDoodleIndividual());
            individual.attachEntity();
        }
    }

    @Override
    public void crossover() {
        for (int i = 0; i < populationSize; i++) {
            population.get(i).getDoodleModel().dettachEntity();
        }
        Array<DoodleIndividual> newPopulation = new Array<DoodleIndividual>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            //DoodleModel individual = new DoodleModel();
            //newPopulation.add(individual.getDoodleIndividual());
            newPopulation.add(null);
        }

        for (int populationIndex = 0; populationIndex < size(); populationIndex++) {
            DoodleIndividual parent1 = getFittest(populationIndex);

            // Apply crossover to this individual?
            if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {

                DoodleModel offspring = new DoodleModel();

                // Find second parent
                //DoodleIndividual parent2 = (DoodleIndividual) SelectionUtils.wheelRoulette(this);
                DoodleIndividual parent2 = (DoodleIndividual) SelectionUtils.tournament(this, 5);

                CrossoverUtils.uniform(parent1, parent2, offspring.getDoodleIndividual());

                // Add offspring to new population
                newPopulation.set(populationIndex, offspring.getDoodleIndividual());
            } else {
                // Add individual to new population without applying crossover
                newPopulation.set(populationIndex, parent1);
            }
        }
        for (int i = 0; i < populationSize; i++) {
            if (!newPopulation.contains(population.get(i), true)) {
                population.get(i).getDoodleModel().dispose();
            }
        }

        for (int i = 0; i < populationSize; i++) {
            if (newPopulation.get(i) == null) {
                DoodleModel doodle = new DoodleModel();
                newPopulation.add(doodle.getDoodleIndividual());
            }
        }

        population.clear();
        population.addAll(newPopulation);
        for (int i = 0; i < populationSize; i++) {
            population.get(i).getDoodleModel().reset();
            population.get(i).getDoodleModel().attachEntity();
        }

        VerticalCamera.get().snapToTarget(0, 0);
        VerticalCamera.get().yHeigher = 0;
        LevelManager.get().reset();
        LevelManager.get().generate();
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
            population.get(i).loadChromosoneToBrain();
        }
    }

    @Override
    public DoodlePopulationComponent getPopulationComponent() {
        return new DoodlePopulationComponent(this);
    }


    public int populationAlive() {
        int survivors = 0;
        for (int i = 0; i < populationSize; i++) {
            if (population.get(i).getDoodleModel().isAlive()) {
                survivors++;
            }
        }
        return survivors;
    }
}
