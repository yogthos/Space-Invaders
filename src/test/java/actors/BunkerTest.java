package actors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import game.Stage;

public class BunkerTest {

    @Test
    public void testInstantiation() {
        Stage stage = new Stage();
        Bunker bunker = new Bunker(stage);
        assertNotNull(bunker);
    }
}
