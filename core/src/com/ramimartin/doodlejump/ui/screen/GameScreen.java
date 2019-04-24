package com.ramimartin.doodlejump.ui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ramimartin.doodlejump.camera.VerticalCamera;
import com.ramimartin.doodlejump.ga.DoodlePopulation;
import com.ramimartin.doodlejump.ga.JumpSystem;
import com.ramimartin.doodlejump.level.LevelManager;
import com.ramimartin.doodlejump.physics.WorldPhysics;
import com.ramimartin.doodlejump.resource.LevelAssets;
import com.ramimartin.doodlejump.resource.MyAssetManager;
import com.ramimartin.doodlejump.ui.hud.HudStage;
import com.ramimartin.ecs.ECS;
import com.ramimartin.ecs.system.ShapeRenderingSystem;
import com.ramimartin.ecs.system.SpriteRenderingSystem;
import com.ramimartin.ecs.system.StageRenderingSystem;
import com.ramimartin.ecs.system.UpdatableSystem;

public class GameScreen implements Screen, LoaderListener {

    private SpriteBatch batch;
    private ShapeRenderer shape;
    private VerticalCamera camera;

    private DoodlePopulation population;

    public static float SPEED = 1f;

    public GameScreen() {
    }

    @Override
    public void initAssets() {
        LevelAssets.loadAll();
    }

