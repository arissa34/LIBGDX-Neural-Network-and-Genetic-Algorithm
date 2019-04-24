package com.ramimartin.ecs;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;

public class ECS {

    private static final String TAG = ECS.class.getSimpleName();
    private static boolean DISPLAY_LOG = true;

    // ===== SINGLETON
    private static ECS instance;

    public static ECS get() {
        if (instance == null) instance = new ECS();
        return instance;
    }
    // ===== SINGLETON


    private Engine mEngine;

    public void createEngine(){
        mEngine = new Engine();
    }

    public Engine getEngine(){
        if(mEngine == null) createEngine();
        return mEngine;
    }

    public void addSystem(EntitySystem system){
        if(mEngine == null) createEngine();
        mEngine.addSystem(system);
    }

    public void updateEngine(float delta){
        getEngine().update(delta);
    }
}
