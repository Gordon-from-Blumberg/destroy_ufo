package com.gordonfromblumberg.games.core.ufo;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.gordonfromblumberg.games.core.common.Main;
import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.core.common.utils.ConfigManager;
import com.gordonfromblumberg.games.core.common.world.WorldRenderer;
import com.gordonfromblumberg.games.core.common.world.WorldScreen;

public class UfoScreen extends WorldScreen<UfoWorld> {

    public UfoScreen(SpriteBatch batch) {
        this(batch, new UfoWorld());
    }

    protected UfoScreen(SpriteBatch batch, UfoWorld world) {
        super(batch, world);
    }

    @Override
    protected void initialize() {
        super.initialize();

        final ConfigManager configManager = AbstractFactory.getInstance().configManager();
        configManager.getColor("clearColor", color);

        setGameObjectSizes();
    }

    @Override
    protected WorldRenderer<UfoWorld> createWorldRenderer() {
        return new UfoRenderer(world, batch);
    }

    private static void setGameObjectSizes() {
        final AssetManager assets = Main.getInstance().assets();
        final TextureAtlas atlas = assets.get("image/texture_pack.atlas", TextureAtlas.class);

        setGameObjectSize(atlas.findRegion("turret"), UfoWorld.TURRET_SIZE);
        setGameObjectSize(atlas.findRegion("bullet"), UfoWorld.BULLET_SIZE);
    }

    private static void setGameObjectSize(TextureAtlas.AtlasRegion region, Vector2 size) {
        size.set(region.getRegionWidth(), region.getRegionHeight());
    }
}
