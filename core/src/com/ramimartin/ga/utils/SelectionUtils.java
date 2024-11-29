package com.ramimartin.ga.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.ramimartin.ga.individual.Individual;
import com.ramimartin.ga.population.Population;

import java.util.Comparator;
import java.util.Random;

public class SelectionUtils {

    private static Array<Individual> populationTournament = new Array<Individual>();

    public static Individual wheelRoulette(Population population){
        double rouletteWheelPosition = Math.random() * population.populationFitness;
        double spinWheel = 0;
        for (int i = 0; i < population.size(); ++i) {
            spinWheel += population.getIndividual(i).getFitness().getValue();
            if (spinWheel >= rouletteWheelPosition) {
                return population.getIndividual(i);
            }
        }
        return population.getIndividual(population.size() - 1);
    }

    public static Individual tournament(Population population, int tournamentSize){
        if(tournamentSize > population.size())
            tournamentSize = population.size();
        //population.shuffle();
        population.sort();
        return population.getIndividual(MathUtils.random(0, tournamentSize));

    }

}
