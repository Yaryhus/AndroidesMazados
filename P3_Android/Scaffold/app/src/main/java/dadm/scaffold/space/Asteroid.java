package dadm.scaffold.space;

import android.util.Log;

import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Asteroid extends Sprite {

    private double speedFactor;


    private int maxX;
    private int maxY;
    Random rnd = new Random();
    int rnd1,rnd2;

    public Asteroid(GameEngine gameEngine){
        super(gameEngine, R.drawable.robot);

        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;

        typeS = "asteroid";

        //randoms para posiciones y velocidades
        rnd1 = rnd.nextInt(10+1+10)-10;
        rnd2 = rnd.nextInt(10+1+10)-10;

        //Velocidad
        speedFactor = gameEngine.pixelFactor * -3d / 100d;
    }

    @Override
    public void startGame() {
        positionX = rnd.nextInt(maxX);
        positionY  = rnd.nextInt(maxY);
        //positionX = gameEngine.height / 2;
        //positionY = maxY / 2;
    }

    /*
    public int genRandom()
    {
        int random = rnd.nextInt(10)+(-10);
        return random;
    }
    */

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {

        positionY += rnd1 * speedFactor * elapsedMillis;
        positionX += rnd2 * speedFactor * elapsedMillis;

        if (positionY < -imageHeight) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            //parent.releaseBullet(this);
        }
        if (positionX < -imageWidth) {
            gameEngine.removeGameObject(this);
            // And return it to the pool
            //parent.releaseBullet(this);
        }
    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

        Log.d("OnColission_Asteroide","He colisionado");

        //Si colisiona con un asteroide o enemigo, rebota
        if(collider.typeS.equals("asteroid") || collider.typeS.equals("enemy")) {
            Ricochet();
        }
        else {
            gameEngine.removeGameObject(this);
        }
    }

    //Rebote
    public void Ricochet()
    {
        rnd1 = -rnd1;
        rnd2 = -rnd2;
    }

    public void init(double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
    }
}
