package airplanes;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import projectiles.Torpedo;

public class DoubleTorpedoPlane extends ProjectilePlane {
	double velX, velY;
	final int DAMAGE_AMOUNT = 10;
	double speedBuff;
	boolean hasShot;
	Bitmap image;
	PlayerPlane player;
	
	boolean isSmart;
	public DoubleTorpedoPlane(int x, int y, boolean isVisible, int isSmart, PlayerPlane plane){
		super(PlaneType.DOUBLETORPEDO, x, y, Game.sizeX(86), Game.sizeY(50));
		this.isVisible = isVisible;
		if(isSmart == 1){
			this.isSmart = true;
		}
		else if(isSmart == 0){
			this.isSmart = false;
		}
		else {
			this.isSmart = false;
		}
		hasShot = false;
		player = plane;
		image = game.getResizedBitmap(game.getBitmap(R.drawable.doubletorpedoplane), getSizeX(), getSizeY());
	}
	
	/*
	@Override
	public void useSound(){
		if(usedSound == false){
			try {
				AudioInputStream input = AudioSystem.getAudioInputStream(Resources.class.getResource("AllahuAkbar.wav"));
				Clip clip = AudioSystem.getClip();
				clip.open(input);
				clip.start();
				usedSound = true;
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		}
	}
	*/

	public void draw(Canvas g) {
		super.draw(g);
		g.drawBitmap(getBitmap(), (int) getX(), (int) getY(), game.painter());
	}
	
	@Override
	public void updateMovement() {
		super.updateMovement();
		if(isVisible){
			if(getX() < -96 || getX() > Game.getGameViewWidth()){
				isVisible = false;
			}
			if(getX() <= Game.getGameViewWidth() / 4.26 && hasShot == false && isSmart == false){
				velX = Game.sizeX(4.2 / 1.86) + speedBuff;
				incrX(velX);
			}
			if(isSmart == true){
				velX = Game.sizeX(4.2 / 1.86) + speedBuff;
				incrX(velX);
			}
			if(getX() >= Game.getGameViewWidth() / 4.26 && hasShot == false){
				add(new Torpedo(getX(),getY() - sizeY(20), Game.sizeX(2), Game.sizeY(.6), true, 40,player, this));
				add(new Torpedo(getX(),getY() + sizeY(40), Game.sizeX(2), Game.sizeY(.6), true, 40,player, this));
				hasShot = true;
			}
			if(hasShot && isSmart == false){
				incrX(-(velX));
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
