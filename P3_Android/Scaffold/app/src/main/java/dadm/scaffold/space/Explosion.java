package dadm.scaffold.space;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameEvent;
import dadm.scaffold.engine.Sprite;

public class Explosion extends Sprite {

    private static long TIME_REDUCED = 1000;

    private long timeSinceReduced;

    public Explosion(GameEngine gameEngine, double x, double y, int drawable){
        super(gameEngine, drawable);
        typeS = "explosion";
        positionX = x;
        positionY = y;


    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        if(timeSinceReduced > TIME_REDUCED) {

            gameEngine.removeGameObject(this);


        } else{
            timeSinceReduced += elapsedMillis;
        }
    }



    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

        }

}

