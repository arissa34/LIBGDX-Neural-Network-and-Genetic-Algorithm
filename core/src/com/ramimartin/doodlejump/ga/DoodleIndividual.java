package com.ramimartin.doodlejump.ga;

import com.ramimartin.doodlejump.model.DoodleModel;
import com.ramimartin.ga.individual.Individual;

public class DoodleIndividual extends Individual<JumpChromosone> {

    DoodleModel doodleModel;

    public DoodleIndividual(DoodleModel doodleModel) {
        this.doodleModel = doodleModel;
        initGenetic();
    }

    public DoodleModel getDoodleModel() {
        return doodleModel;
    }

    private void initGenetic() {
        addChromosome(new JumpChromosone(doodleModel.getBrain().exportAxons()));
        addFitness(new JumpFitness(doodleModel));
        chromosome.generateGenes();
    }

    public void loadChromosoneToBrain(){
        doodleModel.loadChromosoneToBrain(chromosome);
    }

}
