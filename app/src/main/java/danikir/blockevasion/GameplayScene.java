package danikir.blockevasion;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.Random;

public class GameplayScene implements Scene {

    private Rect r = new Rect();
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    private boolean movingPlayer = false;
    private boolean gameOver = false;

    private long gameOverTime;
    private Random random = new Random();

    public GameplayScene() {
        player = new RectPlayer(new Rect(250, 250, 500, 500), Color.GREEN);

        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);

        switch(random.nextInt(5) + 1) {
            case 1:
                obstacleManager = new ObstacleManager(500, 925 , 195, Color.RED);
                break;
            case 2:
                obstacleManager = new ObstacleManager(500, 925 , 195, Color.CYAN);
                break;
            case 3:
                obstacleManager = new ObstacleManager(500, 925 , 195, Color.MAGENTA);
                break;
            case 4:
                obstacleManager = new ObstacleManager(500, 925 , 195, Color.YELLOW);
                break;
            case 5:
                obstacleManager = new ObstacleManager(500, 925 , 195, Color.GRAY);
                break;
        }
    }

    public void reset() {
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);

        switch(random.nextInt(5) + 1) {
            case 1:
                obstacleManager = new ObstacleManager(500, 925 , 195, Color.RED);
                break;
            case 2:
                obstacleManager = new ObstacleManager(500, 925 , 195, Color.CYAN);
                break;
            case 3:
                obstacleManager = new ObstacleManager(500, 925 , 195, Color.MAGENTA);
                break;
            case 4:
                obstacleManager = new ObstacleManager(500, 925 , 195, Color.YELLOW);
                break;
            case 5:
                obstacleManager = new ObstacleManager(500, 925 , 195, Color.GRAY);
                break;
        }

        movingPlayer = false;
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY())) {
                    movingPlayer = true;
                }
                if (gameOver && System.currentTimeMillis() - gameOverTime >= 100) {
                    Constants.scores.add(obstacleManager.getScore());
                    //Constants.GAMES_COUNTER += 1;
                    Constants.updateGamesCounter();
                    Constants.updateHighScore();
                    reset();
                    gameOver = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!gameOver && movingPlayer) {
                    playerPoint.set((int) event.getX(), (int) event.getY());
                }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }

    @Override
    public void terminate() {

        SceneManager.ACTIVE_SCENE = 0;
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        player.draw(canvas);
        obstacleManager.draw(canvas);

        if (gameOver) {
            //Constants.scores.add(obstacleManager.getScore());
            //Constants.GAMES_COUNTER += 1;
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.WHITE);
            drawCenterText(canvas, paint, "Touch to restart & save score");
        }

    }
    @Override
    public void update() {
        if (!gameOver) {
            player.update(playerPoint);
            obstacleManager.update();
            if (obstacleManager.playerCollide(player)) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }

    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }

}
