package helicopters;


import airplanes.Plane;
import game.com.airpain.airpain.Game;
import interfaces.SpeedBuffInterface;

public abstract class Helicopter extends Plane implements SpeedBuffInterface {
	
	//Print p;
	
	Game game;

	
	public Helicopter(Game game, PlaneType type, int x, int y, int sizeX, int sizeY) {
		super(type, x, y, sizeX, sizeY);
		this.game = game;
	}


	
	public boolean closetoPlayer(int x, int y){
		if(game.getPlayerPlane().isShrunken()){
			if(x > game.getPlayerPlane().getX() - sizeX(20) && x < game.getPlayerPlane().getX() + sizeX(20)
			&& y < game.getPlayerPlane().getY() + sizeY(50) && y > game.getPlayerPlane().getY()){
				return true;
			}
			
			else {
				return false;
			}
		}
		else if(!game.getPlayerPlane().isShrunken()){
			if(x > game.getPlayerPlane().getX() - sizeX(120) && x < game.getPlayerPlane().getX() + sizeX(120) 
			&& y < game.getPlayerPlane().getY() + sizeY(20) && y > game.getPlayerPlane().getY()){
				return true;
			}
			else {
				return false;
			}
		}
		else 
			return false;
	}
	
	
	public void useSound(){
	}
	
	public void setBuff(double buff){
		
	}

	public double getBuff(){
		return 0;
	}
	
	public void destroy(int target){
		game.getPlanes().remove(target);
	}
	
	public void destroy(Plane plane){
		game.getPlanes().remove(plane);
	}
}
