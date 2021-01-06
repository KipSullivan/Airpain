package airplanes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.View;

import java.util.ArrayList;

import animations.ImageAnimation;
import animations.TextAnimation;
import game.com.airpain.airpain.BackgroundHandler;
import game.com.airpain.airpain.Game;
import game.com.airpain.airpain.R;
import interfaces.DeathListener;
import inventory.Inventory;
import utilities.HitBox;


public class PlayerPlane extends Plane implements DeathListener, View.OnClickListener {
	
	

	Bitmap plane;
	Bitmap cloakedplane;

	Bitmap healthPack;
	
	ArrayList<Bitmap> smokeBitmaps = new ArrayList<>();

	Rect hitBox = HitBox.nullHitBox();
	
	Game game;
	
	PlayerHandler p;
	
	ImageAnimation smoke;
	TextAnimation addedHealth;

	Inventory inventory;

	double velX, velY = 1;
	double decrease = 0;

	int health = 100;
	public int number_of_inventory_slots = 5;
	
	
	protected boolean isDead = false;
	protected boolean goingDown = true;
	protected boolean movingDown = false;
	protected boolean hasHealthPack = false;
	
	
	protected boolean hasPowerUp = false;
	public boolean isShrunken = false;
	protected boolean hovering = false;
	
	
	protected int hoverTimer = 0;

	protected int DAMAGE_AMOUNT = 0;
	
	
	public PlayerPlane(int x, int y, Game game){
		super(PlaneType.UNKNOWN, x, y, game.sizeX(96), game.sizeY(58));

		healthPack = game.getBitmap(R.drawable.healthpack);

		inventory = new Inventory(game, this, number_of_inventory_slots);

        plane = game.getResizedBitmap(game.getBitmap(R.drawable.blueplane), getSizeX(), getSizeY());
		cloakedplane = game.getResizedBitmap(game.getBitmap(R.drawable.cloakedplane), getSizeX(), getSizeY());

		p = new PlayerHandler(this);
		game.setOnTouchListener(p);
		
		this.game = game;
		//this.boot = boot;
		
		smoke = new ImageAnimation(game, false);
		addedHealth = new TextAnimation(game, 2, getX(), getY() - Game.sizeY(30));
		
		smokeBitmaps.add(game.getResizedBitmap(game.getBitmap(R.drawable.smoke1), sizeX(30), sizeY(20)));
		smokeBitmaps.add(game.getResizedBitmap(game.getBitmap(R.drawable.smoke2), sizeX(30), sizeY(20)));
		smokeBitmaps.add(game.getResizedBitmap(game.getBitmap(R.drawable.smoke3), sizeX(30), sizeY(20)));
		smokeBitmaps.add(game.getResizedBitmap(game.getBitmap(R.drawable.smoke4), sizeX(30), sizeY(20)));
		smokeBitmaps.add(game.getResizedBitmap(game.getBitmap(R.drawable.smoke4), sizeX(30), sizeY(20)));
		
		smoke.setImages(smokeBitmaps, 10);
		
		isVisible = true;
	}

	
	public void draw(Canvas g) {
		if(isVisible) {
			super.draw(g);
		} else {
			g.drawBitmap(cloakedplane, (int) getX(), (int) getY(), getGame().painter());
		}
	}

	@Override
	public void updateMovement() {

		if(isDead == false){
			super.updateMovement();
			hitBox = getHitBox();

			setX(Game.getGameViewWidth() - sizeX(380));
			
			smoke.setX( (int) (getX() + sizeX(90)));
			smoke.setY((int)getY() + sizeY(30));

			if(goingDown){
				decrease += sizeY(.045);
			}

			if(movingDown){
				decrease += sizeY(.087);
			}
			else if(goingDown == false){
				decrease -= sizeY(.045);
			}

			if(hovering && hoverTimer < 300){
				hoverTimer += 1;
				decrease = 0;
			}
			if(hoverTimer >= 300 && hovering){
				hoverTimer = 800;
				hovering = false;
				smoke.stop();
			}
			if(hovering == false && hoverTimer > 0){
				hoverTimer -= 1;
			}


			//incrX(velX);

			checkToIncrBackground();

			checkDying();
			//
			//
		}
	}

	public void checkToIncrBackground() {
		if(game.backgrounds[0].y <= 1 && game.backgrounds[game.backgrounds.length - 1].y > 1) {
			BackgroundHandler.incr(game, -decrease);
		}
		else {
			if(game.backgrounds[0].y >= 1) {    //// if you reach the top
				if (getY() > (game.getWindowHeight() / 2) - (getSizeY() / 2) && decrease > 0) {
					BackgroundHandler.incr(game, -decrease);
					incrY(decrease);
					BackgroundHandler.setY(game.getWindowHeight());
				}
			}
			if(game.backgrounds[game.backgrounds.length - 1].y <= 1) { ////if you reach the bottom of the screen
				if(getY() < (game.getWindowHeight() / 2) - (getSizeY() / 2) && decrease < 0) {
					BackgroundHandler.incr(game, -decrease);
					incrY(decrease);
					BackgroundHandler.setY(-game.getWindowHeight());
				}
			}
			incrY(decrease);

		}
	}

