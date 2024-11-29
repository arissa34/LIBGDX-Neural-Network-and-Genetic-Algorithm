package com.ramimartin;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.ramimartin.doodlejump.ui.screen.GameScreen;
import com.ramimartin.doodlejump.ui.screen.ScreenLoader;

public class MainClass extends Game {

    public static final int WIDTH = 700;
    public static final int HEIGHT = 1000;

    GameScreen doodleScreen;

    private void loadSettings(){
        Gdx.graphics.setVSync(true);
        Gdx.graphics.setWindowedMode(WIDTH, HEIGHT);
        Gdx.input.setCatchBackKey(true);

        Gdx.input.setInputProcessor(new InputMultiplexer(){
            @Override
            public boolean keyDown(int keycode) {
                if(keycode == Input.Keys.BACK){
                    return false;
                }
                return super.keyDown(keycode);
            }
        });
    }

	@Override
	public void create () {
        loadSettings();
	    setScreen(new ScreenLoader(this, doodleScreen = new GameScreen()));
	}

	@Override
	public void dispose () {
	}
}
