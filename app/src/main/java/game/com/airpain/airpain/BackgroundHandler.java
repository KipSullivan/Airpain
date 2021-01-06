package game.com.airpain.airpain;

/**
 * Created by KipJay on 11/21/2017.
 */

public class BackgroundHandler {


    public static double y = 0;

    public static void setY(double y) {
        BackgroundHandler.y = y;
    }

    public static void incr(Game view, double incr) {
        y += incr;
        for(int i = 0; i < view.backgrounds.length; i++) {
            view.backgrounds[i].y += incr;
        }
    }
}
