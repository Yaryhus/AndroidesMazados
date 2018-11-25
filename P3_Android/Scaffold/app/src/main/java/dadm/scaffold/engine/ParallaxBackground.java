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

    double targetWidth;

    double positionY = 0;



    Matrix matrix;

    public ParallaxBackground(GameEngine gameEngine, int speed, int drawableResId) {
        Drawable spriteDrawable = gameEngine.getContext().getResources().getDrawable(drawableResId);
        bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();
        pixelFactor = gameEngine.pixelFactor;
        speedY = speed * pixelFactor / 1000d;
        imageHeight = spriteDrawable.getIntrinsicHeight() * pixelFactor;
        imageWidth = spriteDrawable.getIntrinsicWidth() * pixelFactor;
        screenHeight = gameEngine.height;
        screenWidth = gameEngine.width;
        targetWidth = Math.min(imageWidth, screenWidth);

         typeGO = "background";
         matrix = new Matrix();
    }

    @Override
    public void startGame() {

    }

    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
         positionY += speedY*elapsedMillis;
    }

    @Override
    public void onDraw(Canvas canvas) {

        if (positionY > 0) {

            matrix.reset();
            matrix.postScale((float) (pixelFactor),        (float) (pixelFactor));
            matrix.postTranslate(0, (float) (positionY - imageHeight));
            canvas.drawBitmap(bitmap, matrix, null);
        }
        matrix.reset();
        matrix.postScale((float) (pixelFactor),      (float) (pixelFactor));
        matrix.postTranslate(0, (float) positionY);
        canvas.drawBitmap(bitmap, matrix, null);
        if (positionY > screenHeight) {
            positionY -= imageHeight;
        }

    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite collider) {

    }

    /*
    private void efficientDraw(Canvas canvas) {
        if (positionY < 0) {
            mSrcRect.set(0,      (int) (-mPositionY/mPixelFactor),      (int) (mTargetWidth/mPixelFactor),      (int) ((mScreenHeight - mPositionY)/mPixelFactor));
            mDstRect.set(0,      0,      (int) mTargetWidth,      (int) mScreenHeight);

            canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null);
        }
        else
            {
                mSrcRect.set(0,      0,      (int) (mTargetWidth/mPixelFactor),      (int) ((mScreenHeight - mPositionY) / mPixelFactor));
                mDstRect.set(0,      (int) mPositionY,      (int) mTargetWidth,      (int) mScreenHeight);    canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null);
                // We need to draw the previous block
                mSrcRect.set(0,      (int) ((mImageHeight - mPositionY) / mPixelFactor),      (int) (mTargetWidth/mPixelFactor),      (int) (mImageHeight/mPixelFactor));
                mDstRect.set(0,      0,      (int) mTargetWidth,      (int) mPositionY);
                canvas.drawBitmap(mBitmap, mSrcRect, mDstRect, null);
            }
            if (mPositionY > mScreenHeight) {
            mPositionY -= mImageHeight;
        }
    }

*/
    }

