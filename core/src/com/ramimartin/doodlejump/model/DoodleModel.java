package com.ramimartin.doodlejump.model;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.ramimartin.MainClass;
import com.ramimartin.doodlejump.ai.DoodleBrain;
import com.ramimartin.doodlejump.camera.VerticalCamera;
import com.ramimartin.doodlejump.ga.DoodleIndividual;
import com.ramimartin.doodlejump.ga.JumpChromosone;
import com.ramimartin.doodlejump.physics.WorldPhysics;
import com.ramimartin.doodlejump.resource.LevelAssets;
import com.ramimartin.doodlejump.resource.MyAssetManager;
import com.ramimartin.ecs.component.listener.ShapeRenderListener;
import com.ramimartin.ecs.component.listener.SpriteRenderListener;
import com.ramimartin.ecs.component.listener.UpdatableListener;
import com.ramimartin.neural.Axon;
import com.ramimartin.neural.FeedForwardNeuralNetwork;

public class DoodleModel extends AbsBodyModel implements UpdatableListener, SpriteRenderListener, ShapeRenderListener, Pool.Poolable {

    public enum DoodleState implements State<DoodleModel> {
        IDLE {
            @Override
            public void enter(DoodleModel entity) {
                entity.setIdleTex();
            }
        },
        JUMP {
            @Override
            public void enter(DoodleModel entity) {
                entity.setJumpTex();
            }
        },
        FALL {
            @Override
            public void enter(DoodleModel entity) {
                entity.setIdleTex();
            }
        },
        DIE {
            @Override
            public void enter(DoodleModel entity) {
                entity.setDieTex();
            }
        };

        @Override
        public void enter(DoodleModel entity) {

        }

        @Override
        public void update(DoodleModel entity) {

        }

        @Override
        public void exit(DoodleModel entity) {

        }

        @Override
        public boolean onMessage(DoodleModel entity, Telegram telegram) {
            return false;
        }
    }

    private StateMachine<DoodleModel, DoodleState> stateMachine;
    private DoodleBrain brain;
    private DoodleIndividual doodleIndividual;
    private int character;
    private float bestHeight;
    private boolean isAlive;

    public DoodleModel() {
        super(getIdleTexture(0));
        getSprite().setScale(0.3f, 0.3f);
        character = MathUtils.random(1, 9);
        bestHeight = 0;
        isAlive = true;
        stateMachine = new DefaultStateMachine<DoodleModel, DoodleState>(this, DoodleState.IDLE);

        createDynamicBody(2.4f, 100, 0, WorldPhysics.DOODLE_FLAG, WorldPhysics.WORLD_FLAG);
        body.setUserData(this);
        brain = new DoodleBrain(this);
        doodleIndividual = new DoodleIndividual(this);

        zComponent.zIndex = -1;

        setPosition(0, -0.1f);
    }

    public DoodleIndividual getDoodleIndividual() {
        return doodleIndividual;
    }

    @Override
    public void reset() {
        bestHeight = 0;
        setPosition(0, -0.1f);
        isAlive = false;
        body.setLinearVelocity(0, 1);
        stateMachine.changeState(DoodleState.IDLE);
    }

    public void attachEntity() {
        isAlive = true;
        super.attachEntity();
    }

    Array<Axon> list = new Array<Axon>();
    public void loadChromosoneToBrain(JumpChromosone chromosome) {
        list.clear();
        for (int i = 0; i < chromosome.getListGene().size; i++) {
            list.add(chromosome.getListGene().get(i).getAxon());
        }
        getBrain().importAxons(list);
    }

    public void setIdleTex() {
        getSprite().setTexture(MyAssetManager.get().get(getIdleTexture(character), Texture.class));
    }

    public void setJumpTex() {
        getSprite().setTexture(MyAssetManager.get().get(getJumpTexture(character), Texture.class));
    }

