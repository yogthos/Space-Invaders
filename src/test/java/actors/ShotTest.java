package actors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import game.Stage;

public class ShotTest {

    @Test
    public void testInstantiation() {
        Stage stage = new Stage();
        Shot shot = new Shot(stage);
        assertNotNull(shot);
    }
}
