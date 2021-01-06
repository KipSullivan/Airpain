package game.com.airpain.airpain;


import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;

import files.FileManager;

/**
 * Created by KipJay on 11/1/2016.
 */

public class GameLaunch extends Activity {

    Game v;
    String mode = "REGULAR";

    int score = 0;
    int randomScore = 0;


    FileManager m;



    protected void onCreate(Bundle bundle){

        super.onCreate(bundle);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        v = new Game(this);

        recieve();

        v.putExtra(this, mode);

        setContentView(v);
    }

    private void recieve(){
        Intent in = getIntent();
        Bundle b = in.getExtras();
        String mode = b.getString("Mode");
        int[] scoreList = b.getIntArray("Scores");
        score = scoreList[0];
        randomScore = scoreList[1];
        this.mode = mode;

    }

    protected void onResume(){
        super.onResume();
        //Log.d("Running", "Resuming");
        v.resume();
    }


    protected void onPause(){
        super.onPause();
        //Log.d("Running", "Pausing");
        v.pause();
    }


    protected void quit(){
        //Log.d("Ending", "Ending");

        Intent in = new Intent();

        v.setHighScore(in);
        v.setRandomHighScore(in);

        setResult(RESULT_OK, in);
        finish();
    }

    public int getHighScore(){
        return score;
    }

    public int getRandomScore() {
        return randomScore;
    }

    public void setHighScore(int score){
        this.score = score;
    }

    public void setRandomScore(int score) {
        this.randomScore = score;
    }



    public Game getGame(){
        return v;
    }
}
