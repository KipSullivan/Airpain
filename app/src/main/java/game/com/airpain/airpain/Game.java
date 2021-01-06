package game.com.airpain.airpain;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import airplanes.BlimpPlane;
import airplanes.Boss;
import airplanes.DoubleTorpedoPlane;
import airplanes.HealthPlane;
import airplanes.InvisiblePlane;
import airplanes.InvisibleRapidFirePlane;
import airplanes.PelletPlane;
import airplanes.Plane;
import airplanes.PlayerPlane;
import airplanes.RapidFirePlane;
import airplanes.RocketPlane;
import airplanes.SpeedBuffPlane;
import airplanes.SuicidePlane;
import airplanes.TorpedoPlane;
import animations.MovementSequence;
import enemybuffs.EnemyBuff;
import gamestages.GameStage;
import helicopters.Helicopter;
import helicopters.HomingHelicopter;
import helicopters.PelletHelicopter;
import items.Cloud;
import items.InvisibleCloak;
import items.PowerUp;
import items.ItemID;
import items.ShrinkMachine;
import items.TMP;
import threadhandler.ThreadHandler;
import utilities.CountdownTimer;
import utilities.HitBox;

/**
 * Created by KipJay on 11/1/2016.
 */

public class Game extends SurfaceView implements SurfaceHolder.Callback, MediaPlayer.OnCompletionListener {

    volatile ArrayList<Plane> planes = new ArrayList<>();

    volatile ArrayList<Helicopter> helicopters = new ArrayList<>();
    ///powerups

    volatile ArrayList<PowerUp> powerUps = new ArrayList<>();

    ///arrays of enemybuffs
    volatile ArrayList<EnemyBuff> buffs = new ArrayList<>();
    volatile ArrayList<MovementSequence> animations = new ArrayList<>();

    volatile int heightOfBackgrounds = 5;

