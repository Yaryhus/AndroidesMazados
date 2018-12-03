package dadm.scaffold.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;


public class ParallaxBackground extends GameObject {

    Bitmap bitmap;

    double pixelFactor;

    double speedY;

    double imageHeight;

    double imageWidth;

    int screenHeight;

    double screenWidth;


    double positionY = 0;


    Matrix matrix;

    public ParallaxBackground(GameEngine gameEngine, int speed, int drawableResId) {
        Drawable spriteDrawable = gameEngine.getContext().getResources().getDrawable(drawableResId);
        bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
        pixelFactor = gameEngine.pixelFactor;
        speedY = speed * pixelFactor / 1000d;

        screenHeight = gameEngine.height;
        screenWidth = gameEngine.width;

        int newWidth = gameEngine.width;
        int newHeight = (bitmap.getHeight() * newWidth) / bitmap.getWidth();

        //   h    -   w
        //   x   -   w'


        bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);


        imageHeight = newHeight;
        imageWidth = newWidth;

        positionY = imageWidth;


        typeGO = "background";
        matrix = new Matrix();
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        positionY -= speedY * elapsedMillis;


    }

    @Override
    public void onDraw(Canvas canvas) {


        matrix.reset();

        matrix.postTranslate((float) (positionY - imageWidth), 0);
        canvas.drawBitmap(bitmap, matrix, null);

        matrix.reset();

        matrix.postTranslate((float) positionY, 0);
        canvas.drawBitmap(bitmap, matrix, null);
        if (positionY < 0) {
            positionY = imageWidth;
        }


    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

    }


}




