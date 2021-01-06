package gamestages;

import android.graphics.Bitmap;

import java.util.ArrayList;

import airplanes.Plane;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import items.ItemID;

/**
 * Created by KipJay on 11/24/2017.
 */

public class RegularGameStage extends GameStage {



    public RegularGameStage(Game view) {
        super(view, StageType.REGULAR, 40);


        setup();
    }

    ArrayList<SendStagePlane> levels = new ArrayList<>();

    public void setup() {

        ArrayList<SendPlane> planes1 = new ArrayList<>();
        planes1.add(new SendPlane(game, Plane.PlaneType.SUICIDE, 200, false));
        planes1.add(new SendPlane(game, Plane.PlaneType.TORPEDO, 600, true));
        levels.add(new SendStagePlane(planes1, 20, 40));


        SendStagePlane two = new SendStagePlane(30, 50);
        two.add(new SendPlane(game, Plane.PlaneType.TORPEDO, 500, true));
        two.add(new SendPlane(game, Plane.PlaneType.PELLETHELI, 700, false));
        levels.add(two);

        SendStagePlane three = new SendStagePlane(30, 50);
        three.add(new SendPlane(game, Plane.PlaneType.TORPEDO, 400, true));
        three.add(new SendPlane(game, Plane.PlaneType.PELLET, 200, false));
        levels.add(three);

        SendStagePlane four = new SendStagePlane(30, 50);
        four.add(new SendPlane(game, Plane.PlaneType.TORPEDO, 500, true));
        four.add(new SendPlane(game, Plane.PlaneType.PELLETHELI, 700, false));
        levels.add(four);

        SendStagePlane five = new SendStagePlane(30, 50);
        five.add(new SendPlane(game, Plane.PlaneType.HOMINGHELI, 500, false));
        five.add(new SendPlane(game, Plane.PlaneType.PELLETHELI, 700, false));
        levels.add(five);

        SendStagePlane six = new SendStagePlane(30, 50);
        six.add(new SendPlane(game, Plane.PlaneType.RAPID, 1500, false));
        six.add(new SendPlane(game, Plane.PlaneType.SPEEDBUFF, 300, false));
        six.add(new SendPlane(game, Plane.PlaneType.SUICIDE, 200, false));
        six.add(new SendPlane(game, Plane.PlaneType.INVISIBLE, 800, false));
        levels.add(six);

        SendStagePlane seven = new SendStagePlane(30, 50);
        seven.add(new SendPlane(game, Plane.PlaneType.TORPEDO, 500, false));
        seven.add(new SendPlane(game, Plane.PlaneType.PELLETHELI, 700, false));
        levels.add(seven);

        SendStagePlane eight = new SendStagePlane(30, 50);
        eight.add(new SendPlane(game, Plane.PlaneType.TORPEDO, 500, false));
        eight.add(new SendPlane(game, Plane.PlaneType.PELLETHELI, 700, false));
        levels.add(eight);
    }



    @Override
    public void alwaysRunning() {
        game.sendPlane(Plane.PlaneType.HEALTH, 4550, false);
        game.sendPowerup(ItemID.INVISIBLECLOAK, 20200);
        game.sendPowerup(ItemID.CLOUD, 2500);
        game.sendPowerup(ItemID.MINIPLANE, 1800);
        game.sendPowerup(ItemID.TMP, 3300);

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
