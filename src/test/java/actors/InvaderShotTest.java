package actors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import game.Stage;

public class InvaderShotTest {

    @Test
    public void testInstantiation() {
        Stage stage = new Stage();
        InvaderShot shot = new InvaderShot(stage);
        assertNotNull(shot);
    }
}
