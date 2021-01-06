package animations;

import android.graphics.Canvas;

import game.com.airpain.airpain.Game;

public abstract class MovementSequence {
	
	Game game;
	
	public MovementSequence(Game game){
		this.game = game;
	}
	
	public abstract boolean currentlyRunning();


	public void draw(Canvas g) {

	}
	
	public void start() {
		game.getAnimations().add(this);
	}
	
	public void stop() {
		game.getAnimations().remove(this);
	}
	
	public int speedPerSecond(){
		return 0;
	}
	
	public double runsPerSecond(){
		return 0;
	}

	public void destroy() {
		game.getAnimations().remove(this);
	}

	
	public void onRun(){
		
	}
	
	public void doSequences(){
		
	}

	
}
