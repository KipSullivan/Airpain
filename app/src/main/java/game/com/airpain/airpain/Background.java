package game.com.airpain.airpain;


import android.graphics.Bitmap;
import android.graphics.Canvas;


public class Background {

	Bitmap background;

	Game game;

	public double x, y;

	double startY;

	public double x2, y2;

	double velX;

	public Background(int x, int y, Bitmap image, Game view){
		background = image;
		this.x = x;
		this.x2 = (int) -Game.getWindowWidth();
		this.y = y;
		this.startY = y;

		//this.background = image;

		this.game = view;

		velX = Game.sizeX(5);
	}

	public void set() {
		this.x2 = -Game.getWindowWidth();
	}


	public void draw(Canvas g) {

		g.drawBitmap(background, (int) x, (int) y, game.painter());
		g.drawBitmap(background, (int) x2, (int) y2, game.painter());

	}



	public void update(){

		x += velX;
		x2+= velX;

		if(x >= Game.width){
			x = -Game.width;
		}
		if(x2 >= Game.width){
			x2 = -Game.width;
		}
		y2 = y;
	}
}