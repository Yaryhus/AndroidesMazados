package dadm.scaffold.space;


import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameEvent;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class SpaceShipPlayer extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 12;
    private static final int INITIAL_AUTOBULLET_POOL_AMOUNT = 10;

    private static long TIME_BETWEEN_BULLETS = 1500;
    private static long TIME_BETWEEN_AUTOBULLETS = 1500;

    public int numberOfBombs = 4;
    private static long TIME_REDUCED = 10000;

    List<Bullet> bullets = new ArrayList<Bullet>();
    List<AutoBullet> autobullets = new ArrayList<AutoBullet>();

    private long timeSinceLastFire;
    private long timeSinceLastAutoFire;
    private long timeSinceReduced;


    public int HP;
    private int score;
    private int maxX;
    private int maxY;
    private double speedFactor;

    boolean timeReduced = false;


    public SpaceShipPlayer(GameEngine gameEngine, int drawableRes) {
        super(gameEngine, drawableRes);
        speedFactor = pixelFactor * 100d / 250d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
        typeS = "player";
        HP = 10;
        initBulletPool(gameEngine);
    }

    public int getHP() {
        return HP;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHP(int hp) {
        this.HP = hp;
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i = 0; i < INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }

        for (int i = 0; i < INITIAL_AUTOBULLET_POOL_AMOUNT; i++) {
            autobullets.add(new AutoBullet(gameEngine));
        }
    }

    private AutoBullet getAutoBullet() {
        if (autobullets.isEmpty()) {
            return null;
        }
        return autobullets.remove(0);
    }

    void releaseAutoBullet(AutoBullet bullet) {

        autobullets.add(bullet);


    }

    private Bullet getBullet() {
        if (bullets.isEmpty()) {
            return null;
        }
        return bullets.remove(0);
    }

    void releaseBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    @Override
    public void startGame() {
        positionX = maxX / 2;
        positionY = maxY / 2;
    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        // Get the info from the inputController
        updatePosition(elapsedMillis, gameEngine.theInputController);
        checkFiring(elapsedMillis, gameEngine);
        checkAutoFiring(elapsedMillis, gameEngine);


        if (timeReduced) {
            if (timeSinceReduced > TIME_REDUCED) {

                TIME_BETWEEN_BULLETS = 1500;
                TIME_BETWEEN_AUTOBULLETS = 1500;

                timeSinceReduced = 0;

                timeReduced = false;
            } else {
                timeSinceReduced += elapsedMillis;
            }
        }
    }

    //Si colisiona con algo pierde vida
    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

        //Si no es un item ni una bala propia, pierde vida
        if (!collider.typeS.equals("bullet") && !collider.typeS.equals("explosion") && !collider.typeS.equals("time") && !collider.typeS.equals("life")) {
            gameEngine.onGameEvent(GameEvent.SpaceshipHit);
            HP -= 1;
        }

    }

    private void updatePosition(long elapsedMillis, InputController inputController) {
        positionX += speedFactor * inputController.horizontalFactor * elapsedMillis;
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionX > maxX) {
            positionX = maxX;
        }
        positionY += speedFactor * inputController.verticalFactor * elapsedMillis;
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionY > maxY) {
            positionY = maxY;
        }
    }

    private void checkAutoFiring(long elapsedMillis, GameEngine gameEngine) {
        if (timeSinceLastAutoFire > TIME_BETWEEN_AUTOBULLETS) {
            AutoBullet bullet = getAutoBullet();

            if (bullet == null) {

                return;

            }


            bullet.init(this, positionX + imageWidth, positionY + imageHeight / 2);
            gameEngine.addGameObject(bullet);
            timeSinceLastAutoFire = 0;
            gameEngine.onGameEvent(GameEvent.LaserFired);
        } else {
            timeSinceLastAutoFire += elapsedMillis;
        }

    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (numberOfBombs > 0) {
            if (gameEngine.theInputController.isFiring && timeSinceLastFire > TIME_BETWEEN_BULLETS) {


                Bullet bullet = getBullet();
                numberOfBombs--;
                if (bullet == null) {
                    return;
                }
                bullet.init(this, positionX + imageWidth, positionY + imageHeight / 2, 30);
                gameEngine.addGameObject(bullet);


                bullet = getBullet();
                if (bullet == null) {
                    return;
                }
                bullet.init(this, positionX + imageWidth, positionY + imageHeight / 2, 0);
                gameEngine.addGameObject(bullet);

                bullet = getBullet();
                if (bullet == null) {
                    return;
                }
                bullet.init(this, positionX + imageWidth, positionY + imageHeight / 2, -30);
                gameEngine.addGameObject(bullet);


                timeSinceLastFire = 0;
                gameEngine.onGameEvent(GameEvent.LaserFired);
            } else {
                timeSinceLastFire += elapsedMillis;
            }
        }
    }


    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void reduceTime() {
        TIME_BETWEEN_BULLETS = 500;
        TIME_BETWEEN_AUTOBULLETS = 500;

        timeReduced = true;

    }

}
