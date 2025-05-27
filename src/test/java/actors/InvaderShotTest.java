package actors;

import game.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InvaderShotTest {

    @Test
    public void testInstantiation() {
        Stage stage = new Stage();
        InvaderShot shot = new InvaderShot(stage);
        assertNotNull(shot);
        assertEquals(1, shot.bulletSpeed);
        assertFalse(shot.up);
    }

    @Test
    public void testShotMovesDown() {
        Stage stage = new Stage();
        InvaderShot shot = new InvaderShot(stage);
        shot.setY(50);
        int initialY = shot.getY();
        shot.act();
        int newY = shot.getY();
        assertEquals(initialY + 1, newY, "InvaderShot should move down with speed 1");
    }

    @Test
    public void testCollisionWithInvaderIgnored() {
        Stage stage = new Stage();
        InvaderShot shot = new InvaderShot(stage);
        Invader invader = new Invader(stage);
        shot.collision(invader);
        assertFalse(shot.isMarkedForRemoval(), "InvaderShot should not be removed when hitting an Invader");
    }

    @Test
    public void testCollisionWithOtherActorMarksForRemoval() {
        Stage stage = new Stage();
        InvaderShot shot = new InvaderShot(stage);
        Player player = new Player(stage);
        shot.collision(player);
        assertTrue(shot.isMarkedForRemoval(), "InvaderShot should be removed when hitting a non-invader actor");
    }

    

    @Test
    public void testShotNotRemovedWhenInBounds() {
        Stage stage = new Stage();
        InvaderShot shot = new InvaderShot(stage);
        shot.setY(100);
        shot.act();
        assertFalse(shot.isMarkedForRemoval(), "InvaderShot should stay in game when within bounds");
    }
}
