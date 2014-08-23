package com.robotfriendgames.ld30;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.robotfriendgames.ld30.game.LD30;

public class LD30Game extends ApplicationAdapter {
    public static final String TAG = LD30Game.class.getSimpleName();

	private SpriteBatch batch;
    private Viewport viewport;
	private Texture img;
	
	@Override
	public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        LD30.init();
    	batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.app.log(TAG, "loading last texture");
        img = new Texture("badlogic.jpg");
	}

    @Override
    public void resize (int width, int height) {
        viewport.update(width, height);
    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
        LD30.assetManager.dispose();
    }
}
