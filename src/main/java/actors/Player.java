package actors;

import game.Stage;
import java.awt.event.KeyEvent;

public class Player extends Actor {
	
	private boolean up,down,left,right;
	private int score = 0;
	
	public Player(Stage stage) {
		super(stage);

		sprites = new String[]{"player.gif"};
		frame = 0;
		frameSpeed = 35;
		actorSpeed = 10;
		width = 32;
		height = 20;
		posX = Stage.WIDTH/2;
		posY = Stage.HEIGHT/2;
	}

	public void act() {
		super.act();		
	}
	
	protected void updateSpeed() {
		vx = 0;
		vy = 0;
		if (down)
			vy = actorSpeed;
		if (up)
			vy = -actorSpeed;
		if (left)
			vx = -actorSpeed;
		if (right)
			vx = actorSpeed;
		
		//don't allow scrolling off the edge of the screen		
		if (posX - width/2 > 0 && vx < 0)
			posX += vx;
		else if (posX + width  + (width/2)< Stage.WIDTH && vx > 0)
			posX += vx;
		else if (posY - height/2 > 0 && vy < 0)
			posY += vy;
		else if (posY + height + (height/2) < Stage.HEIGHT && vy > 0)
			posY += vy;
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		}
		updateSpeed();
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		///*
		case KeyEvent.VK_UP:
			up = true;
			break;
		//*/
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		///*
		case KeyEvent.VK_DOWN:
			down = true;
			break;
	    //*/
		case KeyEvent.VK_SPACE: 
			fire(); 
			break;

		}
		updateSpeed();
	}

	public void collision(Actor a) {		
		stage.endGame();
	}

	private void fire() {
		Actor shot = new Shot(stage);
		shot.setX(posX);
		shot.setY(posY - shot.getHeight());
		stage.actors.add(shot);
		playSound("photon.wav");
	}

	public void updateScore(int score) {
		this.score += score;
	}

	public int getScore() {
		return score;
	}
}
