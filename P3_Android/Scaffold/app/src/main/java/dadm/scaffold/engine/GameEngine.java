package dadm.scaffold.engine;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dadm.scaffold.input.InputController;

public class GameEngine {


    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    private List<GameObject> objectsToAdd = new ArrayList<GameObject>();
    private List<GameObject> objectsToRemove = new ArrayList<GameObject>();

    private UpdateThread theUpdateThread;
    private DrawThread theDrawThread;
    public InputController theInputController;
    private final GameView theGameView;

    public int width;
    public int height;
    public double pixelFactor;

    private Activity mainActivity;

    public GameEngine(Activity activity, GameView gameView) {
        mainActivity = activity;

        theGameView = gameView;
        theGameView.setGameObjects(this.gameObjects);
        this.width = theGameView.getWidth()
                - theGameView.getPaddingRight() - theGameView.getPaddingLeft();
        this.height = theGameView.getHeight()
                - theGameView.getPaddingTop() - theGameView.getPaddingTop();

        this.pixelFactor = this.height / 400d;


    }

    public void setTheInputController(InputController inputController) {
        theInputController = inputController;
    }

    public void startGame() {
        // Stop a game if it is running
        stopGame();

        // Setup the game objects
        int numGameObjects = gameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            gameObjects.get(i).startGame();
        }

        // Start the update thread
        theUpdateThread = new UpdateThread(this);
        theUpdateThread.start();

        // Start the drawing thread
        theDrawThread = new DrawThread(this);
        theDrawThread.start();
    }

    public void stopGame() {
        if (theUpdateThread != null) {
            theUpdateThread.stopGame();
        }
        if (theDrawThread != null) {
            theDrawThread.stopGame();
        }
    }

    public void pauseGame() {
        if (theUpdateThread != null) {
            theUpdateThread.pauseGame();
        }
        if (theDrawThread != null) {
            theDrawThread.pauseGame();
        }
    }

    public void resumeGame() {
        if (theUpdateThread != null) {
            theUpdateThread.resumeGame();
        }
        if (theDrawThread != null) {
            theDrawThread.resumeGame();
        }
    }

    public void addGameObject(GameObject gameObject) {
        if (isRunning()) {
            objectsToAdd.add(gameObject);

        } else {
            gameObjects.add(gameObject);
        }
        mainActivity.runOnUiThread(gameObject.onAddedRunnable);
    }

    public void removeGameObject(GameObject gameObject) {
        objectsToRemove.add(gameObject);
        mainActivity.runOnUiThread(gameObject.onRemovedRunnable);
    }

    public void onUpdate(long elapsedMillis) {
        int numGameObjects = gameObjects.size();
        for (int i = 0; i < numGameObjects; i++) {
            gameObjects.get(i).onUpdate(elapsedMillis, this);
        }
        synchronized (gameObjects) {
            while (!objectsToRemove.isEmpty()) {
                gameObjects.remove(objectsToRemove.remove(0));
            }
            while (!objectsToAdd.isEmpty()) {
                gameObjects.add(objectsToAdd.remove(0));
            }

            if(!gameObjects.isEmpty()) {
                for (int i = 0; i < gameObjects.size() - 1; i++) {
                    for (int j = 0; j < gameObjects.size() - 1; j++) {
                        colisionDetection(gameObjects.get(i), gameObjects.get(j));
                    }
                }
            }

        }

    }

    //Detectamos colisiones entre dos objetos, a y b. Gracias a sus Sprites, que tienen las dimensiones.
    public boolean colisionDetection(GameObject a, GameObject b)
    {
        //casteamos a Sprite si podemos.
        Sprite aa, bb;

        //Si el GameObject es de tipo sprite

            if(b.typeGO != null && a.typeGO != null && a.typeGO.equals("sprite") && b.typeGO.equals("sprite")){
            aa = (Sprite) a;
            bb = (Sprite) b;

            //Calculamos si colisionan

            if (Math.abs(aa.positionX + aa.imageWidth/2 - bb.positionX + bb.imageWidth/2)<=(aa.imageWidth + bb.imageWidth)/2f
                    && Math.abs(aa.positionY + aa.imageHeight/2 - bb.positionY + bb.imageHeight/2)<=(aa.imageHeight+bb.imageHeight)/2f)

                //Esquina sup dcha, sup izqda, inf derecha e inf izq. Centro
                // bb.positionY + bb.imageHeight
                // bb.positionY - bb.imageHeight
                // bb.positionX + bb.imageWidth
                // bb.positionX - bb.imageWidth
                // bb.positionX + bb.imageWidth/2 , bb.positionY + bb.imageHeight/2

                /*
                if(-bb.positionY <= -aa.positionY - aa.imageHeight &&
                        -bb.positionY - bb.imageHeight >= aa.positionY    &&
                        -bb.positionX - bb.imageWidth >= aa.positionX &&
                        bb.positionX <= -aa.positionX - aa.imageWidth)
                //*/

            {
                Log.d("AuxilioMeDesmayo", "No detectada colision entre" + a + " y " + b);

                //Ejecutamos sus métodos de colision
                a.onCollision(this,bb);
                b.onCollision(this,aa);
                return true;
            }
            else {
                //Log.d("Colision", "No detectada colision entre" + a + " y " + b);
                return false;
            }
        }//Si el Gameobject NO es de tipo Sprite
        else
            return false;


    }




    public void onDraw() {
        theGameView.draw();
    }

    public boolean isRunning() {
        return theUpdateThread != null && theUpdateThread.isGameRunning();
    }

    public boolean isPaused() {
        return theUpdateThread != null && theUpdateThread.isGamePaused();
    }

    public Context getContext() {
        return theGameView.getContext();
    }
}
