package items;

import android.graphics.Canvas;

import java.util.ArrayList;

import game.com.airpain.airpain.Game;
import projectiles.Projectile;
import utilities.HitBox;

/**
 * Created by gsmki on 12/31/2017.
 */

public abstract class Gun extends PowerUp {


    ArrayList<Projectile> bullets = new ArrayList<>();

    int damage = 5;

    public Gun(Game game, ItemID id, int amo, int damage, double x, double y, double sizeX, double sizeY) {
        super(id, game.getPlayer(), -1, amo, x, y, sizeX, sizeY);
        this.damage = damage;
    }

    public ArrayList<Projectile> getBullets() {
        return bullets;
    }

    public int getDamage() {
        return damage;
    }

    public void updateMovement() {

        if(!pickedUp) {
            background.setX((int) getX() - (gapX / 2));
            background.setY((int) getY() - (gapY / 2));
            background.onRun();
        }
        if(!pickedUp) {
            hitBox = HitBox.createHitBox(getX(), getY(), getSizeX(), getSizeY());
        }
        if(getX() > Game.getGameViewWidth()){
            player.getGame().getPowerUps().remove(this);
        }
        if(hitBox.intersect(player.getHitBox())) {
            if(player.getInventory().addItemToInventory(this)) {
                pickedUp = true;
                hitBox = HitBox.nullHitBox();
            }
        }

        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).updateMovement();
        }

        if(uses <= 0) {
            destroy();
        }
    }

    public void destroy() {
        if(bullets.size() <= 0) {
            super.destroy();
        }
    }

    public int getAmo() {
        return uses;
    }

    public void incrAmo(int incr) {
        incrUses(incr);
    }

    public void draw(Canvas g) {
        super.draw(g);
        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }
    }

    public abstract void addBullets();

    public void onUse() {
        addBullets();
    }

    public void use() {
        if(uses > 0) {
            uses -= 1;
            onUse();
        }
    }

    public void onEndOfUse() {
        super.onEndOfUse();
    }

}
