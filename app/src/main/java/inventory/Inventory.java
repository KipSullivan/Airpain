package inventory;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import entities.Entity;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import items.Item;

/**
 * Created by gsmki on 12/28/2017.
 */

public class Inventory {

    volatile Game game;

    volatile int num_slots = 3;

    volatile int selector = 0;

    volatile Slot[] slots;

    volatile Entity host;

    public Inventory(Game game, Entity host, int num_slots) {
        slots = new Slot[num_slots];
        this.num_slots = slots.length;
        this.game = game;

        this.host = host;

        for(int i = 0; i < slots.length; i++) {
            slots[i] = new Slot(game, host, i);
        }
        slots[0].setSelected(true);
    }

    public synchronized void draw(Canvas g) {

        game.painter().setTextSize(Game.sizeY(25));
        game.painter().setColor(Color.argb(255, 253, 159, 2));

        g.drawText(game.getResources().getString(R.string.howtouseitem),
                slots[slots.length - 1].position * Slot.SLOT_SIZE, Game.getWindowHeight(),
                game.painter());

        game.defaultPaintSettings();

        for(int i = 0; i < slots.length; i++) {
            slots[i].draw(g);
        }

    }

    public synchronized void selectItem(Rect touchHitBox) {
        for(int i = 0; i < slots.length; i++) {
            if(slots[i].getHitBox().intersect(touchHitBox)) {
                slots[selector].setSelected(false);
                selector = i;///selecter number update
                slots[selector].setSelected(true);
            }
        }
    }

    public synchronized boolean addItemToInventory(Item item) {

        if(item == null || item.toString() == null) {
            Log.d("Running", "Caught a runaway null item");
            return false;
        }

        for(int i = 0; i < slots.length; i++) {
            if(slots[i].hasItem() == false) {
                slots[i].addItem(item);
                return true;
            }
        }

        return false;
    }

    public synchronized String getSelectedItemName() {
        if(slots[selector].getItem() != null && slots[selector].getItem().toString() != null) {
            return slots[selector].getItem().toString();
        }
        return "Empty";
    }

    public void useSelectedItem() {
        slots[selector].useItem();
    }



}
