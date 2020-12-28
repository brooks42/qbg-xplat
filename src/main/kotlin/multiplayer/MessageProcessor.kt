package desktopkt.multiplayer

interface MessageProcessor<T: Message> {

    fun process(message: T)
}