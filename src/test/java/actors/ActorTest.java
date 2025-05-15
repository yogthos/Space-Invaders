package actors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import game.Stage;

public class ActorTest {

    @Test
    public void testInstantiation() {
        Stage stage = new Stage();
        Actor actor = new Actor(stage);
        assertNotNull(actor);
    }
}
