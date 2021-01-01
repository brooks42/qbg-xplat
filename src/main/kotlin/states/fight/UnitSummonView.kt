package desktopkt.states.fight

import com.jme3.scene.Node
import com.simsilica.lemur.*
import com.simsilica.lemur.component.BoxLayout
import com.simsilica.lemur.component.IconComponent
import desktop.QbgApplication

class UnitSummonView(node: Container, application: QbgApplication) {

    val window = Container()

    val menu = Container()

    init {
        setupAndAttachToNode(node, application)
    }

    private fun setupAndAttachToNode(node: Node, application: QbgApplication) {

        val boxLayout = BoxLayout(Axis.X, FillMode.None)

        menu.layout = boxLayout
        menu.setLocalTranslation(100F, 200F, 0F)

        val knightButton = menu.addChild(Button(null))
        knightButton.background = IconComponent("human_knight_display.png")
        knightButton.addClickCommands(object : Command<Button?> {
            override fun execute(source: Button?) {
                println("tick")
            }
        })

        val assassinButton = menu.addChild(Button(null))
        assassinButton.background = IconComponent("human_assassin_display.png")
        assassinButton.addClickCommands(object : Command<Button?> {
            override fun execute(source: Button?) {
                println("tick2")
            }
        })

        val archerButton = menu.addChild(Button(null))
        archerButton.background = IconComponent("human_spearman_display.png")
        archerButton.addClickCommands(object : Command<Button?> {
            override fun execute(source: Button?) {
                println("tick")
            }
        })
    }
}