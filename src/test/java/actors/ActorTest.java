package actors;

import game.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.*;

public class ActorTest {

    private Actor actor;
    private Stage stage;

    @BeforeEach
    public void setUp() {
        stage = new Stage();
        actor = new Actor(stage);
        actor.sprites = new String[]{"sprite1.gif", "sprite2.gif"};
        actor.setWidth(10);
        actor.setHeight(20);
        actor.setX(50);
        actor.setY(100);
    }

    @Test
    public void testInstantiation() {
        assertNotNull(actor);
    }

    @Test
    public void testPositionSetAndGet() {
        actor.setX(123);
        actor.setY(456);
        assertEquals(123, actor.getX());
        assertEquals(456, actor.getY());
    }

    @Test
    public void testSizeSetAndGet() {
        actor.setWidth(30);
        actor.setHeight(40);
        assertEquals(30, actor.getWidth());
        assertEquals(40, actor.getHeight());
    }

    @Test
    public void testVelocitySetAndGet() {
        actor.setVx(5);
        actor.setVy(-3);
        assertEquals(5, actor.getVx());
        assertEquals(-3, actor.getVy());
    }

    @Test
    public void testGetBounds() {
        Rectangle bounds = actor.getBounds();
        assertEquals(50, bounds.x);
        assertEquals(100, bounds.y);
        assertEquals(10, bounds.width);
        assertEquals(20, bounds.height);
    }

    @Test
    public void testMarkedForRemovalFlag() {
        assertFalse(actor.isMarkedForRemoval());
        actor.setMarkedForRemoval(true);
        assertTrue(actor.isMarkedForRemoval());
    }

    @Test
    public void testGetPointValue() {
        assertEquals(0, actor.getPointValue());
    }

    @Test
    public void testUpdateFrameCyclesCorrectly() {
        actor.frameSpeed = 1; // force animation update every tick
        actor.frame = 0;
        actor.time = 0;
        actor.sprites = new String[]{"a.gif", "b.gif", "c.gif"};
        actor.updateFrame(); // should go to frame 1
        assertEquals(1, actor.frame);
        actor.updateFrame(); // should go to frame 2
        assertEquals(2, actor.frame);
        actor.updateFrame(); // should wrap to frame 0
        assertEquals(0, actor.frame);
    }

    @Test
    public void testActCallsUpdateFrame() {
        actor.frameSpeed = 1;
        actor.sprites = new String[]{"frame0.gif", "frame1.gif"};
        actor.frame = 0;
        actor.act();
        assertEquals(1, actor.frame);
    }
}
