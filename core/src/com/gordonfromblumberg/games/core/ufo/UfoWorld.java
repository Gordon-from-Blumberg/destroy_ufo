package com.gordonfromblumberg.games.core.ufo;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.gordonfromblumberg.games.core.common.factory.AbstractFactory;
import com.gordonfromblumberg.games.core.common.model.GameObject;
import com.gordonfromblumberg.games.core.common.model.PhysicsGameObject;
import com.gordonfromblumberg.games.core.common.physics.GravityMovingStrategy;
import com.gordonfromblumberg.games.core.common.utils.ConfigManager;
import com.gordonfromblumberg.games.core.common.utils.RandomGen;
import com.gordonfromblumberg.games.core.common.world.World;

import java.util.Iterator;

public class UfoWorld extends World {
    private static final int MAX_ANGLE_VELOCITY = 7;
    private static final int SHOOT_DELAY = 12;
    private static final float BULLET_VELOCITY = 42;
    private static final GravityMovingStrategy BULLET_MOVING_STRATEGY = new GravityMovingStrategy(0, -2);
    private static final Vector2 TURRET_ORIGIN = new Vector2();
    private static final Rectangle SCREEN_BOUNDS = new Rectangle(0, 0, 640, 480);

    static final Vector2 TURRET_SIZE = new Vector2();
    static final Vector2 BULLET_SIZE = new Vector2();

    static {
        final ConfigManager configManager = AbstractFactory.getInstance().configManager();
        TURRET_ORIGIN.set(configManager.getFloat("worldWidth") / 2,
                configManager.getFloat("turretY"));
    }

    private final RandomGen rand;
    private final Pool<PhysicsGameObject> gameObjectPool = new Pool<>() {
        @Override
        protected PhysicsGameObject newObject() {
            return new PhysicsGameObject();
        }
    };
    private final Controls controls;

    private int angle; // 0 - up, 90 - right, -90 - left
    private int angleVelocity;
    private int shootDelay;

    final Array<PhysicsGameObject> bullets = new Array<>();
    private final Vector2 temp = new Vector2();
    private final Array<GameObject> objectsToRemove = new Array<>();

    public UfoWorld() {
        this(PlayerControls.INSTANCE, RandomGen.INSTANCE.nextLong());
    }

    public UfoWorld(Controls controls, long seed) {
        this.controls = controls;
        this.rand = new RandomGen(seed);
    }

    public int getAngle() {
        return angle;
    }

    @Override
    public void update(float delta, float mouseX, float mouseY) {
        super.update(delta, mouseX, mouseY);

        if (controls.isLeftPressed()) {
            if (angleVelocity > -MAX_ANGLE_VELOCITY)
                --angleVelocity;
        } else if (controls.isRightPressed()) {
            if (angleVelocity < MAX_ANGLE_VELOCITY)
                ++angleVelocity;
        } else {
            angleVelocity = 0;
        }

        angle += angleVelocity;
        if (angle < -45) angle = -45;
        else if (angle > 45) angle = 45;

        --shootDelay;
        if (shootDelay < 0) shootDelay = 0;
        if (controls.isFirePressed() && shootDelay == 0) {
            shoot();
        }

        for (PhysicsGameObject bullet : bullets) {
            bullet.update(1);
            if (isOutOfScreen(bullet)) {
                objectsToRemove.add(bullet);
            }
        }


    }

    private void shoot() {
        shootDelay = SHOOT_DELAY;

        PhysicsGameObject bullet = obtainGameObject();
        bullet.setMovingStrategy(BULLET_MOVING_STRATEGY);
        bullet.setSize(BULLET_SIZE.x, BULLET_SIZE.y);
        final Vector2 temp = this.temp.set(TURRET_SIZE.y, 0).setAngleDeg(-angle);
        temp.set(-temp.y, temp.x).add(TURRET_ORIGIN);
        bullet.setPosition(temp.x, temp.y);
        bullet.velocity.set(0, BULLET_VELOCITY).rotateDeg(-angle);
        bullet.setActive(true);
        bullet.setRegion("bullet");
        bullets.add(bullet);
    }

    private PhysicsGameObject obtainGameObject() {
        return gameObjectPool.obtain();
    }

    private void releaseGameObject(PhysicsGameObject object) {
        gameObjectPool.free(object);
    }

    public Vector2 getTurretOrigin() {
        return TURRET_ORIGIN;
    }

    private boolean isOutOfScreen(GameObject object) {
        return !SCREEN_BOUNDS.overlaps(object.getBoundingRectangle())
                && object.position.y < SCREEN_BOUNDS.height;
    }
}
