package airplanes;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;

public class SuicidePlane extends Plane {
	
	
	double velX, velY;
	private final int DAMAGE_AMOUNT = 30;
	double speedBuff;
	boolean isVisible;
	boolean usedSound;
	
	Bitmap image;
	PlayerPlane player;
	//Print print;
	
	
	public SuicidePlane(int x, int y, boolean isVisible, PlayerPlane player){
		super(PlaneType.SUICIDE, x, y, Game.sizeX(96), Game.sizeY(58));
		speedBuff = 0;
		usedSound = false;
		this.player = player;
		this.isVisible = isVisible;
		image = player.getGame().getResizedBitmap(player.getGame().getBitmap(R.drawable.suicideplane), getSizeX(), getSizeY());
	}

	
	public boolean isVisible(){
		return isVisible;
	}


	public void draw(Canvas g) {
		super.draw(g);
	}

	@Override
	public void updateMovement() {
		super.updateMovement();
		if(isVisible){
			velX = sizeX(5.04 / 1.86) + speedBuff;
			incrX(velX);

			if(getHitBox().intersect(player.hitBox)){
				destroy();
				player.damage(DAMAGE_AMOUNT);
			}
			if(getX() > Game.getGameViewWidth()){
				destroy();
			}

		}
	}

	public void destroy() {
		isVisible = false;
		player.getGame().getPlanes().remove(this);
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
