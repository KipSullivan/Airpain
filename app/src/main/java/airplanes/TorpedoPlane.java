package airplanes;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import projectiles.Torpedo;

public class TorpedoPlane extends ProjectilePlane {
	double velX, velY;
	double speedBuff;
	final int DAMAGE_AMOUNT = 30;
	boolean hasShot;
	boolean isVisible;
	boolean isStupid;
	Bitmap image;
	PlayerPlane player;
	public TorpedoPlane(int x, int y, boolean isVisible, boolean isStupid, PlayerPlane plane){
		super(PlaneType.TORPEDO, x, y, Game.sizeX(86), Game.sizeY(50));
		this.isVisible = isVisible;
		this.isStupid = isStupid;
		hasShot = false;
		player = plane;
		image = game.getResizedBitmap(game.getBitmap(R.drawable.torpedoplane), getSizeX(), getSizeY());
	}


	public void draw(Canvas g) {
		super.draw(g);
	}

	@Override
	public void updateMovement() {
		super.updateMovement();
		if(isVisible){
			if(getX() < -Game.sizeX(getSizeX()) || getX() > Game.getGameViewWidth()){
				isVisible = false;
				kill();
			}
			if(getX() <= sizeX(300) && hasShot == false){
				velX = Game.sizeX(1.3) + speedBuff;
				incrX(velX);
			}
			if(getX() >= sizeX(300) && hasShot == false){
				add(new Torpedo(getX(),getY(),true, isStupid, 50, player, this));
				hasShot = true;
			}
			if(hasShot){
				velX = -(Game.sizeX(1.3) + speedBuff);
				incrX(velX);
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
