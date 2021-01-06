package files;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import game.com.airpain.airpain.MainMenu;


public class HighScore extends FileType {
	
	
	String directory = "highScore.txt";

	MainMenu game;

	File highScore;
	
	public HighScore(FileManager file, MainMenu game){

		this.game = game;
		
		file.files.add(this);
	}
	
	public void saveHighScores(FileOutputStream stream, int score, int randomScore){
		
		try {
			
			stream = game.openFileOutput(directory, game.MODE_PRIVATE);
			ObjectOutputStream s = new ObjectOutputStream(stream);

			if(score > 0) {
				s.writeInt(score);

			}
			else {
				s.writeInt(game.getScore());
			}

			if(randomScore > 0) {
				s.writeInt(randomScore);
			}
			else {
				s.writeInt(game.getRandomScore());
			}
			s.flush();
			s.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getHighScores(FileInputStream stream){

		
		try {
				stream = game.openFileInput(directory);
				ObjectInputStream s = new ObjectInputStream(stream);
				int score = s.readInt();
				int randomScore = s.readInt();
				s.close();

				String results = score + "," + randomScore;

				Log.d("Running", "Scores: " + results);

				return results;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "0,0";
	}

	@Override
	public void deleteFile() {
		game.deleteFile(directory);
	}
	
}
