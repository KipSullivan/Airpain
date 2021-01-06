package items;


import android.graphics.Bitmap;

import airplanes.PlayerPlane;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;

public class InvisibleCloak extends PowerUp {
	static ItemID id = ItemID.INVISIBLECLOAK;
	double velX, velY;
	boolean isVisible;

	final static int SECONDS_USED = 15;

	final static int uses = 1;

	Bitmap image;
	PlayerPlane player;
	public InvisibleCloak(int x, int y, PlayerPlane player){
		super(id,player, SECONDS_USED, uses, x, y, (int) (Game.getGameViewWidth() / 40),(Game.getGameViewHeight() / 22.5));
		this.player = player;
		isVisible = true;
		id = ItemID.INVISIBLECLOAK;
		image = player.getGame().getResizedBitmap(player.getGame().getBitmap(R.drawable.cloak), getSizeX(), getSizeY());
	}
	public ItemID getID() {
		return id;
	}

	public Bitmap getBitmap(){
		return image;
	}
	public boolean isVisible(){
		return isVisible;
	}

	public void onEndOfUse() {
		player.setVisible(true);
	}

	public void onUse() {
		player.setVisible(false);
	}


	public void updateMovement(){
		super.updateMovement();
		if(isVisible && !pickedUp){

			velX = sizeX(4.48 / 1.86);
			incrX(velX);
			incrY(velY);
			if(getX() > Game.getGameViewWidth()){
				isVisible = false;
				player.getGame().getPowerUps().remove(this);
			}
			if(getX() > Game.getGameViewWidth() - sizeX(630)){
				if(getY() + Game.getGameViewHeight() / 36 < player.getY()){
					velY = -sizeY(.80);
				}
				if(getY() > player.getY()){
					velY = sizeY(.80);
				}
			}
		}
	}

}
