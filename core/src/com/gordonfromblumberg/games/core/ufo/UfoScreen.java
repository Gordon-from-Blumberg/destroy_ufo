package com.gordonfromblumberg.games.core.ufo;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    protected WorldRenderer<UfoWorld> createWorldRenderer() {
        return new UfoRenderer(world, batch);
    }
}
