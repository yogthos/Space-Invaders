package game;

import java.awt.Canvas;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import actors.Actor;

public class Stage extends Canvas implements ImageObserver {

	private static final long serialVersionUID = 1L;
	//public static final int WIDTH = 320;
	//public static final int HEIGHT = 240;
	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;
	public static final int DESIRED_FPS = 50; 
	
	protected boolean gameWon = false;
	protected boolean gameOver = false;
	public List<Actor> actors = new ArrayList<Actor>();	
	
	public Stage() {
	}
	
	public void endGame() {
		gameOver = true;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		return false;
	}	
}
