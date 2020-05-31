/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import com.jme3.math.Vector2f;

/**
 *
 * @author brooks42
 */
// TODO: pretty sure this doesn't do anything novel, investigate removing soon
public class MathUtil {

    /**
     * 
     * @param p1
     * @param p2
     * @return 
     */
    public static Vector2f makeNormalForPoints(Vector2f p1, Vector2f p2) {
        float diffY = p2.y - p1.y;
        float diffX = p2.x - p1.x;
        return new Vector2f(-diffY / diffX, 1);
    }

    /**
     * 
     * @param vector
     * @param angleRadian 
     */
    public static void rotateAroundZ(Vector2f vector, float angleRadian) {

        double cosAngle = Math.cos(angleRadian);
        double sinAngle = Math.sin(angleRadian);
        float savecValue = vector.x;
        vector.x = (float) (vector.x * cosAngle - vector.y * sinAngle);
        vector.y = (float) (savecValue * sinAngle + vector.y * cosAngle);

    }

    /**
     * 
     * @param degres
     * @return 
     */
    public static float degresToRadians(float degres) {
        return (float) (degres * 2 * Math.PI / 360);
    }

    /**
     * 
     * @param radians
     * @return 
     */
    public static float radiansToDegres(float radians) {
        return (float) (360 * radians / (2 * Math.PI));
    }
}
