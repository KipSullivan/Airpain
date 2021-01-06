package airplanes;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;

public class InvisiblePlane extends Plane {
	double velX, velY;
	double speedBuff;
	private final int DAMAGE_AMOUNT = 35;
	boolean isVisible;
	boolean usedSound = false;
	int hasBitmap;
	Bitmap image;
	PlayerPlane player;
	public InvisiblePlane(int x, int y, boolean isVisible, PlayerPlane player){
		super(PlaneType.INVISIBLE, x, y, Game.sizeX(96), Game.sizeY(58));
		this.player = player;
		hasBitmap = 0;
		this.isVisible = isVisible;
		image = game.getResizedBitmap(game.getBitmap(R.drawable.invisibleplane), getSizeX(), getSizeY());
	}

	
	public boolean isVisible(){
		return isVisible;
	}


	/*
	@Override
	public void useSound(){
		if(usedSound == false){
			try {
				Print.print("Playing allahu");
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
		if(hasBitmap == 0 || hasBitmap == 2) {
			g.drawBitmap(getBitmap(), (int) getX(), (int) getY(), game.painter());
		}
	}

	@Override
	public void updateMovement() {
		super.updateMovement();
		if(isVisible){

			velX = Game.sizeX(3.36 / 1.86) + speedBuff;
			incrX(velX);
			if(getX() > Game.getGameViewWidth() / 10.6 && hasBitmap == 0){
				image = player.getGame().noBitmap();
				hasBitmap = 1;
			}
			if(getX() >= sizeX(800.0) - sizeX(96) && hasBitmap == 1){
				hasBitmap = 2;
				image = game.getResizedBitmap(game.getBitmap(R.drawable.invisibleplane), getSizeX(), getSizeY());
			}
			if(getHitBox().intersect(player.getHitBox())){
				destroy();
				player.damage(DAMAGE_AMOUNT);
			}
			if(getX() > Game.getGameViewWidth()){
				destroy();
			}

		}
	}

	@Override
	public Bitmap getBitmap() {
		return image;
	}

	@Override
	public void destroy() {
		isVisible = false;
		player.getGame().getPlanes().remove(this);
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
