package actors;

import game.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShotTest {

    @Test
    public void testInstantiation() {
        Stage stage = new Stage();
        Shot shot = new Shot(stage);
        assertNotNull(shot);
        assertEquals(10, shot.getWidth());
        assertEquals(15, shot.getHeight());
        assertTrue(shot.getY() >= 0);
    }

    @Test
    public void testShotMovesUpWhenUpTrue() {
        Stage stage = new Stage();
        Shot shot = new Shot(stage);
        shot.setY(100);
        int startY = shot.getY();
        shot.act();
        int newY = shot.getY();
        assertEquals(startY - shot.bulletSpeed, newY);
    }

    @Test
    public void testShotMovesDownWhenUpFalse() {
        Stage stage = new Stage();
        Shot shot = new Shot(stage);
        shot.up = false;
        shot.setY(100);
        int startY = shot.getY();
        shot.act();
        int newY = shot.getY();
        assertEquals(startY + shot.bulletSpeed, newY);
    }

    @Test
    public void testShotRemovedWhenOutOfTopBound() {
        Stage stage = new Stage();
        Shot shot = new Shot(stage);
        shot.setY(1);
        shot.act();  // will go below 0
        assertTrue(shot.isMarkedForRemoval());
    }

    @Test
    public void testCollisionMarksForRemoval() {
        Stage stage = new Stage();
        Shot shot = new Shot(stage);
        Actor dummy = new Ufo(stage);  // any actor works
        shot.collision(dummy);
        assertTrue(shot.isMarkedForRemoval());
    }

    @Test
    public void testShotNotRemovedWhenInBounds() {
        Stage stage = new Stage();
        Shot shot = new Shot(stage);
        shot.setY(100);
        shot.act();
        assertFalse(shot.isMarkedForRemoval());
    }
}
