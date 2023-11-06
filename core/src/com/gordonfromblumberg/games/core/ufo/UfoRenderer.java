package com.gordonfromblumberg.games.core.ufo;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.gordonfromblumberg.games.core.common.world.WorldRenderer;

public class UfoRenderer extends WorldRenderer<UfoWorld> {

    private final Batch batch;

    public UfoRenderer(UfoWorld world, Batch batch) {
        super(world);

        this.batch = batch;
    }
}
