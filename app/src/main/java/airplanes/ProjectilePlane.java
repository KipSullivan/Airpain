package airplanes;

import android.graphics.Canvas;

import java.util.ArrayList;

import entities.ProjectileCraft;
import projectiles.Projectile;

/**
 * Created by KipJay on 11/14/2017.
 */

public abstract class ProjectilePlane extends Plane implements ProjectileCraft {

    ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    public ProjectilePlane(PlaneType type, int x, int y, int sizeX, int sizeY) {
        super(type, x, y, sizeX, sizeY);
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

    public boolean onScreen() {
        if(super.onScreen()) {
            for(int i = 0; i < getProjectiles().size(); i++) {
                Projectile p = getProjectiles().get(i);
                if(p.onScreen() || super.onScreen()) { ///if just one of the projectiles are on screen
                    return true;
                }
            }
        }
        return false;
    }

    public void kill() {
        isVisible = false;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public void add(Projectile p) {
        projectiles.add(p);
    }

    public void destroy() {
        if(getProjectiles().size() < 1) {
            game.getPlanes().remove(this);
        }
    }


}
