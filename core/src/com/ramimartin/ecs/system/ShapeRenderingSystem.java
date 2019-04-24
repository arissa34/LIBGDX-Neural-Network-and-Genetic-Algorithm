package com.ramimartin.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ramimartin.ecs.component.ShapeRenderableComponent;

/**
 * Created by Rami on 10/02/2018.
 */

public class ShapeRenderingSystem extends IteratingSystem {

    private ShapeRenderer shapeRenderer;
    private Camera camera;
    private ComponentMapper<ShapeRenderableComponent> renderableComponentMapper;

    public ShapeRenderingSystem(ShapeRenderer shapeRenderer, Camera camera){
        super(Family.all(ShapeRenderableComponent.class).get());
        this.shapeRenderer = shapeRenderer;
        this.camera = camera;
        renderableComponentMapper = ComponentMapper.getFor(ShapeRenderableComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderableComponentMapper.get(entity).shapeRenderListener.draw(shapeRenderer, camera);
    }
}
