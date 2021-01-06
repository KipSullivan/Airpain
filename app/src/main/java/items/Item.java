package items;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import animations.ImageAnimation;
import entities.Entity;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;

/**
 * Created by gsmki on 12/28/2017.
 */

public abstract class Item extends Entity {

    public volatile boolean using = false;

    public volatile boolean beenUsed = false;

    public volatile int use_seconds = 0;

    double startTime = System.currentTimeMillis();

    ImageAnimation background;

    Bitmap[] images;

    Game game;

    ItemID id;

    int gapX = Game.sizeX(6);
    int gapY = Game.sizeY(6);

    boolean pickedUp = false;

    int uses = 1;

    public Item(Game game, ItemID id, int seconds, int uses, double x, double y, double sizeX, double sizeY) {
        super(game, x, y, sizeX, sizeY);
        ////seconds is the number of seconds the powerup can be used for.
        this.uses = uses;
        background = new ImageAnimation(game, false, 0,0, (int) sizeX, (int) sizeY);

        images = new Bitmap[] {
                game.getResizedBitmap(game.getBitmap(R.drawable.itembackground1),
                        getSizeX() + (gapX), getSizeY() + (gapY)),
                game.getResizedBitmap(game.getBitmap(R.drawable.itembackground2),
                        getSizeX() + (gapX), getSizeY() + (gapY)),
                game.getResizedBitmap(game.getBitmap(R.drawable.itembackground3),
                        getSizeX() + (gapX), getSizeY() + (gapY)),
                game.getResizedBitmap(game.getBitmap(R.drawable.itembackground4),
                        getSizeX() + (gapX), getSizeY() + (gapY)),
                game.getResizedBitmap(game.getBitmap(R.drawable.itembackground5),
                        getSizeX() + (gapX), getSizeY() + (gapY)),
                game.getResizedBitmap(game.getBitmap(R.drawable.itembackground6),
                        getSizeX() + (gapX), getSizeY() + (gapY))
        };

        background.setImages(images, 8);
        background.optionalStart();

        this.id = id;

        this.game = game;

        this.use_seconds = seconds;
    }

    public String toString() {
        return id.toString(id);
    }

    public ItemID getId() {
        return id;
    }

    public void draw(Canvas g) {
        if(!pickedUp) {
            background.draw(g);
        }

        super.draw(g);
    }

    public void updateMovement() {

        if(!pickedUp) {
            background.setX((int) getX() - (gapX / 2));
            background.setY((int) getY() - (gapY / 2));
            background.onRun();
        }

        if(getX() > Game.getGameViewWidth()){
            destroy();
        }

        if(using) {
            background.optionalStop();
            onUse();

            if(use_seconds != -1) {
                if (System.currentTimeMillis() - startTime > (use_seconds * 1000)) {
                    using = false;
                    beenUsed = true;
                    onEndOfUse();
                    destroy();
                }
            }
        }
    }

    public void incrUses(int incr) {
        uses += incr;
    }

    public int getNumberOfUses() {
        return uses;
    }

    public void kill() {
        beenUsed = true;
        using = false;
        onEndOfUse();
        destroy();
    }

    public abstract void onUse();

    public void onEndOfUse() {

    }

    public void use() {
        if(uses > 0) {
            using = true;
            uses -= 1;
            startTime = System.currentTimeMillis();
        }
    }
}
