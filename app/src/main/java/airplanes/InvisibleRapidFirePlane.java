package airplanes;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import projectiles.Projectile;
import projectiles.Torpedo;

public class InvisibleRapidFirePlane extends ProjectilePlane {
	double velX, velY;
	double speedBuff;
	final int DAMAGE_AMOUNT = 30;
	int hasBitmap;
	boolean hasShot;
	boolean usedSound = false;
	Bitmap image;
	PlayerPlane player;
	public InvisibleRapidFirePlane(int x, int y, PlayerPlane plane){
		super(PlaneType.INVISIBLERAPID, x, y, Game.sizeX(86), Game.sizeY(50));
		hasBitmap = 0;
		hasShot = false;
		player = plane;
		image = game.getResizedBitmap(game.getBitmap(R.drawable.invisrapidfireplane), getSizeX(), getSizeY());
	}

	public void draw(Canvas g) {

		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			if(p.isVisible()) {
				p.draw(g);
			}
		}

		if(hasBitmap == 0 || hasBitmap == 2 && isVisible) {
			g.drawBitmap(getBitmap(), (int) getX(), (int) getY(), game.painter());
		}
	}

	@Override
	public void updateMovement() {
		super.updateMovement();
		if(isVisible){

			velX = sizeX(5.6 / 1.86) + speedBuff;
			incrX(velX);
			if(getX() < -96 || getX() > Game.getGameViewWidth()){
				isVisible = false;
			}
			if(getX() >= Game.getGameViewWidth() / 4.26 && getX() <= Game.getGameViewWidth() / 2.56 && hasShot == false){
				add(new Torpedo(getX(),getY() + sizeY(20), Game.sizeX(2), Game.sizeY(.6), true, 40, player, this));
				hasShot = true;
			}
			if(getX() > Game.getGameViewWidth() / 10.6 && hasBitmap == 0){
				image = player.getGame().noBitmap();
				hasBitmap = 1;
			}
			if(getX() > sizeX(800.0) - sizeX(96) && hasBitmap == 1){
				hasBitmap = 2;
				image = game.getResizedBitmap(game.getBitmap(R.drawable.invisrapidfireplane), getSizeX(), getSizeY());
			}
			if(hasShot && getX() >= Game.getGameViewWidth() / 2.13){
				add(new Torpedo(getX(),getY() + sizeY(40),true,50, player, this));
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
