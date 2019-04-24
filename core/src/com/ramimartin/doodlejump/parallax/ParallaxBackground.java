package com.ramimartin.doodlejump.parallax;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.ramimartin.ecs.component.SpriteRenderableComponent;
import com.ramimartin.ecs.component.UpdatableComponent;
import com.ramimartin.ecs.component.ZComponent;
import com.ramimartin.ecs.component.listener.SpriteRenderListener;
import com.ramimartin.ecs.component.listener.UpdatableListener;
import com.ramimartin.ecs.utils.EntityUtils;

public class ParallaxBackground  implements UpdatableListener {

    public interface Listener{
        void onParallaxPositionChanged(float x, float y);
    }

    public Array<Layer> listLayer;
    public Array<Listener> listListener;

    private Camera camera;

    private float scroll;
    float x,y,width,heigth,scaleX,scaleY;
    int originX, originY,rotation,srcX,srcY;
    boolean flipX,flipY;
    float lastYCam;

    public ParallaxBackground(Camera camera){
        this.camera = camera;

        listLayer = new Array<Layer>();
        listListener = new Array<Listener>();

        width =  Gdx.graphics.getWidth();
        heigth = Gdx.graphics.getHeight();

        scroll = 0;
        originX = originY = rotation = srcY = 0;
        scaleX = scaleY = 1;
        flipX = flipY = false;

        EntityUtils.registerEntityAndAddComponent(new UpdatableComponent(this));
    }

    Vector3 pos = new Vector3();
    public ParallaxBackground addLayer(final Layer layer){
        listLayer.add(layer);
        Entity entity = EntityUtils.registerEntity();
        EntityUtils.addComponents(
                entity,
                new SpriteRenderableComponent(new SpriteRenderListener() {
                    @Override
                    public void draw(SpriteBatch batch, Camera camera) {
                        srcY = -(int) (scroll + (layer.speed * scroll));
                        float widthTotal=0;
                        for(Texture texture : layer.listTexture){
                            if(texture.getUWrap()== Texture.TextureWrap.Repeat || texture.getUWrap()== Texture.TextureWrap.MirroredRepeat){
                                Vector3 v = camera.unproject(pos.set(x, 0, 0));
                                batch.draw(texture, v.x, v.y-heigth, originX, originY, width, layer.fitHeight ? heigth : texture.getHeight(),scaleX,scaleY,rotation,srcX,srcY,texture.getWidth(),texture.getHeight(),flipX,flipY);
                            }else{
                                batch.draw(texture, x+layer.xStart+widthTotal-srcX, y, texture.getWidth(), heigth);
                            }
                            widthTotal += texture.getWidth();
                        }
                        for(ParallaxRenderer parallaxRenderer : layer.listRenderer){
                            parallaxRenderer.draw(batch, x, y, originX, originY, width, heigth,scaleX,scaleY,rotation,srcX,srcY,flipX,flipY, layer.fitHeight, layer.xStart, widthTotal);
                            widthTotal += parallaxRenderer.getWidthForParralax();
                        }
                    }
                }),
                new ZComponent(layer.zIndex)
        );
        return this;
    }

    public ParallaxBackground setSize(float width, float heigth){
        this.width = width;
        this.heigth = heigth;
        return this;
    }

    public ParallaxBackground setPosition(float x, float y){
        onPositionchanged(x, y);
        this.x = x;
        this.y = y;
        return this;
    }

    public void addListener(Listener listener){
        listListener.add(listener);
    }

    public void removeListener(Listener listener){
        listListener.removeValue(listener, true);
    }

    private void onPositionchanged(float newX, float newY){
        float offsetX = newX - this.x;
        float offsetY = newY - this.y;
        for(int i = 0; i < listListener.size; i ++){
            listListener.get(i).onParallaxPositionChanged(offsetX, offsetY);
        }
    }

    public void draw(Batch batch) {
        for(int i = 0; i <listLayer.size;i++) {
            final Layer layer = listLayer.get(i);
            srcX = (int) (scroll + (layer.speed * scroll));
            float widthTotal=0;
            for(Texture texture : layer.listTexture){
                if(texture.getUWrap()== Texture.TextureWrap.Repeat || texture.getUWrap()== Texture.TextureWrap.MirroredRepeat){
                    batch.draw(texture, x, y, originX, originY, width, heigth,scaleX,scaleY,rotation,srcX,srcY,texture.getWidth(),texture.getHeight(),flipX,flipY);
                }else{
                    batch.draw(texture, x+layer.xStart+widthTotal-srcX, y, texture.getWidth(), heigth);
                }
                widthTotal += texture.getWidth();
            }
        }
    }

    @Override
    public void update(float delta) {
        y = camera.position.y - camera.viewportWidth /2;
        scroll+=((y - lastYCam)*0.32f);
        lastYCam = y;
    }
}
