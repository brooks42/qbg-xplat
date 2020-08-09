/*
 * The Random class contains some good methods for using a single static
 * Random object to generate random numbers. It also allows you to use a 
 * custom seed. 
 * 
 * Basically this is a static wrapper for the java Random class.
 */
package models

import java.util.Random

/**
 *
 * @author brooks42
 */
object Random {
    // the static Random instance
    private var random: Random? = null

    /**
     * Creates this Random object with the passed seed.
     *
     * @param seed
     */
    fun setSeed(seed: Long) {
        random = Random(seed)
    }
}