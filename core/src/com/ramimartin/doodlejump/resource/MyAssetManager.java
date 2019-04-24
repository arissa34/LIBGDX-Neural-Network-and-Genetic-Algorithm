package com.ramimartin.doodlejump.resource;

import com.badlogic.gdx.assets.AssetManager;

public class MyAssetManager extends AssetManager {

    public MyAssetManager(){

    }




    /*******************************/

    private static MyAssetManager instance;

    public static MyAssetManager get() {
        if (instance == null) instance = new MyAssetManager();
        return instance;
    }
}
