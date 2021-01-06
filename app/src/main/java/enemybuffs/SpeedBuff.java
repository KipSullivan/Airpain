package enemybuffs;


import android.graphics.Bitmap;

import airplanes.Plane;
import airplanes.PlayerPlane;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;

public class SpeedBuff extends EnemyBuff {
	double velY;
	Bitmap image;
	PlayerPlane player;

	public SpeedBuff(double x, double y, PlayerPlane player){
		super(player.getGame(), x, y, Game.sizeX(32), Game.sizeY(32));

		isVisible = true;
		this.player = player;
		image = player.getGame().getResizedBitmap(player.getGame().getBitmap(R.drawable.speedbuff), getSizeX(), getSizeY());
	}


	@Override
	public void updateMovement() {

		if(isVisible){
			super.updateMovement();
			velY = Game.getGameViewHeight() / 1028.5;
			incrY(velY);

		}
	}

	public void detectPlanes(){
		for(int t = 0; t < game.getPlanes().size(); t++){
			Plane p = game.getPlanes().get(t);
			if(p.getHitBox().intersect(hitBox)){
				destroy();
				p.setBuff(Game.sizeX(2.8));
			}
		}
	}

	@Override
	public Bitmap getBitmap() {
		return image;
	}


}
