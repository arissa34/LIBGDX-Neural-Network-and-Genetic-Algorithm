package com.ramimartin.doodlejump.ga;

import com.ramimartin.doodlejump.model.DoodleModel;
import com.ramimartin.ga.fitness.Fitness;

public class JumpFitness extends Fitness {

    private DoodleModel doodleModel;

    public JumpFitness(DoodleModel doodleModel) {
        this.doodleModel = doodleModel;
    }

    @Override
    public float caclFitness() {
         fitness = doodleModel.getBestHeight();
        return fitness;
    }

}
