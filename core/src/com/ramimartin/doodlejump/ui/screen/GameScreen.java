package com.ramimartin.doodlejump.ui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ramimartin.Config;
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
    public static boolean FORCE_NEW_GENERATION = false;

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
        population = new DoodlePopulation(Config.nbrPopulation, Config.mutationRate, Config.crossoverRate, Config.elitismCount);
        population.generateNewPopulation();

        /*
            public static int nbrPopulation = 400;
            public static int nbrInput = 6;
            public static int nbrLayers = 2;
            public static int nbrHidden = 7;
            public static float mutationRate = 0.35f;
            public static float crossoverRate = 0.7f;
            public static int elitismCount = 40;
            public static int fitnessGoal = 1000;
         */
         //population.getIndividual(0).getDoodleModel().getBrain().importAxons(-0.45655036,0.90897226,-0.8086878,0.79011965,-0.29765773,0.06820011,0.15749598,-0.11848545,-7.189512E-4,-0.9875096,0.93533623,0.581627,-0.2888509,-0.39196515,0.25599706,-0.3354752,0.76286435,0.7604449,0.40369534,-0.29765773,-0.8268255,0.5239432,-0.81198823,-0.5327798,-0.81483483,-0.39589882,-0.78337383,-0.48093307,-0.4599918,0.25997567,-0.1989212,0.73325896,-0.27371395,-0.08795977,-0.8145169,0.32572627,0.89457166,-0.17934895,0.46874392,-0.39589882,0.47822273,0.7478775,0.5029962,0.62145126,-0.7219155,1.0,0.7478775,-0.5935774,0.26349556,0.36538947,0.31059563,0.73252654,-0.09493768,0.36128438,0.05367148,-0.9234946,0.16768408,0.75538814,-0.14902425,-0.519078,-0.9697703,-0.63659155,0.36128438,0.7407589,0.46100152,0.75700533,-0.28939986,0.05367148,-0.45178223,0.9289334,-0.63659155,0.05367148,0.15809417,0.8930787,0.82166326,0.32491326,-0.47594047,0.521222,0.24609733,0.04662025,0.05367148,0.9179009,-0.8150052,-0.63659155,0.37734258,0.6158359,-0.09125495,0.05367148,0.99764085,0.474113,-0.6146096,-0.15587354,0.10179615,-0.20488381,-0.10793638,0.3613627,0.7278428,0.16768408,-0.95757437,-0.78989697,-0.58421755,-0.5127158,-0.9210205,-0.519078,-0.4760747,-0.15587354,-0.20488381);
         //population.getIndividual(1).getDoodleModel().getBrain().importAxons(-1.0,1.0,0.015388012,0.09175718,-0.36638403,0.3170781,0.5712428,-0.1324395,-0.9924822,-0.37361467,0.6575891,0.57754517,-0.47098327,0.15918887,0.07343602,0.007744789,-0.8454683,0.64745224,0.82197297,-0.7453226,-0.64931345,-0.7423526,-0.58414483,0.2987151,-0.36739242,0.180004,-0.2736069,-0.97903395,0.60628986,-0.06051898,0.18356824,0.86229813,0.7294978,-0.2484467,-0.90743446,-0.4152794,-0.032058835,-0.30749857,0.27448344,0.23604214,0.43484402,0.11266613,0.5924468,0.8031738,-0.059340715,0.40477788,0.43216205,-0.95833635,-0.30742335,-0.88676083,-0.3760928,-0.47283947,0.26621246,-0.18187106,0.36895728,0.27412772,0.83650017,0.39936936,-0.27958298,-0.052063942,-0.5201349,0.62615526,-0.75869644,-0.9683033,0.72005343,-0.98929346,0.37968588,-0.07820606,-0.75904894,-0.7029139,0.6418015,-0.5392301,0.36435425,-0.84232736,0.857723,0.76901674,-0.52575934,-0.025572896,-0.50279486,-0.11790991,0.18583286,0.28402674,0.9822402,0.20177543,0.15703392,-0.008057356,0.08274913,0.8088492,0.9035969,-0.004602194,-0.5830071,-0.31030202,-0.14731598,0.9125036,0.94663703,-0.40378845,0.14745152,0.7718353,0.3947364,-0.50604844,0.8162416,-0.70590997,0.35008,0.8645443,0.0227741,0.47225702,0.5341039);
         //population.getIndividual(2).getDoodleModel().getBrain().importAxons(-1.0,1.0,-0.69394577,-0.33845234,-0.89763093,0.32768714,0.5659492,0.679029,0.13718402,0.17192304,0.44053662,0.17177927,0.9475088,-0.86910784,-0.42321014,-0.2463535,-0.7039082,-0.8259567,-0.7705991,0.6022239,-0.018041372,0.7856976,-0.52903557,-0.5912671,0.78737724,-0.11227119,0.48811817,-0.84957993,0.08493471,0.5398849,0.46079051,-0.52820754,-0.32776356,0.6536499,-0.3911413,-0.5973184,-0.06904578,-0.2932831,-0.34927154,0.90256083,-0.5565877,0.099384665,0.01157105,0.27102506,0.30232382,-0.24500513,-0.58893263,0.9226053,-0.7851831,-0.53299737,0.39436877,0.5087582,-0.8116683,0.5338613,-0.30490184,-0.44329023,0.42197728,0.71633744,0.6308001,0.91756034,0.041588187,-0.63759494,0.56987035,-0.7489666,-0.51453567,-0.7449436,-0.2862798,0.46953702,-0.17262542,-0.6770123,-0.68947494,0.9496001,0.41899025,0.7112348,-0.7319449,-0.08379829,0.4932927,-0.6039039,0.87319076,-0.15842295,-0.16494954,0.61759627,-0.51904416,-0.15286529,0.3331623,-0.6683656,-0.2296207,0.795321,0.15984201,0.28668928,0.15580034,-0.108759284,0.22996068,0.49781275,-0.52455664,-0.4290067,0.7201556,0.8644409,0.0022655725,0.3614775,0.97736716,-0.2694162,-0.27040076,0.477566,0.048093677,0.1329397,0.14847493);
         //population.getIndividual(3).getDoodleModel().getBrain().importAxons(-0.9634316,0.37442303,0.16675234,0.8305141,-0.64165616,0.8066188,-0.6670513,0.9168731,-0.82236576,0.32819152,-0.2691077,-0.97235787,-0.92492557,-0.5769142,0.36840427,-0.60425365,-0.03578317,-0.11847758,0.061336517,-0.9799539,0.21124351,-0.72905695,0.72803485,0.577425,-0.8241806,0.82106733,0.79456186,-0.3992877,-0.52642405,0.42110264,0.6247568,0.23983943,-0.83235455,-0.8020419,0.21039057,0.14743614,-0.74677205,-0.22030115,-0.5555061,-0.8895974,0.18031228,0.14196885,-0.27001238,0.33675802,-0.09682667,0.005578637,-0.42876875,-0.72628844,-0.92335534,-0.45818722,0.77998483,-0.51900935,0.80236495,0.88661027,0.89959395,-0.0995425,0.3626051,-0.361323,0.20901191,-0.6365285,-0.358096,-0.5648235,-0.95247364,0.09394801,-0.9556229,-0.7667738,-0.046307206,-0.37564576,-0.63465405,-0.9698155,-0.9947016,-0.70853865,0.7527642,-0.5555651,0.658208,-0.40000916,-0.25397575,0.52901316,-0.8363806,-0.169191,0.47966516,0.91805565,0.76361156,0.3031608,-0.43753707,-0.3401916,-0.48394632,-0.3384261,0.80575633,-0.739179,-0.6938697,0.0996393,-0.4336115,0.5088303,0.17978299,-0.95460963,-0.9917271,-0.37140763,0.99572814,0.22062492,0.7008779,0.40391946,-0.052821755,0.05317366,-0.03805566,-0.24344969,-0.001401186);

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

        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            FORCE_NEW_GENERATION = true;
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
