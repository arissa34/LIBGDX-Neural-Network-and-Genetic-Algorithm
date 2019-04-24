package com.ramimartin.ga.gene;

import com.badlogic.gdx.math.MathUtils;

public class BitGene extends Gene {

    private int value;

    public BitGene(){
        value = MathUtils.random(0, 1);
    }

    public BitGene(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public void mutate() {
        if(value == 1)
            value = 0;
        else
            value = 1;
    }
}
