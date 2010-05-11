package actors;

import game.Stage;

public class Ufo extends Actor {
	
	private static final int POINT_VALUE = 50;
	
	public Ufo(Stage stage) {		
		super(stage);		
		sprites = new String[]{"ufo0.gif","ufo1.gif","ufo2.gif","ufo3.gif","ufo4.gif"};
		frameSpeed = 100;
		width = 30;
		height = 17;
		posX = Stage.WIDTH/2;
		posY = Stage.HEIGHT/2;
		setVx(1);
		setVy(0);
	}
	
	public void act() {
		super.act();
		updateXSpeed();
		updateYSpeed();
	}
		
	private void updateXSpeed() {
		posX += getVx();
		if (posX > stage.getWidth()) setMarkedForRemoval(true);		
	}
	
	private void updateYSpeed() {
		
	}	
	
	public void collision(Actor a) {
		if (a instanceof Shot)
			setMarkedForRemoval(true);
	}
	
	public int getPointValue() {
		return Ufo.POINT_VALUE;
	}
	
}
