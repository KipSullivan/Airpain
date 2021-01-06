package utilities;

import android.util.Log;

/**
 * Created by gsmki on 1/5/2018.
 */

public class CountdownTimer {

    volatile int startSeconds = 3;

    volatile int secondsLeft = 3;

    volatile boolean started = false;

    double startTime = System.currentTimeMillis();

    volatile boolean done = false;

    public CountdownTimer(int seconds) {
        this.startSeconds = seconds;
    }

    public void start() {
        done = false;
        startTime = System.currentTimeMillis();
        started = true;
    }


    public void stop() {
        done = true;
        started = false;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isDone() {
        return done;
    }

    public synchronized int secondsLeft() {
        return secondsLeft;
    }

    public synchronized void update() {

        if(done) {
            return;
        }

        secondsLeft = (int) (((startTime - System.currentTimeMillis()) / 1000) + startSeconds + 1);

        if(System.currentTimeMillis() - startTime > (startSeconds * 1000)) {

            stop();
        }
    }

}
