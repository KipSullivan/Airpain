package inventory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import entities.Entity;
import game.com.airpain.airpain.Game;
import items.Item;
import utilities.HitBox;

/**
 * Created by gsmki on 12/28/2017.
 */

public class Slot {

    volatile Entity host;

    volatile Item item;

    volatile Bitmap itemImage;

    boolean isSelected = false;

    Rect hitBox = HitBox.nullHitBox();

    Game game;

    public static final int SLOT_SIZE = Game.sizeY(100);

    public static final int STROKE_WIDTH = Game.sizeX(10);

    int position;

    public Slot(Game game, Entity host, int position) {
        this.host = host;

        this.position = position * SLOT_SIZE + (STROKE_WIDTH * (position));

        this.game = game;
        hitBox = HitBox.createHitBox(position, Game.getWindowHeight() - SLOT_SIZE, SLOT_SIZE, SLOT_SIZE);

    }

    public void addItem(Item item) {
        if(this.item == null && item != null) {
            this.item = item;
            itemImage = host.game.getResizedBitmap(item.getBitmap(), SLOT_SIZE, SLOT_SIZE);
        }
    }

    public boolean hasItem() {
        if(item == null) {
            return false;
        }
        else {
            return true;
        }
    }

    public synchronized void draw(Canvas g) {
        try {
            if (item != null && itemImage != null) {
                g.drawBitmap(itemImage, position, Game.getWindowHeight() - SLOT_SIZE, host.game.painter());

                host.game.painter().setTextSize(Game.sizeY(20));
                host.game.painter().setStrokeWidth(Game.sizeX(3));
                host.game.painter().setColor(Color.GRAY);

                g.drawText("" + item.getNumberOfUses(), position + STROKE_WIDTH, (int) (Game.getWindowHeight()
                        - STROKE_WIDTH),
                        host.game.painter());

                host.game.defaultPaintSettings();
            }

            host.game.painter().setColor(Color.GRAY);
            host.game.painter().setStyle(Paint.Style.FILL_AND_STROKE);
            host.game.painter().setStrokeWidth(STROKE_WIDTH);

            if (isSelected) {
                host.game.painter().setColor(Color.BLUE);
                host.game.painter().setStyle(Paint.Style.FILL_AND_STROKE);
                host.game.painter().setStrokeWidth(STROKE_WIDTH);
            }

            hitBox = HitBox.createHitBox(position,
                    Game.getWindowHeight() - SLOT_SIZE, SLOT_SIZE, SLOT_SIZE);

            HitBox.showHitBox(hitBox, g);
            host.game.painter().setColor(Color.BLACK);

            host.game.painter().setStyle(Paint.Style.FILL);

            if (item != null) {
                if (item.using) {
                    itemImage = null;
                    item = null;
                }
            }

            host.game.defaultPaintSettings();
        } catch(Exception e) {
            Log.d("Running", e.getMessage());
            e.printStackTrace();
        }
    }

    public int getPosition() {
        return position;
    }

    public void useItem() {
        if(item != null) {
            item.use();

            if(item != null && item.getNumberOfUses() <= 0) {
                item = null;
                return;
            }
            return;
        }
    }

    public Rect getHitBox() {
        return hitBox;
    }

    public void setSelected(boolean toggle) {
        this.isSelected = toggle;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public Item getItem() {
        return item;
    }

    public Entity getHost() {
        return host;
    }
}
