package com.ramimartin.doodlejump.ga;

import com.badlogic.gdx.math.MathUtils;
import com.ramimartin.ga.gene.Gene;
import com.ramimartin.neural.Axon;

public class AxonGene extends Gene {

    private Axon axon;

    public AxonGene(Axon axon) {
        this.axon = axon;
    }

    public Axon getAxon() {
        return axon;
    }

    @Override
    public void mutate() {
        float newWeight = MathUtils.random(-1f, 1f);
        axon.setWeight(newWeight);
    }

    @Override
    public Gene clone() {
        return new AxonGene(axon.clone());
    }
}
