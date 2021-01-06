package utilities;


import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import game.com.airpain.airpain.Game;

public class HitBox  {



	static Game game;

	public static void set(Game game){
		HitBox.game = game;
	}


	public static Rect nullHitBox(){
		return new Rect();
	}

	public static Rect createHitBox(int x, int y, int width, int height){
		Rect rect = new Rect();
		rect.set((int)x, (int)y, (int)(x + width), (int)(y + height));
		return rect;
	}

	public static Rect createHitBox(double x, double y, double width, double height){
		Rect rect = new Rect();
		rect.set((int)x, (int)y, (int)(x + width), (int)(y + height));
		return rect;
	}

	public static Rect createHitBox(Rect rect){
		return new Rect(rect);
	}

	public static void showHitBox(Rect rect, Canvas g){
		if(rect != null){
			game.painter().setStyle(Paint.Style.STROKE);
			g.drawRect(rect, game.painter());
			game.painter().setStyle(Paint.Style.FILL);
		}
	}

}
