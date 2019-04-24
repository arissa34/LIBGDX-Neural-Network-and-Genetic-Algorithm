package com.ramimartin.ecs.component.listener;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface SpriteRenderListener {
    void draw(SpriteBatch batch, Camera camera);
}
