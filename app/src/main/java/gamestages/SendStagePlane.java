package gamestages;


import android.os.CountDownTimer;

import java.util.ArrayList;

/**
 * Created by KipJay on 11/24/2017.
 */

public class SendStagePlane {

    ArrayList<SendPlane> planes = new ArrayList<>();

    CountDownTimer timer;

    int healAmount;

    int seconds;

    public boolean hasStarted = false;

    public boolean hasEnded = false;

    public boolean finished = false;

    double startTime = System.currentTimeMillis();

    public SendStagePlane(ArrayList<SendPlane> planes, int seconds, int healAmount) {
        this.planes = planes;
        this.seconds = seconds;
        this.healAmount = healAmount;
    }

    public SendStagePlane(int seconds, int healAmount) {
        this.seconds = seconds;
        this.healAmount = healAmount;
    }

    public void add(SendPlane plane) {
        planes.add(plane);
    }

    public void start() {
        hasStarted = true;

        startTime = System.currentTimeMillis();
    }


    public int timeLeft() {
        return (int) (seconds + ((startTime - System.currentTimeMillis()) / 1000));
    }

    public void sendPlanes() {

        if(System.currentTimeMillis() - startTime > (seconds * 1000)) {
            hasEnded = true;
        }

        if(!hasEnded) {
            for (int i = 0; i < planes.size(); i++) {
                planes.get(i).send();
            }
        }
    }

}
