package actors;

import game.Stage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UfoTest {

    @Test
    public void testInstantiation() {
        Stage stage = new Stage();
        Ufo ufo = new Ufo(stage);
        assertNotNull(ufo);
    }

    @Test
    public void testPointValue() {
        Stage stage = new Stage();
        Ufo ufo = new Ufo(stage);
        assertEquals(50, ufo.getPointValue());
    }

    @Test
    public void testInitialPositionAndVelocity() {
        Stage stage = new Stage();
        Ufo ufo = new Ufo(stage);
        assertEquals(Stage.WIDTH / 2, ufo.getX());
        assertEquals(Stage.HEIGHT / 2, ufo.getY());
        assertEquals(1, ufo.getVx());
        assertEquals(0, ufo.getVy());
    }

    @Test
    public void testActIncreasesX() {
        Stage stage = new Stage();
        Ufo ufo = new Ufo(stage);
        int initialX = ufo.getX();
        ufo.act();
        int newX = ufo.getX();
        assertEquals(initialX + 1, newX);
    }

    @Test
    public void testMarkedForRemovalWhenOutOfBounds() {
        Stage stage = new Stage();
        Ufo ufo = new Ufo(stage);
        ufo.setX(stage.getWidth());  // exactly at right edge
        ufo.setVx(1);
        ufo.act(); // should move out of bounds and be removed
        assertTrue(ufo.isMarkedForRemoval());
    }

    @Test
    public void testNotMarkedIfStillInBounds() {
        Stage stage = new Stage();
        Ufo ufo = new Ufo(stage);
        ufo.setX(stage.getWidth() - 1); // near the right edge
        ufo.setVx(0); // no movement
        ufo.act();
        assertFalse(ufo.isMarkedForRemoval());
    }

    @Test
    public void testCollisionWithShotRemovesUfo() {
        Stage stage = new Stage();
        Ufo ufo = new Ufo(stage);
        Shot shot = new Shot(stage);
        ufo.collision(shot);
        assertTrue(ufo.isMarkedForRemoval());
    }

    @Test
    public void testCollisionWithOtherActorDoesNotRemoveUfo() {
        Stage stage = new Stage();
        Ufo ufo = new Ufo(stage);
        Invader invader = new Invader(stage);  // not a Shot
        ufo.collision(invader);
        assertFalse(ufo.isMarkedForRemoval());
    }
}
