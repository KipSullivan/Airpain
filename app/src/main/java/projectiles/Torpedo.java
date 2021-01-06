package projectiles;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import airplanes.PlayerPlane;
import airplanes.ProjectilePlane;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;

public class Torpedo extends Projectile {
	
	
	double velX, velY;
	
	double setVelX, setVelY;
	
	
	int DAMAGE_AMOUNT = 0;
	
	PlayerPlane player;

	Bitmap image;

	
	
	boolean isVisible;
	boolean setableVelY;
	boolean isStupid;
	
	
	public Torpedo(double x, double y, boolean isVisible, int damage, PlayerPlane player, ProjectilePlane host){
		super(player.getGame(), host, x, y, (int) (Game.getWindowWidth() / 64), (int) (Game.getWindowHeight() / 36));

		setableVelY = false;
		DAMAGE_AMOUNT = damage;
		this.isVisible = isVisible;
		this.player = player;
		image =  player.getGame().getResizedBitmap(player.getGame().getBitmap(R.drawable.torpedo), getSizeX(), getSizeY());
	}
	
	
	public Torpedo(double x, double y, boolean isVisible, boolean isStupid, int damage, PlayerPlane player, ProjectilePlane host){
		super(player.getGame(), host, x, y, (int) (Game.getWindowWidth() / 64), (int) (Game.getWindowHeight() / 36));

		setableVelY = false;
		this.isStupid = isStupid;
		DAMAGE_AMOUNT = damage;
		this.isVisible = isVisible;
		this.player = player;
		image =  player.getGame().getResizedBitmap(player.getGame().getBitmap(R.drawable.torpedo), getSizeX(), getSizeY());
	}
	
	
	public Torpedo(double x, double y, double velX, double velY, boolean isVisible, int damage, PlayerPlane player, ProjectilePlane host){
		super(player.getGame(), host, x, y, (int) (Game.getWindowWidth() / 64), (int) (Game.getWindowHeight() / 36));

		setableVelY = true;
		DAMAGE_AMOUNT = damage;
		image =  player.getGame().getResizedBitmap(player.getGame().getBitmap(R.drawable.greentorpedo), getSizeX(), getSizeY());
		this.setVelX = velX;
		this.setVelY = velY;
		this.isVisible = isVisible;
		this.player = player;
	}
	

	
	public boolean isVisible(){
		return isVisible;
	}

	public void draw(Canvas g) {
		super.draw(g);
	}

	@Override
	public void updateMovement() {
		if(isVisible){
			super.updateMovement();
			incrX(velX);
			incrY(velY);

			
			if(getY() < player.getY() + Game.getGameViewHeight() / 36 && setableVelY == false && player.isVisible() && isStupid == false){
				if(velY < sizeY(1.68 / 1.86)){
					velY += sizeY(.168 / 1.86);
				}
			}
			
			if(getY() > player.getY() + Game.getGameViewHeight() / 36 && setableVelY == false && player.isVisible() && isStupid == false){
				if(velY > -sizeY(1.68 / 1.86)){
				velY -= sizeY(.168 / 1.86);
				}
			}
			
			if(getX() < player.getX() && setableVelY == false && player.isVisible() && isStupid == false){
				velX = Game.sizeX(2.5);
			}
			
			if(player.isVisible() == false && setableVelY == false && isStupid == false){
				velX = Game.sizeX(2.5);
				velY = 0;
			}
			
			if(isStupid){
				velX = Game.sizeX(2.5);
			}
			
			if(getX() > Game.getGameViewWidth()){
				isVisible = false;
				destroy();
			}


			if(getHitBox().intersect(player.getHitBox()) && player.isVisible()){
				if(player.isVisible()) {
					player.damage(DAMAGE_AMOUNT);
					isVisible = false;
					destroy();
				}
			}
			
			///
			if(getY() < player.getY() + Game.getGameViewHeight() / 36 && setableVelY && player.isVisible() && isStupid == false){
				velY = setVelY;
			}
			
			if(getY() > player.getY() + Game.getGameViewHeight() / 36 && setableVelY && player.isVisible() && isStupid == false){
				velY = -setVelY;
			}
			
			if(getX() < player.getX() && setableVelY && player.isVisible() && isStupid == false){
				velX = setVelX;
			}
			
			if(player.isVisible() == false && setableVelY && isStupid == false){
				velX = setVelX;
				velY = 0;
			}
			
		}
	}

	
	@Override
	public Bitmap getBitmap() {
		return image;
	}


}
