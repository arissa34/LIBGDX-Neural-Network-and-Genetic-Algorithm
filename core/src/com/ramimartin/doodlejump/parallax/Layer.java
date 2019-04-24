package com.ramimartin.doodlejump.parallax;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Layer {
    public Array<Texture> listTexture = new Array<Texture>();
    public Array<ParallaxRenderer> listRenderer = new Array<ParallaxRenderer>();
    public float speed;
    public int zIndex;
    public int xStart = 0;
    public boolean fitHeight = true;

    public Layer addRenderer(ParallaxRenderer parallaxRenderer){
        listRenderer.add(parallaxRenderer);
        return this;
    }

    public Layer addReapedTexture(Texture texture){
        Texture texture1 = new Texture(texture.getTextureData());
        texture1.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        listTexture.add(texture1);
        return this;
    }

    public Layer addTexture(Texture texture){
        Texture texture1 = new Texture(texture.getTextureData());
        listTexture.add(texture1);
        return this;
    }

    public Layer setSpeed(float speed){
        this.speed = speed;
        return this;
    }

    public Layer setZIndex(int zIndex){
        this.zIndex = zIndex;
        return this;
    }

    public Layer setXStart(int xStart){
        this.xStart = xStart;
        return this;
    }

    public Layer fitHeight(boolean fitHeight){
        this.fitHeight = fitHeight;
        return this;
    }
}
