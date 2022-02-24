package com.gordonfromblumberg.games.core.common.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gordonfromblumberg.games.core.common.Main;
import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.core.common.utils.ConfigManager;
import com.gordonfromblumberg.games.core.common.world.GameWorld;
import com.gordonfromblumberg.games.core.common.world.GameWorldRenderer;

public class GameScreen extends AbstractScreen {
    private GameWorld gameWorld;
    private GameWorldRenderer renderer;

    private final Vector3 coords = new Vector3();

    protected GameScreen(SpriteBatch batch) {
        super(batch);

        gameWorld = new GameWorld();
    }

    @Override
    public void initialize() {
        super.initialize();

        final ConfigManager configManager = AbstractFactory.getInstance().configManager();
        gameWorld.initialize();
        worldRenderer = renderer = new GameWorldRenderer(gameWorld, batch, viewport);
        renderer.initialize(viewport, viewport.getWorldHeight(), viewport.getWorldHeight());

        stage.addListener(new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                camera.zoom += amountY * 0.25;
                if (camera.zoom <= 0)
                    camera.zoom = 0.25f;
                return true;
            }
        });
    }

    @Override
    protected void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            camera.translate(-10, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            camera.translate(10, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            camera.translate(0, 10);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            camera.translate(0, -10);

        super.update(delta);            // apply camera moving and update batch projection matrix
        gameWorld.update(delta);        // update game state
    }

    @Override
    public void dispose() {
        gameWorld.dispose();

        super.dispose();
    }

    @Override
    protected void createUI() {
        super.createUI();

        final Skin uiSkin = assets.get("ui/uiskin.json", Skin.class);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.SPACE) {
                    gameWorld.pause();
                    return true;
                }
                return false;
            }
        });
    }
}
