package dadm.scaffold.engine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import dadm.scaffold.space.SpaceShipPlayer;

public class ScoreCounter extends GameObject {

    private final float textWidth;
    private final float textHeight;

    double width;

    private Paint paint;
    private int HP;
    private float score;
    private int enemiesKilled;

    private String ScoreText = "";
    private String HPText = "";
    private SpaceShipPlayer player;
    private String BombsText = "";
    private String EnemiesText = "";

    //Inicializamos valores
    public ScoreCounter(GameEngine gameEngine) {
        paint = new Paint();
        paint.setTextAlign(Paint.Align.LEFT);
        textHeight = (float) (60 * gameEngine.pixelFactor);
        textWidth = (float) (60 * gameEngine.pixelFactor);
        paint.setTextSize(textHeight);
        player = gameEngine.getPlayer();
        width = gameEngine.width;

    }

    @Override
    public void startGame() {

    }

    //Recogemos y pintamos vida y puntuacion del jugador
    @Override
    public void onUpdate(long elapsedMillis, GameEngine gameEngine) {
        HP = player.getHP();
        score = player.getScore();
        enemiesKilled = gameEngine.getEnemiesKilled();
        ScoreText = "Score: " + (int) score;
        HPText = "Lives: " + (int) HP;
        EnemiesText = "Kills: " + (int) enemiesKilled;
        BombsText = "Bombs: " + (int) player.numberOfBombs;
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        //canvas.drawRect(0, 0, textWidth, textWidth, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText(HPText, textWidth / 2, textHeight * 1f, paint);
        canvas.drawText(BombsText, textWidth / 2, textHeight * 2f, paint);
        canvas.drawText(EnemiesText, textWidth / 2, textHeight * 3f, paint);
        canvas.drawText(ScoreText, (float) width / 2 - textWidth / 2, textHeight * 1f, paint);
    }

    @Override
    public void onCollision(GameEngine gameEngine, Sprite other) {
        //Do nothing
    }

}
