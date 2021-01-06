package animations;

import android.graphics.Canvas;
import android.graphics.Color;

import game.com.airpain.airpain.Game;

/**
 * Created by gsmki on 12/30/2017.
 */

public class TextAnimation extends MovementSequence {

    volatile boolean showingText = false;

    volatile int secondsOnScreen = 3;

    volatile int color = Color.BLACK;

    volatile float width = .1f;

    volatile int textSize = Game.sizeY(30);

    volatile double x, y;

    volatile double startTime = System.currentTimeMillis();

    volatile boolean done = false;

    volatile String text = "";

    Game game;

    public TextAnimation(Game game, int secondsOnScreen, double x, double y) {
        super(game);
        this.x = x;
        this.y = y;
        this.game = game;
        this.secondsOnScreen = secondsOnScreen;
    }

    public TextAnimation(Game game, double x, double y) {
        super(game);
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public void onRun() {
         if(showingText) {
             if(System.currentTimeMillis() - startTime > (secondsOnScreen * 1000)) {
                stop();
             }
         }
    }

    public void draw(Canvas g) {
         if(showingText) {
             game.painter().setColor(color);
             game.painter().setStrokeWidth(width);
             game.painter().setTextSize(textSize);
             g.drawText(text, (int)x, (int)y, game.painter());
             game.defaultPaintSettings();
         }
    }

    /*
    Sets color of text when drawn
     */

    public boolean isDone() {
        return done;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setTextSize(int size) {
        this.textSize = size;
    }

    public void setStrokeWidth(float width) {
        this.width = width;
    }

    public void restart() {
        startTime = System.currentTimeMillis();
        showingText = true;
    }

    @Override
    public boolean currentlyRunning() {
        return showingText;
    }

    @Override
    public void start() {
        super.start();
        showingText = true;
        startTime = System.currentTimeMillis();
    }

    @Override
    public void stop() {
        super.stop();
        showingText = false;
    }
}
