package buttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import utilities.HitBox;

/**
 * Created by KipJay on 12/19/2016.
 */

public class LButton {

    int x, y, sizeX, sizeY;

    int textSize;

    boolean showOutline = false;

    String text = "";

    Context context;

    Bitmap image;

    Bitmap normalImage;
    Bitmap pressedImage;

    Rect size;

    public LButton(int x, int y, int sizeY, Bitmap image, Context context) {

        this.x = x;
        this.y = y;
        this.sizeX = 30;
        this.sizeY = sizeY;

        this.image = image;
        this.normalImage = image;
        this.pressedImage = image;

        size = HitBox.createHitBox(x,y,sizeX,sizeY);

        this.context = context;
    }

    public LButton(int x, int y, int sizeY, Bitmap image, Bitmap pressedImage, Context context) {

        this.x = x;
        this.y = y;
        this.sizeX = 30;
        this.sizeY = sizeY;

        this.image = image;
        this.normalImage = image;
        this.pressedImage = pressedImage;

        size = HitBox.createHitBox(x,y,sizeX,sizeY);

        this.context = context;
    }

    public void setPressedImage(Bitmap image) {
        this.pressedImage = image;
    }

    public void setNormalImage(Bitmap image) {
        this.normalImage = image;
    }

    public void resize() {

        double dividen = text.length() / 14;

        double m = (text.length() + dividen);

        int n = (int) (m * (sizeY / 2));

        sizeX = (int) (m * (sizeY / 2) + (sizeY / 2));

        image = getResizedBitmap(image, sizeX, sizeY);
        normalImage = getResizedBitmap(normalImage, sizeX, sizeY);
        pressedImage = getResizedBitmap(pressedImage, sizeX, sizeY);


        this.size = HitBox.createHitBox(x, y, image.getWidth(), image.getHeight());

        textSize = image.getHeight();
    }

    public void setText(String text) {

        this.text = text;

        resize();
    }

    public void showOutline(boolean toggle) {
        this.showOutline = toggle;
    }

    public void draw(Canvas g, Paint painter) {

        this.size = HitBox.createHitBox(x, y, image.getWidth(), textSize);

        g.drawBitmap(image, x, y, painter);

        painter.setTextSize(textSize);
        painter.setColor(Color.BLACK);
        g.drawText(text, x, (y + image.getHeight()) - (image.getHeight() / 6), painter);


        if(showOutline) {
            painter.setColor(Color.BLACK);

            HitBox.showHitBox(size, g);
        }
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getNormalImage() {
        return normalImage;
    }

    public Bitmap getPressedImage() {
        return pressedImage;
    }

    public Rect getHitBox() {
        return size;
    }

    public Bitmap getBitmap(Context context, int id){
        return BitmapFactory.decodeResource(context.getResources(), id);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        if(newHeight > 0 && newWidth > 0) {
            Bitmap resized = Bitmap.createScaledBitmap(bm, newHeight, newWidth, false);
            return resized;
        }
        else
            return bm;

    }
}
