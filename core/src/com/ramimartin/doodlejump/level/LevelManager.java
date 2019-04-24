package com.ramimartin.doodlejump.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.ramimartin.MainClass;
import com.ramimartin.doodlejump.camera.VerticalCamera;
import com.ramimartin.doodlejump.model.DoodleModel;
import com.ramimartin.doodlejump.model.LevelModel;
import com.ramimartin.doodlejump.model.PlateformModel;
import com.ramimartin.doodlejump.physics.WorldPhysics;
import com.ramimartin.ecs.component.UpdatableComponent;
import com.ramimartin.ecs.component.listener.UpdatableListener;
import com.ramimartin.ecs.utils.EntityUtils;


public class LevelManager implements UpdatableListener {

    private Array<PlateformModel> platforms;
    private float currentYgenerated = 300/ WorldPhysics.PTM;
    private float yMaxxToAdd = 2000/ WorldPhysics.PTM;
    private VerticalCamera camera;

    public LevelManager() {

        this.camera = VerticalCamera.get();
        new LevelModel(camera);
        platforms = new Array<PlateformModel>();

        generate();

        EntityUtils.registerEntityAndAddComponent(
                new UpdatableComponent(this)
        );
    }

    public void generate() {
        platforms.clear();
        float x = MathUtils.random(-270f/ WorldPhysics.PTM, -170f/ WorldPhysics.PTM);
        currentYgenerated = 0;
        platforms.add(new PlateformModel(x, currentYgenerated));
        platforms.first().setHasObstacle(false);
        platforms.first().setMoving(false);
        platforms.first().attachEntity();
        addPlatforms();
    }

    private void addPlatforms(){
        currentYgenerated += MathUtils.random(250f/ WorldPhysics.PTM, 310f/ WorldPhysics.PTM);
        float max = currentYgenerated + yMaxxToAdd;
        int index = platforms.size;
        for (float y = currentYgenerated; y < max; y += MathUtils.random(240f/ WorldPhysics.PTM, 300f/ WorldPhysics.PTM)) {
            PlateformModel plateformModel = new PlateformModel(MathUtils.random(-270f/ WorldPhysics.PTM, 270f/ WorldPhysics.PTM), y);
            platforms.add(plateformModel);
            plateformModel.attachEntity();
            currentYgenerated = y;
            if(y < MainClass.HEIGHT /WorldPhysics.PTM){
                plateformModel.setHasObstacle(false);
                plateformModel.setMoving(false);
            }else{

                if(plateformModel.hasObstacle() && platforms.get(index - 1) != null && platforms.get(index - 1).hasObstacle()){
                    plateformModel.setHasObstacle(false);
                }

                if(plateformModel.hasObstacle() && platforms.get(index - 2) != null && platforms.get(index - 2).hasObstacle()){
                    plateformModel.setHasObstacle(false);
                }

                if(plateformModel.hasObstacle() && platforms.get(index - 3) != null && platforms.get(index - 3).hasObstacle()){
                    plateformModel.setHasObstacle(false);
                }

                if (plateformModel.isMoving() && platforms.get(index - 1) != null && platforms.get(index - 1).isMoving()) {
                    plateformModel.setMoving(false);
                }

                if (plateformModel.isMoving() && platforms.get(index - 2) != null && platforms.get(index - 2).isMoving()) {
                    plateformModel.setMoving(false);
                }

                if (plateformModel.isMoving() && platforms.get(index - 3) != null && platforms.get(index - 3).isMoving()) {
                    plateformModel.setMoving(false);
                }
            }
            index++;
        }
    }

    public void reset() {
        for (int i = 0; i < platforms.size; i++) {
            platforms.get(i).reset();
        }
        platforms.clear();
    }

    @Override
    public void update(float delta) {
        if (camera.position.y > currentYgenerated - 500/ WorldPhysics.PTM) {
            addPlatforms();
        }
    }


    public PlateformModel getPlatformBehind(DoodleModel doodle) {
        for (int i = platforms.size - 1; i >= 0; i--) {
            if (platforms.get(i).getY() < doodle.getY()) {
                return platforms.get(i);
            }
        }
        return null;
    }

    public PlateformModel getPlatformAbove(DoodleModel doodle) {
        for (int i = 0; i < platforms.size; i++) {
            if (platforms.get(i).getY() > doodle.getY()) {
                return platforms.get(i);
            }
        }
        return null;
    }


    /*******************************/

    private static LevelManager instance;

    public static LevelManager get() {
        if (instance == null) instance = new LevelManager();
        return instance;
    }
}