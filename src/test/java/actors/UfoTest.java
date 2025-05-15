package actors;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import game.Stage;

public class UfoTest {

    @Test
    public void testInstantiation() {
        Stage stage = new Stage();
        Ufo ufo = new Ufo(stage);
        assertNotNull(ufo);
    }
}
