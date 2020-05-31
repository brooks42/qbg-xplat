/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import states.GameStateController;

/**
 *
 * @author brooks42
 */
public class GameLoop {

    private static long last_loop_milli = 0;

    /**
     * Begins the game loop, loading resources.
     */
    public static void begin() {
        GameStateController.getInstance();
        last_loop_milli = System.currentTimeMillis();
    }

    /**
     * Updates this GameLoop.
     */
    public static void update() {
        long time_diff = last_loop_milli;
        last_loop_milli = System.currentTimeMillis();
        // update with the passed number of milliseconds
        GameStateController.mouseInput();
        GameStateController.update(last_loop_milli - time_diff);
    }

    /**
     * Renders the screen.
     */
    public static void render() {
        GameStateController.render();
    }

    /**
     * Ends the game.
     */
    public static void end() {
    }
}
