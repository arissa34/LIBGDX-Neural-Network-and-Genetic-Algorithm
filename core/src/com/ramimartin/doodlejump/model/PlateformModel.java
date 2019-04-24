package com.ramimartin.doodlejump.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;
import com.ramimartin.MainClass;
import com.ramimartin.doodlejump.physics.WorldPhysics;
import com.ramimartin.doodlejump.resource.LevelAssets;
import com.ramimartin.doodlejump.resource.MyAssetManager;
import com.ramimartin.ecs.component.listener.SpriteRenderListener;
import com.ramimartin.ecs.component.listener.UpdatableListener;

public class PlateformModel extends AbsBodyModel implements UpdatableListener, SpriteRenderListener, Pool.Poolable {

    private Sprite obstacleSprite;
    private float x, y;
    public float scaleX, scaleY;
    private boolean hasObstacle;
    private boolean isMoving;
    private float velocityX;

    public PlateformModel() {
        this(0, 0);
    }

    public PlateformModel(float x, float y) {
        super(LevelAssets.platform);
        this.scaleX = 1f;
        this.scaleY = 0.8f;

        hasObstacle = MathUtils.random(0f, 1f) > 10.85f;
        if (hasObstacle) {
            obstacleSprite = new Sprite(MyAssetManager.get().get(LevelAssets.obstacle, Texture.class));
            obstacleSprite.setScale((1 / WorldPhysics.PTM));
        }

        isMoving = MathUtils.random(0f, 1f) > 0.92f;
        if (isMoving) {
            velocityX = MathUtils.randomBoolean() ? 0.5f : -0.5f;
            createKineticody(WorldPhysics.PLATFORM_FLAG, WorldPhysics.DOODLE_FLAG);
        } else {
            createStaticBody(WorldPhysics.PLATFORM_FLAG, WorldPhysics.DOODLE_FLAG);
        }
        body.setUserData(this);
        setPosition(x, y);

    }

    public PlateformModel setPos(float x, float y) {
        setPosition(x, y);
        return this;
    }

    public PlateformModel attach() {
        attachEntity();
        return this;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean hasObstacle() {
        return hasObstacle;
    }

    public void setHasObstacle(boolean hasObstacle) {
        this.hasObstacle = hasObstacle;
    }

    public float getObstacleWidth() {
        return obstacleSprite.getWidth() * obstacleSprite.getScaleX();
    }

    public float getObstacleHeight() {
        return obstacleSprite.getHeight() * obstacleSprite.getScaleY();
    }

    @Override
    public void update(float delta) {
        if(isMoving) {
            if (getX() < -(MainClass.WIDTH / 2) / WorldPhysics.PTM) {
                velocityX = 0.4f;
            } else if (getX() > (MainClass.WIDTH / 2) / WorldPhysics.PTM) {
                velocityX = -0.4f;
            }
            body.setLinearVelocity(velocityX, 0);
        }
        super.update(delta);
    }

    @Override
    public void draw(SpriteBatch batch, Camera camera) {
        if (camera.frustum.sphereInFrustum(getX(), getY(), 0, getReelWidth() / 2)) {
            batch.draw(getSprite(), getX() - getReelWidth() / 2, getY() - getReelHeight() / 2, getReelWidth(), getReelHeight());
            if (hasObstacle) {
                batch.draw(obstacleSprite, getX() - getReelWidth() / 2 + (getReelWidth() - getObstacleWidth()) - 0.01f, getY() + getReelHeight() / 2, getObstacleWidth(), getObstacleHeight());
            }
        }
    }

    @Override
    public void reset() {
        dettachEntity();
        destroyBody();
        shape.dispose();
    }
}
