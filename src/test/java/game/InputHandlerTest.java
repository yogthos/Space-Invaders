package game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;
import actors.Player;

public class InputHandlerTest {
    // Temporary solution to avoid headless environment issues
    @Disabled("Fails in headless environments")
    @Test
    public void testInstantiation() {
        Stage stage = new Stage();
        Player player = new Player(stage);
        Invaders invaders = new Invaders();
        InputHandler handler = new InputHandler(invaders, player);
        assertNotNull(handler);
    }
}
