package dadm.scaffold.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public abstract class Item extends Sprite {

    Random rnd = new Random();


    public Item(GameEngine gameEngine, int drawable) {
        super(gameEngine, drawable);

        start(gameEngine);
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {


    }

    public void start(GameEngine gameEngine) {

        positionX = rnd.nextInt((int) Math.abs((gameEngine.width * 0.66) - imageWidth));

        positionY = rnd.nextInt(Math.abs((gameEngine.height - imageHeight) - imageHeight));
    }

    @Override
    public abstract void onCollision(GameEngine gameEngine, Sprite collider);

}
