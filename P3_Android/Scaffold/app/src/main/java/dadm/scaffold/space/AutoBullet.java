package dadm.scaffold.space;

import android.util.Log;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameEvent;
import dadm.scaffold.engine.Sprite;

public class AutoBullet extends Sprite {

    private double speedFactor;
    double maxX, maxY;

    private SpaceShipPlayer parent;

    public AutoBullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.bullet);
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
            Log.i ("HOLA", "HEEEY1");
            parent.releaseAutoBullet(this);
            gameEngine.removeGameObject(this);
            // And return it to the pool
        }


        if (positionX > maxX)  {
            Log.i ("HOLA", "HEEEY2");
            parent.releaseAutoBullet(this);
            gameEngine.removeGameObject(this);
            // And return it to the pool
        }


        if (positionY < 0) {
            //Ricochet();
            Log.i ("HOLA", "HEEEY3");
            parent.releaseAutoBullet(this);
            gameEngine.removeGameObject(this);
            // And return it to the pool
        }
        if (positionX < 0) {
            // Ricochet();
            Log.i ("HOLA", "HEEEY4");
            parent.releaseAutoBullet(this);
            gameEngine.removeGameObject(this);
            // And return it to the pool

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

           gameEngine.getPlayer().setScore(gameEngine.getPlayer().getScore()+1);

            parent.releaseAutoBullet(this);

            gameEngine.removeGameObject(this);
            gameEngine.onGameEvent(GameEvent.AsteroidHit);
        }
    }
}
