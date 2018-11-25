package dadm.scaffold.space;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameEvent;
import dadm.scaffold.engine.Sprite;
import dadm.scaffold.input.InputController;

public class SpaceShipPlayer extends Sprite {

    private static final int INITIAL_BULLET_POOL_AMOUNT = 50;
    private static final int INITIAL_AUTOBULLET_POOL_AMOUNT = 80;

    private static final long TIME_BETWEEN_BULLETS = 800;
    private static final long TIME_BETWEEN_AUTOBULLETS = 550;

    List<Bullet> bullets = new ArrayList<Bullet>();
    List<AutoBullet> autobullets = new ArrayList<AutoBullet>();

    private long timeSinceLastFire;

    private int HP;
    private int score;
    private int maxX;
    private int maxY;
    private double speedFactor;


    public SpaceShipPlayer(GameEngine gameEngine) {
        super(gameEngine, R.drawable.s_duck_a);
        speedFactor = pixelFactor * 100d / 500d; // We want to move at 100px per second on a 400px tall screen
        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
        typeS = "player";
        HP = 10;
        initBulletPool(gameEngine);
    }

    public int getHP()
    {
        return HP;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score=score;
    }

    public void setHP(int hp)
    {
        this.HP=hp;
    }

    private void initBulletPool(GameEngine gameEngine) {
        for (int i=0; i<INITIAL_BULLET_POOL_AMOUNT; i++) {
            bullets.add(new Bullet(gameEngine));
        }

        for (int i=0; i<INITIAL_AUTOBULLET_POOL_AMOUNT; i++) {
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
        checkAutoFiring(elapsedMillis,gameEngine);
    }

    //Si colisiona con algo pierde vida
    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

        if(!collider.typeS.equals("bullet")){
            gameEngine.onGameEvent(GameEvent.SpaceshipHit);
            HP-=1;
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

    private void checkAutoFiring(long elapsedMillis,GameEngine gameEngine) {
        if (timeSinceLastFire > TIME_BETWEEN_AUTOBULLETS) {
            AutoBullet bullet = getAutoBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + imageWidth/2, positionY);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
            gameEngine.onGameEvent(GameEvent.LaserFired);
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }

    }

    private void checkFiring(long elapsedMillis, GameEngine gameEngine) {
        if (gameEngine.theInputController.isFiring && timeSinceLastFire > TIME_BETWEEN_BULLETS) {
            Bullet bullet = getBullet();
            if (bullet == null) {
                return;
            }
            bullet.init(this, positionX + imageWidth/2, positionY);
            gameEngine.addGameObject(bullet);
            timeSinceLastFire = 0;
            gameEngine.onGameEvent(GameEvent.LaserFired);
        }
        else {
            timeSinceLastFire += elapsedMillis;
        }
    }

}