    public void setDieTex() {
        getSprite().setTexture(MyAssetManager.get().get(getDieTexture(character), Texture.class));
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void jump() {
        if (isAlive) {
            body.setLinearVelocity(0, 0);
            body.applyLinearImpulse(0, 0.028f, getX(), getY(), true);
        }
    }

    public void goLeft(float force) {
        if (!isAlive) return;
        if (body.getLinearVelocity().x > -0.9f) {
            body.applyForce(-force, 0, getX(), getY(), true);
            //body.applyForce(-0.055f, 0, getX(), getY(), true);
        }
    }

    public void goRight(float force) {
        if (!isAlive) return;
        if (body.getLinearVelocity().x < 0.9f) {
            body.applyForce(force, 0, getX(), getY(), true);
            //body.applyForce(0.055f, 0, getX(), getY(), true);
        }
    }


    public boolean isJumping() {
        return body.getLinearVelocity().y > 0;
    }

    public boolean isFalling() {
        return body.getLinearVelocity().y < 0;
    }

    public void kill() {
        jump();
        isAlive = false;
        stateMachine.changeState(DoodleState.DIE);
    }

    public FeedForwardNeuralNetwork getBrain() {
        return brain.getFeedForwardNeuralNetwork();
    }

    @Override
    public void update(float delta) {
        if (delta == 0) return;
        super.update(delta);

        updateBestHeight();
        VerticalCamera.get().setHeigher(getY());

        if(isAlive){
            brain.update();
            brain.think();
        }

        if (isAlive && isJumping() && !stateMachine.isInState(DoodleState.JUMP)) {
            stateMachine.changeState(DoodleState.JUMP);
        } else if (isAlive && isFalling() && !stateMachine.isInState(DoodleState.FALL)) {
            stateMachine.changeState(DoodleState.FALL);
        }

        if (!isAlive) {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }
    }

    private void updateBestHeight() {
        if (!isAlive) return;
        if (getY() > bestHeight) {
            bestHeight = getY();
        }
        if (getY() < bestHeight - MainClass.HEIGHT / WorldPhysics.PTM) {
            kill();
        }
    }

    public float getBestHeight() {
        return bestHeight;
    }

    @Override
    public void draw(ShapeRenderer shapeRenderer, Camera camera) {
        brain.renderNeuralNetwork(shapeRenderer);
    }

    @Override
    public void dispose() {
        brain.dispose();
        super.dispose();
    }

    private static String getIdleTexture(int i) {
        switch (i) {
            case 1:
                return LevelAssets.character_01_idle;
            case 2:
                return LevelAssets.character_02_idle;
            case 3:
                return LevelAssets.character_03_idle;
            case 4:
                return LevelAssets.character_04_idle;
            case 5:
                return LevelAssets.character_05_idle;
            case 6:
                return LevelAssets.character_06_idle;
            case 7:
                return LevelAssets.character_07_idle;
            case 8:
                return LevelAssets.character_08_idle;
            case 9:
                return LevelAssets.character_09_idle;
            default:
                return LevelAssets.character_01_idle;
        }
    }

    private static String getJumpTexture(int i) {
        switch (i) {
            case 1:
                return LevelAssets.character_01_jump;
            case 2:
                return LevelAssets.character_02_jump;
            case 3:
                return LevelAssets.character_03_jump;
            case 4:
                return LevelAssets.character_04_jump;
            case 5:
                return LevelAssets.character_05_jump;
            case 6:
                return LevelAssets.character_06_jump;
            case 7:
                return LevelAssets.character_07_jump;
            case 8:
                return LevelAssets.character_08_jump;
            case 9:
                return LevelAssets.character_09_jump;
            default:
                return LevelAssets.character_01_jump;
        }
    }

    private static String getDieTexture(int i) {
        switch (i) {
            case 1:
                return LevelAssets.character_01_die;
            case 2:
                return LevelAssets.character_02_die;
            case 3:
                return LevelAssets.character_03_die;
            case 4:
                return LevelAssets.character_04_die;
            case 5:
                return LevelAssets.character_05_die;
            case 6:
                return LevelAssets.character_06_die;
            case 7:
                return LevelAssets.character_07_die;
            case 8:
                return LevelAssets.character_08_die;
            case 9:
                return LevelAssets.character_09_die;
            default:
                return LevelAssets.character_01_die;
        }
    }
}
