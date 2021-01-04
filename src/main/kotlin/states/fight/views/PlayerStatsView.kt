package desktopkt.states.fight.views

import com.jme3.math.ColorRGBA
import com.simsilica.lemur.Container
import com.simsilica.lemur.TextField
import desktopkt.states.fight.Player

class PlayerStatsView: Container() {

    private val healthText = "Health:"

    private val manaText = "Mana:"

    private val healthDisplay = TextField("$healthText XXXX")

    private val manaDisplay = TextField("$manaText XXXX")

    init {
        healthDisplay.color = ColorRGBA.Red
        manaDisplay.color = ColorRGBA.Blue

        addChild(healthDisplay)
        addChild(manaDisplay)
    }

    fun update(player: Player) {
        healthDisplay.text = "$healthText ${player.health}"
        manaDisplay.text = "$manaText ${player.mana}"

        healthDisplay.updateModelBound()
        manaDisplay.updateModelBound()
    }
}