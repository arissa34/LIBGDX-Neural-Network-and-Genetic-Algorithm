package com.ramimartin.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.ramimartin.ecs.component.UpdatableComponent;

/**
 * Created by Rami on 10/02/2018.
 */

public class UpdatableSystem extends IteratingSystem {

    private ComponentMapper<UpdatableComponent> componentMapper;

    public UpdatableSystem(){
        super(Family.all(UpdatableComponent.class).get());
        componentMapper = ComponentMapper.getFor(UpdatableComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        componentMapper.get(entity).listener.update(deltaTime);
    }
}
