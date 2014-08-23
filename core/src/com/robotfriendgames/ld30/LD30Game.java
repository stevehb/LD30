package com.robotfriendgames.ld30;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.robotfriendgames.ld30.data.GameState;
import com.robotfriendgames.ld30.data.RenderLevel;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.InputProcessor;
import com.robotfriendgames.ld30.game.LD30;
import com.robotfriendgames.ld30.logic.IntroLogic;
import com.robotfriendgames.ld30.logic.OutroLogic;
import com.robotfriendgames.ld30.logic.PlayLogic;

public class LD30Game extends ApplicationAdapter {
    public static final String TAG = LD30Game.class.getSimpleName();

    private IntroLogic introLogic;
    private PlayLogic playLogic;
    private OutroLogic outroLogic;

	private SpriteBatch batch;
    private Viewport viewport;

	@Override
	public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        LD30.init();

        introLogic = new IntroLogic();
        playLogic = new PlayLogic();
        outroLogic = new OutroLogic();

    	batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Gdx.input.setInputProcessor(new InputProcessor());
        LD30.data.gameState = GameState.INTRO_IN;
	}

    @Override
    public void resize (int width, int height) {
        viewport.update(width, height);
    }

    @Override
	public void render () {
        float delta = Gdx.graphics.getDeltaTime();
        for(int i = 0; i < LD30.data.entities.size; i++) {
            GameEntity entity = LD30.data.entities.get(i);
            entity.update(delta);
        }

        introLogic.update(delta);
        playLogic.update(delta);
        outroLogic.update(delta);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //batch.setProjectionMatrix(viewport.getCamera().projection);
		batch.begin();
        for(int i = 0; i < RenderLevel.levels.length; i++) {
            LD30.data.renderLevel = RenderLevel.levels[i];
            for(int j = 0; j < LD30.data.entities.size; j++) {
                GameEntity entity = LD30.data.entities.get(j);
                entity.draw(batch);
            }
        }
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
