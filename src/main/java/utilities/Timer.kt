/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities

import interfaces.Updateable

/**
 * Discussion: at the moment this is completely unused but as the codebase matures I think it'll be a super useful class,
 * I like the idea of using it to build out animations or purely just events, stuff like damage over time etc
 * Need to rethink its usefulness and architecture
 *
 * @author brooks42
 */
class Timer(waitTime: Long) : Updateable {

    // the time to wait before this Timer's tick() method is called
    private val waitTime = 0L

    // the amount of time that has passed so far
    private var passedTime = 0L
    override fun update(tpf: Float) {
        passedTime += tpf.toLong()
        if (passedTime >= waitTime) {
            tick()
        }
    }

    /**
     * Resets this Timer. This is useful for calling during the tick() method,
     * if you'd like the timer to repeat.
     */
    fun reset() {
        passedTime = 0L
    }

    /**
     * This Timer object's tick() method.
     *
     * You should create anonymous Timer classes that override this click, much
     * in the same way you create separate threads (although this will be in the
     * main thread)
     */
    fun tick() { }
}