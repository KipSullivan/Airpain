package items;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import airplanes.Plane;
import airplanes.PlayerPlane;
import game.com.airpain.airpain.BackgroundHandler;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import helicopters.Helicopter;

public class Cloud extends PowerUp {
	
	static ItemID id = ItemID.CLOUD;
	double velX, velY;
	int useOnce;
	boolean isVisible;
	boolean isPlaced;
	boolean canUse;
	double health;

	final static int SECONDS_USED = 60;

	Bitmap image;
	Bitmap big;
	PlayerPlane player;
	
	public Cloud(int x, int y, PlayerPlane player){
		super(id,player, SECONDS_USED, 1, x, y, (int) (Game.getGameViewWidth() / 40), (int) (Game.getGameViewHeight() / 22.5));
		this.player = player;
		isPlaced = false;
		canUse = true;
		useOnce = 0;
		health = 2;
		isVisible = true;
		id = ItemID.CLOUD;
		image = player.getGame().getResizedBitmap(player.getGame().getBitmap(R.drawable.cloud), getSizeX(), getSizeY());
		big = player.getGame().getResizedBitmap(player.getGame().getBitmap(R.drawable.cloud), sizeX(120), sizeY(120));
	}
	
	public ItemID getID() {
		return id;
	}

	public Bitmap getBitmap(){
		return image;
	}

	public Bitmap getBigBitmap(){
		return big;
	}
	
	public boolean isVisible(){
		return isVisible;
	}
	
	public boolean canUse(){
		return canUse;
	}
	
	public boolean isPlaced(){
		return isPlaced;
	}
	
	public void destroyCloud(){
		isPlaced = false;
		isVisible = false;
		player.getGame().getPowerUps().remove(this);
		canUse = false;
	}

	public void draw(Canvas g) {
		if(!isPlaced && !pickedUp) {
			super.draw(g);
		} else if(using){
			g.drawBitmap(big, (int) getX(), (int) getY(), player.getGame().painter());
			g.drawRect(getHitBox(), player.getGame().painter());
			int textSize = 30;
			player.getGame().painter().setTextSize(Game.sizeX(textSize));
			g.drawText("" + health + "HP", (int) getX(), (int) (getY() - Game.sizeY(textSize)), player.getGame().painter());
		}
	}

	public void onEndOfUse() {
		destroy();
	}

	public void onUse() {
		isPlaced = true;
	}

	public void updateMovement(){
		super.updateMovement();
		if(isVisible && !pickedUp){
			if(health < 0){
				destroyCloud();
			}
			velX = Game.sizeX(3.9 / 1.86);
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
		
		
		/// when the cloud is placed in the sky
		if(isPlaced && canUse && pickedUp){
			if(health < 0){
				destroyCloud();
			}
			if(useOnce == 0){
				setX(player.getX() - sizeX(400));
				setY(player.getY() - BackgroundHandler.y);
				useOnce = 1;
			}
			for(int t = 0; t < player.getGame().getPlanes().size(); t++){
				Plane p = player.getGame().getPlanes().get(t);
				if(p.getHitBox().intersect(getHitBox())){
					p.destroy(p);
					health = health - 1;
					//Print.print(health);
					break;
				}
			}
			for(int t = 0; t < player.getGame().getHelicopters().size(); t++){
				Helicopter p = player.getGame().getHelicopters().get(t);
				if(p.getHitBox().intersect(getHitBox())){
					destroyCloud();
					break;
				}
			}
		}
	}
	
	@Override
	public int getSizeX() {
		if(!isPlaced){
			return (int) (Game.getGameViewWidth() / 40);
		}
		else {
			return sizeX(120);
		}
	}

	public int getSizeY() {
		if(!isPlaced){
			return (int) (Game.getGameViewHeight() / 22.5);
		}
		else 
			return sizeY(120);
	}
	

	
}
