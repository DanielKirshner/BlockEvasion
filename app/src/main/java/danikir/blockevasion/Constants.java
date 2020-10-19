package danikir.blockevasion;

import android.content.Context;

import java.util.LinkedList;

public class Constants {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static long INIT_TIME;
    public static Context CURRENT_CONTEXT;

    public static LinkedList<Integer> scores = new LinkedList<Integer>();
    public static int GAMES_COUNTER;
    public static int HIGH_SCORE;


    public static void updateHighScore() {

        if (scores.isEmpty())
            return;
        int max = 0;
        for (int score : scores) {
            if (score > max) {
                max = score;
            }
        }
        if(max > HIGH_SCORE)
            HIGH_SCORE = max;

    }

    public static void updateGamesCounter() {
        GAMES_COUNTER = GAMES_COUNTER + 1;
    }
}
