/*
 * The PButton class represents a Button object made of up to 3 sprites: a 
 * default, a mouse-over and a depressed.
 * 
 */
package gui;

import com.jme3.texture.Texture;
import interfaces.HitDetectible;
import java.awt.Rectangle;
import org.lwjgl.input.Mouse;
import sounds.Sounds;
import sprites.PSprite;
import utilities.Settings;

/**
 *
 * @author brooks42
 */
public class PButton extends ScreenElem {
    
    public static final int LEFT_MOUSE_BUTTON = 0;
    public static final int DEFAULT = 0;
    public static final int HOVER = 1;
    public static final int PRESSED = 2;
    // the Sprite for this Button
    private PSprite sprite;
    private Texture[] textures;
    private boolean hovering = false;
    private boolean mouse_was_down = false;

    /**
     * Creates a new PButton object.
     */
    public PButton(PSprite sprite, Texture[] textures) {
        this.sprite = sprite;
        this.textures = textures;
    }

    /**
     *
     * @return
     */
    @Override
    public Rectangle getBounds() {
        return sprite.getBounds();
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    @Override
    public boolean hittest(int x, int y) {
        return sprite.hittest(x, y);
    }

    /**
     *
     * @param hit
     * @return
     */
    @Override
    public boolean hittest(HitDetectible hit) {
        return sprite.hittest(hit);
    }

    /**
     *
     */
    @Override
    public void calculateHitBox() {
        sprite.calculateHitBox();
    }

    @Override
    public void update(float tpf) {
        sprite.update(tpf);

        // now do detection events
        if (hittest(Mouse.getX(), Settings.SCREEN_HEIGHT - Mouse.getY())) {
            // if the mouse is inside of the button
            // if it's not hovering, mark it as hovering
            if (!hovering) {
                hovering = true;
                onButtonHovered();
                // now check if the mouse is also pressed. 
                /*if (Mouse.isButtonDown(LEFT_MOUSE_BUTTON)) {
                 mouse_was_down = true;
                 }*/
            } else {
                // if it's pressed and the mouse wasn't down, do press
                if (Mouse.isButtonDown(LEFT_MOUSE_BUTTON) && !mouse_was_down) {
                    this.mouse_was_down = true;
                    onButtonPressed();
                    // else if the mouse is down and the mouse was down
                }
            }
            
            if (!Mouse.isButtonDown(LEFT_MOUSE_BUTTON)) {
                if (mouse_was_down) {
                    onButtonClicked();
                }
                mouse_was_down = false;
            }
        }
    }

    /**
     *
     */
    @Override
    public void render() {
        sprite.render();
    }

    /**
     * Called when the button is first pressed.
     *
     * By default, moves the button to its PRESSED texture.
     */
    public void onButtonPressed() {
        sprite.setTexture(textures[PRESSED]);
    }

    /**
     * Called when the button is first hovered over. By default this will move
     * the button's texture to its HOVERED texture.
     *
     * Override this method to add functionality to a button
     */
    public void onButtonHovered() {
        sprite.setTexture(textures[HOVER]);
    }

    /**
     * Called when the button is released after having been clicked. This method
     * is only called if the button is released inside of the button (ie if the
     * user presses, drags away and then releases, it will not call this
     * method).
     *
     * By default this will return the button to its DEFAULT state. Override
     * this method to add functionality to clicks.
     */
    public void onButtonClicked() {
        sprite.setTexture(textures[DEFAULT]);
        Sounds.playSoundEffect("click");
    }
}
