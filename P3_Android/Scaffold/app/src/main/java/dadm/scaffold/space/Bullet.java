package dadm.scaffold.space;

import dadm.scaffold.EndGame;
import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameEvent;
import dadm.scaffold.engine.Sprite;

public class Bullet extends Sprite {

    private double speedFactor;

    private SpaceShipPlayer parent;
    double maxX, maxY;

    public Bullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.s_coffee);
        typeS = "bullet";
        speedFactor = gameEngine.pixelFactor * -300d / 1000d;

        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionX -= speedFactor * elapsedMillis;

        if (positionY > maxY)  {
            parent.releaseBullet(this);
            gameEngine.removeGameObject(this);
        }


        if (positionY > maxX)  {
            parent.releaseBullet(this);
            gameEngine.removeGameObject(this);
        }

        if (positionY < 0) {
            parent.releaseBullet(this);
            gameEngine.removeGameObject(this);
        }
        if (positionX < 0) {
            parent.releaseBullet(this);
            gameEngine.removeGameObject(this);

        }

    }


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
        parent = parentPlayer;
    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

        //Si no es otra bala, se destruye.
        if(!collider.typeS.equals("bullet")) {

           //gameEngine.getPlayer().setScore(gameEngine.getPlayer().getScore()+1);

            parent.releaseBullet(this);
            gameEngine.removeGameObject(this);

            gameEngine.onGameEvent(GameEvent.AsteroidHit);
        }
    }
}
