package actors;

import game.Stage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InvaderTest {

    @Test
    public void testInstantiation() {
        Stage stage = new Stage();
        Invader invader = new Invader(stage);
        assertNotNull(invader);
    }

    @Test
    public void testPointValue() {
        Stage stage = new Stage();
        Invader invader = new Invader(stage);
        assertEquals(10, invader.getPointValue());
    }

    @Test
    public void testFireAddsShot() {
        Stage stage = new Stage();
        Invader invader = new Invader(stage);
        int before = stage.actors.size();
        invader.fire();
        int after = stage.actors.size();
        assertEquals(before + 1, after);
        assertTrue(stage.actors.get(stage.actors.size() - 1) instanceof InvaderShot);
    }

    @Test
    public void testCollisionWithShotMarksForRemoval() {
        Stage stage = new Stage();
        Invader invader = new Invader(stage);
        Shot shot = new Shot(stage);
        invader.collision(shot);
        assertTrue(invader.isMarkedForRemoval());
    }

    @Test
    public void testCollisionWithInvaderShotDoesNotRemove() {
        Stage stage = new Stage();
        Invader invader = new Invader(stage);
        InvaderShot shot = new InvaderShot(stage);
        invader.collision(shot);
        assertFalse(invader.isMarkedForRemoval());
    }

    @Test
    public void testWallSettersAffectMovementBounds() {
        Stage stage = new Stage();
        Invader invader = new Invader(stage);
        // Set tight bounds around current position
        int initialX = invader.getX();
        invader.setLeftWall(initialX - 10);
        invader.setRightWall(initialX + 10);
        invader.setVx(5); // Set speed to move right
        // Call act 100 times to simulate time % actorSpeed == 0
        for (int i = 0; i < 100; i++) {
            invader.act();
        }
        int newX = invader.getX();
        // Must have moved, but still within bounds
        assertNotEquals(initialX, newX);
        assertTrue(newX >= initialX - 10 && newX <= initialX + 10);
    }
    

    @Test
    public void testActUpdatesStepAndDropsY() {
        Stage stage = new Stage();
        Invader invader = new Invader(stage);
        int initialY = invader.getY();
        // simulate enough steps to trigger vertical drop
        for (int i = 0; i < 1000; i++) {
            invader.act();
        }
        int newY = invader.getY();
        assertEquals(initialY + invader.getHeight(), newY);
    }
}
