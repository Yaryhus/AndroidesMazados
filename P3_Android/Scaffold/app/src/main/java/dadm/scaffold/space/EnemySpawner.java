package dadm.scaffold.space;

import android.graphics.Canvas;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameEvent;
import dadm.scaffold.engine.GameObject;
import dadm.scaffold.engine.ParallaxBackground;
import dadm.scaffold.engine.Sprite;

public class EnemySpawner extends GameObject {



    private static final int INITIAL_ENEMY_POOL_AMOUNT = 2;

    private static final int INITIAL_SMARTENEMY_POOL_AMOUNT = 1;
    private static long TIME_BETWEEN_ENEMIES = 2000;
 //  List<BulletEnemy> bullets = new ArrayList<BulletEnemy>();
    private long timeSinceLastFire;
    int mEnemiesSpawned;
    int mCurrentMillis = 0;


    List<Enemy> enemies = new ArrayList<Enemy>();
    List<Asteroid> asteroids = new ArrayList<Asteroid>();
    List<SmartEnemy> smartEnemies = new ArrayList<SmartEnemy>();

    public EnemySpawner(GameEngine gameEngine){
     //   super(gameEngine, R.drawable.ship);
       // typeS = "none";

        for (int i=0; i<INITIAL_ENEMY_POOL_AMOUNT; i++) {
            enemies.add(new Enemy(gameEngine));

        }

        for (int i=0; i<INITIAL_SMARTENEMY_POOL_AMOUNT; i++) {

            asteroids.add(new Asteroid(gameEngine));
            smartEnemies.add(new SmartEnemy(gameEngine));
        }

    }





    void releaseEnemy(Enemy enemy) {

        enemies.add(enemy);

    }

    void releaseSmartEnemy(SmartEnemy smartEnemy) {

        smartEnemies.add(smartEnemy);

    }

    void releaseAsteroid(Asteroid asteroid) {

        asteroids.add(asteroid);

    }

    @Override
    public void startGame() {

    }

    boolean createEnemies = false;

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        mCurrentMillis += elapsedMillis;




            if(gameEngine.getPlayer().getScore() > 50 && createEnemies == false) {
                for (int i = 0; i < 2; i++) {
                    enemies.add(new Enemy(gameEngine));
                    asteroids.add(new Asteroid(gameEngine));
                    smartEnemies.add(new SmartEnemy(gameEngine));
         //           gameEngine.addGameObject(  new ParallaxBackground(gameEngine, 300,    R.drawable.rampage));
                }

                gameEngine.addGameObject(  new ParallaxBackground(gameEngine, 300,    R.drawable.rampage));
                createEnemies = true;
                TIME_BETWEEN_ENEMIES = 100;
            }

        if(mCurrentMillis>TIME_BETWEEN_ENEMIES) {

            checkEnemies(elapsedMillis, gameEngine);
            checkAsteroids(elapsedMillis, gameEngine);
            checkSmartEnemies(elapsedMillis, gameEngine);
            mCurrentMillis = 0;
        }


    }


    private void checkEnemies(long elapsedMillis, GameEngine gameEngine) {

        if (enemies.isEmpty()) {
            return;
        }
       Enemy enemy = enemies.remove(0);

        enemy.init(this, gameEngine);

        gameEngine.addGameObject(enemy);

    }

    private void checkSmartEnemies(long elapsedMillis, GameEngine gameEngine) {

        if (smartEnemies.isEmpty()) {
            return;
        }
        SmartEnemy smartEnemy = smartEnemies.remove(0);

        smartEnemy.init(this, gameEngine);

        gameEngine.addGameObject(smartEnemy);

    }


    private void checkAsteroids(long elapsedMillis, GameEngine gameEngine) {


        if (asteroids.isEmpty()) {
            return;
        }
        Asteroid asteroid = asteroids.remove(0);

        asteroid.init(this, gameEngine);

        gameEngine.addGameObject(asteroid);

    }

    @Override
    public void onDraw(Canvas canvas) {

    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

    }

}