    @Override
    public void loadingFinished(Game game) {
        camera = VerticalCamera.get();
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        batch.enableBlending();

        LevelManager.get();

        ECS.get().getEngine().addSystem(new JumpSystem());
        population = new DoodlePopulation(40, 0.1f, 0.90f, 5);
        //population = new DoodlePopulation(10, 0.00001f, 0.90f, 2);
        population.generateNewPopulation();

        //population.getIndividual(0).getDoodleModel().getBrain().importAxons(0.38557982,1.520718,1.9585252,0.59921205,-0.72486675,-0.9628526,-0.5350195,-0.17717719,0.9743874,1.1679392,-1.5520642,1.1894375,0.824255,-0.63471735,-0.613333,0.9610354,0.118808985,0.13785923,-0.4531734,-0.57130647,-1.7794355,1.5783337,-1.2202457,-1.0936869,0.071026206,-0.8391247,-0.6636807,1.3251522,-1.4290105,-0.76186013,0.9343399,0.47776735);
        //population.getIndividual(1).getDoodleModel().getBrain().importAxons(1.0,1.0,0.9597616,-0.2745005,-0.24955511,0.41575825,-0.16536355,0.76421356,-0.7499052,0.27717352,-0.7222822,-0.22119081,-0.42468417,0.98553514,0.09578335,-0.4059806,0.27343667,0.57858324,0.49254847,0.6056429,-0.5241171,-0.5038588,0.41723526,0.6821418,0.9699073,0.9020008,0.6218276,0.82977116,-0.73237133,-0.9416609,-0.11689365,-0.9476664,0.5628873,-0.097117424,0.626227,-0.4545871,0.77248836,-0.7536969,-0.97528017,0.108325005,-0.07169223,0.86483705,-0.53988826,-0.41334295);
        //population.getIndividual(2).getDoodleModel().getBrain().importAxons(1.0,1.0,0.9597616,-0.2745005,-0.24955511,-0.79488873,0.29249334,0.76421356,-0.7499052,0.27717352,0.037511587,0.8174176,-0.42468417,0.98553514,0.09578335,0.722538,-0.48319018,0.57858324,0.49254847,0.6056429,-0.77394855,0.4038291,0.41723526,0.7037022,0.9699073,0.8544036,0.66657996,0.82977116,-0.73237133,-0.9416609,0.7638879,-0.9476664,-0.30522108,0.13429523,0.626227,-0.9435266,0.77248836,0.257748,-0.97528017,0.108325005,-0.07169223,0.86483705,-0.53988826,-0.41334295);
        //population.getIndividual(3).getDoodleModel().getBrain().importAxons(1.0,1.0,0.9597616,-0.2745005,-0.24955511,0.41575825,-0.16536355,0.76421356,-0.7499052,0.27717352,-0.7222822,-0.22119081,-0.42468417,0.98553514,0.09578335,-0.4059806,0.27343667,0.57858324,0.49254847,0.6056429,-0.5241171,-0.5038588,0.41723526,0.6821418,0.9699073,0.9020008,0.6218276,0.82977116,-0.73237133,-0.9416609,-0.11689365,-0.9476664,0.5628873,-0.097117424,0.626227,-0.4545871,0.77248836,-0.7536969,-0.97528017,0.108325005,-0.07169223,0.86483705,-0.53988826,-0.41334295);
        //population.getIndividual(4).getDoodleModel().getBrain().importAxons(1.0,1.0,0.9597616,-0.2745005,0.5598037,-0.79488873,0.65767324,0.76421356,-0.7499052,0.27717352,0.037511587,0.88320255,-0.42468417,0.98553514,0.09578335,-0.4059806,0.42890382,0.57858324,0.49254847,0.6056429,-0.03007245,-0.5038588,0.41723526,0.7037022,0.9699073,0.9020008,0.6218276,0.82977116,-0.73237133,-0.9416609,0.021095753,-0.9476664,0.5628873,0.13429523,0.626227,-0.4545871,0.77248836,-0.7536969,-0.97528017,0.108325005,-0.5672816,0.86483705,-0.53988826,-0.41334295);
        //population.getIndividual(5).getDoodleModel().getBrain().importAxons(1.0,1.0,0.9597616,-0.2745005,0.5598037,-0.79488873,0.65767324,0.76421356,-0.7499052,0.27717352,0.037511587,0.88320255,-0.42468417,0.98553514,0.09578335,-0.4059806,0.42890382,0.57858324,0.49254847,0.6056429,-0.03007245,-0.5038588,0.41723526,0.7037022,0.9699073,0.9020008,0.6218276,0.82977116,-0.73237133,-0.9416609,0.021095753,-0.9476664,0.5628873,0.13429523,0.626227,-0.4545871,0.77248836,-0.7536969,-0.97528017,0.108325005,-0.5672816,0.86483705,-0.53988826,-0.41334295);
        //population.getIndividual(6).getDoodleModel().getBrain().importAxons(1.0,1.0,0.9597616,-0.2745005,-0.24955511,-0.79488873,-0.16536355,0.76421356,-0.7499052,0.27717352,-0.7222822,0.88320255,-0.42468417,0.98553514,0.09578335,-0.4059806,0.42890382,0.57858324,0.49254847,0.6056429,-0.03007245,0.4038291,0.7915257,0.7037022,0.9699073,0.8544036,0.66657996,0.82977116,-0.73237133,-0.9416609,-0.11689365,-0.9476664,0.4787022,0.13429523,0.626227,-0.9435266,0.77248836,-0.7536969,-0.97528017,0.108325005,-0.07169223,0.86483705,-0.53988826,-0.41334295);
        //population.getIndividual(7).getDoodleModel().getBrain().importAxons(1.0,1.0,0.9597616,-0.2745005,-0.24955511,-0.79488873,-0.16536355,0.76421356,-0.7499052,0.27717352,-0.7222822,0.88320255,-0.42468417,0.98553514,0.09578335,-0.4059806,0.42890382,0.57858324,0.49254847,0.6056429,-0.03007245,0.4038291,0.7915257,0.7037022,0.9699073,0.8544036,0.66657996,0.82977116,-0.73237133,-0.9416609,-0.11689365,-0.9476664,0.4787022,0.13429523,0.626227,-0.9435266,0.77248836,-0.7536969,-0.97528017,0.108325005,-0.07169223,0.86483705,-0.53988826,-0.41334295);
        //population.getIndividual(0).getDoodleModel().getBrain().importAxons(2.8806605,1.455181,-0.47778964,0.06813896,1.1222538,-0.23489773,0.6829989,-4.1453114,-1.6087394,-6.6414824,-5.9580946,1.4061422,-3.2102227,-1.6394889,-0.3520646,0.440418,-3.9455261,-3.5707633,1.166964,-11.097876,0.9009638,-0.69625545,0.9696857,0.51161325,-0.0736233,-2.4811172,-1.0232399,2.3838615,-2.352206,-0.86262596,-6.4140296,2.5375273,-0.59793544,-1.4907414,-4.065405,0.77874076,-5.171497,1.1471564,1.9083631,4.7774763,2.1879501,0.2701242,-0.566417,0.024888039);
        //population.getIndividual(1).getDoodleModel().getBrain().importAxons(0.24374461,0.3486271,-2.9178753,1.4461529,1.0439252,-1.863698,0.5131427,-1.5845978,-0.22559059,-7.4107857,-7.661153,-0.2148354,-4.514313,-1.5766177,0.91884685,-0.6043267,0.7206663,-3.713079,0.40622294,-12.117977,-0.47265399,-1.2153429,0.99794686,0.17210543,-3.3445656,0.4733107,-1.9539851,2.3955693,-0.57590187,-2.3382056,-5.894305,0.42356873,-1.0938902,0.25849152,-5.795568,3.591219,-5.0673532,-0.59556186,3.5602818,5.599575,2.477213,-1.5495981,-0.67152894,-1.6469527);
        //population.getIndividual(2).getDoodleModel().getBrain().importAxons(3.5054467,0.3486271,-0.33516777,0.27825153,0.17779565,-1.8614519,-0.13874042,-3.959812,-0.55647814,-6.5461283,-4.4718475,-0.5268692,-1.2179518,0.3336823,2.5118127,1.7044176,-5.5538063,-1.0013965,-2.3968675,-13.447054,1.204132,-0.5725173,0.65976024,-1.102708,-0.32136834,-3.4313695,-2.6957226,3.1237714,-1.8715184,-1.0421139,-7.2226615,0.7792922,1.5260885,-2.7134857,-7.4194317,2.9751806,0.8456515,-0.08769572,3.5215628,6.797588,-0.86227655,-1.623712,-0.08778393,0.033489466);
        //population.getIndividual(4).getDoodleModel().getBrain().importAxons( 5.0464315,-2.7378209,-1.4047222,0.34576,1.5000466,1.9057295,-1.0489845,-0.79584754,-0.3755479,-6.6659336,-3.8831449,0.071619034,0.87717783,-0.38859713,0.76752746,-2.5867343,-2.069396,-0.6948662,-0.92008483,-12.561887,1.9196113,2.0438533,1.26606,0.94971037,-1.0001502,2.6129546,-4.183101,2.4908962,-1.6881268,-1.961295,-7.229415,0.45013654,-0.43164003,-0.15921772,-9.084012,4.204491,-6.130778,0.58508146,3.9335341,5.40977,4.4415708,3.205604,0.035027146,-2.313723);
        //population.getIndividual(5).getDoodleModel().getBrain().importAxons( -19.926292,20.282703,4.795083,49.0111,48.666706,31.26437,12.105368,24.263607,26.286602,-9.712246,21.687637,-3.08603,0.84426975,18.15757,-7.19625,16.61298,-9.290888,-21.520866,8.463079,10.082289,-45.213924,-27.100412,3.3217611,20.608246,-12.830281,4.808407,-13.466646,-0.5423422,-0.6598158,57.51346,34.135788,-3.845234,-11.64321,18.49422,28.29152,-26.724865,19.67181,-28.609818,4.6720266,3.408102,27.62274,9.203963,-10.445643,-33.78904);
        population.getIndividual(5).getDoodleModel().getBrain().importAxons(  1.0753951,1.4850715,4.5117197,-0.32828712,-9.00802,5.859152);

        population.startGeneticSelection(ECS.get().getEngine());

        new HudStage(population);

        game.setScreen(this);
    }

