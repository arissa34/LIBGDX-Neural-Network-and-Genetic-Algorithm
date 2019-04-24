package com.ramimartin.doodlejump.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.ramimartin.doodlejump.physics.WorldPhysics;

public class DoodleSensor {

    private Body bodyLine;
    //private EdgeShape edgeShape;
    private ChainShape shape;
    private float distanceContact;
    private boolean hasContactWithObstacle;
    private boolean hasContact;
    private Vector2 position;
    private boolean hasToBeDestroy = false;

    public DoodleSensor(float angleStart, float angle){
        bodyLine = WorldPhysics.get().createDynamicBody(0, 0);

        shape = new ChainShape();
        Vector2[] chain = new Vector2[4];
        chain[0] = new Vector2(0, 0);
        chain[1] = new Vector2(getNewPosX(0.4f, 0, angleStart), getNewPosY(0.4f, 0, angleStart));
        chain[2] = new Vector2(getNewPosX(0.4f, 0, angleStart+angle), getNewPosY(0.4f, 0, angleStart+angle));
        chain[3] = new Vector2(0, 0);
        shape.createChain(chain);

        //edgeShape = new EdgeShape();
        //edgeShape.set(0, 0, getNewPosX(1, 0, angle), getNewPosY(1, 0, angle));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0f;
        fixtureDef.friction = 0f;
        fixtureDef.isSensor = true;
        bodyLine.createFixture(fixtureDef);
        bodyLine.setUserData(this);

        distanceContact = 0;
        hasContactWithObstacle = false;
        hasContact = false;
        position = new Vector2();
    }

    public void updatePos(Vector2 parentPos){
        bodyLine.setTransform(parentPos, bodyLine.getAngle());
        position.set(parentPos);
    }

    public float getY() {
        return position.y;
    }

    public Vector2 getPosition() {
        return position;
    }

    private static float getNewPosX(float x, float y, float angle) {
        return y * MathUtils.sin(angle) + x * MathUtils.cos(angle);
    }

    private static float getNewPosY(float x, float y, float angle) {
        return y * MathUtils.cos(angle) - x * MathUtils.sin(angle);
    }

    public float getDistanceContact() {
        return distanceContact;
    }

    public void setDistanceContact(float distanceContact) {
        this.distanceContact = Math.abs(distanceContact);
    }

    public boolean hasContact() {
        return hasContact;
    }

    public void setHasContact(boolean hasContact) {
        this.hasContact = hasContact;
    }

    public boolean hasContactWithObstacle() {
        return hasContactWithObstacle;
    }

    public void setHasContactWithObstacle(boolean hasContactWithObstacle) {
        this.hasContactWithObstacle = hasContactWithObstacle;
    }

    public void setHasToBeDestroy(boolean hasToBeDestroy) {
        this.hasToBeDestroy = hasToBeDestroy;
    }

    public boolean hasToBeDestroy() {
        return hasToBeDestroy;
    }

    public void destroyBody(){
        //WorldPhysics.get().getWorld().destroyBody(bodyLine);
        //bodyLine.setUserData(null);
        //shape.dispose();
        setHasToBeDestroy(true);
    }
}
