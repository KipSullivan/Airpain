package threadhandler;

import game.com.airpain.airpain.Game;

/**
 * Created by KipJay on 11/5/2016.
 */

public class ThreadHandler {

    Game game;

    Update u;
    Draw d;

    Thread updateThread;

    Thread drawThread;

    public volatile static double FPS = 0;

    public ThreadHandler(Game game){
        this.game = game;
    }

    public void startAllThreads(){
        d = new Draw();
        u = new Update();
        updateThread = new Thread(u);
        updateThread.start();

        drawThread = new Thread(d);
        drawThread.start();

    }

    public void pause(){
        if(game.started) {
            d.alive = false;
            u.alive = false;
            try {
                updateThread.interrupt();
                drawThread.interrupt();
                updateThread = null;
                drawThread = null;

            } catch(Exception e){

            }
        }
    }

    public void resume(){
        if(game.started && updateThread == null && drawThread == null) {

            d.alive = true;
            u.alive = true;

            if(updateThread != null && drawThread != null){
                updateThread = new Thread(u);
                drawThread = new Thread(d);

                updateThread.start();
                drawThread.start();
            }
        }
    }

    public void quit(){
        if(game.started) {
            d.alive = false;
            u.alive = false;
            updateThread.interrupt();
            drawThread.interrupt();
        }
    }


    private class Update implements Runnable {

        private boolean alive = false;

        public Update(){
            alive = true;
        }

        @Override
        public void run() {
            while(alive) {
                long lastTime = System.nanoTime();
                double nsPerTick = 1000000000D/120D;

                int ticks = 0;
                int frames = 0;

                long lastTimer = System.currentTimeMillis();
                double delta = 0;

                while(game.started && alive && Game.gameState == Game.RUNNING && !Thread.currentThread().isInterrupted()){
                    long now = System.nanoTime();
                    delta += (now - lastTime) / nsPerTick;
                    lastTime = now;
                    while(delta >= 1){
                        ticks++;
                        delta -= 1;
                        game.update();
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                    frames++;

                    if(System.currentTimeMillis() - lastTimer >= 1000){
                        frames = 0;
                        ticks = 0;
                        lastTimer+= 1000;
                    }
                }
            }
            }
        }


    private class Draw implements Runnable {

        private boolean alive = false;

        public Draw(){
            alive = true;
        }

        @Override
        public void run() {
            while (alive) {
                long lastTime = System.nanoTime();
                double nsPerTick = 1000000000D / 120D;

                int ticks = 0;
                int frames = 0;

                long lastTimer = System.currentTimeMillis();
                double delta = 0;

                while (game.started && alive && Game.gameState == Game.RUNNING && !Thread.currentThread().isInterrupted()) {
                    long now = System.nanoTime();
                    delta += (now - lastTime) / nsPerTick;
                    lastTime = now;
                    while (delta >= 1) {
                        ticks++;
                        delta -= 1;
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    frames ++;
                    game.paint();

                    if (System.currentTimeMillis() - lastTimer >= 1000) {
                        frames = 0;
                        ticks = 0;
                        lastTimer += 1000;
                    }
                }
            }
        }
    }

}
