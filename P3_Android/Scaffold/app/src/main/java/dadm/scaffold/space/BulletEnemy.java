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

    public BulletEnemy(GameEngine gameEngine){
        super(gameEngine, R.drawable.bullet);
        typeS = "bulletenemy";

        double angle = rnd.nextDouble()*Math.PI/3d-Math.PI/6d;
        speedX = speedFactor * Math.sin(angle);
        speedY = speedFactor * Math.cos(angle);


      //  speedFactor = gameEngine.pixelFactor * -300d / 1000d;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        positionY += speedY * elapsedMillis;
        positionX += speedX * elapsedMillis;

        if (positionY < -imageHeight) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }
        if (positionX < -imageWidth) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            parent.releaseBullet(this);
        }


    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {
        gameEngine.removeGameObject(this);

    }


    public void init(Enemy parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentPlayer;
    }
}
