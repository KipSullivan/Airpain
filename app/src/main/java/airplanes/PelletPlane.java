package airplanes;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import projectiles.Pellet;

public class PelletPlane extends ProjectilePlane {
	double speedBuff;
	double velY;
	boolean hasShot;
	boolean shootLow;
	Bitmap image;
	PlayerPlane player;
	public PelletPlane(int x, int y, PlayerPlane player){
		super(PlaneType.PELLET, x, y, Game.sizeX(96), Game.sizeY(70));
		this.player = player;
		hasShot = false;
		image = game.getResizedBitmap(game.getBitmap(R.drawable.pelletplane), getSizeX(), getSizeY());
		int random = (int) (Math.random() * 2);
		if(random == 1){
			shootLow = true;
		}
		else
			shootLow = false;
	}
	@Override
	public void setBuff(double buff) {
		speedBuff = buff;
	}

	@Override
	public double getBuff() {
		return speedBuff;
	}

	public void draw(Canvas g) {
		super.draw(g);
	}

	@Override
	public void updateMovement() {
		super.updateMovement();
		if(isVisible){
			velY = -sizeY(2.7);
			incrY(velY);
			if(getY() < getStartY() - Game.sizeY(430) && hasShot == false && shootLow) {
				add(new Pellet(getX(), getY(), player, this));
				add(new Pellet(getX(), getY(), player, this));
				add(new Pellet(getX(), getY(), player, this));
				add(new Pellet(getX(), getY(), player, this));
				add(new Pellet(getX(), getY(), player, this));
				add(new Pellet(getX(), getY(), player, this));
				hasShot = true;
			}
			if(getY() < getStartY() - Game.sizeY(260) && hasShot == false && shootLow == false){
				add(new Pellet(getX(), getY(), player, this));
				add(new Pellet(getX(), getY(), player, this));
				add(new Pellet(getX(), getY(), player, this));
				add(new Pellet(getX(), getY(), player, this));
				add(new Pellet(getX(), getY(), player, this));
				add(new Pellet(getX(), getY(), player, this));
				hasShot = true;
			}
			if(getY() < game.backgrounds[0].y){
				isVisible = false;
			}
		}
	}

	@Override
	public Bitmap getBitmap() {
		return image;
	}


	@Override
	public boolean isVisible() {
		return isVisible;
	}


}
