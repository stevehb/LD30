package com.robotfriendgames.ld30;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.robotfriendgames.ld30.data.GameStates;
import com.robotfriendgames.ld30.data.RenderLevel;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.InputProcessor;
import com.robotfriendgames.ld30.game.LD;
import com.robotfriendgames.ld30.logic.IntroLogic;
import com.robotfriendgames.ld30.logic.OutroLogic;
import com.robotfriendgames.ld30.logic.PlayLogic;
import com.robotfriendgames.ld30.systems.SoundSystem;

public class LD30Game extends ApplicationAdapter {
    public static final String TAG = LD30Game.class.getSimpleName();

    private IntroLogic introLogic;
    private PlayLogic playLogic;
    private OutroLogic outroLogic;

    private SpriteBatch batch;

    //private Box2DDebugRenderer debugRenderer;

    @Override
    public void create () {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        LD.init();

        introLogic = new IntroLogic();
        playLogic = new PlayLogic();
        outroLogic = new OutroLogic();

        batch = new SpriteBatch();
        LD.data.worldWidth = Gdx.graphics.getWidth() * LD.settings.pixelsToWorld;
        LD.data.worldHeight = Gdx.graphics.getHeight() * LD.settings.pixelsToWorld;
        LD.data.camera = new OrthographicCamera(LD.data.worldWidth, LD.data.worldHeight);
        LD.data.viewport = new FitViewport(LD.data.worldWidth, LD.data.worldHeight, LD.data.camera);

        LD.data.soundSystem = new SoundSystem();
        LD.data.soundSystem.start();

        //debugRenderer = new Box2DDebugRenderer();
        Gdx.input.setInputProcessor(new InputProcessor());
        LD.data.gameState = GameStates.INTRO_IN;
    }

    @Override
    public void resize (int width, int height) {
        LD.data.viewport.update(width, height, true);
    }

    @Override
    public void render () {
        float delta = Gdx.graphics.getDeltaTime();
        for(int i = 0; i < LD.entityPool.getActive().size; i++) {
            GameEntity entity = LD.entityPool.getActive().get(i);
            entity.update(delta);
        }

        introLogic.update(delta);
        playLogic.update(delta);
        outroLogic.update(delta);

        LD.data.camera.update();
        batch.setProjectionMatrix(LD.data.camera.combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for(int i = 0; i < RenderLevel.levels.length; i++) {
            LD.data.renderLevel = RenderLevel.levels[i];
            for(int j = 0; j < LD.entityPool.getActive().size; j++) {
                GameEntity entity = LD.entityPool.getActive().get(j);
                entity.draw(batch);
            }
        }

        batch.end();
        if(LD.data.world != null) {
            //debugRenderer.render(LD.data.world, LD.data.camera.combined);
            LD.data.world.step(delta, 6, 2);
        }
    }

    @Override
    public void pause () { Gdx.app.log(TAG, "pause"); }

    @Override
    public void resume () { Gdx.app.log(TAG, "resume"); }

    @Override
    public void dispose () {
        LD.data.soundSystem.stop();
        LD.assetManager.dispose();
    }
}
