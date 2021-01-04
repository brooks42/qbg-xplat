package desktopkt.states.fight.views

import com.jme3.math.Vector2f
import com.simsilica.lemur.*
import com.simsilica.lemur.component.BoxLayout
import com.simsilica.lemur.component.IconComponent
import desktopkt.states.fight.UnitFactory
import desktopkt.states.fight.UnitType

class UnitSummonView: Container() {

    private val buttonList = mutableListOf<Button>()

    init {
        val boxLayout = BoxLayout(Axis.X, FillMode.None)
        layout = boxLayout
    }

    fun addButton(unitType: UnitType, onClick: ()->Unit) {
        val button = addChild(Button(null))
        buttonList.add(button)

        val buttonBackground = IconComponent(UnitFactory.displayTextureNameForUnitType(unitType))
        buttonBackground.iconSize = Vector2f(50f, 50f)
        button.background = buttonBackground
        button.addClickCommands(object : Command<Button?> {
            override fun execute(source: Button?) {
                onClick()
            }
        })
    }

    class Builder {

        val view = UnitSummonView()

        fun addButton(unitType: UnitType, onClick: ()->Unit) {
            view.addButton(unitType, onClick)
        }
    }
}