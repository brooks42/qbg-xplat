package desktopkt.states.fight

class Player(val name: String) {

    var mana: Float = defaultMana

    var units = arrayListOf<Unit>()

    companion object {

        val defaultMana = 10.0f
    }
}