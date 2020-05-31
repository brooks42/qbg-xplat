/*
 * The InlayMenu contains a number of buttons and other UI entities.
 * 
 * The InlayMenu is always drawn on top of the current game state - think of it
 * as a baby GameState without its own custom "screen".
 */
package states;

import interfaces.Renderable;
import interfaces.Updateable;

/**
 *
 * @author brooks42
 */
public abstract class InlayMenu implements Updateable, Renderable {

    /**
     * Called to build this InlayMenu, generating its resources and preparing it
     * for the update/render cycle.
     */
    public abstract void setup();

    /**
     * Called when dismissing this Inlay. Frees its resources.
     */
    public abstract void destroy();

    @Override
    public abstract void update(float tpf);

    /**
     * Renders this InlayMenu
     */
    @Override
    public abstract void render();
}