	public Inventory getInventory() {
		return inventory;
	}
	
	public void damage(int amount){
		this.DAMAGE_AMOUNT = amount;
		health = health - DAMAGE_AMOUNT;
		//Print.print(amount);
		startHealthText(amount, false);
		//updateBitmap();
		final MediaPlayer mp = MediaPlayer.create(game.getContext(), R.raw.planehit);
		mp.start();
		//hitSound();
	}
	
	public void heal(int amount){
		startHealthText(amount, true);

		if(health + amount >= 100) {
			health = 100;
			return;
		}

		health += amount;
	}
	///if player was healed, put true for the parameter healed, if not put false
	public void startHealthText(int amount, boolean healed) {
		if(healed) {
			addedHealth.setText("+ " + (amount));
		}
		else {
			addedHealth.setText("- " + (amount));
		}
		addedHealth.setColor(Color.RED);
		addedHealth.setTextSize(Game.sizeY(30));

		if(addedHealth.currentlyRunning()) {
			addedHealth.restart();
		}

		addedHealth.start();
	}

	
	public Game getGame(){
		return game;

	}
	
	public boolean isVisible(){
		return isVisible;
	}
	
	public boolean isShrunken(){
		return isShrunken;
	}
	
	public int health(){
		return health;
	}
	
	public int damageAmount(){
		return DAMAGE_AMOUNT;
	}
	
	public void reset(){
		isDead = false;
		hasHealthPack = false;
		health = 100;
		setX(Game.getGameViewWidth() - sizeX(380));
		setY(Game.getGameViewHeight() / 3.6);
	}
	
	public void giveHealthPack(){
		hasHealthPack = true;
	}
	
	public Bitmap getCloakedPlane(){
		return cloakedplane;
	}
	
	public Bitmap getHealthPackBitmap(){
		return healthPack;
	}
	
	public boolean isDead(){
		return isDead;
	}
	
	public boolean hasHealthPack(){
		return hasHealthPack;
	}
	
	public boolean hasPowerUp(){
		return hasPowerUp;
	}
	
	public boolean offScreen(){
		return(getY() < -Game.getGameViewHeight() / 8.4 || getY() > Game.getGameViewHeight() - 5);
	}


	/*
	public void updateBitmap(){
		if(health > 70 && boot.colorSelect == boot.BLUE){
			plane = Resources.getBitmap("blueplane");
		}
		if(health <= 70 && health > 50 && boot.colorSelect == boot.BLUE){
			plane = Resources.getBitmap("brokenstage1");
		}
		if(health <= 50 && health > 20 && boot.colorSelect == boot.BLUE){
			plane = Resources.getBitmap("brokenstage2");
		}
		if(health <= 20 && boot.colorSelect == boot.BLUE){
			plane = Resources.getBitmap("brokenstage3");
		}
		if(health > 70 && boot.colorSelect == boot.RED){
			plane = Resources.getBitmap("redplane");
		}
		if(health <= 70 && health > 50 && boot.colorSelect == boot.RED){
			plane = Resources.getBitmap("redbrokenstage1");
		}
		if(health <= 50 && health > 20 && boot.colorSelect == boot.RED){
			plane = Resources.getBitmap("redbrokenstage2");
		}
		if(health <= 20 && boot.colorSelect == boot.RED){
			plane = Resources.getBitmap("redbrokenstage3");
		}
		if(health > 70 && boot.colorSelect == boot.PURPLE){
			plane = Resources.getBitmap("purpleplane");
		}
		if(health <= 70 && health > 50 && boot.colorSelect == boot.PURPLE){
			plane = Resources.getBitmap("purpbrokenstage1");
		}
		if(health <= 50 && health > 20 && boot.colorSelect == boot.PURPLE){
			plane = Resources.getBitmap("purpbrokenstage2");
		}
		if(health <= 20 && boot.colorSelect == boot.PURPLE){
			plane = Resources.getBitmap("purpbrokenstage3");
		}
	}
	*/

	public void shrink() {
		setSizeX(sizeX(20));
		setSizeY(sizeY(20));
		updateImageSize();
		isShrunken = true;
	}

	public void enlarge() {
		setSizeX(sizeX(96));
		setSizeY(sizeY(70));
		updateImageSize();
		isShrunken = false;
	}

	private void updateImageSize() {
		plane = game.getResizedBitmap(game.getBitmap(R.drawable.blueplane), getSizeX(), getSizeY());
	}

	public Bitmap getBitmap() {
		return plane;
	}
	
	@Override
	public void destroy() {
		isDead = true;
	}
	
	@Override
	public void setBuff(double buff) {
	}
	
	@Override
	public double getBuff() {
		return 0;
	}
	
	@Override
	public void checkDying() {
		if(health <= 0 || offScreen()){
			isDead = true;
			game.quit();
		}
		else {
			isDead = false;
		}
	}

	public PlayerHandler playerHandler(){
		return p;
	}

	@Override
	public void onClick(View v) {

	}
}
