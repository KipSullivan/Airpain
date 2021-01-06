package airplanes;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;

import entities.Entity;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import interfaces.SpeedBuffInterface;
import utilities.HitBox;

public abstract class Plane extends Entity implements SpeedBuffInterface {


	public enum PlaneType {

		UNKNOWN(0), SUICIDE(1), TORPEDO(2), DOUBLETORPEDO(3), ROCKET(4), SPEEDBUFF(5),
		INVISIBLE(6), INVISIBLERAPID(7), RAPID(8), HEALTH(9), BLIMP(10),
		PELLET(11), PELLETHELI(12), HOMINGHELI(13);

		public int type;

		PlaneType(int type) {
			this.type = type;
		}

	}

	PlaneType type;

	static Game game;

	private Rect hitBox = HitBox.nullHitBox();

    boolean playedSound = false;

	public boolean isVisible = true;

	public Plane(PlaneType type, int x, int y, int sizeX, int sizeY) {
		super(game, x, y, sizeX, sizeY);
		this.type = type;
	}

	public PlaneType getPlaneType() {
		return type;
	}

	
	public boolean closetoPlayer(int x, int y){
		if(game.getPlayerPlane().isShrunken()){
			if(x > game.getPlayerPlane().getX() - sizeX(20) && x < game.getPlayerPlane().getX() + sizeX(20) 
			&& y < game.getPlayerPlane().getY() + sizeY(50) && y > game.getPlayerPlane().getY()){
				MediaPlayer md = MediaPlayer.create(game.getContext(), R.raw.airplanewizzing);
				md.start();
                playedSound = true;
				return true;
			}
			else {
				return false;
			}
		}
		else if(!game.getPlayerPlane().isShrunken()){
			if(x > game.getPlayerPlane().getX() - sizeX(240) && x < game.getPlayerPlane().getX() + sizeX(240)
			&& y < game.getPlayerPlane().getY() + sizeY(100) && y > game.getPlayerPlane().getY()){
				if(!playedSound) {
					//MediaPlayer md = MediaPlayer.create(game.getContext(), R.raw.boughttadie);
					//md.start();
					playedSound = true;
					return true;
				}
				return true;
			}
			else {
				return false;
			}
		}
		else 
			return false;
	}

    public void updateMovement() {
		hitBox = HitBox.createHitBox((int) getX(), (int) getY(), getSizeX(), getSizeY());
        if(!(this instanceof PlayerPlane) && !playedSound) {
            closetoPlayer((int) getX(), (int) getY());
        }
    }

    public boolean onScreen() {
		if(getY() + getSizeY() > 0 && getY() < Game.getWindowHeight()) {
			return true;
		}
		return false;
	}


    public void draw(Canvas g) {
		if(isVisible) {
			g.drawBitmap(getBitmap(), (int) getX(), (int) getY(), game.painter());

			super.draw(g);
		}
	}

	public Bitmap resizeBitmap(Bitmap map, int sizeX, int sizeY){
		map = game.getResizedBitmap(map, sizeX, sizeY);
		return map;
	}

	public void setBitmap(Bitmap bit1, Bitmap bit2){
		bit1 = bit2;
	}


	public void shrinkBitmap(int sizeX, int sizeY){

	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}


	public Rect getHitBox() {
		return hitBox;
	}

	public void enlargeBitmap(int sizeX, int sizeY){

	}
	public void setBitmap(Bitmap map){}



	public void useSound(){
	}
	
	public void destroy(int target){
		game.getPlanes().remove(target);
	}
	
	public void destroy(Plane plane){
		game.getPlanes().remove(plane);
	}

	public static void set(Game gam){
		game = gam;
	}
}
