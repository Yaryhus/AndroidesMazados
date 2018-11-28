package dadm.scaffold.space;

import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class SmartBullet extends Sprite {

    private double speedFactor = 1;

    private SmartEnemy parent;

    Random rnd = new Random();

    double speedX, speedY;

    GameEngine gameEngine;

    public SmartBullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.space_mine);
        typeS = "bulletenemy";


this.gameEngine = gameEngine;


        //  speedFactor = gameEngine.pixelFactor * -300d / 1000d;
    }

    @Override
    public void startGame() {}

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

        parent.releaseBullet(this);
        gameEngine.removeGameObject(this);



    }


    public void init(SmartEnemy parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;


        parent = parentPlayer;






        double angle = (double) Math.toDegrees(Math.atan2(gameEngine.getPlayer().getPositionY() - initPositionY, gameEngine.getPlayer().getPositionX() - initPositionX));

        if(angle < 0){
            angle += 360;
        }



        // They initialize in a [-30, 30] degrees angle
        angle = rnd.nextDouble()*3d*Math.PI/4d - 5d*Math.PI/4d;

        speedX = speedFactor * Math.cos(angle);
        speedY = speedFactor * Math.sin(angle);

    }
}
