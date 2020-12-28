package desktopkt.multiplayer

class Server: MessageTransport {

    private val registeredProcessors = mutableMapOf<Class<*>, MessageProcessor<Message>>()

    fun <T: Message> registerProcessor(processor: MessageProcessor<T>, messageType: Class<T>) {
        registeredProcessors[messageType] = processor as MessageProcessor<Message>
    }

    override fun send(message: Message) {
        print("sending message $message")

        // TODO: for now this just acts similarly to a single-player server, any messages that get sent immediately get bounced back for testing
        serverDidReceive(message)
    }

    fun <T: Message> serverDidReceive(message: T) {
        registeredProcessors[message.javaClass]?.let {
            it.process(message)
        }
    }
}