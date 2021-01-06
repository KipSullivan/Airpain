package enemybuffs;

import android.graphics.Canvas;
import android.graphics.Rect;

import entities.Entity;
import game.com.airpain.airpain.Game;
import utilities.HitBox;


public abstract class EnemyBuff extends Entity {


	Rect hitBox = HitBox.nullHitBox();

	public boolean isVisible = true;

	public EnemyBuff(Game game, double x, double y, double sizeX, double sizeY) {
		super(game, x, y, sizeX, sizeY);
	}

	public void updateMovement() {
		hitBox = HitBox.createHitBox((int) getX(), (int) getY(), getSizeX(), getSizeY());
		detectPlanes();
	}

	public void draw(Canvas g) {
		if(isVisible) {
			g.drawBitmap(getBitmap(),(int) getX(), (int) getY(), game.painter());
		}
	}

	public void destroy() {
		isVisible = false;
		super.game.getBuffs().remove(this);
	}

	public Rect getHitBox() {
		return hitBox;
	}

	public abstract void detectPlanes();

	public boolean isVisible() {
		return isVisible;
	}
	
	public int sizeX(int size){
		int divisible = 1280 / size;
		return (int) Game.getGameViewWidth() / divisible;
	}
	
	public int sizeY(int size){
		int divisible = 720 / size;
		return (int) Game.getGameViewHeight() / divisible;
	}
	
	public double sizeX(double size){
		double divisible =  1280 / size;
		return Game.getGameViewWidth() / divisible;
	}
	
	public double sizeY(double size){
		double divisible = 720 / size;
		return Game.getGameViewHeight() / divisible;
	}
	
}
