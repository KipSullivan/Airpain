package items;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import airplanes.PlayerPlane;
import game.com.airpain.airpain.Game;
import utilities.HitBox;

public abstract class PowerUp extends Item {
	ItemID id;
	PlayerPlane player;

	Rect hitBox = HitBox.nullHitBox();
	public PowerUp(ItemID id, PlayerPlane player, int seconds, int uses, double x, double y, double sizeX, double sizeY){
		super(player.getGame(), id, seconds, uses, x, y, sizeX, sizeY);
		this.id = id;
		this.player = player;
	}

	public int sizeX(int size){
		int divisible = 1280 / size;
		return (int) Game.getWindowWidth() / divisible;
	}
	public int sizeY(int size){
		int divisible = 720 / size;
		return (int) Game.getWindowHeight() / divisible;
	}
	public double sizeX(double size){
		double divisible =  1280 / size;
		return Game.getWindowWidth() / divisible;
	}
	public double sizeY(double size){
		double divisible = 720 / size;
		return Game.getWindowHeight() / divisible;
	}

	public void updateMovement() {
		super.updateMovement();

		if(!pickedUp) {
			hitBox = HitBox.createHitBox(getX(), getY(), getSizeX(), getSizeY());
		}
		if(hitBox.intersect(player.getHitBox())) {
			if(player.getInventory().addItemToInventory(this)) {
				pickedUp = true;
				hitBox = HitBox.nullHitBox();
			}
		}

	}

	public void draw(Canvas g) {
        super.draw(g);
	    if(!pickedUp && uses > 0) {
            g.drawBitmap(getBitmap(), (int) getX(), (int) getY(), player.getGame().painter());
        }
	}

	public Bitmap resizeBitmap(Bitmap map, int sizeX, int sizeY){
		map = player.getGame().getResizedBitmap(map, sizeX, sizeY);
		return map;
	}

	public void destroy() {
		player.getGame().getPowerUps().remove(this);
	}

	public void setBitmap(Bitmap bit1, Bitmap bit2){
		bit1 = bit2;
	}
	
	public abstract Bitmap getBitmap();

	
	public abstract boolean isVisible();
	
	public Rect getHitBox() {
		return hitBox;
	}
}
