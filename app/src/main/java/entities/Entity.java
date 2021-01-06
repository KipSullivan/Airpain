package entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import airplanes.PlayerPlane;
import game.com.airpain.airpain.BackgroundHandler;
import game.com.airpain.airpain.Game;

public abstract class Entity {
	
	public Game game;

	double x, y, sizeX, sizeY;

	double startY;
	
	public Entity(Game game, double x, double y, double sizeX, double sizeY){
		this.game = game;
		this.x = x;
		this.y = y - BackgroundHandler.y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.startY = y;
		/*
		if(!(this instanceof PlayerPlane)) {
			Log.d("Running", " : " + (y + BackgroundHandler.y) + "   " + BackgroundHandler.y);
		}
		*/
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		if(this instanceof PlayerPlane) {
			return y;
		}
		else {
			return y + BackgroundHandler.y;
		}
	}

	public double getStartY() {
		return startY + BackgroundHandler.y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void incrX(double incr) {
		x += incr;
	}

	public void incrY(double incr) {
		y += incr;
	}
	
	public int getSizeX() {
		return (int) sizeX;
	}

	
	public int getSizeY() {
		return (int) sizeY;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
	
	public abstract void updateMovement();
	
	public abstract Bitmap getBitmap();

	public void draw(Canvas g) {
		//getBitmap().recycle();
	}
	
	public abstract void destroy();
	
	public abstract boolean isVisible();
	
	public int sizeX(int size){
		return Game.sizeX(size);
	}
	
	public int sizeY(int size){
		return Game.sizeY(size);
	}
	
	public double sizeX(double size){
		return Game.sizeX(size);
	}
	
	public double sizeY(double size){
		return Game.sizeY(size);
	}
	
}
