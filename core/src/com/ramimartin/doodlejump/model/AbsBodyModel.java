package com.ramimartin.doodlejump.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.ramimartin.doodlejump.physics.WorldPhysics;

public abstract class AbsBodyModel extends AbsModel {

    protected Body body;
    protected Shape shape;
    private boolean hasToBeDestroy = false;

    public AbsBodyModel(String pathSprite) {
        super(pathSprite);
    }

    protected void createDynamicBody(float density, float friction, float restitution){
        createDynamicBody(density, friction, restitution,  (short)-1, (short)-1);
    }
    protected void createDynamicBody(float density, float friction, float restitution, short categoryBits, short maskBits){
        body = WorldPhysics.get().createDynamicBody(getSprite().getX(), getSprite().getY());
        shape = new CircleShape();
        //((PolygonShape)shape).setAsBox((getReelWidth()/2)/WorldPhysics.PTM, (getReelHeight()/2)/WorldPhysics.PTM);
        ((CircleShape) shape).setRadius((getReelWidth()/3)/WorldPhysics.PTM);
        FixtureDef fixtureDef = WorldPhysics.get().createFixture(shape, density, friction, restitution, categoryBits, maskBits);
        body.createFixture(fixtureDef);
        getSprite().setScale((1 / WorldPhysics.PTM)*getSprite().getScaleX());
    }

    protected void createStaticBody(short categoryBits, short maskBits){
        body = WorldPhysics.get().createStaticBody(getSprite().getX(), getSprite().getY());
        shape = new PolygonShape();
        ((PolygonShape)shape).setAsBox((getReelWidth()/2)/WorldPhysics.PTM, (getReelHeight()/2)/WorldPhysics.PTM);
        FixtureDef fixtureDef = WorldPhysics.get().createFixture(shape, 1f, 0, 0, categoryBits, maskBits);
        body.createFixture(fixtureDef);
        getSprite().setScale((1 / WorldPhysics.PTM)*getSprite().getScaleX());
    }

    protected void createKineticody(short categoryBits, short maskBits){
        body = WorldPhysics.get().createKinematicBody(getSprite().getX(), getSprite().getY());
        shape = new PolygonShape();
        ((PolygonShape)shape).setAsBox((getReelWidth()/2)/WorldPhysics.PTM, (getReelHeight()/2)/WorldPhysics.PTM);
        FixtureDef fixtureDef = WorldPhysics.get().createFixture(shape, 1f, 0, 0, categoryBits, maskBits);
        body.createFixture(fixtureDef);
        getSprite().setScale((1 / WorldPhysics.PTM)*getSprite().getScaleX());
    }

    public Body getBody() {
        return body;
    }

    public void destroyBody(){
        WorldPhysics.get().getWorld().destroyBody(body);
    }

    public void setSensor(boolean sensor){
        body.getFixtureList().first().setSensor(sensor);
    }

    public void setPosition(float x, float y){
        body.setTransform(position.set(x, y), body.getAngle());
    }

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public void update(float delta) {
        getSprite().setPosition(getX(), getY());
    }

    @Override
    public void draw(SpriteBatch batch, Camera camera) {
        if (camera.frustum.sphereInFrustum(getX(), getY(), 0, getReelWidth() / 2)) {
            batch.draw(getSprite(), getX()-getReelWidth()/2, getY()-getReelHeight()/2, getReelWidth(), getReelHeight());
        }
    }

    public void setHasToBeDestroy(boolean hasToBeDestroy) {
        this.hasToBeDestroy = hasToBeDestroy;
    }

    public boolean hasToBeDestroy() {
        return hasToBeDestroy;
    }

    @Override
    public void dispose() {
        //destroyBody();
        //body.setUserData(null);
        dettachEntity();
        setHasToBeDestroy(true);
        //shape.dispose();
    }

}
