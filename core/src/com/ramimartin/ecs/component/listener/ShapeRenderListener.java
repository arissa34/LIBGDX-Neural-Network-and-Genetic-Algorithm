package com.ramimartin.ecs.component.listener;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface ShapeRenderListener {
    void draw(ShapeRenderer shapeRenderer, Camera camera);
}
