/*
 * The PSprite object is a Possibility Sprite. It includes all of the fancy,
 * expensive stuff for sprites.
 */
package sprites;

import interfaces.Updateable;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import physics.Body;

/**
 *
 * @author User
 */
public class PSprite {}

//extends Sprite implements Updateable {
//
//    protected float rotation;
//    private Polygon hitbox;
//    // the Body object integrated into this PSprite
//    private Body body;
//    // the last rotation value at which the hit box was calculated
//    protected float last_rot_calc = 0f;
//
//    /**
//     * Creates a new PSprite with the same info as the passed Sprite.
//     *
//     * One convenient way to make a PSprite is to do
//     *
//     * new PSprite(SpriteFactory.getSprite(...));
//     *
//     * @param sprite
//     */
//    public PSprite(Sprite sprite) {
//        super(sprite.getTexture(), sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
//    }
//
//    /**
//     * Sets the alpha for this Sprite to the passed float.
//     *
//     * The float should be between 0 and 1.
//     *
//     * @param alpha
//     */
//    public void setAlpha(float alpha) {
//        this.alpha = alpha;
//    }
//
//    /**
//     * Returns the alpha for this Sprite.
//     */
//    public float getAlpha() {
//        return alpha;
//    }
//
//    /**
//     * Sets the rotation for this PSprite.
//     *
//     * @param rot
//     */
//    public void setRotation(float rot) {
//        this.rotation = rot;
//        calculateHitBox();
//    }
//
//    /**
//     * Returns the rotation for this PSprite.
//     *
//     * @return
//     */
//    public float getRotation() {
//        return rotation;
//    }
//
//    /**
//     * Returns whether or not the passed point (point_x, point_y) is within the
//     * bounds of this image.
//     *
//     * Global hittest, use this if you're not detecting clicks :P
//     *
//     * @param point_x
//     * @param point_y
//     * @return
//     */
//    @Override
//    public boolean hittest(int x, int y) {
//        if (rotation != 0) {
//            return rotatedHitTest(x, y);
//        }
//        return getBounds().contains(x, y);
//    }
//
//    /**
//     * Returns whether or not the passed Rectangle is hit-testing with this
//     * Sprite.
//     *
//     * @param bounds the Rectangle to hittest against
//     * @return
//     */
//    @Override
//    public boolean hittest(Rectangle bounds) {
//        if (this.rotation != 0) {
//            return (rotatedHitTest(bounds.x, bounds.y)
//                    || rotatedHitTest(bounds.x, bounds.y + bounds.height)
//                    || rotatedHitTest(bounds.x + bounds.width, bounds.y + bounds.height)
//                    || rotatedHitTest(bounds.x + bounds.width, bounds.y));
//        }
//        return getBounds().intersects(bounds);
//    }
//
//    /**
//     * Returns whether or not the passed Sprite is hit-testing with this Sprite.
//     *
//     * @param sprite the sprite to test against
//     * @return
//     */
//    public boolean hittest(PSprite sprite) {
//        if (sprite.rotation != 0 || this.rotation != 0) {
//            return rotatedHitTest(sprite);
//        }
//        return getBounds().intersects(sprite.getBounds());
//    }
//
//    /*
//     * Performs complicated hit tests, don't do this unless the Sprite is rotated.
//     */
//    private boolean rotatedHitTest(int x, int y) {
//        if (hitbox == null || last_rot_calc != rotation) {
//            calculateHitBox();
//        }
//
//        if (hitbox.contains(new Point(x, y))) {
//            return true;
//        }
//
//        return false;
//    }
//
//    /*
//     * Performs complicated hit tests, don't do this unless the Sprite is rotated.
//     */
//    private boolean rotatedHitTest(PSprite sprite) {
//        if (hitbox == null || last_rot_calc != rotation) {
//            calculateHitBox();
//        }
//        if (sprite.hitbox == null || sprite.last_rot_calc != sprite.rotation) {
//            sprite.calculateHitBox();
//        }
//
//        PathIterator i = sprite.hitbox.getPathIterator(new AffineTransform());
//        while (!i.isDone()) {
//            double[] xy = new double[2];
//            i.currentSegment(xy);
//            if (hitbox.contains(new Point((int) xy[0], (int) xy[1]))) {
//                return true;
//            }
//            i.next();
//        }
//
//        return false;
//    }
//
//    @Override
//    public Rectangle getBounds() {
//        return new Rectangle((int) x, (int) y, width, height);
//    }
//
//    @Override
//    public void calculateHitBox() {
//        AffineTransform at = AffineTransform.getRotateInstance(
//                Math.toRadians(rotation), getBounds().getCenterX(), getBounds().getCenterY());
//
//        hitbox = new Polygon();
//
//        PathIterator i = getBounds().getPathIterator(at);
//        while (!i.isDone()) {
//            double[] xy = new double[2];
//            i.currentSegment(xy);
//            hitbox.addPoint((int) xy[0], (int) xy[1]);
//            i.next();
//        }
//        this.last_rot_calc = rotation;
//        //System.out.println(getBounds());
//        //System.out.println("rotatedHitTest(" + rotation + "): " + Arrays.toString(hitbox.xpoints) + ", " + Arrays.toString(hitbox.ypoints));
//    }
//
//    /**
//     * Updates this PSprite.
//     *
//     * @param dt
//     */
//    @Override
//    public void update(float tpf) {
//    }
//
//    /**
//     * Renders this PSprite.
//     */
//    @Override
//    public void render() {
//
////        // store the current model matrix
////        glPushMatrix();
////
////        if (alpha != 0) {
////            glColor4f(0f, 0f, 0f, alpha);
////            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
////        }
////
////        // bind to the appropriate texture for this sprite
////        texture.bind();
////
////        if (rotation == 0) {
////            // draw a quad textured to match the sprite
////            glTranslatef(x, y, 0);
////            glBegin(GL_QUADS);
////            {
////                glTexCoord2f(0, 0);
////                glVertex2f(0, 0);
////                glTexCoord2f(texture.getWidth(), 0);
////                glVertex2f(width, 0);
////                glTexCoord2f(texture.getWidth(), texture.getHeight());
////                glVertex2f(width, height);
////                glTexCoord2f(0, texture.getHeight());
////                glVertex2f(0, height);
////            }
////        } else {
////            glTranslatef(x + (width / 2), y + (height / 2), 0);
////            glRotatef(rotation, 0f, 0f, 1f);
////            glBegin(GL_QUADS);
////            {
////                glTexCoord2f(0, 0);
////                glVertex2f(-width / 2, -(height / 2));
////                glTexCoord2f(texture.getWidth(), 0);
////                glVertex2f((width / 2), -(height / 2));
////                glTexCoord2f(texture.getWidth(), texture.getHeight());
////                glVertex2f((width / 2), (height / 2));
////                glTexCoord2f(0, texture.getHeight());
////                glVertex2f(-width / 2, height / 2);
////            }
////        }
////        glEnd();
////
////        if (alpha != 0) {
////            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
////        }
////
////        // restore the model view matrix to prevent contamination
////        glPopMatrix();
//    }
//}
