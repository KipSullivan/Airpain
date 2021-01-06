package airplanes;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import projectiles.Torpedo;


public class BlimpPlane extends ProjectilePlane {
	double velX, velY;
	boolean usedSound = false;
	final int DAMAGE_AMOUNT = 15;
	double speedBuff;
	Bitmap image;
	PlayerPlane player;
	public BlimpPlane(int x, int y, PlayerPlane player){

		super(PlaneType.BLIMP, x, y, (int) (Game.sizeX(320)), (int) (Game.sizeY(256)));
		this.player = player;
		image = player.getGame().getResizedBitmap(player.getGame().getBitmap(R.drawable.blimp), getSizeX(), getSizeY());
	}
	public BlimpPlane(int x, int y, boolean isBoss, PlayerPlane player){
		super(PlaneType.BLIMP, x, y, (int) (Game.getGameViewWidth() / 5), (int) (Game.getGameViewHeight() / 2.13));
		this.player = player;
	}


	public void draw(Canvas g) {
		super.draw(g);
	}

	@Override
	public void updateMovement() {
		super.updateMovement();
		if(isVisible){
			int random = (int) (Math.random() * 600);
			if(random == 1 && getX() + sizeX(120) < player.getX()){
				add(new Torpedo(getX() + 90, getY() + sizeY(150),true,30,player, this));
			}
			velX = Game.sizeX(2.8 / 1.86) + speedBuff;
			incrX(velX);
			if(getX() + sizeX(35) > Game.getGameViewWidth()) {
				setVisible(false);
				kill();
			}
			if(getHitBox().intersect(player.getHitBox())){
				setVisible(false);
				player.damage(DAMAGE_AMOUNT);
				kill();
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
