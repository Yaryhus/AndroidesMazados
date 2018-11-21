package dadm.scaffold.space;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class EnemySpawner extends Sprite {



    private static final int INITIAL_ENEMY_POOL_AMOUNT = 6;
    private static final long TIME_BETWEEN_ENEMIES = 250;
    List<BulletEnemy> bullets = new ArrayList<BulletEnemy>();
    private long timeSinceLastFire;
    int mEnemiesSpawned;
    int mCurrentMillis = 0;



    public EnemySpawner(GameEngine gameEngine){
        super(gameEngine, R.drawable.ship);
        typeS = "none";

    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        mCurrentMillis += elapsedMillis;

        long waveTimestamp = TIME_BETWEEN_ENEMIES;
        if (mCurrentMillis > waveTimestamp) {
            gameEngine.addGameObject(new Asteroid(gameEngine));
            mCurrentMillis = 0;
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

    }

}
