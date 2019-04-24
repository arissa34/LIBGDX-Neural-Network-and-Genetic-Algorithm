package com.ramimartin.ecs.system;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ramimartin.ecs.component.SpriteRenderableComponent;
import com.ramimartin.ecs.component.ZComponent;

/**
 * Created by Rami on 10/02/2018.
 */
public class SpriteRenderingSystem extends SortedIteratingSystem {

    private SpriteBatch spriteBatch;
    private Camera camera;
    private ComponentMapper<SpriteRenderableComponent> renderableComponentMapper;

    public SpriteRenderingSystem( SpriteBatch spriteBatch, Camera camera){
        super(Family.all(SpriteRenderableComponent.class, ZComponent.class).get(), new ZComparatorInverse());
        this.spriteBatch = spriteBatch;
        this.camera = camera;
        renderableComponentMapper = ComponentMapper.getFor(SpriteRenderableComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderableComponentMapper.get(entity).spriteRenderListener.draw(spriteBatch, camera);
    }
}
