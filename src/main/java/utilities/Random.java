/*
 * The Random class contains some good methods for using a single static
 * Random object to generate random numbers. It also allows you to use a 
 * custom seed. 
 * 
 * Basically this is a static wrapper for the java Random class.
 */
package utilities;

/**
 *
 * @author brooks42
 */
public class Random {
    
    // the static Random instance
    private static java.util.Random random;
    
    /**
     * Creates this Random object with the passed seed.
     * 
     * @param seed 
     */
    public static void setSeed(long seed){
        random = new java.util.Random(seed);
    }
}
