package dadm.scaffold.space;

import android.util.Log;

import java.util.Random;

import dadm.scaffold.R;
import dadm.scaffold.engine.GameEngine;
import dadm.scaffold.engine.Sprite;

public class Asteroid extends Sprite {

    private double speedFactor = 1;


    private int maxX;
    private int maxY;
    Random rnd = new Random();
    int rnd1,rnd2;
    double speedX;
    double speedY;

    public Asteroid(GameEngine gameEngine){
        super(gameEngine, R.drawable.s_bread);

        maxX = gameEngine.width - imageWidth;
        maxY = gameEngine.height - imageHeight;

        typeS = "asteroid";


        //speedFactor = gameEngine.pixelFactor * -3d / 100d;
        // They initialize in a [-30, 30] degrees angle
        double angle = rnd.nextDouble()*Math.PI/3d-Math.PI/6d;
        speedX = speedFactor * Math.sin(angle);
        speedY = speedFactor * Math.cos(angle);


        // Asteroids initialize in the central 50% of the screen
        positionX = rnd.nextInt(gameEngine.width/2)+
                gameEngine.width/4;
        // They initialize outside of the screen vertically
        positionY = -imageHeight;
/*
        //randoms para posiciones y velocidades
        rnd1 = rnd.nextInt(10+1+10)-10;
        rnd2 = rnd.nextInt(10+1+10)-10;

        //Velocidad
        speedFactor = gameEngine.pixelFactor * -3d / 100d;

        */
    }

    @Override
    public void startGame() {
      //  positionX = rnd.nextInt(maxX);
     //   positionY  = rnd.nextInt(maxY);


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

        positionY += speedY*elapsedMillis;
        positionX += speedX*elapsedMillis;

        if (positionY < -imageHeight) {
            Ricochet();
            //gameEngine.removeGameObject(this);
            // And return it to the pool
            //parent.releaseBullet(this);
        }
        if (positionX < -imageWidth) {
            Ricochet();
            // gameEngine.removeGameObject(this);
            // And return it to the pool
            //

        }
        if (positionY > gameEngine.height) {
            Ricochet();
            //gameEngine.removeGameObject(this);
            // And return it to the pool
            //parent.releaseBullet(this);
        }
        if (positionX > gameEngine.width) {
            Ricochet();
            // gameEngine.removeGameObject(this);
            // And return it to the pool
            //

        }

    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

        //Log.d("OnColission_Asteroide","He colisionado");

        //Si colisiona con un asteroide o enemigo, rebota
        if(collider.typeS.equals("asteroid") || collider.typeS.equals("enemy") ) {
            Ricochet();
        }
        else {
            gameEngine.removeGameObject(this);
        }
    }

    //Rebote
    public void Ricochet()
    {
        speedX = -speedX;
        speedY = -speedY;
    }

    public void init(double initPositionX, double initPositionY) {
        positionX = initPositionX - imageWidth/2;
        positionY = initPositionY - imageHeight/2;
    }
}
