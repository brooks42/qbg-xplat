/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites;

import com.jme3.texture.Texture;
import utilities.ImageManager;

/**
 *
 * @author User
 */
public class SpriteFactory {

    /**
     * Returns a Sprite based on the Image of the passed ID and the passed (x,y)
     *
     * @param ID
     * @param x
     * @param y
     * @return
     */
    public static Sprite getSprite(String ID, int x, int y, int x2, int y2) {
        try {
            return new Sprite(ImageManager.getImage(ID), x, y, x2, y2);
        } catch (NullPointerException e) {
            System.out.println("ImageManager cannot find image: " + ID);
            return null;
        }
    }

    /**
     * Returns a Sprite based on the Image of the passed ID and the passed (x,y)
     *
     * @param x
     * @param y
     * @return
     */
    public static Sprite getSprite(Texture text, int x, int y, int x2, int y2) {
        return new Sprite(text, x, y, x2, y2);
    }
    
    /**
     * Returns a Sprite based on the Image of the passed ID and the passed (x,y)
     *
     * @param ID
     * @param x
     * @param y
     * @return
     */
    /*public static Sprite getSprite(String ID, Color color, int x, int y, int x2, int y2) {
     try {
     return new Sprite(getImage(ID, color), x, y, x2, y2);
     } catch (NullPointerException e) {
     System.out.println("ImageManager.getSprite() Cannot find image: " + ID);
     return null;
     }
     }*/
}
