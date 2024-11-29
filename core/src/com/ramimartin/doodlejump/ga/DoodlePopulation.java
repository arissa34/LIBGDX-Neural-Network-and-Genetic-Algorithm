package com.ramimartin.doodlejump.ga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.ramimartin.Config;
import com.ramimartin.doodlejump.camera.VerticalCamera;
import com.ramimartin.doodlejump.level.LevelManager;
import com.ramimartin.doodlejump.model.DoodleModel;
import com.ramimartin.doodlejump.model.PlateformModel;
import com.ramimartin.ga.population.Population;
import com.ramimartin.ga.utils.CrossoverUtils;
import com.ramimartin.ga.utils.MutationUtils;
import com.ramimartin.ga.utils.SelectionUtils;

public class DoodlePopulation extends Population<DoodleIndividual, DoodlePopulationComponent> {


    private final Pool<DoodleModel> doodleModelPool = new Pool<DoodleModel>(Config.nbrPopulation) {
        @Override
        protected DoodleModel newObject() {
            return new DoodleModel();
        }
    };

    public DoodlePopulation(int populationSize, float mutationRate, float crossoverRate, int elitismCount) {
        super(populationSize, mutationRate, crossoverRate, elitismCount);
    }

    @Override
    public void generateNewPopulation() {
        for (int i = 0; i < populationSize; i++) {
            DoodleModel individual = doodleModelPool.obtain();
            individual.getBrain().randomizeAxonValues();
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
            newPopulation.add(null);
        }

        sort();
        for (int populationIndex = 0; populationIndex < size(); populationIndex++) {

            if(populationIndex <= this.elitismCount){ /// Keep our best individual
                newPopulation.set(populationIndex, getIndividual(populationIndex));
            } else if(this.crossoverRate > Math.random()) { /// Crossover
                DoodleModel offspring = doodleModelPool.obtain();
                DoodleIndividual parentElite1 = (DoodleIndividual) SelectionUtils.tournament(this, Config.elitismCount);
                DoodleIndividual parentElite2 = (DoodleIndividual) SelectionUtils.tournament(this, Config.elitismCount);
                //DoodleIndividual parent2 = getIndividual(populationIndex);
                CrossoverUtils.uniform(parentElite1, parentElite2, offspring.getDoodleIndividual());
                //CrossoverUtils.singlePoint(parentElite1, parentElite2, offspring.getDoodleIndividual(), offspring.getDoodleIndividual().getChromosome().getListGene().size/2);
                // Add offspring to new population
                newPopulation.set(populationIndex, offspring.getDoodleIndividual());
            } else { /// New random individual
                DoodleIndividual randomIndividual = doodleModelPool.obtain().getDoodleIndividual();
                randomIndividual.getDoodleModel().getBrain().randomizeAxonValues();
                newPopulation.set(populationIndex, randomIndividual);
            }
        }

        for (int i = 0; i < populationSize; i++) { /// Dispose unused doodle
            if (!newPopulation.contains(population.get(i), true)) {
                doodleModelPool.free(population.get(i).getDoodleModel());
            }
        }

        for (int i = 0; i < populationSize; i++) { /// Add new doodle if position is empty
            if (newPopulation.get(i) == null) {
                DoodleModel doodle = doodleModelPool.obtain();
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
        //LevelManager.get().reset();
        //LevelManager.get().generate();
    }

    @Override
    public void mutate() {
        sort();
        for (int i = 0; i < population.size; ++i) {
            if (i <= Config.elitismCount) continue;
            //boolean hasChanged = MutationUtils.swapGene(population.get(i));
            //boolean hasChanged = MutationUtils.randomMutation(population.get(i));
            //boolean hasChanged = MutationUtils.perturbWeight(population.get(i));
            boolean hasChanged = MutationUtils.invertWeight(population.get(i));
            if(hasChanged){
                population.get(i).loadChromosoneToBrain();
            }
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
