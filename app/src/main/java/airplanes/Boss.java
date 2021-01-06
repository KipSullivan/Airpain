package airplanes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import projectiles.Torpedo;
import utilities.HitBox;


public class Boss extends ProjectilePlane {

	double velX, velY;
	boolean isVisible;
	boolean usedSound = false;
	
	int HEALTH;
	Bitmap image;
	PlayerPlane player;
	Rect hitBox = HitBox.nullHitBox();


	public Boss(double x, double y, PlayerPlane player){
		super(PlaneType.UNKNOWN, (int)x, (int) y, (int) (Game.getGameViewWidth() / 5), (int) (Game.getGameViewHeight() / 2.13));
		HEALTH = 200;
		this.player = player;
		this.isVisible = true;

		image = player.getGame().getImage(R.drawable.blimp);
	}

	public int getHealth(){
		return HEALTH;
	}
	public boolean isVisible(){
		return isVisible;
	}
	public void drawBossHealth(Canvas g){
		//g.drawText("HEALTH :" + HEALTH,(int) x + sizeX(40),(int) y + sizeY(90));
	}
	private void damage(int damage){
		HEALTH = HEALTH - damage;
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
	}

	@Override
	public void updateMovement() {
		super.updateMovement();
		if(isVisible){
			if(HEALTH < 0){
				isVisible = false;
			}
			int random = (int) (Math.random() * 890);
			if(getX() < sizeX(300)) {
				velX = sizeX(.75);
			}
			else {
				velX = 0;
			}
			incrX(velX);
			if(getX() >= sizeX(299) && random == 1){
				add(new Torpedo(getX() + sizeX(128), getY() + sizeY(140),true, 30, player, this));
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
		
	}
	@Override
	public void setBuff(double buff) {
		
	}
	@Override
	public double getBuff() {
		return 0;
	}

}
