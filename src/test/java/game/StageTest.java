package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StageTest {

    @Test
    public void testInstantiation() {
        Stage stage = new Stage();
        assertNotNull(stage);
    }
}
