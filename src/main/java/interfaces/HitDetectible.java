/*
 * 
 */
package interfaces;

import java.awt.Rectangle;

/**
 *
 * @author brooks42
 */
public interface HitDetectible {
    
    /**
     * Returns the Bounds of this HitDetectible.
     *
     * @return
     */
    public abstract Rectangle getBounds();

    /**
     * Returns whether or not the passed (x, y) is within the
     * bounds of this HitDetectible.
     *
     * @param point_x
     * @param point_y
     * @return
     */
    public abstract boolean hittest(int x, int y);

    /**
     * Returns whether or not the passed HitDetectible is hitting this one.
     *
     * @param hit the opposing HitDetectible
     * @return
     */
    public abstract boolean hittest(HitDetectible hit) ;

    /*
     * 
     */
    public abstract void calculateHitBox();
}
