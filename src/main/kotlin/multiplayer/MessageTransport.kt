package desktopkt.multiplayer

interface MessageTransport {

    fun send(message: Message)
}