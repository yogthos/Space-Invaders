package actors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import game.Stage;

public class InvaderTest {

    @Test
    public void testInstantiation() {
        Stage stage = new Stage();
        Invader invader = new Invader(stage);
        assertNotNull(invader);
    }
}
