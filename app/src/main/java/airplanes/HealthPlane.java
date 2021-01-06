package airplanes;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import animations.ImageAnimation;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;

public class HealthPlane extends Plane {
	double velY;
	double velX;
	double speedBuff;
	boolean isVisible;

	Bitmap[] images;

	ImageAnimation background;

	int gapX = Game.sizeX(5);
	int gapY = Game.sizeY(5);

	PlayerPlane player;
	Bitmap image;
	public HealthPlane(int x, int y, PlayerPlane player){
		super(PlaneType.HEALTH, x, y, Game.sizeX(96), Game.sizeY(58));

		this.player = player;
		isVisible = true;
		image = game.getResizedBitmap(game.getBitmap(R.drawable.healthplane), getSizeX(), getSizeY());

		background = new ImageAnimation(game, false, 0,0, (int) getSizeX(), (int) getSizeY());

		images = new Bitmap[] {
				game.getResizedBitmap(game.getBitmap(R.drawable.itembackground1),
						getSizeX() + (gapX), getSizeY() + (gapY)),
				game.getResizedBitmap(game.getBitmap(R.drawable.itembackground2),
						getSizeX() + (gapX), getSizeY() + (gapY)),
				game.getResizedBitmap(game.getBitmap(R.drawable.itembackground3),
						getSizeX() + (gapX), getSizeY() + (gapY)),
				game.getResizedBitmap(game.getBitmap(R.drawable.itembackground4),
						getSizeX() + (gapX), getSizeY() + (gapY)),
				game.getResizedBitmap(game.getBitmap(R.drawable.itembackground5),
						getSizeX() + (gapX), getSizeY() + (gapY)),
				game.getResizedBitmap(game.getBitmap(R.drawable.itembackground6),
						getSizeX() + (gapX), getSizeY() + (gapY))
		};

		background.setImages(images, 8);
		background.optionalStart();
	}

	public boolean isVisible(){
		return isVisible;
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
		background.draw(g);
		g.drawBitmap(getBitmap(), (int) getX(), (int) getY(), game.painter());
	}

	@Override
	public void updateMovement() {
		super.updateMovement();
		if(isVisible){
			background.setX((int) getX());
			background.setY((int) getY());
			background.onRun();

			if(getHitBox().intersect(player.getHitBox())){
				if(!player.hasHealthPack()){
					player.giveHealthPack();
				}

				player.heal(40);

				destroy();
			}
			velX = sizeX(5.6 / 1.86) + speedBuff;
			incrX(velX);
			incrY(velY);
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
		this.background.optionalStop();
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
