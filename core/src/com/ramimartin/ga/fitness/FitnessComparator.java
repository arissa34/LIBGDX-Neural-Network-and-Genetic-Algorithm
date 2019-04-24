package com.ramimartin.ga.fitness;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import java.util.Comparator;


/**
 * Created by Rami on 21/10/2017.
 */

public class FitnessComparator<F extends Fitness>  implements Comparator<Entity> {

    private ComponentMapper<F> fitnessComponent;

    public FitnessComparator(Class<F> type){
        fitnessComponent = ComponentMapper.getFor(type);
    }

    @Override
    public int compare(Entity e1, Entity e2) {
        if(e1 == null) return 0;
        if(e2 == null) return 0;
        if(fitnessComponent.get(e1) == null) return 0;
        if(fitnessComponent.get(e2) == null) return 0;
        return Float.compare(fitnessComponent.get(e2).getValue(), fitnessComponent.get(e1).getValue());
    }
}