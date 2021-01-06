package airplanes;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import game.com.airpain.airpain.Game;
import utilities.HitBox;

public class PlayerHandler implements View.OnTouchListener {

	PlayerPlane p;

	public boolean holding = false;
	public boolean tapped = false;
	public long time = 0;

	public int taps = 0;

	public int test = 0;

	public PlayerHandler(PlayerPlane player) {
		this.p = player;
	}

	/*
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_LEFT || code == KeyEvent.VK_RIGHT){
			if(p.hoverTimer == 0){
				p.hovering = true;
				
				p.smoke.sizeX(p.sizeX(30));
				p.smoke.sizeY(p.sizeY(20));
				
				p.smoke.start();
			}
		}
		if(code == KeyEvent.VK_UP && p.goingDown){
			p.goingDown = false;
			p.movingDown = false;
			p.decrease = -Window.getWindowHeight() / 720;
		}
		if(code == KeyEvent.VK_DOWN){
			p.movingDown = true;
		}
		if(code == KeyEvent.VK_ESCAPE){
			p.quit = 0;
		}
		if(code == KeyEvent.VK_SPACE){
			if(p.hasHealthPack){
				p.heal(30);
				p.hasHealthPack = false;
			}
		}
		if(code == KeyEvent.VK_SHIFT){
			if(p.hasPowerUp && p.usingPowerUp == false && p.isVisible && p.isShrunken == false){
				p.powerUpID = p.storedID;
				p.usingPowerUp = true;
				p.hasPowerUp = false;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if(code == KeyEvent.VK_UP){
			p.goingDown = true;
			p.decrease = -Window.getWindowHeight() / 720;
		}
		if(code == KeyEvent.VK_ESCAPE){
			if(p.quit == 0){
				if(p.game.isPaused() == false){
					p.quit = 1;
					p.game.stop();
					Print.print("GameLaunch is paused");
				}
			}
		}
		
		if(code == KeyEvent.VK_F){
			if(fpsShown()){
				p.game.toggleFPS(false);
			}
			else {
				p.game.toggleFPS(true);
			}
		}
		
		if(code == KeyEvent.VK_H){
			if(hitBoxesShown()){
				p.game.toggleHitBoxes(false);
			}
			else {
				p.game.toggleHitBoxes(true);
			}
		}
		
		if(code == KeyEvent.VK_DOWN){
			p.movingDown = false;
		}
	}
	*/

	
	private boolean fpsShown(){
		if(p.game.showFPS){
			return true;
		}
		else {
			return false;
		}
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {

		Rect hitBox = HitBox.createHitBox(event.getX(), event.getY(), Game.sizeX(10), Game.sizeY(10));
		////hitbox of the most recent player tap/touch

		switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:

				p.inventory.selectItem(hitBox);

				if ((System.currentTimeMillis() - time) <= 230) {
					p.inventory.useSelectedItem();
				}

				holding = true;

				if(hitBox.left > Game.getWindowWidth() / 2) {
					moveUp();
				}
				if(hitBox.left <= Game.getWindowWidth() / 2) {
					moveDown();
				}

				tapped = true;
				time = System.currentTimeMillis();

				break;
			case MotionEvent.ACTION_UP:

				if(!p.movingDown) {
					holding = false;
					p.goingDown = true;
					p.decrease = -Game.sizeY(0.5);
				}

				if(p.movingDown) {
					p.movingDown = false;
				}


				break;

			}

		return holding;
	}

	public void moveUp() {
		if(p.goingDown) {
			p.goingDown = false;
			p.movingDown = false;

			p.decrease = -p.sizeY(.015);
		}
	}

	public void moveDown() {
		p.movingDown = true;
	}



}
