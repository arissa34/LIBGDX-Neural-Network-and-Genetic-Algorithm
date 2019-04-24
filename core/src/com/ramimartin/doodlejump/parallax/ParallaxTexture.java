package com.ramimartin.doodlejump.parallax;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Batch;

public class ParallaxTexture extends Texture implements ParallaxRenderer {

    public ParallaxTexture(TextureData data) {
        super(data);
    }

    @Override
    public float getWidthForParralax() {
        return getWidth();
    }

    @Override
    public void draw(Batch batch, float x, float y, int originX, int originY, float width, float heigth,float scaleX, float scaleY, int rotation, int srcX, int srcY, boolean flipX, boolean flipY, boolean fitHeight, float xStart, float offestWitdh) {
        if(getUWrap()== TextureWrap.Repeat || getUWrap()== TextureWrap.MirroredRepeat){
            batch.draw(this, x, y, originX, originY, width, fitHeight ? heigth : getHeight(),scaleX,scaleY,rotation,srcX,srcY,getWidth(),getHeight(),flipX,flipY);
        }else{
            batch.draw(this, x+xStart+offestWitdh-srcX, y, getWidth(), heigth);
        }
    }
}
