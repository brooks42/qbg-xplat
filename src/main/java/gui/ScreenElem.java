/*
 * The ScreenElem class represents a single element of the screen GUI; this 
 * could be a button, text area, whatever.
 */
package gui;

import interfaces.HitDetectible;
import interfaces.Renderable;
import interfaces.Updateable;
import java.awt.Rectangle;

/**
 *
 * @author brooks42
 */
public abstract class ScreenElem implements HitDetectible, Renderable, Updateable {

    @Override
    public abstract Rectangle getBounds();

    @Override
    public abstract boolean hittest(int x, int y);

    @Override
    public abstract boolean hittest(HitDetectible hit);

    @Override
    public abstract void calculateHitBox();

    @Override
    public abstract void update(float tpf);

    @Override
    public abstract void render();
}
