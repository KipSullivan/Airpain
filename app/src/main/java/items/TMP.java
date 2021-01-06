package items;

import android.graphics.Bitmap;

import java.util.ArrayList;

import entities.ProjectileCraft;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import projectiles.Projectile;
import projectiles.ReverseTorpedos;

/**
 * Created by gsmki on 12/31/2017.
 */

public class TMP extends Gun implements ProjectileCraft {

    Bitmap image;

    double velX = 0;
    double velY = 0;

    boolean isVisible = true;

    static int initial_amo = 30;

    static int damage = 5;

    public TMP(Game game, double x, double y) {
        super(game, ItemID.TMP, initial_amo, damage,  x, y, Game.sizeX(64), Game.sizeY(64));
        image = game.getResizedBitmap(game.getBitmap(R.drawable.tmp), getSizeX(), getSizeY());
    }

    @Override
    public Bitmap getBitmap() {
        return image;
    }

    public void updateMovement() {
        super.updateMovement();

        if(!pickedUp) {
            velX = Game.sizeX(2.14);
            incrX(velX);
            incrY(velY);
            if(getX() > Game.getGameViewWidth() - sizeX(630)){
                if(getY() + Game.getGameViewHeight() / 36 < player.getY()){
                    velY = -sizeY(1.5);
                }
                if(getY() > player.getY()){
                    velY = sizeY(1.5);
                }
            }
        }



    }

    public void addBullets() {
        add(new ReverseTorpedos(game.getPlayer().getX() - getSizeX(), game.getPlayer().getY(),
                damage, game, this));
    }

    @Override
    public boolean isVisible() {
        return isVisible;
    }

    @Override
    public void onUse() {
        super.onUse();

    }

    public void onEndOfUse() {
        destroy();
    }


    @Override
    public ArrayList<Projectile> getProjectiles() {
        return bullets;
    }

    @Override
    public void add(Projectile p) {
        bullets.add(p);
    }
}
