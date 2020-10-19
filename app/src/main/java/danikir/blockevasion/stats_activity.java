package danikir.blockevasion;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Random;

public class stats_activity extends Activity {

    TextView gamesPlayed, highScore;
    MediaPlayer bp1,bp2;
    Random r = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_stats_activity);

        bp1 = MediaPlayer.create(stats_activity.this, R.raw.button_pressed_1);
        bp2 = MediaPlayer.create(stats_activity.this, R.raw.button_pressed_2);

        gamesPlayed = (TextView) findViewById(R.id.games_played);
        highScore = (TextView) findViewById(R.id.high_score);

        /*MainActivity.prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        Constants.HIGH_SCORE = MainActivity.prefs.getInt("score", 0);
        Constants.GAMES_COUNTER = MainActivity.prefs.getInt("counter", 0);*/

        update_highScore();

        highScore.setText(Constants.HIGH_SCORE + "");
        gamesPlayed.setText(Constants.GAMES_COUNTER + "");
    }

    public void update_highScore() {
        if (!Constants.scores.isEmpty()) {
            int max = 0;
            for (int score : Constants.scores) {
                if (score > max) {
                    max = score;
                }
            }

            if (max > Constants.HIGH_SCORE)
                Constants.HIGH_SCORE = max;
        }
        MainActivity.prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = MainActivity.prefs.edit();
        editor.putInt("score", Constants.HIGH_SCORE);
        editor.putInt("counter", Constants.GAMES_COUNTER);
        editor.commit();

    }

    public void back(View view) {
        if(r.nextBoolean()) bp1.start();
        else bp2.start();
        finish();
    }
}
