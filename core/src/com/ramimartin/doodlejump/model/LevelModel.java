package com.ramimartin.doodlejump.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.ramimartin.doodlejump.parallax.Layer;
import com.ramimartin.doodlejump.parallax.ParallaxBackground;
import com.ramimartin.doodlejump.physics.WorldPhysics;
import com.ramimartin.doodlejump.resource.LevelAssets;
import com.ramimartin.doodlejump.resource.MyAssetManager;

public class LevelModel  {

    private MyAssetManager assets;
    private ParallaxBackground parallaxBackground;
    private GroundModel groundModel;

    public LevelModel(Camera camera) {
        assets = MyAssetManager.get();
        buildParallax(camera);

        groundModel = new GroundModel();
        groundModel.setPosition((-camera.viewportWidth / 2)/ WorldPhysics.PTM, (-camera.viewportHeight / 2)+(groundModel.getReelHeight()/2));
    }

    private void buildParallax(Camera camera) {
        parallaxBackground = new ParallaxBackground(camera)
                .addLayer(new Layer()
                        .addReapedTexture(assets.get(LevelAssets.layer_1, Texture.class))
                        .setSpeed(0.8f*WorldPhysics.PTM).setZIndex(31).fitHeight(true)
                )
                .addLayer(new Layer()
                        .addReapedTexture(assets.get(LevelAssets.layer_2, Texture.class))
                        .setSpeed(0.5f*WorldPhysics.PTM).setZIndex(30).fitHeight(true)
                )
                .addLayer(new Layer()
                        .addReapedTexture(assets.get(LevelAssets.layer_3, Texture.class))
                        .setSpeed(0.3f*WorldPhysics.PTM).setZIndex(28).fitHeight(true)
                )
                .setSize(camera.viewportWidth, camera.viewportHeight)// * 0.85f
                .setPosition(0, 0);//* 0.15f
    }
}
