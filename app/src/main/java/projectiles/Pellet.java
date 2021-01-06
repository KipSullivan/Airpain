package projectiles;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import airplanes.PlayerPlane;
import airplanes.ProjectilePlane;
import entities.ProjectileCraft;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;

public class Pellet extends Projectile {
	
	double velY, velX;
	double inaccuracy;
	
	private int DAMAGE_AMOUNT = 10;
	boolean isVisible;
	
	Bitmap image;
	PlayerPlane player;
	
	public Pellet(double x, double y, PlayerPlane player, ProjectileCraft host){
		super(player.getGame(), host, x, y, Game.sizeX(10), Game.sizeY(10));
		int random = (int) (Math.random() * 2);
		if(random == 1){
			inaccuracy = Math.random() * sizeY(.3);
		}
		else {
			inaccuracy = Math.random() * -sizeY(.3);
		}
		this.player = player;
		isVisible = true;
		image = player.getGame().getResizedBitmap(player.getGame().getBitmap(R.drawable.pellet), getSizeX(), getSizeY());
	}


	
	public Pellet(double x, double y, int damage, PlayerPlane player, ProjectilePlane host){
		super(player.getGame(), host, x, y, Game.sizeX(10), Game.sizeY(10));
		int random = (int) (Math.random() * 2);
		if(random == 1){
			inaccuracy = Math.random() * sizeY(.3);
		}
		else {
			inaccuracy = Math.random() * -sizeY(.3);
		}
		this.player = player;
		isVisible = true;
		DAMAGE_AMOUNT = damage;
	}

	public void draw(Canvas g) {
		super.draw(g);
	}

	@Override
	public void updateMovement() {
		if(isVisible){
			super.updateMovement();
			velX = sizeX(8.4 / 1.86);
			velY = inaccuracy;
			incrX(velX);
			incrY(velY);
			if(getHitBox().intersect(player.getHitBox()) && player.isVisible()){
				destroy();
				player.damage(DAMAGE_AMOUNT);
			}
			if(getX() > Game.getWindowWidth()){
				destroy();
			}
		}
	}

	public boolean isVisible(){
		return isVisible;
	}

	
	@Override
	public Bitmap getBitmap() {
		return image;
	}


}
