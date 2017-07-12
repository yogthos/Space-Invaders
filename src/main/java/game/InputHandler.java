package game;

import java.awt.event.KeyEvent;

import actors.Player;

/**
 * creates a thread to process player input
 * @author ghast
 *
 */
public class InputHandler {

	private Invaders invaders = null;
	private Player player  = null;
	public Action action;

	public InputHandler(Invaders invaders, Player player) {
		this.invaders = invaders;
		this.player = player;
	}

	public void handleInput(KeyEvent event) {
		if (action == Action.PRESS) {
			if (KeyEvent.VK_ENTER == event.getKeyCode()) {
				if (invaders.gameOver || invaders.gameWon) {
					invaders.initWorld();
					invaders.game();
				}
			}

			else
				player.keyPressed(event);
		}
		else if (action == Action.RELSEASE)
			player.keyReleased(event);
	}

	public enum Action {
		PRESS,
		RELSEASE
	}
}
