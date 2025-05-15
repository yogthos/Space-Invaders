package game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import static org.junit.jupiter.api.Assertions.*;

public class InvadersTest {
    // Temporary solution to avoid headless environment issues
    @Disabled("Fails in headless environments")
    @Test
    public void testInstantiation() {
        Invaders invaders = new Invaders();
        assertNotNull(invaders);
    }
}
