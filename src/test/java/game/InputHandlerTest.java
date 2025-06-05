package game;

import actors.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

public class InputHandlerTest {

    private boolean initCalled;
    private boolean gameCalled;
    private boolean keyPressedCalled;
    private boolean keyReleasedCalled;

    private class TestPlayer extends Player {
        public TestPlayer(Stage stage) {
            super(stage);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            keyPressedCalled = true;
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keyReleasedCalled = true;
        }
    }

    private class TestInvaders extends Invaders {
        @Override
        public void initWorld() {
            initCalled = true;
        }

        @Override
        public void game() {
            gameCalled = true;
        }
    }

    private InputHandler handler;
    private TestPlayer player;
    private TestInvaders invaders;
    @Tag("gui")
    @BeforeEach
    public void setUp() {
        keyPressedCalled = false;
        keyReleasedCalled = false;
        initCalled = false;
        gameCalled = false;

        invaders = new TestInvaders() {
            @Override public void initWorld() { initCalled = true; }
            @Override public void game() { gameCalled = true; }
        };

        player = new TestPlayer(invaders);
    }
    @Tag("gui")
    @Test
    public void testHandleKeyPressDelegatesToPlayer() {
        handler = new InputHandler(invaders, player);
        handler.action = InputHandler.Action.PRESS;

        KeyEvent rightKey = new KeyEvent(invaders, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, ' ');
        handler.handleInput(rightKey);

        assertTrue(keyPressedCalled, "Player.keyPressed should be called for movement keys");
    }
    @Tag("gui")
    @Test
    public void testHandleKeyReleaseDelegatesToPlayer() {
        handler = new InputHandler(invaders, player);
        handler.action = InputHandler.Action.RELSEASE;

        KeyEvent leftKey = new KeyEvent(invaders, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, ' ');
        handler.handleInput(leftKey);

        assertTrue(keyReleasedCalled, "Player.keyReleased should be called on key release");
    }
    @Tag("gui")
    @Test
    public void testEnterKeyRestartsGameWhenGameOver() {
        handler = new InputHandler(invaders, player);
        handler.action = InputHandler.Action.PRESS;

        invaders.gameOver = true;

        KeyEvent enterKey = new KeyEvent(invaders, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '\n');
        handler.handleInput(enterKey);

        assertTrue(initCalled, "initWorld should be called on ENTER when game over");
        assertTrue(gameCalled, "game should be restarted on ENTER");
    }
    @Tag("gui")
    @Test
    public void testEnterKeyRestartsGameWhenGameWon() {
        handler = new InputHandler(invaders, player);
        handler.action = InputHandler.Action.PRESS;

        invaders.gameWon = true;

        KeyEvent enterKey = new KeyEvent(invaders, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, '\n');
        handler.handleInput(enterKey);

        assertTrue(initCalled, "initWorld should be called on ENTER when game won");
        assertTrue(gameCalled, "game should be restarted on ENTER");
    }
}
