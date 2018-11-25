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
        super(gameEngine, R.drawable.s_drop);
        typeS = "bulletenemy";



        // They initialize in a [-30, 30] degrees angle
        double angle = rnd.nextDouble()*3d*Math.PI/4d - 5d*Math.PI/4d;
        speedX = speedFactor * Math.cos(angle);
        speedY = speedFactor * Math.sin(angle);





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
