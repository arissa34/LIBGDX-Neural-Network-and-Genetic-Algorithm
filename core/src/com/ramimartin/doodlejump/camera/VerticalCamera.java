package com.ramimartin.doodlejump.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.ramimartin.MainClass;
import com.ramimartin.doodlejump.physics.WorldPhysics;

public class VerticalCamera extends OrthographicCamera {

    protected Vector3 targetPosition = new Vector3();
    protected float speed = 30f;

    public VerticalCamera() {
    }

    public VerticalCamera(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
    }

    public void update(float delta) {
        if (targetPosition.y < 0) targetPosition.y = 0;
        lerp(position, targetPosition, speed, delta);
        if (position.y < 0) position.y = 0;
        update();
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void moveTo(float x, float y) {
        targetPosition.set(x, y, 0);
    }

    public void moveToY(float y) {
        targetPosition.set(position.x, y, 0);
    }

    public float yHeigher;

    public void setHeigher(float y) {
        if (y > yHeigher) {
            yHeigher = y;
            moveToY(yHeigher - viewportHeight/2 + 200/WorldPhysics.PTM);
        }
    }

    public void translate(float y) {
        targetPosition.add(0, y, 0);
    }

    public void translate(float x, float y) {
        targetPosition.add(x, y, 0);
    }

    public void snapToTarget(float x, float y) {
        targetPosition.set(x, y, 0);
        position.set(targetPosition);
    }

    public void forceToCurrentTarget() {
        position.set(targetPosition);
    }

    public static Vector3 lerp(Vector3 vec, Vector3 target, float speed, float deltaTime) {
        deltaTime *= 0.01f;
        vec.x -= (vec.x - target.x)
                * Math.min(1.0f / 30f, Math.max(1.0f / 120f, deltaTime))
                * speed;
        vec.y -= (vec.y - target.y)
                * Math.min(1.0f / 30f, Math.max(1.0f / 120f, deltaTime))
                * speed;
        vec.z -= (vec.z - target.z)
                * Math.min(1.0f / 30f, Math.max(1.0f / 120f, deltaTime))
                * speed;
        return vec;
    }

    /*******************************/

    private static VerticalCamera instance;

    public static VerticalCamera get() {
        if (instance == null) instance = new VerticalCamera(MainClass.WIDTH/ WorldPhysics.PTM, MainClass.HEIGHT/WorldPhysics.PTM);
        return instance;
    }
}
