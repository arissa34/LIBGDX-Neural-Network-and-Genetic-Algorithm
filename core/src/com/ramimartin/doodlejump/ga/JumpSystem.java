package com.ramimartin.doodlejump.ga;

import com.badlogic.gdx.Gdx;
import com.ramimartin.Config;
import com.ramimartin.doodlejump.utils.ChronoUtils;
import com.ramimartin.ga.fitness.Fitness;
import com.ramimartin.ga.population.Population;
import com.ramimartin.ga.system.SearchSpaceSystem;

import static com.ramimartin.doodlejump.ui.screen.GameScreen.FORCE_NEW_GENERATION;

public class JumpSystem extends SearchSpaceSystem<DoodlePopulationComponent> {

    private ChronoUtils chrono;
    private float oldPopulationFitness;
    private float oldBestPopulationFitness;
    private float wait = 5;
    private boolean isPaused = false;

    public JumpSystem() {
        super(DoodlePopulationComponent.class);
        chrono = new ChronoUtils();
        chrono.startTimer();
        oldBestPopulationFitness = 0;
    }

    @Override
    public void evalPopulation(Population population) {
        super.evalPopulation(population);
        if (population.populationFitness > population.bestFitnessEver) {
            population.bestFitnessEver = population.populationFitness;
        }
    }

    @Override
    public boolean isTerminationConditionMet(Population population) {
        for (int i = 0; i < population.size(); ++i) {
            Fitness fitness = population.getFittest(i).getFitness();
            if (fitness.getValue() > Config.fitnessGoal) return true;
        }
        return false;
    }

    @Override
    public void onTerminateConditionMet(Population population) {
        Gdx.app.log("", "IS TERMINATED generation : " + population.generation + " gene : " + population.getFittest(0).getChromosome().toString());
        setProcessing(false);
    }

    @Override
    public boolean isGenerationTerminate(Population population) {
        if (isPaused) {
            oldPopulationFitness = 0;
            return false;
        }
        if (FORCE_NEW_GENERATION) {
            FORCE_NEW_GENERATION = false;
            chrono.startTimer();
            findAndLogBest(population);
            return true;
        }
        //Gdx.app.log("", "oldPopulationFitness = "+oldPopulationFitness+ " population.populationFitness : "+population.populationFitness);
        if (oldPopulationFitness != population.populationFitness) {
            oldPopulationFitness = population.populationFitness;
            chrono.startTimer();
        } else if (chrono.getTimeEllapsed(ChronoUtils.TimeUnit.SECOND) < wait) {
            oldPopulationFitness = population.populationFitness;
        } else if (oldPopulationFitness == population.populationFitness && chrono.getTimeEllapsed(ChronoUtils.TimeUnit.SECOND) > wait) {
            chrono.startTimer();
            findAndLogBest(population);
            return true;
        }
        return false;
    }

    private void findAndLogBest(Population population) {
        oldPopulationFitness = 0;
        if (population.bestFitnessEver > oldBestPopulationFitness) {
            oldBestPopulationFitness = population.bestFitnessEver;
            Gdx.app.log("", "Best : " + population.getFittest(0).getChromosome().toString());
        }
    }

    @Override
    public void update(float delta) {
        if (delta == 0) {
            if (!isPaused) isPaused = true;
            return;
        } else if (isPaused) {
            isPaused = false;
        }
        super.update(delta);
    }
}
