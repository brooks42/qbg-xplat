/*
 * An interface for things that can be updated, such as AnimatedSprites.
 */
package interfaces;

/**
 *
 * @author brooks42
 */
public interface Updateable {
    
    /**
     * Updates this Updateable thing.
     * 
     * @param tpf the time since the last call to update, in seconds
     */
    public abstract void update(float tpf);
}
