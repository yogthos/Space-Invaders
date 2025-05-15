package integration;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import game.Stage;
import actors.Invader;
import actors.Shot;
import actors.Actor;

public class CollisionIntegrationTest {

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
