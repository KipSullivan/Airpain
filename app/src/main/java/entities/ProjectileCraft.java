package entities;

import java.util.ArrayList;

import projectiles.Projectile;

/**
 * Created by KipJay on 11/15/2017.
 */

public interface ProjectileCraft {
    public ArrayList<Projectile> getProjectiles();
    public void add(Projectile p);
}
