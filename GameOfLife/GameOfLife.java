 

import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class GameOfLife {

    private static Game game;

    public static void main(String[] args) {
        initDisplay();
        initGL();

        initGame();

        gameLoop();
        cleanUp();
    }

    private static void initGame() {
        game = new Game();
    }

    private static void getInput() {
        game.getInput();
    }

    private static void update() {
        game.update();
    }

    private static void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        glLoadIdentity();

        game.render();

        Display.update();
        Display.sync(20);
    }

    private static void gameLoop() {
        while (!Display.isCloseRequested()) {
            update();
            getInput();
            render();
        }
    }

    private static void initGL() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
        glMatrixMode(GL_MODELVIEW);

        glClearColor(0.75f, 0.75f, 0.75f, 1);

        glDisable(GL_DEPTH_TEST);
    }

    private static void cleanUp() {
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
    }

    private static void initDisplay() {
        try {
            Display.setDisplayMode(new DisplayMode(960, 960));
            Display.create();
            Display.setVSyncEnabled(true);
            Keyboard.create();
            Mouse.create();
        } catch (LWJGLException ex) {
            Logger.getLogger(GameOfLife.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}