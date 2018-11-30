package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class SmartEnemy extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final long TIME_BETWEEN_BULLETS = 2000;
    List<SmartBullet> bullets = new ArrayList<SmartBullet>();
    private long timeSinceLastFire;

    private int maxX;
    private int maxY;
    private double speedFactor;

    Random rnd = new Random();


    private final double mSpeed = 0.2;
    private double mSpeedX;
    private double mSpeedY;

    EnemySpawner parent;

    public SmartEnemy(GameEngine gameEngine){
        super(gameEngine, R.drawable.spaceship_enemy_red);

        typeS = "enemy";


    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new SmartBullet(gameEngine));
        }
    }

    private SmartBullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    void releaseBullet(SmartBullet bullet) {
        bullets.add(bullet);
    }


    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

      //  positionY += mSpeedY * elapsedMillis;
        positionX += mSpeedX * elapsedMillis;

        if (positionY < -imageHeight) {
            parent.releaseSmartEnemy(this);
            gameEngine.removeGameObject(this);
            // And return it to the pool
            //parent.releaseBullet(this);
        }
        if (positionX < -imageWidth) {
            parent.releaseSmartEnemy(this);
            gameEngine.removeGameObject(this);
            // And return it to the pool
            //parent.releaseBullet(this);
        }


        checkFiring(elapsedMillis, gameEngine);
    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {


        //Si colisiona con una bala
        if(collider.typeS.equals("bullet")||collider.typeS.equals("player")) {

            gameEngine.addGameObject(new Explosion(gameEngine,positionX- imageWidth/2,positionY- imageHeight/2, R.drawable.galaxy_3));
            gameEngine.getPlayer().setScore(gameEngine.getPlayer().getScore()+10);

            parent.releaseSmartEnemy(this);
            gameEngine.removeGameObject(this);
        }
    }


    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            SmartBullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX - imageWidth/2, positionY + imageWidth/2);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
    }


    public void init(EnemySpawner parent, GameEngine gameEngine) {
        this.parent = parent;


        // They initialize in a [-30, 30] degrees angle
        double angle = rnd.nextDouble() * 3d * Math.PI / 4d - 5d * Math.PI / 4d;
        mSpeedX = mSpeed * Math.cos(angle);
        mSpeedY = mSpeed * Math.sin(angle);


        // They initialize outside of the screen vertically
        positionX = gameEngine.width;

        // Asteroids initialize in the central 50% of the screen
        positionY = rnd.nextInt(gameEngine.height / 2) +
                gameEngine.height / 4;


        initBulletPool(gameEngine);
    }

}
