/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package models

import com.jme3.math.Vector2f

/**
 *
 * @author brooks42
 */
// TODO: pretty sure this doesn't do anything novel, investigate removing soon
object MathUtil {
    /**
     *
     * @param p1
     * @param p2
     * @return
     */
    fun makeNormalForPoints(p1: Vector2f, p2: Vector2f): Vector2f {
        val diffY = p2.y - p1.y
        val diffX = p2.x - p1.x
        return Vector2f(-diffY / diffX, 1F)
    }

    /**
     *
     * @param vector
     * @param angleRadian
     */
    fun rotateAroundZ(vector: Vector2f, angleRadian: Float) {
        val cosAngle = Math.cos(angleRadian.toDouble())
        val sinAngle = Math.sin(angleRadian.toDouble())
        val savecValue = vector.x
        vector.x = (vector.x * cosAngle - vector.y * sinAngle).toFloat()
        vector.y = (savecValue * sinAngle + vector.y * cosAngle).toFloat()
    }

    /**
     *
     * @param degres
     * @return
     */
    fun degresToRadians(degres: Float): Float {
        return (degres * 2 * Math.PI / 360).toFloat()
    }

    /**
     *
     * @param radians
     * @return
     */
    fun radiansToDegres(radians: Float): Float {
        return (360 * radians / (2 * Math.PI)).toFloat()
    }
}