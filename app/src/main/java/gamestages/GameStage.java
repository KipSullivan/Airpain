package gamestages;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;

import game.com.airpain.airpain.Game;

/**
 * Created by KipJay on 11/24/2017.
 */

public abstract class GameStage {


    public enum StageType {

        REGULAR(0), RANDOM(1), SPACE(2);

        int type;

        StageType(int type) {
            this.type = type;
        }

        int getType() {
            return type;
        }
    }


    public Game game;

    StageType type;

    int num_rounds;

    int round_num = 0;

    boolean running = true;

    public double score = 0;

    public GameStage(Game view, StageType type, int num_rounds) {
        this.game = view;
        this.num_rounds = num_rounds;
        this.type = type;
        running = true;
    }

    public StageType getType() {
        return type;
    }

    public static GameStage getModeByType(Game view, StageType type) {
        switch(type) {
            case REGULAR:
                return new RegularGameStage(view);
            case RANDOM:
                return new RandomGameStage(view);
        }

        return null;
    }



    public void update() {
        if(running) {
            alwaysRunning();
            score += .1;

            if (round_num <= num_rounds) {
                SendStagePlane stage = getStagePlanes().get(round_num);

                if (stage.hasStarted == false) {
                    stage.start();
                }
                if (!stage.hasEnded) {
                    stage.sendPlanes();
                }

                if (stage.finished == false && stage.hasEnded){
                    stage.finished = true;
                    game.getPlayer().heal(getStagePlanes().get(round_num).healAmount);
                    round_num = round_num + 1;
                    return;
                }
            }
            else {
                Log.d("Running", "YOU WIN");
                game.quit();
            }

        }

    }


    public void stop() {
        running = false;
        getStagePlanes().removeAll(getStagePlanes());
    }

    public String getRoundNumberString() {
        return "" + (round_num + 1);
    }

    public int getRoundNumber() {
        return round_num + 1;
    }

    public int getNumRounds() {
        return num_rounds;
    }

    public abstract void setup();

    public abstract void alwaysRunning();

    public abstract ArrayList<SendStagePlane> getStagePlanes();

    public abstract Bitmap[] getBackgroundImages();


}
