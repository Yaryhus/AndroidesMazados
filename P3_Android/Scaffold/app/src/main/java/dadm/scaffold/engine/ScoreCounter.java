package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import dadm.scaffold.space.SpaceShipPlayer;

public class ScoreCounter extends GameObject {

    private final float textWidth;
    private final float textHeight;

    private Paint paint;
    private int HP;
    private float score;

    private String ScoreText = "";
    private String HPText = "";
    private SpaceShipPlayer player;
    //Inicializamos valores
    public ScoreCounter(GameEngine gameEngine) {
        paint = new Paint();
        paint.setTextAlign(Paint.Align.LEFT);
        textHeight = (float) (40* gameEngine.pixelFactor);
        textWidth = (float) (60 * gameEngine.pixelFactor);
        paint.setTextSize(textHeight / 2);
        player = gameEngine.getPlayer();
    }

    @Override
    public void startGame() {

    }

    //Recogemos y pintamos vida y puntuacion del jugador
    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        HP=player.getHP();
        score=player.getScore();
        ScoreText="Score: "+score;
        HPText = "Lives: "+ HP;
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        //canvas.drawRect(0, 0, textWidth, textWidth, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText(HPText, textWidth/2, textHeight*1f, paint);
        canvas.drawText(ScoreText, textWidth/2, textHeight*2f, paint);
    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite other) {
        //Do nothing
    }

}
