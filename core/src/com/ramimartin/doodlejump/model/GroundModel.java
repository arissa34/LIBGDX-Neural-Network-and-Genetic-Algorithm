package com.ramimartin.doodlejump.model;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.ramimartin.doodlejump.physics.WorldPhysics;
import com.ramimartin.doodlejump.resource.LevelAssets;

public class GroundModel extends AbsBodyModel {

    public GroundModel() {
        super(LevelAssets.ground);

        zComponent.zIndex = 1;

        body = WorldPhysics.get().createStaticBody(getSprite().getX(), getSprite().getY());
        shape = new PolygonShape();
        ((PolygonShape)shape).setAsBox((getReelWidth()*200)/WorldPhysics.PTM, (getReelHeight()/6)/WorldPhysics.PTM);
        FixtureDef fixtureDef = WorldPhysics.get().createFixture(shape, 1f, 0, 0, WorldPhysics.WORLD_FLAG, WorldPhysics.DOODLE_FLAG);
        body.createFixture(fixtureDef);
        getSprite().setScale((1 / WorldPhysics.PTM)*getSprite().getScaleX());
        body.setUserData(this);

        attachEntity();
    }

}
