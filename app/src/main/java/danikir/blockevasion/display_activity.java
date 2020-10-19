package danikir.blockevasion;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class display_activity extends Activity {

    MediaPlayer gameplay_full_music;
    int length = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Making the app on fullScreen:
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        gameplay_full_music = MediaPlayer.create(display_activity.this, R.raw.gameplay_music_full);

        /*MainActivity.prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        Constants.HIGH_SCORE = MainActivity.prefs.getInt("score", 0);*/

        gameplay_full_music.setLooping(true);
        gameplay_full_music.start();

        //Starting the game Panel:
        setContentView(new GamePanel(this));
    }

    protected void onDestroy() {
        super.onDestroy();
        gameplay_full_music.setLooping(false);
        gameplay_full_music.stop();
        finish();
    }

    public void onPause() {
        super.onPause();
        if (gameplay_full_music.isPlaying() || gameplay_full_music.isLooping()) {
            gameplay_full_music.setLooping(false);
            gameplay_full_music.pause();
        }
        length = gameplay_full_music.getCurrentPosition();
    }

    public void onStop() {
        super.onStop();
        if (gameplay_full_music.isPlaying() || gameplay_full_music.isLooping()) {
            gameplay_full_music.setLooping(true);
            gameplay_full_music.pause();
        }
        length = gameplay_full_music.getCurrentPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        gameplay_full_music.seekTo(length);
        gameplay_full_music.setLooping(true);
        gameplay_full_music.start();
    }
}