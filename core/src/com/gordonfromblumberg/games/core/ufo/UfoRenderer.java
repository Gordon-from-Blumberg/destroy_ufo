package com.gordonfromblumberg.games.core.ufo;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gordonfromblumberg.games.core.common.Main;
import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.core.common.model.PhysicsGameObject;
import com.gordonfromblumberg.games.core.common.utils.ConfigManager;
import com.gordonfromblumberg.games.core.common.world.WorldRenderer;

public class UfoRenderer extends WorldRenderer<UfoWorld> {

    private final Batch batch;

    private TextureRegion turret;
    private TextureRegion turretCover;

    public UfoRenderer(UfoWorld world, Batch batch) {
        super(world);

        this.batch = batch;

        init();
    }

    @Override
    public void render(float dt) {
        final UfoWorld world = this.world;
        final Batch batch = this.batch;
        final float width = viewport.getWorldWidth();
        final float height = viewport.getWorldHeight();
        final Vector2 turretOrigin = world.getTurretOrigin();

        batch.begin();

        batch.draw(turret, turretOrigin.x - turret.getRegionWidth() * 0.5f, turretOrigin.y,
                turret.getRegionWidth() * 0.5f, 0,
                turret.getRegionWidth(), turret.getRegionHeight(), 1, 1, -world.getAngle());
        batch.draw(turretCover, (width - turretCover.getRegionWidth()) / 2, 0);

        for (PhysicsGameObject bullet : world.bullets) {
            bullet.render(batch);
        }

        batch.end();
    }

    @Override
    protected Viewport createViewport(Camera camera) {
        return new ScreenViewport(camera);
    }

    private void init() {
        final ConfigManager configManager = AbstractFactory.getInstance().configManager();
        final AssetManager assets = Main.getInstance().assets();
        final TextureAtlas atlas = assets.get("image/texture_pack.atlas", TextureAtlas.class);

        turret = atlas.findRegion("turret");
        turretCover = atlas.findRegion("turret_cover");
    }
}
