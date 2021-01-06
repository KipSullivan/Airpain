package helicopters;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import animations.ImageAnimation;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;

public class HomingHelicopter extends Helicopter {

	double velX, velY;
	
	int DAMAGE_AMOUNT = 30;
	
	boolean isVisible = true;
	
	Game game;
	
	//Print p;
	
	ImageAnimation top;
	ImageAnimation back;
	
	Bitmap image;
	
	Bitmap fin1, fin2, fin3,
			fin4, fin5, fin6, fin7, fin8;
	
	public HomingHelicopter(Game game, int x, int y) {
		super(game, PlaneType.HOMINGHELI, x, y, Game.sizeX(40), Game.sizeY(30));

		image = game.getResizedBitmap(game.getBitmap(R.drawable.homerhelicopter), getSizeX(), getSizeY());

		fin1 = game.getResizedBitmap(game.getBitmap(R.drawable.fin1), sizeX(35), sizeY(25));
		fin2 = game.getResizedBitmap(game.getBitmap(R.drawable.fin2), sizeX(35), sizeY(25));
		fin3 = game.getResizedBitmap(game.getBitmap(R.drawable.fin3), sizeX(35), sizeY(25));
		fin4 = game.getResizedBitmap(game.getBitmap(R.drawable.fin4), sizeX(35), sizeY(25));

		fin5 = game.getResizedBitmap(game.getBitmap(R.drawable.fin1), sizeX(25), sizeY(16));
		fin6 = game.getResizedBitmap(game.getBitmap(R.drawable.fin2), sizeX(25), sizeY(16));
		fin7 = game.getResizedBitmap(game.getBitmap(R.drawable.fin3), sizeX(25), sizeY(16));
		fin8 = game.getResizedBitmap(game.getBitmap(R.drawable.fin4), sizeX(25), sizeY(16));

		this.game = game;

		
		top = new ImageAnimation(game, false, x, y, sizeX(35), sizeY(25));
		top.setImages(fin1, fin2, fin3, fin4, 3);
		top.start();
		
		back = new ImageAnimation(game, false, x, y, sizeX(25), sizeY(16));
		back.setImages(fin5, fin6, fin7, fin8, 3);
		back.start();
	}


	public void draw(Canvas g) {
		g.drawBitmap(getBitmap(), (int) getX(), (int) getY(), game.painter());
	}

	@Override
	public void updateMovement() {

		if(!isVisible) {
			back.destroy();
			top.destroy();
			destroy();
		}

		if(isVisible){
			super.updateMovement();
			if(getX() + getSizeX() > Game.getGameViewWidth()){
				top.destroy();
				back.destroy();
				isVisible = false;
				destroy();
			}
			
			if(getHitBox().intersect(game.getPlayerPlane().getHitBox())){
				game.getPlayerPlane().damage(DAMAGE_AMOUNT);
				destroy();
			}
			
			if(getX() < game.getPlayer().getX() && getY() - sizeY(20) < game.getPlayerPlane().getY() && game.getPlayer().isVisible()){
				if(velY < sizeY(2) && game.getPlayer().isVisible()){
					velY += sizeY(.013);
				}
			}
			if(getX() < game.getPlayer().getX() && getY() > game.getPlayerPlane().getY() && game.getPlayer().isVisible()){
				if(velY > -sizeY(2) && game.getPlayer().isVisible()){
					velY -= sizeY(.013);
				}
			}
			top.setX((int) (getX() + sizeX(8)));
			top.setY((int) (getY() - sizeY(5)));
			
			back.setX((int) (getX() - sizeX(6)));
			back.setY((int)(getY() + sizeY(10)));
			
			velX = sizeX(2.5);
			
			incrX(velX);
			incrY(velY);
			
		}
	}

	@Override
	public Bitmap getBitmap() {
		return image;
	}

	@Override
	public void destroy() {
		back.destroy();
		top.destroy();
		game.getHelicopters().remove(this);
	}

	@Override
	public boolean isVisible() {
		return isVisible;
	}


}
