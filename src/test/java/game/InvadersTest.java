package game;

import actors.Actor;
import actors.Invader;
import actors.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class InvadersTest {

    private Invaders invaders;

    @BeforeEach
    public void setup() {
        // Skip GUI rendering: suppress constructor GUI calls
        // This test will not work in headless CI without proper JavaFX mocking
        invaders = new Invaders() {
            @Override
            public void initWorld() {
                this.actors = new java.util.ArrayList<>();
                this.gameOver = false;
                this.gameWon = false;
            }
        };
    }

    @Test
    @Disabled("Relies on graphics environment")
    public void testInstantiation() {
        assertNotNull(new Invaders());
    }

    @Test
    public void testAddInvadersAddsActors() {
        invaders.addInvaders();
        assertFalse(invaders.actors.isEmpty(), "Invaders should be added to the actor list");
        assertTrue(invaders.actors.get(0) instanceof Invader, "Actors should include Invaders");
    }

    @Test
    public void testUpdateWorldRemovesMarkedActorsAndIncrementsScore() throws Exception {
        // Add dummy invader and mark it for removal
        Invader dummy = new Invader(invaders);
        dummy.setMarkedForRemoval(true);
        invaders.actors.add(dummy);

        // Access private player to check score update
        Field playerField = Invaders.class.getDeclaredField("player");
        playerField.setAccessible(true);
        Player player = new Player(invaders);
        playerField.set(invaders, player);

        int scoreBefore = player.getScore();

        invaders.updateWorld(); // should remove dummy and update score

        int scoreAfter = player.getScore();

        assertTrue(scoreAfter > scoreBefore, "Score should increase after removing actor");
        assertTrue(invaders.actors.isEmpty(), "Removed actor should be gone");
    }

    @Test
    public void testUpdateWorldSetsGameWonWhenNoInvadersRemain() throws Exception {
        // No invaders added = game should be won
        invaders.actors.clear();

        // Inject player
        Field playerField = Invaders.class.getDeclaredField("player");
        playerField.setAccessible(true);
        playerField.set(invaders, new Player(invaders));

        invaders.updateWorld();

        assertTrue(invaders.gameWon, "Game should be won when no invaders remain");
    }
}
