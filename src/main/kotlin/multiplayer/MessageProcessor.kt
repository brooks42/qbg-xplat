package desktopkt.multiplayer

interface MessageProcessor<in T: Message> {

    fun process(message: T)
}