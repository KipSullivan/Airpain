package helicopters;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import animations.ImageAnimation;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import projectiles.Pellet;

public class PelletHelicopter extends ProjectileHelicopter {

	double velX, velY;
	
	int DAMAGE_AMOUNT = 15;
	
	boolean hasShot = false;
	
	Game game;
	
	//Print p;
	
	ImageAnimation top;
	ImageAnimation back;
	
	Bitmap image;
	
	Bitmap fin1, fin2, fin3,
			fin4, fin5, fin6, fin7, fin8 ;
	
	public PelletHelicopter(Game game, int x, int y) {
		super(game, PlaneType.PELLETHELI, x, y, Game.sizeX(40), Game.sizeY(30));
		
		this.game = game;


		image = game.getResizedBitmap(game.getBitmap(R.drawable.pellethelicopter), getSizeX(), getSizeY());

		fin1 = game.getResizedBitmap(game.getBitmap(R.drawable.fin1), sizeX(35), sizeY(25));
		fin2 = game.getResizedBitmap(game.getBitmap(R.drawable.fin2), sizeX(35), sizeY(25));
		fin3 = game.getResizedBitmap(game.getBitmap(R.drawable.fin3), sizeX(35), sizeY(25));
		fin4 = game.getResizedBitmap(game.getBitmap(R.drawable.fin4), sizeX(35), sizeY(25));

		fin5 = game.getResizedBitmap(game.getBitmap(R.drawable.fin1), sizeX(25), sizeY(16));
		fin6 = game.getResizedBitmap(game.getBitmap(R.drawable.fin2), sizeX(25), sizeY(16));
		fin7 = game.getResizedBitmap(game.getBitmap(R.drawable.fin3), sizeX(25), sizeY(16));
		fin8 = game.getResizedBitmap(game.getBitmap(R.drawable.fin4), sizeX(25), sizeY(16));

		top = new ImageAnimation(game, false, x, y, sizeX(35), sizeY(25));
		top.setImages(fin1, fin2, fin3, fin4, 3);
		top.start();

		back = new ImageAnimation(game, false, x, y, sizeX(25), sizeY(16));
		back.setImages(fin5, fin6, fin7, fin8, 3);
		back.start();
	}

	public void draw(Canvas g) {
		super.draw(g);
	}
	
	@Override
	public void updateMovement() {
		super.updateMovement();

		if(!isVisible()) {
			Log.d("Running", "WOOHOO BEEN HIT 2 " + getProjectiles().size());
			back.destroy();
			top.destroy();
		}

		if(isVisible()){
			if(getX() + getSizeX() > Game.getGameViewWidth()){
				top.destroy();
				back.destroy();
				isVisible = false;
			}
			
			if(getHitBox().intersect(game.getPlayerPlane().getHitBox())){
				game.getPlayerPlane().damage(DAMAGE_AMOUNT);
				back.destroy();
				top.destroy();
				isVisible = false;
			}
			
			if(getX() < game.getPlayer().getX() && getY() - sizeY(20) < game.getPlayerPlane().getY() && game.getPlayer().isVisible()){
				if(velY < sizeY(1.5) && game.getPlayer().isVisible()){
					velY += sizeY(.013);
				}
			}
			if(getX() < game.getPlayer().getX() && getY() > game.getPlayerPlane().getY() && game.getPlayer().isVisible()){
				if(velY > -sizeY(1.5) && game.getPlayer().isVisible()){
					velY -= sizeY(.013);
				}
			}
			
			
			if(getX() > sizeX(200) && !hasShot){
				
				add(new Pellet(getX() + sizeX(20), getY() + sizeY(20), game.getPlayer(), this));
				add(new Pellet(getX() + sizeX(20), getY() + sizeY(20), game.getPlayer(), this));
				add(new Pellet(getX() + sizeX(20), getY() + sizeY(20), game.getPlayer(), this));
				add(new Pellet(getX() + sizeX(20), getY() + sizeY(20), game.getPlayer(), this));
				add(new Pellet(getX() + sizeX(20), getY() + sizeY(20), game.getPlayer(), this));
				add(new Pellet(getX() + sizeX(20), getY() + sizeY(20), game.getPlayer(), this));
				add(new Pellet(getX() + sizeX(20), getY() + sizeY(20), game.getPlayer(), this));
				add(new Pellet(getX() + sizeX(20), getY() + sizeY(20), game.getPlayer(), this));
				hasShot = true;
			}

			top.setX((int) (getX() + sizeX(8)));
			top.setY((int) (getY() - sizeY(5)));
			
			back.setX((int) (getX() - sizeX(6)));
			back.setY((int)(getY() + sizeY(10)));
			
			velX = sizeX(2);

			incrX(velX);
			incrY(velY);
			
		}
	}

	public void destroy() {
		back.destroy();
		top.destroy();
		super.destroy();
	}

	@Override
	public Bitmap getBitmap() {
		return image;
	}




}