    @Override
    public void show() {
        ECS.get().getEngine().addSystem(new UpdatableSystem());
        ECS.get().getEngine().addSystem(new SpriteRenderingSystem(batch, camera));
        ECS.get().getEngine().addSystem(new ShapeRenderingSystem(shape, camera));
        ECS.get().getEngine().addSystem(new StageRenderingSystem());
    }

    @Override
    public void render(float delta) {

        delta *= SPEED;

        ECS.get().getEngine().getSystem(JumpSystem.class).update(delta);
        ECS.get().getEngine().getSystem(UpdatableSystem.class).update(delta);

        camera.update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
            ECS.get().getEngine().getSystem(SpriteRenderingSystem.class).update(delta);
        batch.end();

        //shape.setProjectionMatrix(camera.combined);

        //WorldPhysics.get().renderDebugguer(camera);
        population.getFittest(0).getDoodleModel().draw(shape, camera);
        ECS.get().getEngine().getSystem(ShapeRenderingSystem.class).update(delta);
        ECS.get().getEngine().getSystem(StageRenderingSystem.class).update(delta);

        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            camera.translate(0.1f);
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            camera.translate(-0.1f);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            SPEED = SPEED==1 ? 0: 1;
        }
        //Gdx.app.log("", "y : "+camera.position.y);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        WorldPhysics.get().dispose();
        MyAssetManager.get().dispose();
    }
}
