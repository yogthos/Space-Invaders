


package integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.event.KeyEvent;

import game.Stage;
import actors.Invader;
import actors.InvaderShot;
import actors.Player;
import actors.Shot;
import actors.Actor;

public class IntegrationTest {

    @Test
    public void testPlayerFiresShotAndHitsInvader() {
        Stage stage = new Stage();
        Player player = new Player(stage);
        Invader invader = new Invader(stage);

        // Align player below invader
        player.setX(100);
        player.setY(300);
        invader.setX(100);
        invader.setY(280);

        stage.actors.add(player);
        stage.actors.add(invader);

        // Fire a shot
        player.keyPressed(new KeyEvent(stage, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, ' '));

        // Simulate shot movement and collision
        for (int i = 0; i < 30; i++) {
            for (Actor actor : stage.actors) {
                actor.act();
            }
            checkCollisions(stage);
        }

        assertTrue(invader.isMarkedForRemoval(), "Invader should be removed after being hit by a shot");
    }

    @Test
    public void testInvaderShotHitsPlayerAndEndsGame() {
        Stage stage = new Stage();
        Player player = new Player(stage);
        InvaderShot shot = new InvaderShot(stage);

        player.setX(100);
        player.setY(100);
        shot.setX(100);
        shot.setY(100); // Same position for guaranteed collision

        stage.actors.add(player);
        stage.actors.add(shot);

        checkCollisions(stage);

        assertTrue(stage.isGameOver(), "Game should end when player is hit by InvaderShot");
    }

    @Test
    public void testInvaderNotRemovedByOwnShot() {
        Stage stage = new Stage();
        Invader invader = new Invader(stage);
        InvaderShot shot = new InvaderShot(stage);

        invader.setX(150);
        invader.setY(100);
        shot.setX(150);
        shot.setY(100); // Overlap

        stage.actors.add(invader);
        stage.actors.add(shot);

        checkCollisions(stage);

        assertFalse(invader.isMarkedForRemoval(), "Invader should not be removed by InvaderShot");
    }

    @Test
    public void testPlayerScoreUpdates() {
        Stage stage = new Stage();
        Player player = new Player(stage);
        assertEquals(0, player.getScore());

        player.updateScore(10);
        assertEquals(10, player.getScore());

        player.updateScore(5);
        assertEquals(15, player.getScore());
    }

    @Test
    public void testShotRemovesItselfOnCollision() {
        Stage stage = new Stage();
        Shot shot = new Shot(stage);
        Player dummy = new Player(stage);

        shot.setX(100);
        shot.setY(100);
        dummy.setX(100);
        dummy.setY(100);

        stage.actors.add(shot);
        stage.actors.add(dummy);

        checkCollisions(stage);

        assertTrue(shot.isMarkedForRemoval(), "Shot should remove itself after collision");
    }

    // Collision checker (copied from logic assumed in Stage)
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

    @Test
    public void testShotHitsInvader() {
        Stage stage = new Stage();

        Invader invader = new Invader(stage);
        invader.setX(100);
        invader.setY(100);
        stage.actors.add(invader);

        Shot shot = new Shot(stage);
        shot.setX(100);
        shot.setY(100);
        stage.actors.add(shot);

        // Simulate game collision detection manually
        if (shot.getBounds().intersects(invader.getBounds())) {
            shot.collision(invader);
            invader.collision(shot);
        }

        assertTrue(invader.isMarkedForRemoval(), "Invader should be marked for removal after collision.");
        assertTrue(shot.isMarkedForRemoval(), "Shot should be marked for removal after collision.");
    }
}

