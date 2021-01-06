package game.com.airpain.airpain;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import airplanes.Plane;
import animations.MovementSequence;
import enemybuffs.EnemyBuff;
import helicopters.Helicopter;
import items.Cloud;
import items.PowerUp;
import utilities.HitBox;

/**
 * Created by KipJay on 11/2/2016.
 */

public class Drawer {

    Game game;

    public Drawer(Game view){
        this.game = view;
    }

    public synchronized void draw(Canvas g) { ////draws everything on the screen

        try {

            if(game.time >= 30) {
                game.drawBackground(g);

                game.getPlayer().getInventory().draw(g);


                if(!game.timer.isDone()) {
                    drawOpeningBackground(g);
                }

                game.painter().setStyle(Paint.Style.FILL_AND_STROKE);

                drawStrings(g);
                drawPlayer(g);

                game.painter().setStyle(Paint.Style.FILL);

                if (game.showHitBoxes()) {
                    HitBox.showHitBox(game.getPlayer().getHitBox(), g);
                }

                for (int t = 0; t < game.getPlanes().size(); t++) {
                    Plane p = game.getPlanes().get(t);

                    if(p.onScreen()) {
                        p.draw(g);
                    }
                    if (game.showHitBoxes()) {
                        HitBox.showHitBox(p.getHitBox(), g);

                    }
                }
                for (int t = 0; t < game.getHelicopters().size(); t++) {
                    Helicopter p = game.getHelicopters().get(t);
                    p.draw(g);
                    if (game.showHitBoxes()) {
                        HitBox.showHitBox(p.getHitBox(), g);

                    }
                }
                for (int t = 0; t < game.powerUps.size(); t++) {
                    PowerUp p = game.powerUps.get(t);
                    if (p.isVisible()) {
                        p.draw(g);
                        if (game.showHitBoxes()) {
                            HitBox.showHitBox(p.getHitBox(), g);
                        }
                    }
                    if (p instanceof Cloud) {
                        Cloud d = (Cloud) p;
                        if (!d.isVisible() && d.isPlaced()) {
                            p.draw(g);
                            HitBox.showHitBox(p.getHitBox(), g);
                            if (game.showHitBoxes()) {
                                HitBox.showHitBox(p.getHitBox(), g);
                            }
                        }
                    }
                }
                ///
                for (int t = 0; t < game.buffs.size(); t++) {
                    EnemyBuff p = game.buffs.get(t);
                    if (p.isVisible()) {
                        p.draw(g);
                        if (game.showHitBoxes()) {
                            HitBox.showHitBox(p.getHitBox(), g);
                        }
                    }
                }
                for (int t = 0; t < game.animations.size(); t++) {
                    MovementSequence p = game.animations.get(t);
                    p.draw(g);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawStrings(Canvas g) {
        game.paint.setColor(Color.BLUE);
        game.painter().setTextSize(Game.sizeX(30));
        if(game.gameStage != null) {
            g.drawText(game.getResources().getString(R.string.stage) + game.gameStage.getRoundNumberString(), (int) Game.sizeX(680.0), Game.sizeY(30), game.painter());
        }
        drawHealth(g);
        drawPowerUp(g);
        drawScore(g);
    }

    private void drawOpeningBackground(Canvas g) {

        ////create rectangle frames for the up and down areas.
        Rect leftRect = HitBox.createHitBox(0,0, game.getGameViewWidth() / 2, game.getGameViewHeight());
        Rect rightRect = HitBox.createHitBox(game.getGameViewWidth() / 2, 0,
                game.getGameViewWidth() / 2, game.getGameViewHeight());
        game.painter().setColor(Color.RED);
        game.painter().setStyle(Paint.Style.STROKE);
        game.painter().setStrokeWidth(10f);
        g.drawRect(leftRect, game.painter());
        g.drawRect(rightRect, game.painter());

        game.defaultPaintSettings();
        game.painter().setTextSize(Game.sizeX(50));
        game.painter().setColor(Color.BLUE);
        g.drawText("" + game.getResources().getString(R.string.down),
                0, (int) (Game.getWindowHeight() / 2), game.painter());
        g.drawText("" + game.getResources().getString(R.string.up),
                Game.getWindowWidth() / 2, (int) (Game.getWindowHeight() / 2), game.painter());

        game.defaultPaintSettings();

        game.painter().setTextSize(Game.getWindowHeight());
        game.painter().setColor(Color.WHITE);
        g.drawText("" + game.timer.secondsLeft(), (Game.getWindowWidth() / 2) - Game.sizeX(140), ///need a buffer
                Game.getWindowHeight(), game.painter());
        game.defaultPaintSettings();
    }

    private void drawHealth(Canvas g){

        String health = game.getResources().getString(R.string.health);

        game.painter().setTextSize(game.sizeX(30));
        game.painter().setColor(Color.RED);

        g.drawText("" + health + game.player.health(),0, game.sizeY(30), game.painter());
    }

    private void drawPowerUp(Canvas g){

        String powerup = game.getResources().getString(R.string.selecteditem);
        game.painter().setTextSize(game.sizeX(30));
        game.painter().setColor(Color.BLACK);
        g.drawText("" + powerup + game.getPlayer().getInventory().getSelectedItemName(), game.sizeX(150), game.sizeY(30), game.painter());

    }

    private void drawScore(Canvas g) {
        String score = game.getResources().getString(R.string.ingamescore);

        game.painter().setTextSize(game.sizeX(30));
        game.painter().setColor(Color.GRAY);

        g.drawText("" + score + game.getCurrentScore(), (int) game.sizeX(680.0), game.sizeY(60), game.painter());
    }

    private void drawPlayer(Canvas g){
        game.player.draw(g);
    }



}
