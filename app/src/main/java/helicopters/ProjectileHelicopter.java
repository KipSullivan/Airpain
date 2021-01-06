package helicopters;

import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

import entities.ProjectileCraft;
import game.com.airpain.airpain.Game;
import projectiles.Projectile;

/**
 * Created by KipJay on 11/15/2017.
 */

public abstract class ProjectileHelicopter extends Helicopter implements ProjectileCraft {

    ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    public ProjectileHelicopter(Game game, PlaneType type, double x, double y, double sizeX, double sizeY) {
        super(game, type, (int)x, (int) y, (int) sizeX, (int) sizeY);
    }

    public void updateMovement() {
        super.updateMovement();

        if(isVisible() == false) {
            destroy();
        }

        for(int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            if(p.isVisible()) {
                p.updateMovement();
            }
        }
    }

    public void draw(Canvas g) {
        super.draw(g);
        for(int i = 0; i < projectiles.size(); i++) {
            Projectile p = projectiles.get(i);
            if(p.isVisible()) {
                p.draw(g);
            }
        }
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void add(Projectile p) {
        projectiles.add(p);
    }

    public void destroy() {
        Log.d("Running", "trying to kill" + getProjectiles().size());
        if(getProjectiles().size() == 0) {
            game.getHelicopters().remove(this);
        }
    }
}
