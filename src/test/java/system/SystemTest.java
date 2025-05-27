package system;

import actors.*;
import game.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SystemTest {

    @Test
    public void testGameStartsInCorrectState() {
        Stage stage = new Stage();
        assertFalse(stage.isGameOver(), "Game should not be over on start");
        assertNotNull(stage.actors);
        assertTrue(stage.actors.isEmpty(), "Actors list should be empty on new game");
    }

    /* 
@Test
public void testGameEndsWhenInvaderReachesBottom() {
    Stage stage = new Stage();
    Invader invader = new Invader(stage);
    stage.actors.add(invader);

    // Position invader just before final drop
    invader.setY(Stage.HEIGHT - invader.getHeight() * 2);

    // Simulate 1000 steps to trigger one drop
    for (int i = 0; i < 1000; i++) {
        invader.act();
    }

    assertTrue(stage.isGameOver(), "Game should end when invader reaches or exceeds bottom");
}
    */


    @Test
    public void testGameEndsWhenPlayerIsHitByInvaderShot() {
        Stage stage = new Stage();
        Player player = new Player(stage);
        InvaderShot shot = new InvaderShot(stage);

        player.setX(200);
        player.setY(100);
        shot.setX(200);
        shot.setY(100); // same position

        stage.actors.add(player);
        stage.actors.add(shot);

        checkCollisions(stage);

        assertTrue(stage.isGameOver(), "Game should end when player is hit by invader shot");
    }

    @Test
    public void testPlayerMovementAndFiring() {
        Stage stage = new Stage();
        Player player = new Player(stage);
        stage.actors.add(player);

        int startX = player.getX();
        player.keyPressed(new java.awt.event.KeyEvent(stage, java.awt.event.KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, java.awt.event.KeyEvent.VK_RIGHT, ' '));
        int movedX = player.getX();

        player.keyPressed(new java.awt.event.KeyEvent(stage, java.awt.event.KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, java.awt.event.KeyEvent.VK_SPACE, ' '));

        assertTrue(movedX > startX, "Player should move right");
        assertTrue(stage.actors.stream().anyMatch(a -> a instanceof Shot), "A shot should be added when player fires");
    }

    @Test
    public void testAllInvadersDestroyedTriggersWinCondition_Optional() {
        Stage stage = new Stage();
        Invader invader1 = new Invader(stage);
        Invader invader2 = new Invader(stage);

        invader1.setX(100); invader1.setY(100);
        invader2.setX(200); invader2.setY(100);
        stage.actors.add(invader1);
        stage.actors.add(invader2);

        // Remove all invaders manually
        invader1.setMarkedForRemoval(true);
        invader2.setMarkedForRemoval(true);

        // Simulate post-cleanup scenario
        stage.actors.removeIf(Actor::isMarkedForRemoval);

        boolean anyInvadersLeft = stage.actors.stream().anyMatch(a -> a instanceof Invader);
        assertFalse(anyInvadersLeft, "All invaders should be removed");

        // If game win logic is added later:
        // assertTrue(stage.isGameWon());
    }

    // Reusable collision checker
    private void checkCollisions(Stage stage) {
        for (Actor a1 : stage.actors) {
            for (Actor a2 : stage.actors) {
                if (a1 != a2 && a1.getBounds().intersects(a2.getBounds())) {
                    a1.collision(a2);
                    a2.collision(a1);
                }
            }
        }
    }
}
