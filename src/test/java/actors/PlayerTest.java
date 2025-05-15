package actors;

import game.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

    
}
