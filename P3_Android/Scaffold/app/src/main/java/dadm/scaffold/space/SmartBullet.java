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

    public SmartBullet(GameEngine gameEngine) {
        super(gameEngine, R.drawable.space_mine);
        typeS = "bulletenemy";

        this.gameEngine = gameEngine;

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


    public void init(SmartEnemy parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth / 2;
        positionY = initPositionY - imageHeight / 2;


        parent = parentPlayer;

        double angle = (double) Math.toDegrees(Math.atan2(gameEngine.getPlayer().getPositionY() - initPositionY, gameEngine.getPlayer().getPositionX() - initPositionX));

        if (angle < 0) {
            angle += 360;
        }


        speedX = speedFactor * Math.cos(Math.toRadians(angle));
        speedY = speedFactor * Math.sin(Math.toRadians(angle));

    }
}
