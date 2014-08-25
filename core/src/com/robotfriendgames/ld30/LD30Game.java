package com.robotfriendgames.ld30;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.robotfriendgames.ld30.data.GameStates;
import com.robotfriendgames.ld30.data.RenderLevel;
import com.robotfriendgames.ld30.game.GameEntity;
import com.robotfriendgames.ld30.game.InputProcessor;
import com.robotfriendgames.ld30.game.LD;
import com.robotfriendgames.ld30.logic.IntroLogic;
import com.robotfriendgames.ld30.logic.OutroLogic;
import com.robotfriendgames.ld30.logic.PlayLogic;

public class LD30Game extends ApplicationAdapter {
    public static final String TAG = LD30Game.class.getSimpleName();

    private IntroLogic introLogic;
    private PlayLogic playLogic;
    private OutroLogic outroLogic;

    private SpriteBatch batch;

    private Box2DDebugRenderer debugRenderer;
    //private Sprite platform;

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

        debugRenderer = new Box2DDebugRenderer();
        Gdx.input.setInputProcessor(new InputProcessor());
        LD.data.gameState = GameStates.INTRO_IN;

        //Texture platformTexture = new Texture("data/platform.png");
        //platform = new Sprite(platformTexture);
        //platform.setSize(platform.getWidth() * LD.settings.pixelsToWorld, platform.getHeight() * LD.settings.pixelsToWorld);
        //platform.flip(true, false);
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

        //if(LD.data.player != null) {
        //    platform.setPosition(LD.data.player.getX(), LD.data.player.getY());
        //}
        //platform.draw(batch);

        batch.end();


        debugRenderer.render(LD.data.world, LD.data.camera.combined);

        LD.data.world.step(delta, 6, 2);
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
        LD.assetManager.dispose();
    }
}
