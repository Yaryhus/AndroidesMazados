package dadm.scaffold.space;

import dadm.scaffold.EndGame;
import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.GameEvent;
import dadm.scaffold.engine.Sprite;

public class Bullet extends Sprite {

    private double speedFactor;

    private SpaceShipPlayer parent;
    double maxX, maxY, speedX, speedY;



    public Bullet(GameEngine gameEngine){
        super(gameEngine, R.drawable.bomb);
        typeS = "bullet";
        speedFactor = gameEngine.pixelFactor * -300d / 500d;

        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;
    }

    @Override
    public void startGame() {}

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionX -= speedX * elapsedMillis;

        positionY -= speedY * elapsedMillis;

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


    public void init(SpaceShipPlayer parentPlayer, double initPositionX, double initPositionY, int angle) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;


        if(angle < 0){
            angle += 360;
        }



        speedX = speedFactor * Math.cos(Math.toRadians(angle));
        speedY = speedFactor * Math.sin(Math.toRadians(angle));


        parent = parentPlayer;
    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

        //Si no es otra bala, se destruye.
        if(collider.typeS.equals("enemy") || collider.typeS.equals("enemybullet")|| collider.typeS.equals("asteroid")) {

           //gameEngine.getPlayer().setScore(gameEngine.getPlayer().getScore()+1);

            parent.releaseBullet(this);
            gameEngine.removeGameObject(this);

            gameEngine.addGameObject(new Explosion(gameEngine,positionX- imageWidth/2,positionY- imageHeight/2, R.drawable.explosion));

            gameEngine.onGameEvent(GameEvent.AsteroidHit);
        }
    }
}
