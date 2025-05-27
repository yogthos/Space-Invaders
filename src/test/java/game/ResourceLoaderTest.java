package game;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.applet.AudioClip;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import static org.junit.jupiter.api.Assertions.*;

public class ResourceLoaderTest {

    @Test
    public void testGetInstance() {
        ResourceLoader loader = ResourceLoader.getInstance();
        assertNotNull(loader, "ResourceLoader instance should not be null");
    }

    @Test
    public void testLoadExistingImage() {
        BufferedImage sprite = ResourceLoader.getInstance().getSprite("player.gif");
        assertNotNull(sprite, "Existing sprite should load successfully (ensure file exists in res/)");
    }

    @Test
    public void testLoadNonexistentImageReturnsNull() {
        BufferedImage sprite = ResourceLoader.getInstance().getSprite("nonexistent_image.gif");
        assertNull(sprite, "Missing sprite should return null");
    }

    @Test
    @Disabled("refactor code to check for null when fetching sound in resource loader")
    public void testLoadExistingSound() {
        AudioClip clip = ResourceLoader.getInstance().getSound("photon.wav");
        assertNotNull(clip, "Existing sound should load successfully (ensure file exists in res/)");
    }

    @Test
    public void testLoadNonexistentSoundReturnsNull() {
        AudioClip clip = ResourceLoader.getInstance().getSound("nonexistent_sound.wav");
        assertNull(clip, "Missing sound should return null");
    }

    @Test
    public void testCompatibleImageCreation() {
        BufferedImage compatible = ResourceLoader.createCompatible(100, 50, java.awt.Transparency.OPAQUE);
        assertNotNull(compatible, "Compatible image should be created");
        assertEquals(100, compatible.getWidth());
        assertEquals(50, compatible.getHeight());
    }

    @Test
    public void testCleanupDoesNotThrow() {
        ResourceLoader.getInstance().cleanup(); // Should stop all sounds; no assertion needed
    }

    @Test
    public void testImageUpdateReturnsExpected() {
        boolean result = ResourceLoader.getInstance().imageUpdate(null, ImageObserver.ALLBITS, 0, 0, 0, 0);
        assertFalse(result, "imageUpdate should return false when ALLBITS is set");
    }
}
