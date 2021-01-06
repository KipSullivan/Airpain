package items;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import airplanes.PlayerPlane;
import animations.TextAnimation;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import utilities.CountdownTimer;

public class ShrinkMachine extends PowerUp {
	static ItemID id = ItemID.MINIPLANE;
	double velX, velY;
	boolean isVisible;

	final static int SECONDS_USED = 20;

	final int textSize = Game.sizeY(30);

	///shows how many seconds are left on the power up
	volatile CountdownTimer countdownTimer = new CountdownTimer(3);
	Bitmap image;
	PlayerPlane player;

	public ShrinkMachine(int x, int y, PlayerPlane player){
		super(id,player, SECONDS_USED, 1, x, y,(int) (Game.getGameViewWidth() / 40),(int) (Game.getGameViewHeight() / 22.5));
		this.player = player;
		isVisible = true;
		id = ItemID.MINIPLANE;
		image = player.getGame().getResizedBitmap(player.getGame().getBitmap(R.drawable.shrinkmachine), getSizeX(), getSizeY());
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

	public void onEndOfUse() { /// sets player size back to normal
		player.enlarge();
		countdownTimer.stop();
		countdownTimer = null;
	}

	public void onUse() {

	}

	public void draw(Canvas g) {
		super.draw(g);
		if(using) {
			if (System.currentTimeMillis() - startTime > ((use_seconds * 1000) - 3000)) {
				if (countdownTimer != null) {
					if (!countdownTimer.isStarted()) {
						countdownTimer.start();
					}
					countdownTimer.update();

					player.getGame().painter().setTextSize(textSize);
					player.getGame().painter().setColor(Color.GRAY);

					g.drawText("" + countdownTimer.secondsLeft(), (int) player.getX(), (int) (player.getY() - textSize),
							player.getGame().painter());
					player.getGame().defaultPaintSettings();
				}
			}
		}
	}

	public void use() {
		super.use();
		player.shrink();

		for(int i = 0; i < player.getGame().getPowerUps().size(); i++) {
			Item item = player.getGame().getPowerUps().get(i);

			if(item instanceof ShrinkMachine) {
				if(item != this){
					if (item.using) {
						item.destroy();
					}
				}
			}
		}
	}


	public void updateMovement(){
		super.updateMovement();
		if(isVisible && !pickedUp){
			velX = sizeX(3.92 / 1.86);
			incrX(velX);
			incrY(velY);
			if(getX() > Game.getGameViewWidth()){
				isVisible = false;
				player.getGame().getPowerUps().remove(this);
			}
			if(getX() > Game.getGameViewWidth() - sizeX(630)){
				if(getY() + Game.getGameViewHeight() / 36 < player.getY()){
					velY = -sizeY(1.0);
				}
				if(getY() > player.getY()){
					velY = sizeY(1.0);
				}
			}

		}
	}

}
