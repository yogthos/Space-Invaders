package game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ResourceLoaderTest {

    @Test
    public void testGetInstance() {
        ResourceLoader loader = ResourceLoader.getInstance();
        assertNotNull(loader);
    }
}
