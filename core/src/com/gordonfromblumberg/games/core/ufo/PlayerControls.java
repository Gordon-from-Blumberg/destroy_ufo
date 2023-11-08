package com.gordonfromblumberg.games.core.ufo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerControls implements Controls {
    public static final PlayerControls INSTANCE = new PlayerControls();

    @Override
    public boolean isLeftPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT);
    }

    @Override
    public boolean isRightPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }

    @Override
    public boolean isFirePressed() {
        return Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }
}
