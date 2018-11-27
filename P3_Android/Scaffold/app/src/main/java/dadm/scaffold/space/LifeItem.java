package dadm.scaffold.space;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class LifeItem extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 6;
    private static final long TIME_BETWEEN_BULLETS = 500;
    List<BulletEnemy> bullets = new ArrayList<BulletEnemy>();
    private long timeSinceLastFire;

    private int maxX;
    private int maxY;
    private double speedFactor;

    Random rnd = new Random();


    private final double mSpeed = 0.1;
    private double mSpeedX;
    private double mSpeedY;

    EnemySpawner parent;

    public LifeItem(GameEngine gameEngine){
        super(gameEngine, R.drawable.ship);

        typeS = "life";



        start(gameEngine);



    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {


    }

    public void start(GameEngine gameEngine){
        // They initialize outside of the screen vertically
        positionX = rnd.nextInt(gameEngine.width);

        // Asteroids initialize in the central 50% of the screen
        positionY = rnd.nextInt(gameEngine.height);
    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {


        //Si colisiona con un jugador
        if(collider.typeS.equals("player")) {
            ((SpaceShipPlayer) collider).HP = 10;
            start(gameEngine);
        }
    }



}
