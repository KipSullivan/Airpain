package airplanes;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;

public class RocketPlane extends Plane {
	double velX, velY;
	double speedBuff;
	private final int DAMAGE_AMOUNT = 50;
	boolean isVisible;
	Bitmap image;
	PlayerPlane player;
	public RocketPlane(int x, int y, boolean isVisible, PlayerPlane player){
		super(PlaneType.ROCKET, x, y, Game.sizeX(96), Game.sizeY(58));
		this.player = player;
		this.isVisible = isVisible;
		image = game.getResizedBitmap(game.getBitmap(R.drawable.rocketplane), getSizeX(), getSizeY());
	}

	
	public boolean isVisible(){
		return isVisible;
	}

	public void draw(Canvas g) {
		g.drawBitmap(getBitmap(), (int) getX(), (int) getY(), game.painter());
	}
	@Override
	public void updateMovement() {
		super.updateMovement();
		if(isVisible){
			velX = Game.sizeX(11.94 / 1.86) + speedBuff;
			incrX(velX);
			if(getHitBox().intersect(player.getHitBox())){
				destroy();
				player.damage(DAMAGE_AMOUNT);
			}
			if(getX() > Game.getGameViewWidth()){
				destroy();
			}

		}
	}

	@Override
	public Bitmap getBitmap() {
		return image;
	}

	@Override
	public void destroy() {
		isVisible = false;
		player.getGame().getPlanes().remove(this);
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
