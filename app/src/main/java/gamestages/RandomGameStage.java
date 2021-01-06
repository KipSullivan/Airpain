package gamestages;

import android.graphics.Bitmap;

import java.util.ArrayList;

import airplanes.Plane;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import items.ItemID;

/**
 * Created by KipJay on 11/25/2017.
 */

public class RandomGameStage extends GameStage {

    public RandomGameStage(Game view) {
        super(view, StageType.RANDOM, 40);

        setup();
    }

    ArrayList<SendStagePlane> levels = new ArrayList<>();

    public void setup() {

        for(int i = 0; i < 40; i ++) {
            SendStagePlane stage = new SendStagePlane(25, 50);
            SendPlane plane = new SendPlane(game, Plane.PlaneType.TORPEDO, 0, false, true);
            stage.add(plane);
            levels.add(stage);
        }

    }



    @Override
    public void alwaysRunning() {
        game.sendPlane(Plane.PlaneType.HEALTH, 5550, false);
        game.sendPowerup(ItemID.INVISIBLECLOAK, 8000);
        game.sendPowerup(ItemID.CLOUD, 10000);
        game.sendPowerup(ItemID.MINIPLANE, 9000);
        game.sendPowerup(ItemID.TMP, 7000);

        if((getRoundNumber() % 4) == 0){
            game.sendPlane(Plane.PlaneType.HEALTH, 1500, false);
        }
    }

    @Override
    public ArrayList<SendStagePlane> getStagePlanes() {
        return levels;
    }

    public Bitmap[] getBackgroundImages() {
        Bitmap[] images = new Bitmap[3];
        images[0] = game.getResizedBitmap(game.getBitmap(R.drawable.background), Game.getGameViewWidth(), Game.getGameViewHeight());
        images[1] = game.getResizedBitmap(game.getBitmap(R.drawable.background), Game.getGameViewWidth(), Game.getGameViewHeight());
        images[2] = game.getResizedBitmap(game.getBitmap(R.drawable.ground), Game.getGameViewWidth(), Game.getGameViewHeight());
        return images;
    }
}
