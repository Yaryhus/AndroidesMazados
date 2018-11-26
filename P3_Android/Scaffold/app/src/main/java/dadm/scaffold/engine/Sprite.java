package dadm.scaffold.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public abstract class Sprite extends GameObject {

    protected double positionX;
    protected double positionY;
    protected double rotation;

    protected Rect col;
    protected double pixelFactor;

    public String typeS;

    private final Bitmap bitmap;
    protected final int imageHeight;
    protected final int imageWidth;

    private final Matrix matrix = new Matrix();

    protected Sprite (GameEngine gameEngine, int drawableRes) {
        Resources r = gameEngine.getContext().getResources();
        Drawable spriteDrawable = r.getDrawable(drawableRes);

        this.pixelFactor = gameEngine.pixelFactor;

        //Tipo sprite
        typeGO = "sprite";

        this.imageHeight = (int) (spriteDrawable.getIntrinsicHeight() * this.pixelFactor);
        this.imageWidth = (int) (spriteDrawable.getIntrinsicWidth() * this.pixelFactor);
        //this.col = new Rect((int)-imageWidth/2,(int)imageHeight/2,(int) imageWidth/2,(int)-imageHeight/2);
        this.bitmap = ((BitmapDrawable) spriteDrawable).getBitmap();

    }

    @Override
    public void onDraw(Canvas canvas) {
        if (positionX > canvas.getWidth()
                || positionY > canvas.getHeight()
                || positionX < - imageWidth
                || positionY < - imageHeight) {
            return;
        }

        this.col=new Rect((int)positionX,(int)positionY,(int)positionX+imageWidth,(int)positionY+imageHeight);

        /*
        //Pintamos
        if(col != null) {
            Paint p = new Paint();
            p.setColor(Color.GREEN);
            canvas.drawRect(col, p);
        }
        */

        matrix.reset();
        matrix.postScale((float) pixelFactor, (float) pixelFactor);
        matrix.postTranslate((float) positionX, (float) positionY);
        matrix.postRotate((float) rotation, (float) (positionX + imageWidth/2), (float) (positionY + imageHeight/2));
        canvas.drawBitmap(bitmap,matrix,null);

    }
}
