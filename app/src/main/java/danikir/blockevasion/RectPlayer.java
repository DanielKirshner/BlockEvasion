package danikir.blockevasion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import java.util.Random;

public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;

    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;

    private Random r = new Random();

    private AnimationManager animManager;


    public RectPlayer(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        int rand_alien_gen = r.nextInt(5) + 1; // 1 - blue, 2 - beige, 3 - green, 4 - pink, 5 - yellow.
        Bitmap idleImg, walk1, walk2;
        switch (rand_alien_gen) {
            case 1: // blue
            idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue);
            walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk1);
            walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk2);
                break;
            case 2: // beige
                idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienbeige);
                walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienbeige_walk1);
                walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienbeige_walk2);
                break;
            case 3: // green
                idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.aliengreen);
                walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.aliengreen_walk1);
                walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.aliengreen_walk2);
                break;
            case 4: // pink
                idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienpink);
                walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienpink_walk1);
                walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienpink_walk2);
                break;
            default: // yellow
                idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienyellow);
                walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienyellow_walk1);
                walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienyellow_walk2);
                break;
        }

        idle = new Animation(new Bitmap[]{idleImg}, 2);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false);

        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        animManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});
    }

    public Rect getRectangle() {
        return rectangle;
    }

    @Override
    public void draw(Canvas canvas) {
        //Paint paint = new Paint();
        //paint.setColor(color);
        //canvas.drawRect(rectangle, paint);
        animManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        animManager.update();
    }

    public void update(Point point) {
        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2, point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);

        int state =  0;
        if(rectangle.left - oldLeft > 5) {
            state = 1;
        }
        else if(rectangle.left - oldLeft <  -5) {
            state = 2;
        }

        animManager.playAnim(state);
        animManager.update();

    }
}
