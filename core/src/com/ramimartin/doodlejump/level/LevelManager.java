package com.ramimartin.doodlejump.level;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
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

    private final Pool<PlateformModel> platformPool = new Pool<PlateformModel>(10) {
        @Override
        protected PlateformModel newObject() {
            return new PlateformModel();
        }
    };

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
        platforms.add(platformPool.obtain().setPos(x, currentYgenerated));
        platforms.first().setHasObstacle(false);
        platforms.first().setMoving(false);
        platforms.first().attachEntity();
        addPlatforms();
    }

    private void addPlatforms() {
        currentYgenerated += MathUtils.random(250f / WorldPhysics.PTM, 310f / WorldPhysics.PTM);
        float max = currentYgenerated + yMaxxToAdd;

        for (float y = currentYgenerated; y < max; y += MathUtils.random(240f / WorldPhysics.PTM, 300f / WorldPhysics.PTM)) {
            PlateformModel plateformModel = platformPool.obtain().setPos(MathUtils.random(-270f / WorldPhysics.PTM, 270f / WorldPhysics.PTM), y);
            platforms.add(plateformModel);
            plateformModel.attachEntity();
            currentYgenerated = y;

            if (y < MainClass.HEIGHT / WorldPhysics.PTM) {
                plateformModel.setHasObstacle(false);
                plateformModel.setMoving(false);
            } else {
                ensureObstacleConstraints(plateformModel, platforms);
                ensureMovementConstraints(plateformModel, platforms);
            }
        }
    }

    private void ensureObstacleConstraints(PlateformModel plateformModel, Array<PlateformModel> platforms) {
        // Vérifie jusqu'aux 3 dernières plateformes pour éviter les obstacles consécutifs
        int size = platforms.size;
        for (int i = 1; i <= 3; i++) {
            if (size - i >= 0 && plateformModel.hasObstacle() && platforms.get(size - i).hasObstacle()) {
                plateformModel.setHasObstacle(false);
                break;
            }
        }
    }

    private void ensureMovementConstraints(PlateformModel plateformModel, Array<PlateformModel> platforms) {
        // Vérifie jusqu'à 3 dernières plateformes pour éviter plusieurs plateformes mobiles consécutives
        int size = platforms.size;
        int movingCount = 0;

        for (int i = 1; i <= 3; i++) {
            if (size - i >= 0 && platforms.get(size - i).isMoving()) {
                movingCount++;
            }
        }

        // Si 2 ou plus plateformes mobiles consécutives, désactiver le mouvement de l'actuelle
        if (movingCount >= 2) {
            plateformModel.setMoving(false);
        }
    }

    public void reset() {
        for (int i = 0; i < platforms.size; i++) {
            platformPool.free(platforms.get(i));
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