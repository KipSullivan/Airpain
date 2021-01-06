package gamestages;

import airplanes.Plane;
import game.com.airpain.airpain.Game;
import items.ItemID;

/**
 * Created by KipJay on 11/24/2017.
 */

public class SendPlane {


    Plane.PlaneType type;

    ItemID itemType;

    Game view;

    int randomSend;

    boolean random = false;

    boolean modifier;

    public SendPlane(Game view, Plane.PlaneType type, int randomSend, boolean modifier) {
        this.view = view;
        this.type = type;
        itemType = ItemID.NOTHING;
        this.randomSend = randomSend;
        this.modifier = modifier;
    }

    public SendPlane(Game view, Plane.PlaneType type, int randomSend, boolean modifier, boolean random) {
        this.view = view;
        this.type = type;
        this.randomSend = randomSend;
        itemType = ItemID.NOTHING;
        this.modifier = modifier;
        this.random = true;
    }

    public SendPlane(Game view, ItemID type, int randomSend, boolean modifier) {
        this.view = view;
        this.itemType = type;
        this.type = Plane.PlaneType.UNKNOWN;
        this.randomSend = randomSend;
        this.modifier = modifier;
    }


    public void send() {
        if(!random) {
            if(type == Plane.PlaneType.UNKNOWN && itemType != ItemID.NOTHING) {
                view.sendPowerup(itemType, randomSend);
            }
            view.sendPlane(type, randomSend, modifier);
        }
        else {
            view.determinePlanes();
        }

    }


}
