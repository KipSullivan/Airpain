package projectiles;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import airplanes.Plane;
import airplanes.PlayerPlane;
import entities.ProjectileCraft;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import helicopters.Helicopter;

public class ReverseTorpedos extends Projectile {

	double velX, velY;
	int DAMAGE_AMOUNT = 0;
	Game game;
	//Print print;
	Bitmap image;

	private boolean isVisible;
	public ReverseTorpedos(double x, double y, int damage, Game game, ProjectileCraft host){
		super(game, host, x, y, Game.sizeX(10), Game.sizeY(10));
		this.game = game;
		DAMAGE_AMOUNT = damage;
		image = game.getResizedBitmap(game.getBitmap(R.drawable.pellet), sizeX(20), sizeY(20));
		this.isVisible = true;
	}

	public int getDamage(){
		return 15;
	}

	public boolean isVisible(){
		return isVisible;
	}
	public void dispose(){
		isVisible = false;
	}

	public void draw(Canvas g) {
		super.draw(g);
	}

	@Override
	public void updateMovement() {
		if(isVisible){
			super.updateMovement();
			velX = sizeX(-2.25);
			if(getX() - getSizeX() < 0){
				 isVisible = false;
				 destroy();
			}

			for(int i = 0; i < game.getPlanes().size(); i++) {
				Plane plane = game.getPlanes().get(i);
				if(!(plane instanceof PlayerPlane)) {
					if(getHitBox().intersect(plane.getHitBox())) {
						plane.setVisible(false);
						plane.destroy();
						destroy();
					}

					if(plane instanceof ProjectileCraft) {
						for(int t = 0; t < ((ProjectileCraft) plane).getProjectiles().size(); t++) {
							Projectile p = ((ProjectileCraft) plane).getProjectiles().get(t);
							if(p.getHitBox().intersect(getHitBox())) {
								p.destroy();
								destroy();
							}
						}
					}
				}
			}


			for(int i = 0; i < game.getHelicopters().size(); i++) {
				Helicopter plane = game.getHelicopters().get(i);
				if(getHitBox().intersect(plane.getHitBox())) {
					plane.setVisible(false);
					plane.destroy();
					destroy();
				}

				if(plane instanceof ProjectileCraft) {
					for(int t = 0; t < ((ProjectileCraft) plane).getProjectiles().size(); t++) {
						Projectile p = ((ProjectileCraft) plane).getProjectiles().get(t);
						if(p.getHitBox().intersect(getHitBox())) {
							p.destroy();
							destroy();
						}
					}
				}
			}
			incrX(velX);
		}
	}
	@Override
	public Bitmap getBitmap() {
		return image;
	}



}
