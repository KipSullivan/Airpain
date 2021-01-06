package game.com.airpain.airpain;

import airplanes.Plane;
import airplanes.ProjectilePlane;
import animations.MovementSequence;
import enemybuffs.EnemyBuff;
import helicopters.Helicopter;
import items.Cloud;
import items.PowerUp;

/**
 * Created by KipJay on 11/2/2016.
 */

public class Updater {


    Game view;

    public Updater(Game view){
        this.view = view;
    }

    public synchronized void updateMovement() {

        if(view.time >= 30) {
            view.player.updateMovement();

            view.updateBackground();

            int totalBullets = 0;
            for (int t = 0; t < view.getPlanes().size(); t++) {
                Plane p = view.getPlanes().get(t);

                if (p instanceof ProjectilePlane) {
                    ProjectilePlane d = (ProjectilePlane) p;
                    totalBullets += d.getProjectiles().size();
                }
                p.updateMovement();
                if (p.closetoPlayer((int) p.getX(), (int) p.getY())) {
                    p.useSound();

                }
            }

            for (int t = 0; t < view.getHelicopters().size(); t++) {
                Helicopter p = view.getHelicopters().get(t);
                p.updateMovement();

            }
            for (int t = 0; t < view.powerUps.size(); t++) {
                PowerUp p = view.powerUps.get(t);
                if (p.isVisible()) {
                    p.updateMovement();
                }
                if (p instanceof Cloud) {
                    Cloud d = (Cloud) p;
                    if (!d.isVisible() && d.isPlaced()) {
                        p.updateMovement();
                    }
                }
            }
            ///
            for (int t = 0; t < view.buffs.size(); t++) {
                EnemyBuff p = view.buffs.get(t);
                if (p.isVisible()) {
                    p.updateMovement();
                }
            }
            for (int t = 0; t < view.animations.size(); t++) {
                MovementSequence p = view.animations.get(t);
                p.onRun();
            }
        }
    }

}
