package dadm.scaffold.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;


public class ParallaxBackground extends GameObject {

    Bitmap bitmap;

    double pixelFactor;

    double speedY;

    double imageHeight;

    double imageWidth;

    int screenHeight;

    double screenWidth;

    double targetHeight;

    double positionY = 0;

    Rect srcRect = new Rect();
    Rect dstRect = new Rect();



    Matrix matrix;

    public ParallaxBackground(GameEngine gameEngine, int speed, int drawableResId) {
        Drawable spriteDrawable = gameEngine.getContext().getResources().getDrawable(drawableResId);
        bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
        pixelFactor = gameEngine.pixelFactor;
        speedY = speed * pixelFactor / 1000d;

        screenHeight = gameEngine.height;
        screenWidth = gameEngine.width;


        int newHeight = (int) Math.floor((double) bitmap.getHeight() *( (double) gameEngine.width / (double) bitmap.getHeight()));

        int newWidth = (int) Math.floor( ( (double) newHeight * (double) bitmap.getWidth()) / (double) bitmap.getHeight());

        bitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, false);

       // imageHeight = spriteDrawable.getIntrinsicHeight() * pixelFactor;
      //  imageWidth = spriteDrawable.getIntrinsicWidth() * pixelFactor;



       imageHeight = newHeight;
        imageWidth = newWidth;

        targetHeight = Math.min(imageHeight, screenHeight);

        // x    -  y
        // xn   - yn
         typeGO = "background";
         matrix = new Matrix();
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
         positionY -= speedY*elapsedMillis;


    }

    @Override
    public void onDraw(Canvas canvas) {


        Log.i("Hola", "Hey  " + positionY);

       // if (positionY > imageWidth) {

            matrix.reset();
            matrix.postScale((float) (pixelFactor),        (float) (pixelFactor));
            matrix.postTranslate((float) (positionY - imageWidth), 0);
            canvas.drawBitmap(bitmap, matrix, null);
    //    }
        matrix.reset();
        matrix.postScale((float) (pixelFactor),      (float) (pixelFactor));
        matrix.postTranslate((float) positionY , 0);
        canvas.drawBitmap(bitmap, matrix, null);
        if (positionY < 0) {
            positionY += screenWidth;
        }






    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

    }

/*
    @Override
    public void onDraw(Canvas canvas) {

        if (positionY < screenWidth) {
            srcRect.set((int) (-positionY/pixelFactor),      0,      (int) ((screenWidth - positionY)/pixelFactor),      (int) (targetHeight/pixelFactor));
            dstRect.set(0,      0,      (int) screenWidth,      (int) targetHeight);

            canvas.drawBitmap(bitmap, srcRect, dstRect, null);
        }
        else
            {

                srcRect.set(0,      0,      (int) ((screenWidth - positionY)/pixelFactor),      (int) (targetHeight / pixelFactor));
                dstRect.set((int) positionY,      0,      (int) screenWidth,      (int) targetHeight);
                canvas.drawBitmap(bitmap, srcRect, dstRect, null);
                // We need to draw the previous block

                srcRect.set(0,      (int) ((imageHeight - positionY) / pixelFactor),      (int) (targetWidth/pixelFactor),      (int) (imageHeight/pixelFactor));
                dstRect.set(0,      0,      (int) targetWidth,      (int) positionY);
                canvas.drawBitmap(bitmap, srcRect, dstRect, null);


            }
            if (positionY > screenWidth) {
            positionY -= screenWidth;
        }
        }
        */


    }




