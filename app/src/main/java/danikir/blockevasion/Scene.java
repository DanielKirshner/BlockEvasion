package danikir.blockevasion;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Scene {
    public void update();
    public void draw(Canvas canvas) throws InterruptedException;
    public void terminate();
    public void recieveTouch(MotionEvent event);
}
