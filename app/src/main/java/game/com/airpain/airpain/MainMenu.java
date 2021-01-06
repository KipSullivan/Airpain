package game.com.airpain.airpain;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import files.FileManager;
import gamestages.GameStage;

public class MainMenu extends AppCompatActivity implements View.OnClickListener {

    Spinner gameModes;
    String[] modes = new String[3];
    int[] highScores = new int[modes.length];

    Button play;
    Button delete;

    String regularScore;
    String randomScoreString;

    int score;
    int randomScore;

    FileManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_window);

        manager = new FileManager(this);

        score = manager.highScore();
        highScores[0] = score;

        randomScore = manager.randomScore();
        highScores[1] = randomScore;

        highScores[2] = 0;


        setListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater in = getMenuInflater();

        in.inflate(R.menu.windowmenu, menu);

        return true;
    }


    private void setListeners(){

        regularScore = getResources().getString(R.string.score);
        randomScoreString = getResources().getString(R.string.randomscore);

        gameModes = (Spinner) findViewById(R.id.game_mode_spinner);

        GameStage.StageType[] types = GameStage.StageType.values();

        modes = new String[types.length];

        for(int i = 0; i < types.length; i++) {
            modes[i] = types[i].toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, modes);

        gameModes.setAdapter(adapter);

        play = (Button) findViewById(R.id.play);
        play.setOnClickListener(this);

        delete = (Button) findViewById(R.id.delete);
        delete.setOnClickListener(this);

        TextView v = (TextView) findViewById(R.id.score);

        v.setText(regularScore + score);

        TextView random = (TextView) findViewById(R.id.randomScore);

        random.setText(randomScoreString + randomScore);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){

        Bundle b = data.getExtras();

        manager.saveScore(b.getInt("Score"), b.getInt("Random Score"));

        TextView score = (TextView) findViewById(R.id.score);
        TextView random = (TextView) findViewById(R.id.randomScore);

        if(b.getInt("Score") > 0) {
            score.setText(this.regularScore + b.getInt("Score"));
            highScores[0] = b.getInt("Score");
        }

        if(b.getInt("Random Score") > 0) {
            random.setText(this.randomScoreString + b.getInt("Random Score"));
            highScores[1] = b.getInt("Random Score");
        }
    }

    public int getScore(){
        return score;
    }

    public int getRandomScore() {
        return randomScore;
    }

    public void updateScore() {

        TextView v = (TextView) findViewById(R.id.score);

        v.setText(regularScore + score);

        TextView random = (TextView) findViewById(R.id.randomScore);

        random.setText(randomScoreString + score);
    }






    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.deletefolders) {
            DialogFragment fragment = new Alert();
            fragment.show(getFragmentManager(), "delete");
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){

        if(v.getId() == R.id.play) {

            for(int i = 0; i < modes.length; i++) {
                if(gameModes.getSelectedItem() == modes[i]) {
                    Intent in = new Intent(this, GameLaunch.class);
                    in.putExtra("Mode", modes[i]);

                    in.putExtra("Scores", highScores);

                    startActivityForResult(in, 0);
                }
            }
        }

        if(v.getId() == R.id.delete){
            DialogFragment fragment = new Alert();
            fragment.show(getFragmentManager(), "delete");
        }
    }




    public static class Alert extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle d){

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Are you sure you want to delete the score files?");
            builder.setCancelable(true);

            builder.setPositiveButton("Yes", new Dialog.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainMenu activity = (MainMenu) getActivity();
                    activity.score = 0;
                    activity.updateScore();
                    activity.manager.deleteFiles();
                }

            });

            builder.setNegativeButton("No!! Keep them!", new Dialog.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainMenu activity = (MainMenu) getActivity();

                }
            });

            return builder.create();

        }


    }


}
