package com.ramimartin.ga.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.ramimartin.ga.individual.Individual;
import com.ramimartin.ga.population.Population;

import java.util.Comparator;

public class SelectionUtils {

    private static Comparator<Individual> comparator;
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

        populationTournament.clear();

        //TODO different probability settings

        population.shuffle();
        for (int i = 0; i < tournamentSize; i++) {
            Individual tournamentIndividual = population.getIndividual(i);
            populationTournament.add(tournamentIndividual);
        }

        if(comparator == null){
            comparator = new Comparator<Individual>() {
                @Override
                public int compare(Individual i1, Individual i2) {
                    if(i1 == null) return 0;
                    if(i2 == null) return 0;
                    if(i1.getFitness() == null) return 0;
                    if(i2.getFitness() == null) return 0;
                    return Float.compare(i2.getFitness().getValue(), i1.getFitness().getValue());
                }
            };
        }

        populationTournament.sort(comparator);

        return populationTournament.get(0);

    }

}
