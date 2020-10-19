package danikir.blockevasion;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends Activity {
    Random r = new Random();
    MediaPlayer bp1,bp2;

    public static SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        bp1 = MediaPlayer.create(MainActivity.this, R.raw.button_pressed_1);
        bp2 = MediaPlayer.create(MainActivity.this, R.raw.button_pressed_2);
        prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        Constants.HIGH_SCORE = prefs.getInt("score", 0);
        Constants.GAMES_COUNTER = prefs.getInt("counter", 0);
    }
    public void startGame(View view) {
        Toast.makeText(getApplicationContext(), "Avoid the Blocks!", Toast.LENGTH_SHORT).show();
        if(r.nextBoolean()) bp1.start();
        else bp2.start();
        Intent t = new Intent(this, display_activity.class);
        startActivity(t);
    }
    public void exit(View view) {
        if(r.nextBoolean()) bp1.start();
        else bp2.start();
        finish();
        System.exit(0);
    }

    public void runStats(View view) {
        if(r.nextBoolean()) bp1.start();
        else bp2.start();
        Intent t = new Intent(this, stats_activity.class);
        startActivity(t);
    }
}


