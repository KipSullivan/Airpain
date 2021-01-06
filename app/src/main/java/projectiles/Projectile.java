package projectiles;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import entities.Entity;
import entities.ProjectileCraft;
import game.com.airpain.airpain.Game;
import utilities.HitBox;


public abstract class Projectile extends Entity {

	private Rect hitBox = HitBox.nullHitBox();

	ProjectileCraft host;

	Game game;


	protected Projectile(Game game, ProjectileCraft host, double x, double y, double sizeX, double sizeY){
		super(game, x, y, sizeX, sizeY);
		this.host = host;
		this.game = game;
	}



	public void updateMovement() {
		hitBox = HitBox.createHitBox(getX(), getY(), getSizeX(), getSizeY());
		if(getX() + getSizeX() >  Game.getWindowWidth()) {
			destroy();
		}
	}

	public void draw(Canvas g) {
		g.drawBitmap(getBitmap(), (int) getX(), (int) getY(), game.painter());
		if(game.showHitBoxes()) {
			HitBox.showHitBox(getHitBox(), g);
		}
		super.draw(g);
	}

	public boolean onScreen() {
		if(getY() + getSizeY() > 0 && getY() < Game.getWindowHeight()) {
			return true;
		}
		return false;
	}

	public Bitmap resizeBitmap(Bitmap map, int sizeX, int sizeY){
		map = game.getResizedBitmap(map, sizeX, sizeY);
		return map;
	}

	public void setBitmap(Bitmap bit1, Bitmap bit2){
		bit1 = bit2;
	}

	public abstract Bitmap getBitmap();

	public Rect getHitBox() {
		return hitBox;
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

	public void destroy() {
		host.getProjectiles().remove(this);
	}

}
