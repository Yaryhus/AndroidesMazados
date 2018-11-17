package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class Enemy extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final long TIME_BETWEEN_BULLETS = 250;
    List<BulletEnemy> bullets = new ArrayList<BulletEnemy>();
    private long timeSinceLastFire;

    private int maxX;
    private int maxY;
    private double speedFactor;

    Random rnd = new Random();
    int rnd1,rnd2;



    public Enemy(GameEngine gameEngine){
        super(gameEngine, R.drawable.ship);

        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;

        //randoms para posiciones y velocidades
        rnd1 = rnd.nextInt(10+1+10)-10;
        rnd2 = rnd.nextInt(10+1+10)-10;

        //Velocidad
        speedFactor = gameEngine.pixelFactor * -3d / 100d;


        initBulletPool(gameEngine);
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new BulletEnemy(gameEngine));
        }
    }

    private BulletEnemy getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    void releaseBullet(BulletEnemy bullet) {
        bullets.add(bullet);
    }


    @Override
    public void startGame() {
        positionX = rnd.nextInt(maxX);
        positionY  = rnd.nextInt(maxY);
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        positionY += speedFactor * elapsedMillis;
        positionX += speedFactor * elapsedMillis;

        if (positionY < -imageHeight) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            //parent.releaseBullet(this);
        }
        if (positionX < -imageWidth) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            //parent.releaseBullet(this);
        }


        checkFiring(elapsedMillis, gameEngine);
    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

    }


    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            BulletEnemy bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + imageWidth/2, positionY);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
    }

}
