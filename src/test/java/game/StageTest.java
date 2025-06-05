
package game;

import actors.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Image;

import static org.junit.jupiter.api.Assertions.*;

public class StageTest {

    private Stage stage;

    @BeforeEach
    public void setUp() {
        stage = new Stage();
    }

    @Test
    public void testStageInitialState() {
        assertFalse(stage.isGameOver(), "Game should not be over when stage starts");
        assertNotNull(stage.actors, "Stage should initialize actor list");
        assertTrue(stage.actors.isEmpty(), "Actor list should be empty at start");
    }

    @Test
    public void testEndGameSetsGameOver() {
        assertFalse(stage.isGameOver());
        stage.endGame();
        assertTrue(stage.isGameOver(), "endGame should set gameOver to true");
    }

    @Test
    public void testAddActor() {
        Actor dummy = new Actor(stage) {
            // dummy concrete subclass to instantiate Actor
        };
        stage.actors.add(dummy);
        assertTrue(stage.actors.contains(dummy), "Actor should be added to stage");
    }

    @Test
    public void testImageUpdateReturnsFalse() {
        Image img = null;
        boolean result = stage.imageUpdate(img, 0, 0, 0, 0, 0);
        assertFalse(result, "imageUpdate should return false");
    }

    @Test
    public void testStageDimensionsAreCorrect() {
        assertEquals(640, Stage.WIDTH);
        assertEquals(480, Stage.HEIGHT);
        assertEquals(50, Stage.DESIRED_FPS);
    }
}


