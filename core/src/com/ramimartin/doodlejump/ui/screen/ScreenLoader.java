package com.ramimartin.doodlejump.ui.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.ramimartin.doodlejump.resource.LevelAssets;
import com.ramimartin.doodlejump.resource.MyAssetManager;

public class ScreenLoader implements Screen {

    private OrthographicCamera camera;
    private LoaderListener nextScreen;
    private float progress;
    private Game game;

    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private Image image;
    private Texture texture;
    private Color colorFull = Color.WHITE;
    private Color colorFilled = Color.GREEN;

    public ScreenLoader(Game game, LoaderListener nextScreen) {
        this.game = game;
        this.nextScreen = nextScreen;

        camera = new OrthographicCamera();
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
    }

    @Override
    public void show() {


        texture = new Texture(LevelAssets.ldg_bckg);

        image = new Image(texture);
        image.setWidth(Gdx.graphics.getWidth());
        image.setHeight(Gdx.graphics.getHeight());
        image.setScaling(Scaling.fill);

        progress = 0f;
        nextScreen.initAssets();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        image.draw(spriteBatch, 1);
        spriteBatch.end();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(colorFull);
        shapeRenderer.rect(32, Gdx.graphics.getBackBufferHeight() / 7f, Gdx.graphics.getBackBufferWidth() - 62, 16);

        shapeRenderer.setColor(colorFilled);
        shapeRenderer.rect(32, Gdx.graphics.getBackBufferHeight() / 7f, progress * (Gdx.graphics.getBackBufferWidth() - 62), 16);
        shapeRenderer.end();

        update(delta);
    }

    protected void update(float delta) {
        if (MyAssetManager.get().update()) {
            nextScreen.loadingFinished(game);
            dispose();
        }
        progress = MyAssetManager.get().getProgress();
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
        texture.dispose();
        shapeRenderer.dispose();
        spriteBatch.dispose();
    }
}
