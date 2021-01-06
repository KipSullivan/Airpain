package files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import game.com.airpain.airpain.MainMenu;

public class FileManager {


	MainMenu game;
	//Print p;
	
	FileOutputStream write;
	ObjectOutputStream output;
	
	FileInputStream read;
	ObjectInputStream input;
	
	//Window window;
	
	HighScore score;
	
	ArrayList<FileType> files = new ArrayList<>();
	
	public FileManager(MainMenu game){
		score = new HighScore(this, game);
		this.game = game;
	}
	
	public void deleteFiles(){
		for(FileType f : files){
			f.deleteFile();
		}
	}
	
	public int highScore(){
		String[] array = score.getHighScores(read).split(",");

		return Integer.parseInt(array[0]);
	}

	public int randomScore(){

		String[] array = score.getHighScores(read).split(",");

		return Integer.parseInt(array[1]);
	}
	
	public void saveScore(int score, int randomScore){
		this.score.saveHighScores(write, score, randomScore);
	}
	
}
