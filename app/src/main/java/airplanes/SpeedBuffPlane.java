package airplanes;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import enemybuffs.SpeedBuff;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;

public class SpeedBuffPlane extends Plane {
	double velX, velY;
	private final int DAMAGE_AMOUNT = 10;
	double speedBuff;
	boolean isVisible;
	boolean hasDeployed;
	Bitmap image;
	PlayerPlane player;
	//Print print;
	public SpeedBuffPlane(int x, int y, PlayerPlane player){
		super(PlaneType.SPEEDBUFF, x, y, Game.sizeX(96), Game.sizeY(58));
		this.player = player;
		hasDeployed = false;
		isVisible = true;
		image = game.getResizedBitmap(game.getBitmap(R.drawable.speedbuffplane), getSizeX(), getSizeY());
	}

	public void draw(Canvas g) {
		g.drawBitmap(getBitmap(), (int) getX(), (int) getY(), game.painter());
	}

	
	@Override
	public void updateMovement() {
		super.updateMovement();
		if(isVisible){
			velX = Game.sizeX(2.4 / 1.86) + speedBuff;
			incrX(velX);
			if(getHitBox().intersect(player.getHitBox())){
				destroy();
				player.damage(DAMAGE_AMOUNT);
			}
			if(getX() > Game.getGameViewWidth() / 4.26 && hasDeployed == false){
				player.getGame().getBuffs().add(new SpeedBuff(getX() + sizeX(30),getY() + sizeY(50),player));
				hasDeployed = true;
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
	public boolean isVisible() {
		return isVisible;
	}
	@Override
	public void setBuff(double buff) {
		speedBuff = buff;
		
	}
	public double getBuff() {
		return speedBuff;
	}

}
