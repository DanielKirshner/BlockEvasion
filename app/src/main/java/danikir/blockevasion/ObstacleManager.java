package danikir.blockevasion;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

public class ObstacleManager {
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private long startTime;
    private long initTime;
    private int score = 0;
    private Random r = new Random();

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight,int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        startTime = initTime = System.currentTimeMillis();

        startTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    public int getScore() {
        return this.score;
    }

    public boolean playerCollide(RectPlayer player) {
        for(Obstacle ob : obstacles) {
            if(ob.playerCollide(player))
                return true;
        }
        return false;

    }

    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        while (currY < 0) {
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight + obstacleGap;
        }
    }

    private int GetRandomColor() {
        switch (r.nextInt(17) + 1) {
            case 1:
                return Color.RED;
            case 2:
                return Color.CYAN;
            case 3:
                return Color.MAGENTA;
            case 4:
                return Color.YELLOW;
            case 5:
                return Color.argb(255,221,160,221);
            case 6:
                return  Color.argb(255,75,0,130);
            case 7:
                return Color.argb(255,255,127,80);
            case 8:
                return Color.argb(255,255,69,0);
            case 9:
                return Color.argb(255,178,34,34);
            case 10:
                return Color.argb(255,173,255,47);
            case 11:
                return Color.argb(255,60,179,113);
            case 12:
                return Color.argb(255,102,205,170);
            case 13:
                return Color.argb(255,95,158,160);
            case 14:
                return Color.argb(255,123,104,238);
            case 15:
                return Color.argb(255,245,245,220);
            case 16:
                return Color.argb(255,188,143,143);
            default:
                return Color.GRAY;
        }
    }
    public void update() {
        if(startTime < Constants.INIT_TIME) {
            startTime = Constants.INIT_TIME;
        }
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float)(Math.sqrt(1 + (startTime - initTime) / 400.0)) * Constants.SCREEN_HEIGHT / (10000.0f);
        for(Obstacle ob : obstacles) {
            ob.incrementY(speed * elapsedTime);
        }
        if(obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int)(Math.random() * (Constants.SCREEN_WIDTH - playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, GetRandomColor(), xStart, obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap ,playerGap));
            obstacles.remove(obstacles.size() - 1);
            score++;
        }
    }
    public void draw(Canvas canvas) {
        for(Obstacle ob : obstacles)
            ob.draw(canvas);
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
        canvas.drawText("Score: " + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }
}
