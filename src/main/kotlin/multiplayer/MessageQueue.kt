package desktopkt.multiplayer

class MessageQueue {

    private val list = mutableListOf<Message>()

    fun enqueue(message: Message) {
        list.add(message)
    }

    fun dequeue(): Message? {
        if (list.isNotEmpty()) {
            return list.removeAt(0)
        }
        return null
    }
}