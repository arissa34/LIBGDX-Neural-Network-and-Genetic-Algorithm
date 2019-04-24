package com.ramimartin.doodlejump.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.ramimartin.doodlejump.ai.DoodleBrain;
import com.ramimartin.doodlejump.ai.DoodleSensor;
import com.ramimartin.doodlejump.model.AbsBodyModel;
import com.ramimartin.doodlejump.model.DoodleModel;
import com.ramimartin.doodlejump.model.GroundModel;
import com.ramimartin.doodlejump.model.PlateformModel;
import com.ramimartin.ecs.component.UpdatableComponent;
import com.ramimartin.ecs.component.listener.UpdatableListener;
import com.ramimartin.ecs.utils.EntityUtils;

import java.util.Iterator;

public class WorldPhysics implements UpdatableListener, Disposable, ContactListener {

    public static final short DOODLE_FLAG = 0x1;    // 0001
    public static final short WORLD_FLAG = 0x1 << 1; // 0010 or 0x2 in hex
    public static final short PLATFORM_FLAG = 0x2; // 0010 or 0x2 in hex


    public static final float PTM = 1000f;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    public static final Vector2 gravity = new Vector2(0, -10);

    public WorldPhysics(){
        world = new World(gravity, true);
        debugRenderer = new Box2DDebugRenderer();
        world.setContactListener(this);
        EntityUtils.registerEntityAndAddComponent(
                new UpdatableComponent(this)
        );
    }

    public World getWorld() {
        return world;
    }

    public Body createDynamicBody(float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x/PTM, y/PTM);
        return world.createBody(bodyDef);
    }

    public Body createStaticBody(float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x/PTM, y/PTM);
        return world.createBody(bodyDef);
    }

    public Body createKinematicBody(float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(x/PTM, y/PTM);
        return world.createBody(bodyDef);
    }

    public FixtureDef createFixture(Shape shape, float density, float friction, float restitution){
       return createFixture(shape, density, friction, restitution, (short)-1, (short)-1);
    }
    public FixtureDef createFixture(Shape shape, float density, float friction, float restitution, short categoryBits, short maskBits){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;
        fixtureDef.filter.categoryBits = categoryBits;
        fixtureDef.filter.maskBits = maskBits;
        return fixtureDef;
    }

    public void renderDebugguer(Camera camera){
        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void update(float delta) {
        if(delta == 0) return;
        world.step(1/60f, 6, 2);
        checkBodyToDestroy();
    }

    private Array<Body> bodies = new Array<Body>();
    private void checkBodyToDestroy(){
        bodies.clear();
        world.getBodies(bodies);
        Iterator<Body> i = bodies.iterator();
        Body node=i.next();
        while (i.hasNext()) {
            Body oBj=node;
            node=i.next();
            if(oBj.getUserData() instanceof AbsBodyModel && ((AbsBodyModel) oBj.getUserData()).hasToBeDestroy()){
                world.destroyBody(oBj);
            }else if(oBj.getUserData() instanceof DoodleSensor && ((DoodleSensor) oBj.getUserData()).hasToBeDestroy()){
                world.destroyBody(oBj);
            }
        }
    }


    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fb.getBody().getUserData() instanceof DoodleSensor && fa.getBody().getUserData() instanceof PlateformModel){
            PlateformModel plateformModel = (PlateformModel) fa.getBody().getUserData();
            DoodleSensor doodleSensor = (DoodleSensor) fb.getBody().getUserData();
            doodleSensor.setDistanceContact(plateformModel.getY() - doodleSensor.getY());
            doodleSensor.setHasContact(true);
            doodleSensor.setHasContactWithObstacle(plateformModel.hasObstacle());
            return;
        }

        if (fa.getBody().getUserData() instanceof DoodleSensor && fb.getBody().getUserData() instanceof PlateformModel){
            PlateformModel plateformModel = (PlateformModel) fb.getBody().getUserData();
            DoodleSensor doodleSensor = (DoodleSensor) fa.getBody().getUserData();
            doodleSensor.setDistanceContact(plateformModel.getY() - doodleSensor.getY());
            doodleSensor.setHasContact(true);
            doodleSensor.setHasContactWithObstacle(plateformModel.hasObstacle());
            return;
        }

        if (fa.getBody().getUserData() instanceof GroundModel && fb.getBody().getUserData() instanceof DoodleModel){
            ((DoodleModel)fb.getBody().getUserData()).jump();
            return;
        }
        if (fb.getBody().getUserData() instanceof GroundModel && fa.getBody().getUserData() instanceof DoodleModel){
            ((DoodleModel)fb.getBody().getUserData()).jump();
            return;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fb.getBody().getUserData() instanceof DoodleSensor && fa.getBody().getUserData() instanceof PlateformModel){
            DoodleSensor doodleSensor = (DoodleSensor) fb.getBody().getUserData();
            doodleSensor.setDistanceContact(0);
            doodleSensor.setHasContact(false);
            doodleSensor.setHasContactWithObstacle(false);
            return;
        }

        if (fa.getBody().getUserData() instanceof DoodleSensor && fb.getBody().getUserData() instanceof PlateformModel){
            DoodleSensor doodleSensor = (DoodleSensor) fa.getBody().getUserData();
            doodleSensor.setDistanceContact(0);
            doodleSensor.setHasContact(false);
            doodleSensor.setHasContactWithObstacle(false);
            return;
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        contact.setEnabled(false);

        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();


        if (fa.getBody().getUserData() instanceof GroundModel && fb.getBody().getUserData() instanceof DoodleModel){
            contact.setEnabled(true);
            return;
        }
        if (fb.getBody().getUserData() instanceof GroundModel && fa.getBody().getUserData() instanceof DoodleModel){
            contact.setEnabled(true);
            return;
        }

        if (fa.getBody().getUserData() instanceof PlateformModel && fb.getBody().getUserData() instanceof DoodleModel ){
            PlateformModel plateformModel = (PlateformModel) fa.getBody().getUserData();
            DoodleModel doodle = ((DoodleModel)fb.getBody().getUserData());
            if(!doodle.isFalling()) return;
            if(doodle.getY()-doodle.getReelHeight()/2>plateformModel.getY()) {
                if (plateformModel.hasObstacle()) doodle.kill();
                else doodle.jump();
            }
        }
        if (fb.getBody().getUserData() instanceof PlateformModel && fa.getBody().getUserData() instanceof DoodleModel ){
            PlateformModel plateformModel = (PlateformModel) fb.getBody().getUserData();
            DoodleModel doodle = ((DoodleModel)fa.getBody().getUserData());
            if(!doodle.isFalling()) return;
            if(doodle.getY()-doodle.getReelHeight()/2>plateformModel.getY()) {
                if (plateformModel.hasObstacle()) doodle.kill();
                else doodle.jump();
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    @Override
    public void dispose() {
        world.dispose();
    }

    /*******************************/

    private static WorldPhysics instance;

    public static WorldPhysics get() {
        if (instance == null) instance = new WorldPhysics();
        return instance;
    }
}
