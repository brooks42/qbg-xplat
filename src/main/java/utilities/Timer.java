/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import interfaces.Updateable;

/**
 *
 * @author brooks42
 */
public class Timer implements Updateable {

    // the time to wait before this Timer's tick() method is called
    private long waitTime = 0l;
    // the amount of time that has passed so far
    private long passedTime = 0l;

    /**
     * Creates a new Timer with the passed waitTime.
     *
     * @param waitTime the time (in milliseconds) before this time will call its
     * tick() method.
     */
    public Timer(long waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public void update(float tpf) {
        passedTime += tpf;
        if (passedTime >= waitTime) {
            tick();
        }
    }
    
    /**
     * Resets this Timer. This is useful for calling during the tick() method, 
     * if you'd like the timer to repeat.
     */
    public void reset(){
        this.passedTime = 0l;
    }
    
    /**
     * This Timer object's tick() method. 
     * 
     * You should create anonymous Timer classes that override this click, much
     * in the same way you create separate threads (although this will be in the
     * main thread)
     */
    public void tick(){
        
    }
}
