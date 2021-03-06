package dadm.scaffold.space;

import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class BulletEnemy extends Sprite {

    private double speedFactor = 1;

    private Enemy parent;

    Random rnd = new Random();

    double speedX, speedY;

    public BulletEnemy(GameEngine gameEngine) {
        super(gameEngine, R.drawable.bullet_red);
        typeS = "bulletenemy";


        // They initialize in a [-30, 30] degrees angle
        double angle = rnd.nextDouble() * 3d * Math.PI / 4d - 5d * Math.PI / 4d;
        speedX = speedFactor * Math.cos(angle);
        speedY = speedFactor * Math.sin(angle);


        //  speedFactor = gameEngine.pixelFactor * -300d / 1000d;
    }

    @Override
    public void startGame() {
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        positionY += speedY * elapsedMillis;
        positionX += speedX * elapsedMillis;

        if (positionY < -imageHeight) {

            parent.releaseBullet(this);
            gameEngine.removeGameObject(this);
            // And return it to the pool
        }
        if (positionX < -imageWidth) {
            parent.releaseBullet(this);
            gameEngine.removeGameObject(this);
            // And return it to the pool

        }


    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {
        if (collider.typeS.equals("player") || collider.typeS.equals("bullet")) {
            parent.releaseBullet(this);
            gameEngine.addGameObject(new Explosion(gameEngine, positionX - imageWidth / 2, positionY - imageHeight / 2, R.drawable.explosion2));
            gameEngine.removeGameObject(this);
        }

    }


    public void init(Enemy parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth / 2;
        positionY = initPositionY - imageHeight / 2;
        parent = parentPlayer;
    }
}
