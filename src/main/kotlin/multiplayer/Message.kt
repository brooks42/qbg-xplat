package desktopkt.multiplayer

import java.util.*

open class Message {

    val uid = UUID.randomUUID()

    override fun toString(): String {
        return "Message($uid)"
    }
}