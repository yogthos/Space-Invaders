package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.Transparency;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import javax.swing.JFrame;
import javax.swing.JPanel;

import actors.Actor;
import actors.Invader;
import actors.Player;
import actors.Shot;
import actors.Ufo;

public class Invaders extends Stage implements KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	private Player player;		
	public long usedTime;//time taken per game step	
	public BufferStrategy strategy;	 //double buffering strategy
	
	private BufferedImage background, backgroundTile; //background cache
	private int backgroundY; //background cache position
	
	public Invaders() {
		//init the UI
		setBounds(0,0,Stage.WIDTH,Stage.HEIGHT);
		setBackground(Color.BLACK);		
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(Stage.WIDTH,Stage.HEIGHT));
		panel.setLayout(null);
		
		panel.add(this);
		
		JFrame frame = new JFrame("Invaders");
		frame.add(panel);
				
		frame.setBounds(0,0,Stage.WIDTH,Stage.HEIGHT);
		frame.setResizable(false);
		frame.setVisible(true);
		
		//cleanup resources on exit
		frame.addWindowListener( new WindowAdapter() {
			          public void windowClosing(WindowEvent e) {
			        	ResourceLoader.getInstance().cleanup();
			            System.exit(0);
			          }
			        }); 
		

		addKeyListener(this);
		
		//create a double buffer
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		requestFocus();
		initWorld();
		
	}
	
	/**
	 * add a grid of invaders based on the screen size
	 */
	public void addInvaders() {
		Invader invader = new Invader(this);
		//padding between units/rows
		int xPad = invader.getWidth() + 15;
		int yPad = invader.getHeight() + 20;
		//number of units per row
		int unitsPerRow = Stage.WIDTH/(xPad) - 1;		
		int rows = (Stage.HEIGHT/yPad) - 3; //number of invader rows
		
		//create and add invaders for each row
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < unitsPerRow -1; j++) {
				Invader inv = new Invader(this);
				inv.setX((j + 1)*xPad);
				inv.setY((i + 1)*yPad);
				inv.setVx(10);
				//set movement boundaries for each invader
				inv.setLeftWall((j + 1)*xPad - 20);
				inv.setRightWall((j + 1)*xPad + 20);
				actors.add(inv);
			}
		}
	}
	
	public void initWorld() {
		actors = new ArrayList<Actor>();
		gameOver = false;
		gameWon = false;
		//add a player
		player = new Player(this);
		player.setX(Stage.WIDTH/2 - player.getWidth()/2);
		player.setY(Stage.HEIGHT - 50);
		player.setVx(10);				
				
		//load cached background
		backgroundTile = ResourceLoader.getInstance().getSprite("space.gif");
		background = ResourceLoader.createCompatible(
		                    WIDTH, HEIGHT+ backgroundTile.getHeight(),
		                    Transparency.OPAQUE);
		Graphics2D g = (Graphics2D)background.getGraphics();
		g.setPaint( new TexturePaint( backgroundTile,new Rectangle(0,0,backgroundTile.getWidth(),backgroundTile.getHeight())));
		g.fillRect(0,0,background.getWidth(),background.getHeight());
		backgroundY = backgroundTile.getHeight();
		
		addInvaders();
	}
	
	public void paintWorld() {
			
		//get the graphics from the buffer
		Graphics g = strategy.getDrawGraphics();
		//init image to background
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		//load subimage from the background
		g.drawImage( background,0,0,Stage.WIDTH,Stage.HEIGHT,0,backgroundY,Stage.WIDTH,backgroundY+Stage.HEIGHT,this);

		//paint the actors
		for (int i = 0; i < actors.size(); i++) {
			Actor actor = actors.get(i);
			actor.paint(g);
		}

		player.paint(g);
		paintScore(g);		
		paintFPS(g);
		//swap buffer
		strategy.show();		
	}
	
	public void paintGameOver() {
		Graphics g = strategy.getDrawGraphics();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		paintScore(g);
		
		//about 310 pixels wide
		g.setFont(new Font("Arial",Font.BOLD,50));
		g.setColor(Color.RED);
		int xPos = getWidth()/2 - 155;		
		g.drawString("GAME OVER",(xPos < 0 ? 0 : xPos),getHeight()/2);
		
		xPos += 30;
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("ENTER: try again",(xPos < 0 ? 0 : xPos),getHeight()/2 + 50);
		
		strategy.show();		
	}
	
	public void paintGameWon() {
		Graphics g = strategy.getDrawGraphics();
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		paintScore(g);
		
		//about 300 pixels wide
		g.setFont(new Font("Arial",Font.BOLD,50));
		g.setColor(Color.RED);
		int xPos = getWidth()/2 - 145;		
		g.drawString("GAME WON",(xPos < 0 ? 0 : xPos),getHeight()/2);
		
		xPos += 20;
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.drawString("ENTER: try again",(xPos < 0 ? 0 : xPos),getHeight()/2 + 50);
		
		strategy.show();		
	}
	
	public void paintFPS(Graphics g) {
		g.setColor(Color.RED);
		if (usedTime > 0) 
			g.drawString(String.valueOf(1000/usedTime)+" fps",0,Stage.HEIGHT-50);
		else
			g.drawString("--- fps",0,Stage.HEIGHT-50);
	}
	
	public void paintScore(Graphics g) {
		g.setFont(new Font("Arial",Font.BOLD,20));
		g.setColor(Color.green);
		g.drawString("Score: ",20,20);
		g.setColor(Color.red);
		g.drawString("" + player.getScore(), 100, 20);
	}
	
	public void paint(Graphics g) {}
	
	public void updateWorld() {		
		
	    int i = 0;
		int numInvaders = 0;
		while (i < actors.size()) {
			Actor actor = actors.get(i);
			if (actor instanceof Shot)
				checkCollision(actor);
			
			if (actor.isMarkedForRemoval()) {
				player.updateScore(actor.getPointValue());
				actors.remove(i);
			}
			else {
				//check how many invaders are remaining
				//0 means player won the match
				if(actor instanceof Invader)					
					numInvaders++;
				actor.act();
				i++;
			}
		}
		if (numInvaders == 0)
			super.gameWon = true;
		
		checkCollision(player);
		player.act();
	}
	
	private void checkCollision(Actor actor) {
		
		Rectangle actorBounds = actor.getBounds();
		for (int i = 0; i < actors.size(); i ++) {
			Actor otherActor = actors.get(i);
			if (null == otherActor || actor.equals(otherActor)) continue;
			if (actorBounds.intersects(otherActor.getBounds())) {
				actor.collision(otherActor);
				otherActor.collision(actor);
			}
		}
	}
	
	public void loopSound(final String name) {
		new Thread(new Runnable() {
			public void run() {
				ResourceLoader.getInstance().getSound(name).loop();
			}
		}).start();
	}

	
	public void game() {
		loopSound("music.wav");		
		usedTime= 0;				
		while(isVisible()) {
			long startTime = System.currentTimeMillis();
			
			backgroundY--;
			if (backgroundY < 0)
				backgroundY = backgroundTile.getHeight();

			if (super.gameOver) { 
				paintGameOver();
				break;
			} 
			else if (super.gameWon) {
				paintGameWon();
				break;
			}
			
			int random = (int)(Math.random()*1000);
			if (random == 700) {
				Actor ufo = new Ufo(this);
				ufo.setX(0);
				ufo.setY(20);
				ufo.setVx(1);
				actors.add(ufo);
			}
			
			updateWorld();
			paintWorld();
			
			usedTime = System.currentTimeMillis() - startTime;
			
			//calculate sleep time
			if (usedTime == 0) usedTime = 1;			
			int timeDiff = (int) ((1000/usedTime) - DESIRED_FPS);
			if (timeDiff > 0) {				
				try {
					Thread.sleep(timeDiff/100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}			
	}
	
	public void keyPressed(KeyEvent e) {	
		InputHandler inputHandler = new InputHandler(this, player);
		inputHandler.event = e;
		inputHandler.action = InputHandler.Action.PRESS;
		inputHandler.start();
	}

	public void keyReleased(KeyEvent e) {
		InputHandler inputHandler = new InputHandler(this, player);
		inputHandler.event = e;
		inputHandler.action = InputHandler.Action.RELSEASE;
		inputHandler.start();
	}

	public void keyTyped(KeyEvent e) {
	}

	public static void main(String[] args) {
		Invaders inv = new Invaders();
		inv.game();
	}
}
