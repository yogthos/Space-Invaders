package actors;

import game.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;
    private Stage stage;

    @BeforeEach
    public void setUp() {
        stage = new Stage();
        player = new Player(stage);
    }

    @Test
    public void testInitialScoreIsZero() {
        assertEquals(0, player.getScore(), "Initial score should be 0");
    }

    @Test
    public void testUpdateScore() {
        player.updateScore(10);
        assertEquals(10, player.getScore());
        player.updateScore(5);
        assertEquals(15, player.getScore());
    }

    @Test
    public void testFireAddsShotToStage() {
        int before = stage.actors.size();
        player.keyPressed(new KeyEvent(stage, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, ' '));
        int after = stage.actors.size();
        assertEquals(before + 1, after);
        assertTrue(stage.actors.get(stage.actors.size() - 1) instanceof Shot);
    }

    @Test
    public void testKeyPressLeftMovesPlayerLeft() {
        int startX = player.getX();
        player.keyPressed(new KeyEvent(stage, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, ' '));
        int afterX = player.getX();
        assertTrue(afterX < startX, "Player should move left");
    }

    @Test
    public void testKeyPressRightMovesPlayerRight() {
        int startX = player.getX();
        player.keyPressed(new KeyEvent(stage, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, ' '));
        int afterX = player.getX();
        assertTrue(afterX > startX, "Player should move right");
    }

    @Test
    public void testKeyPressUpMovesPlayerUp() {
        int startY = player.getY();
        player.keyPressed(new KeyEvent(stage, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, ' '));
        int afterY = player.getY();
        assertTrue(afterY < startY, "Player should move up");
    }

    @Test
    public void testKeyPressDownMovesPlayerDown() {
        int startY = player.getY();
        player.keyPressed(new KeyEvent(stage, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, ' '));
        int afterY = player.getY();
        assertTrue(afterY > startY, "Player should move down");
    }

    @Test
    public void testKeyReleasedStopsMovement() {
        player.keyPressed(new KeyEvent(stage, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, ' '));
        int midX = player.getX();
        player.keyReleased(new KeyEvent(stage, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, ' '));
        player.act(); // should not move after release
        int afterX = player.getX();
        assertEquals(midX, afterX, "Player should stop moving after key released");
    }

    @Test
    public void testCollisionEndsGame() {
        // Simulate collision
        player.collision(new Invader(stage));
        assertTrue(stage.isGameOver(), "Game should end when player collides");
    }

    @Test
    public void testMovementDoesNotGoOutOfBounds() {
        // Move player left beyond boundary
        for (int i = 0; i < 100; i++) {
            player.keyPressed(new KeyEvent(stage, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, ' '));
        }
        assertTrue(player.getX() > 0, "Player should not move out of left boundary");
    }
}
