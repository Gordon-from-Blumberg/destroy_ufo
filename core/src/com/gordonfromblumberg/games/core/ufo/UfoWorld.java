package com.gordonfromblumberg.games.core.ufo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.gordonfromblumberg.games.core.common.model.GameObject;
import com.gordonfromblumberg.games.core.common.utils.RandomGen;
import com.gordonfromblumberg.games.core.common.world.World;

public class UfoWorld extends World {
    private static final int MAX_ANGLE_VELOCITY = 7;

    private final RandomGen rand;
    private final Pool<GameObject> gameObjectPool = new Pool<>() {
        @Override
        protected GameObject newObject() {
            return new GameObject();
        }
    };

    private int angle; // 0 - up, 90 - right, -90 - left
    private int angleVelocity;

    private final Array<GameObject> bullets = new Array<>();

    public UfoWorld() {
        this(RandomGen.INSTANCE.nextLong());
    }

    public UfoWorld(long seed) {
        rand = new RandomGen(seed);
    }

    public int getAngle() {
        return angle;
    }

    @Override
    public void update(float delta, float mouseX, float mouseY) {
        super.update(delta, mouseX, mouseY);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (angleVelocity > -MAX_ANGLE_VELOCITY)
                --angleVelocity;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (angleVelocity < MAX_ANGLE_VELOCITY)
                ++angleVelocity;
        } else {
            angleVelocity = 0;
        }

        angle += angleVelocity;
        if (angle < -45) angle = -45;
        else if (angle > 45) angle = 45;
    }

    private GameObject obtainGameObject() {
        return gameObjectPool.obtain();
    }

    private void releaseGameObject(GameObject object) {
        gameObjectPool.free(object);
    }
}
