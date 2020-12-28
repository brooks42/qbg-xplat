package desktopkt.mutliplayer

interface MessageProcessor<T: Message> {

    fun process(message: T)
}