package buttons;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import utilities.HitBox;

/**
 * Created by KipJay on 12/19/2016.
 */

public class LButtonManager {

    ArrayList<LButton> buttons = new ArrayList<>();

    LButtonListener listener;

    Rect rect;

    Paint drawer;

    int x, y;

    boolean holding = false;

    public LButtonManager() {
        rect = HitBox.nullHitBox();
    }

    public void addListenerToFrame(Paint drawer, LButtonListener listener) {
        this.drawer = drawer;
        this.listener = listener;
    }

    public void addLButton(LButton button) {
        buttons.add(button);
    }

    public void drawLButtonImages(Canvas g) {

        if(buttons.size() < 1) {
            return;
        }

        for(int t = 0; t < buttons.size(); t++) {
            LButton b = buttons.get(t);
            b.draw(g, drawer);
        }
    }

    public boolean onTouch(View v, MotionEvent event) {

        this.x = (int) event.getX();
        this.y = (int) event.getY();

        switch(event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                rect = HitBox.createHitBox(x, y, 10, 10);

                for(int t = 0; t < buttons.size(); t++) {
                    LButton b = buttons.get(t);
                    if (b.getHitBox().intersect(rect)) {
                        b.setImage(b.getPressedImage());
                        listener.buttonClicked(b);

                        holding = true;
                    }
                }

                break;

            case MotionEvent.ACTION_UP:

                for(int t = 0; t < buttons.size(); t++) {
                    LButton b = buttons.get(t);
                    if(b.getHitBox().intersect(rect)) {
                        b.setImage(b.getNormalImage());
                        listener.buttonReleased(b);
                    }
                }

                holding = false;

                break;
        }

        return holding;
    }
}