    volatile MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.wind);


    int reset = 1;

    public volatile String gameMode = "REGULAR";

    public static volatile int RUNNING = 0;
    public static volatile int PAUSED = 1;
    public static volatile int BOOTUP = 2;

    public volatile boolean started = false;

    volatile public boolean showHitboxes = false;

    public static int gameState = RUNNING;


    public boolean showFPS = false;


    static volatile int width = 0, height = 0;
    volatile boolean alive = false;

    public static final double SPEED = .45;

    public static int time = 0;

    int useOnce = 0;


    volatile GameLaunch gameLaunch;

    volatile Paint paint;
    volatile Canvas g = null;

    volatile ThreadHandler thread;

    volatile SurfaceHolder holder;
    volatile Updater u;
    volatile Drawer d;

    volatile GameStage gameStage;
    volatile CountdownTimer timer;



    public volatile Background[] backgrounds;

    //LButtonManager manager;

    volatile PlayerPlane player;


    public Game(Context context) {
        super(context);

        mp.setOnCompletionListener(this);

        holder = getHolder();
        thread = new ThreadHandler(this);

        holder.addCallback(this);

        width = getWidth();
        height = getHeight();
    }

    protected void putExtra(GameLaunch game, String mode){
        this.gameLaunch = game;
        this.gameMode = mode;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        Log.e("Running", "Resized");
        if(getWidth() > 1 && getHeight() > 1){
            this.width = getWidth();
            this.height = getHeight();
            Log.e("Running", "Starting " + width);
            start();
        }
    }

    public void start(){
        setup();

        thread.startAllThreads();

        mp.start();
    }

    public void setup(){

        if(!started) {
            Plane.set(this);
            HitBox.set(this);

            paint = new Paint();


            u = new Updater(this);
            d = new Drawer(this);

            player = new PlayerPlane(getGameViewWidth() - sizeX(380), (getWindowHeight() / 2), this);

            GameStage.StageType type = GameStage.StageType.valueOf(gameMode);
            gameStage = GameStage.getModeByType(this, type);
            Log.d("Running", "" + gameStage);

            setupBackground();

            timer = new CountdownTimer(3);
            timer.start();

            started = true;
        }
    }

    public synchronized void paint(){

        if(time < 30) {
            time += 1;
        }
        else {
            reset = 0;
        }

        try {
            g = holder.lockCanvas();

            d.draw(g);

            holder.unlockCanvasAndPost(g);
        } catch(Exception e) {
            Log.d("Running", "" + e.getMessage());
        }
    }

    public synchronized void update(){

        if(timer.isDone() == false) {
            timer.update();
        }

        if(timer.isDone()) {
            u.updateMovement();

            if (gameStage != null) {
                gameStage.update();
            }
        }
    }

    public void resume(){
        thread.resume();
    }

    public void pause(){
        if(started) {
            thread.pause();
            paint();
        }
    }

    public void quit(){

        this.planes.removeAll(planes);
        this.powerUps.removeAll(powerUps);

        this.helicopters.removeAll(helicopters);
        this.animations.removeAll(animations);
        gameStage.stop();
        mp.stop();
        thread.quit();
        gameLaunch.quit();
        BackgroundHandler.y = 0;
        gameStage = null;
    }

    public void updateBackground() {
        for(int i = 0; i < heightOfBackgrounds; i++) {
            backgrounds[i].update();
        }
        Log.d("Running", "" + backgrounds[backgrounds.length - 1].y);
    }

    public void drawBackground(Canvas g) {
        for(int i = 0; i < heightOfBackgrounds; i++) {
            backgrounds[i].draw(g);
        }
    }

    public void defaultPaintSettings() {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(.1f);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(sizeY(30));
    }

    public void incrBackground(double incr) {
        BackgroundHandler.incr(this, incr);
    }

    public void setupBackground() {

        heightOfBackgrounds = gameStage.getBackgroundImages().length;

        backgrounds = new Background[heightOfBackgrounds];

        int length = backgrounds.length;
        if(length > 1) {
            for (int i = 0; i < heightOfBackgrounds; i++) {
                int height = -this.getWindowHeight() * (int) ((length / 2)); //sets initial height to
                height += (this.getWindowHeight() * i);
                backgrounds[i] = new Background(0, height, gameStage.getBackgroundImages()[i], this);
            }
        }
        else {
            backgrounds[0] = new Background(0,0, gameStage.getBackgroundImages()[0], this);
        }



    }


    /*public void setHitboxes(boolean f){
        showHitboxes = f;
    }*/

    public boolean showHitBoxes(){
        return showHitboxes;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public PlayerPlane getPlayerPlane(){
        return player;
    }

    public PlayerPlane getPlayer(){
        return player;
    }

    public int getScore(){
        return (int) gameStage.score;
    }


    public void setHighScore(Intent sender){
        if(gameStage.getType() == GameStage.StageType.REGULAR) {
            if (gameStage.score > gameLaunch.getHighScore()) {

                gameLaunch.setHighScore((int) gameStage.score);
                sender.putExtra("Score", (int) gameStage.score);
            } else {
                sender.putExtra("Score", 0);
            }
        }
    }

    public void setRandomHighScore(Intent sender) {
        if(gameStage.getType() == GameStage.StageType.RANDOM) {
            if (gameStage.score > gameLaunch.getRandomScore()) {
                Log.d("Running", " " + gameLaunch.getRandomScore());
                gameLaunch.setRandomScore((int) gameStage.score);
                sender.putExtra("Random Score", (int) gameStage.score);
            } else {
                sender.putExtra("Random Score", 0);
            }
        }
    }

    protected int getCurrentScore() {

        return (int) gameStage.score;
    }



    ///////
    public static int sizeX(int x){
        int divisible = 1280 / x;
        if(width / divisible > 0){
            return width / divisible;
        }
        else
            return 8;
    }

    public static int sizeY(int y){
        int divisible = 720 / y;
        if(height / divisible > 0){
            return height / divisible;
        }
        else
            return 8;
    }

    public static double sizeX(double x){
        double divisible = 1280 / x;
        if(width / divisible > 0){
            return width / divisible;
        }
        else
            return 8;
    }

    public static double sizeY(double y){
        double divisible = 720 / y;
        if(height / divisible > 0){
            return height / divisible;
        }
        else
            return 8;
    }




    public Bitmap getImage(int id){
        return BitmapFactory.decodeResource(getResources(), id);
    }

    /*
    public Bitmap getGif(int id) {
        InputStream gifInputStream = getResources().openRawResource(+ id);
        Movie gif = Movie.decodeStream(gifInputStream);
    }*/

    public Bitmap getBitmap(int id){
        return BitmapFactory.decodeResource(getResources(), id);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        if(newHeight > 0 && newWidth > 0) {
            Bitmap resized = Bitmap.createScaledBitmap(bm, newHeight, newWidth, false);
            return resized;
        }
        else
            return bm;

    }

    public Bitmap noBitmap(){
        return BitmapFactory.decodeResource(getResources(), R.drawable.nothing);
    }


    public static int getWindowWidth(){
        return width;
    }
    public static int getWindowHeight(){
        return height;
    }

    public static int getGameViewWidth(){
        return width;
    }
    public static int getGameViewHeight(){
        return height;
    }

    public int getSize(){
        return (backgrounds.length * getWindowHeight() - (((backgrounds.length / 2) - (1/2)) * getWindowHeight()));
    }





    public ArrayList<Plane> getPlanes(){
        return planes;
    }

    public ArrayList<Helicopter> getHelicopters(){
        return helicopters;
    }

    public ArrayList<PowerUp> getPowerUps(){
        return powerUps;
    }

    public ArrayList<EnemyBuff> getBuffs(){
        return buffs;
    }

    public ArrayList<MovementSequence> getAnimations(){
        return animations;
    }

    public Paint painter(){
        return paint;
    }


    public void sendPowerup(ItemID id, int randomSend) {
        switch(id) {
            case NOTHING:

            case CLOUD:
                if((int) (Math.random() * randomSend) == 1){
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    powerUps.add(new Cloud(0, (int) (Math.random() * getSize() - 1),getPlayer()));
                }
                break;

            case MINIPLANE:
                if((int) (Math.random() * randomSend) == 1){
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    powerUps.add(new ShrinkMachine(0,(int)(Math.random() * getSize() - 1),getPlayer()));
                }
                break;

            case INVISIBLECLOAK:
                if((int) (Math.random() * randomSend) == 1){
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    powerUps.add(new InvisibleCloak(0,(int)(Math.random() * getSize() - 1),getPlayer()));
                }
                break;

            case TMP:
                if((int) (Math.random() * randomSend) == 1){
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    powerUps.add(new TMP(this, 0,(int)(Math.random() * getSize() - 1)));
                }
                break;
        }
    }

    public void sendPlane(Plane.PlaneType type, int randomSend, boolean modifier) {
        switch(type) {
            case BLIMP:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    planes.add(new BlimpPlane(0, result, getPlayer()));
                }
                break;
            case RAPID:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    planes.add(new RapidFirePlane(0, result, getPlayer()));
                }
                break;

            case HEALTH:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    planes.add(new HealthPlane(10, result, getPlayer()));
                }
                break;

            case PELLET:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    planes.add(new PelletPlane(sizeX(50), result, getPlayer()));
                }
                break;

            case ROCKET:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    planes.add(new RocketPlane(0, result, true, getPlayer()));
                }
                break;

            case UNKNOWN:
                break;
            case SUICIDE:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    planes.add(new SuicidePlane(0, result, true, getPlayer()));
                }
                break;

            case TORPEDO:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    planes.add(new TorpedoPlane(0, result, true, modifier, getPlayer()));
                }
                break;

            case INVISIBLE:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    planes.add(new InvisiblePlane(0, result, true, getPlayer()));
                }
                break;

            case SPEEDBUFF:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    planes.add(new SpeedBuffPlane(0, result, getPlayer()));
                }
                break;

            case DOUBLETORPEDO:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    planes.add(new DoubleTorpedoPlane(0, result, true, 0, getPlayer()));
                }
                break;

            case INVISIBLERAPID:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    planes.add(new InvisibleRapidFirePlane(0, result, getPlayer()));
                }
                break;

            case PELLETHELI:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    helicopters.add(new PelletHelicopter(getPlayer().getGame(), 0, result));
                }
                break;
            case HOMINGHELI:
                if ((int) (Math.random() * randomSend) == 1) {
                    int result = generateRandomYValue((int) backgrounds[0].y, (int) backgrounds[backgrounds.length - 1].y);
                    helicopters.add(new HomingHelicopter(getPlayer().getGame(), 0, result));
                }
                break;

        }
    }


    public int generateRandomYValue(int min, int max) {
        int ii = (int) ((Math.random() * (getWindowHeight() * backgrounds.length)) - Math.abs(min));
        return (ii);
    }

    protected void sendBosses(int randomSend){
        int random = (int) (Math.random() * randomSend);
        if(random == 1 && useOnce == 0){
            useOnce = 1;
            planes.add(new Boss(0, ((Math.random() * getSize() - 100)), getPlayer()));
        }
        if(random == 1 && useOnce == 1){
            useOnce = 2;
            planes.add(new Boss(0, ((Math.random() * getSize() - 100)), getPlayer()));
        }
    }


    ////////////

    protected void setupStage(Canvas g, int time, int health){

        //regular.stageTime += SPEED;

        paint.setColor(Color.BLUE);


        /*
        if(regular.stageTime >= time){
            regular.stageTime = 0;
            regular.GAME_STAGE = regular.GAME_STAGE + 1;
            player.heal(health);
        }
        */
    }


    public void randomPlane(int random, int difficulty, int max){

        int min;
        Random r = new Random();

        switch(random){
            case 1:
                min = r.nextInt(max + 1- (62 * difficulty)) - 62 * difficulty;
                sendPlane(Plane.PlaneType.SUICIDE, min, false);
                break;
            case 2:
                min = r.nextInt(max + 1 - (62 * difficulty)) - 62 * difficulty;
                sendPlane(Plane.PlaneType.TORPEDO, min, false);
                break;
            case 3:
                min = r.nextInt(max + 1 - (65 * difficulty)) - 65 * difficulty;
                sendPlane(Plane.PlaneType.DOUBLETORPEDO, min, false);
                break;
            case 4:
                min = r.nextInt(max + 1 - (62 * difficulty)) - 62 * difficulty;
                sendPlane(Plane.PlaneType.INVISIBLE, min, false);
                break;
            case 5:
                min = r.nextInt(max + 1 - (62 * difficulty)) - 62 * difficulty;
                sendPlane(Plane.PlaneType.ROCKET, min, false);
                break;
            case 6:
                min = r.nextInt(max + 1 - (75 * difficulty)) - 75 * difficulty;
                sendPlane(Plane.PlaneType.INVISIBLERAPID, min, false);
                break;
            case 7:
                min = r.nextInt(max + 1 - (62 * difficulty)) - 65 * difficulty;
                sendPlane(Plane.PlaneType.BLIMP, min, false);
                break;
            case 8:
                min = r.nextInt(max + 1 - (62 * difficulty)) - 62 * difficulty;
                sendPlane(Plane.PlaneType.PELLET, min, false);
                break;
            case 9:
                min = r.nextInt(max + 1 - (65 * difficulty)) - 65 * difficulty;
                sendPlane(Plane.PlaneType.HOMINGHELI, min, false);
                break;
            case 10:
                min = r.nextInt(max + 1 - (75 * difficulty)) - 75 * difficulty;
                sendPlane(Plane.PlaneType.PELLETHELI, min, false);
                break;
            case 11:
                min = r.nextInt(max + 1 - (75 * difficulty)) - 75 * difficulty;
                sendPlane(Plane.PlaneType.RAPID, min, false);
                break;
        }


    }

    public void determinePlanes() {
        int random = (int) (Math.random() * 11);
        randomPlane(random, (int) 1, 1000);

    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.start();
    }
}
