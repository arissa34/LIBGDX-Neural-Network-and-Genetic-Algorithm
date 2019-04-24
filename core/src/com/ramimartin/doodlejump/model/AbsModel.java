package com.ramimartin.doodlejump.model;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.ramimartin.doodlejump.resource.MyAssetManager;
import com.ramimartin.ecs.component.SpriteRenderableComponent;
import com.ramimartin.ecs.component.UpdatableComponent;
import com.ramimartin.ecs.component.ZComponent;
import com.ramimartin.ecs.component.listener.ShapeRenderListener;
import com.ramimartin.ecs.component.listener.SpriteRenderListener;
import com.ramimartin.ecs.component.listener.UpdatableListener;
import com.ramimartin.ecs.utils.EntityUtils;

public abstract class AbsModel implements UpdatableListener, SpriteRenderListener, ShapeRenderListener, Disposable {

    private Sprite sprite;
    protected Vector2 position;
    protected Entity entity;
    protected ZComponent zComponent;

    public AbsModel(String pathSprite){
        sprite = new Sprite(MyAssetManager.get().get(pathSprite, Texture.class));
        position = new Vector2();

        entity = EntityUtils.createEntityAndAddComponent(
                new UpdatableComponent(this)
                , new SpriteRenderableComponent(this), zComponent = new ZComponent(0)
                //, new ShapeRenderableComponent(this)
        );
    }

    public void attachEntity(){
        EntityUtils.attachEntity(entity);
    }

    public void dettachEntity(){
        EntityUtils.dettachEntity(entity);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getReelWidth(){
        return sprite.getWidth()*sprite.getScaleX();
    }

    public float getReelHeight(){
        return sprite.getHeight()*sprite.getScaleY();
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(SpriteBatch batch, Camera camera) {
        if (camera.frustum.sphereInFrustum(position.x, position.y, 0, (sprite.getWidth()) / 2)) {
            batch.draw(sprite.getTexture(), position.x, position.y, 0, 0, (int)sprite.getWidth(), (int)sprite.getHeight());
        }
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Camera camera) {
    }

    @Override
    public void dispose() {

    }
}
