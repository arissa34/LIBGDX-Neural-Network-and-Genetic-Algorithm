package com.ramimartin.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.ramimartin.ecs.component.StageRenderableComponent;
import com.ramimartin.ecs.component.ZComponent;

/**
 * Created by Rami on 10/02/2018.
 */

public class StageRenderingSystem extends SortedIteratingSystem {

    private ComponentMapper<StageRenderableComponent> renderableComponentMapper;

    public StageRenderingSystem(){
        super(Family.all(StageRenderableComponent.class, ZComponent.class).get(), new ZComparatorInverse());
        renderableComponentMapper = ComponentMapper.getFor(StageRenderableComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderableComponentMapper.get(entity).renderListener.draw(deltaTime);
    }
}
