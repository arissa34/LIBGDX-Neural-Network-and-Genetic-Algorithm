package com.ramimartin.doodlejump.parallax;

import com.badlogic.gdx.graphics.g2d.Batch;

public interface ParallaxRenderer {
    float getWidthForParralax();
    //FIXME MAYBE PASS THE ALL INSTANCE OF ParrallaxBackground & Layer
    void draw(Batch batch, float x, float y,
              int originX, int originY,
              float width, float heigth,
              float scaleX, float scaleY,
              int rotation,
              int srcX, int srcY,
              boolean flipX, boolean flipY,
              boolean fitHeight, float xStart, float offestWitdh);
}
