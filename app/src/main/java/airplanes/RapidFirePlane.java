package airplanes;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import projectiles.Torpedo;

public class RapidFirePlane extends ProjectilePlane {
	double velX, velY;
	double speedBuff;
	final int DAMAGE_AMOUNT = 25;
	boolean hasShot;
	Bitmap image;
	PlayerPlane player;
	public RapidFirePlane(int x, int y, PlayerPlane plane){
		super(PlaneType.RAPID, x, y, Game.sizeX(96), Game.sizeY(58));
		hasShot = false;
		player = plane;
		image = game.getResizedBitmap(game.getBitmap(R.drawable.rapidfireplane), getSizeX(), getSizeY());
	}

	public void draw(Canvas g) {
		super.draw(g);
	}
	@Override
	public void updateMovement() {
		super.updateMovement();
		if(isVisible){
			velX = game.sizeX(5.6 / 1.86) + speedBuff;
			incrX(velX);
			if(getX() < -96 || getX() > Game.getGameViewWidth()){
				isVisible = false;
			}
			if(getX() >= sizeX(300) && getX() <= sizeX(500) && hasShot == false){
				add(new Torpedo(getX(),getY() + 20, Game.getGameViewWidth() / 1347.36, Game.getGameViewHeight() / 1600.0, true, 40,player, this));
				hasShot = true;
			}
			if(hasShot && getX() >= sizeX(600)){
				add(new Torpedo(getX(),getY() + 40,true, 50, player, this));
				hasShot = false;
			}
			if(getHitBox().intersect(player.getHitBox())){
				isVisible = false;
				player.damage(DAMAGE_AMOUNT);
			}
		}
	}
	@Override
	public Bitmap getBitmap() {
		return image;
	}

	@Override
	public void setBuff(double buff) {
		speedBuff = buff;
		
	}
	@Override
	public double getBuff() {
		return speedBuff;
	}
}
