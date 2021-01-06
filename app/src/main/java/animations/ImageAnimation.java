package animations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.ArrayList;
import game.com.airpain.airpain.Game;

public class ImageAnimation extends MovementSequence {

	Game game;

	//Print p;

	ArrayList<Bitmap> images = new ArrayList<>();

	Bitmap currentImage;

	long breakTime;

	int x, y, sizeX = 50, sizeY = 50;

	int timer = 0;

	int index = 0;

	boolean currentlyAnimating = false;

	boolean canAnimate = true;

	boolean quitAfterRunOnce;

	public ImageAnimation(Game game, boolean quitAfterRunOnce, int x, int y, int sizeX, int sizeY){
		super(game);
		this.game = game;

		this.x = x;
		this.y = y;

		this.sizeX = sizeX;
		this.sizeY = sizeY;


		this.quitAfterRunOnce = quitAfterRunOnce;
	}

	public ImageAnimation(Game game, boolean quitAfterRunOnce, int x, int y){
		super(game);
		this.game = game;

		this.x = x;
		this.y = y;

		game.getAnimations().add(this);

		this.quitAfterRunOnce = quitAfterRunOnce;
	}

	public ImageAnimation(Game game, boolean quitAfterRunOnce){
		super(game);
		this.game = game;

		game.getAnimations().add(this);

		this.quitAfterRunOnce = quitAfterRunOnce;
	}

	public void setImages(ArrayList<Bitmap> images, long breakTime){

		for(int t = 0; t < images.size(); t++){
			if(images.get(t) != null){
				this.images.add(images.get(t));
				currentImage = images.get(0);
			}
		}
		this.breakTime = breakTime;
	}

	public void setImages(Bitmap i, Bitmap i2, Bitmap i3, Bitmap i4, Bitmap i5, long breakTime){
		if(i != null && i2 != null && i3 != null && i4 != null){
			currentImage = i;
			images.add(i);
			images.add(i2);
			images.add(i3);
			images.add(i4);
			images.add(i5);
			images.add(i5);
			this.breakTime = breakTime;
		}
	}

	public void setImages(Bitmap i, Bitmap i2, Bitmap i3, Bitmap i4, long breakTime){
		if(i != null && i2 != null && i3 != null && i4 != null){
			currentImage = i;
			images.add(i);
			images.add(i2);
			images.add(i3);
			images.add(i4);
			images.add(i4);
			this.breakTime = breakTime;
		}
	}

	public void setImages(Bitmap[] array, long breakTime) {

		for(int i = 0; i < array.length; i++) {
			if(array[i] != null) {
				images.add(array[i]);
			}
		}
		currentImage = images.get(0);
		this.breakTime = breakTime;
	}



	public void setImages(Bitmap i, Bitmap i2, Bitmap i3, long breakTime){
		if(i != null && i2 != null && i3 != null){
			currentImage = i;
			images.add(i);
			images.add(i2);
			images.add(i3);
			images.add(i3);
			this.breakTime = breakTime;
		}
	}

	public void setImages(Bitmap i, Bitmap i2, long breakTime){
		if(i != null && i2 != null){
			currentImage = i;
			images.add(i);
			images.add(i2);
			images.add(i2);
			this.breakTime = breakTime;
		}
	}

	public void setImage(Bitmap i, long breakTime){
		if(i != null){
			currentImage = i;
			images.add(i);
			images.add(i);
			this.breakTime = breakTime;
		}
	}

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void sizeX(int x){
		this.sizeX = x;
	}

	public void sizeY(int y){
		this.sizeY = y;
	}

	public void optionalStart() {  ///this is different than start as it doesn't add this animation to the animation arraylist in
									///GameView
		currentlyAnimating = true;
		timer = 0;
		index = 0;
	}

	public void optionalStop() {
		currentlyAnimating = false;
		currentImage = images.get(0);
		index = 0;
	}


	@Override
	public void start() {
		super.start();
		currentlyAnimating = true;
		timer = 0;
		index = 0;
	}

	@Override
	public void stop() {
		super.stop();
		currentlyAnimating = false;
		currentImage = images.get(0);
		index = 0;
	}


	@Override
	public void draw(Canvas g){
		if(currentlyAnimating && canAnimate){
			g.drawBitmap(currentImage, (int)x,(int) y, game.painter());
		}
	}
	
	public void onRun(){
		if(currentlyAnimating && canAnimate){
			if(quitAfterRunOnce){
				
				if(timer == breakTime){
					for(int t = 0; t < images.size();){
						if(index + 1 >= images.size()){
							currentlyAnimating = false;
							stop();
						}
							
						
						index++;
						currentImage = images.get(index);
						timer = 0;
						
						break;
					}
				}
				else {
					timer++;
				}
				
			}
			
			else {
		
				
				if(timer == breakTime){
					for(int t = 0; t < images.size();){
						if(index + 1 >= images.size()){
							index = 0;
						}
						index++;
						currentImage = images.get(index);
						timer = 0;
						break;
						
					}
				}
				else {
					timer ++;
				}
				
			}
		}
	}
	
	public boolean currentlyRunning(){
		return currentlyAnimating;
	}
	
	@Override
	public double runsPerSecond(){
		/*
		int delay = (int) ThreadHandler.getDelay();
		double speed = (1000.0/delay) / breakTime;
		return speed;
		*/
		return 2;
	}
	
}
