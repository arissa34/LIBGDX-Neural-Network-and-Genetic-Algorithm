package com.ramimartin.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.ramimartin.ecs.component.ZComponent;

import java.util.Comparator;


/**
 * Created by Rami on 21/10/2017.
 */

public class ZComparatorInverse implements Comparator<Entity> {

    private ComponentMapper<ZComponent> zComponent;

    public ZComparatorInverse(){
        zComponent = ComponentMapper.getFor(ZComponent.class);
    }

    @Override
    public int compare(Entity e1, Entity e2) {
        if(e1 == null) return 0;
        if(e2 == null) return 0;
        if(zComponent.get(e1) == null) return 0;
        if(zComponent.get(e2) == null) return 0;
        return zComponent.get(e2).zIndex - zComponent.get(e1).zIndex;
    }
}