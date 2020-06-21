/*
 * Shows a mostly black screen, with the story of the game scrolling up behind 
 * it. When complete (or when the player hits the Skip button), transitions to 
 * the Campaign screen.
 */
package states

import com.jme3.app.Application
import com.jme3.app.state.BaseAppState
import com.jme3.asset.plugins.FileLocator
import com.simsilica.lemur.Button
import com.simsilica.lemur.Command
import com.simsilica.lemur.Container
import com.simsilica.lemur.Label
import com.simsilica.lemur.component.IconComponent
import desktop.QbgApplication
import desktopkt.bottomAnchor
import desktopkt.centerAnchor
import desktopkt.rightAnchor
import desktopkt.topAnchor
import gui.PButton
import sprites.PSprite
import java.io.BufferedReader
import java.io.FileReader
import java.util.*

/**
 *
 * @author brooks42
 */
class StoryScreen : BaseAppState() {

    lateinit var application: QbgApplication

    lateinit var backgroundImage: Container
    lateinit var scrollWindow: Container
    lateinit var window: Container

    lateinit var skipButton: Button

    private val labels = ArrayList<Label>()
    private val scrollSpeed = 15.0f

    override fun initialize(app: Application) {

        application = app as QbgApplication

        val executionPath = System.getProperty("user.dir")
        val scanner = Scanner(BufferedReader(FileReader("${executionPath}/assets/main/scripts/storyline.checkem")))

        scanner.useDelimiter("\n")
        scanner.forEachRemaining {
            labels.add(Label(it))
        }

        backgroundImage = Container()
        scrollWindow = Container()
        window = Container()

        val background = IconComponent("game_map.png")
        background.alpha = 0.5F
        backgroundImage.background = background
        backgroundImage.setLocalTranslation(0F, topAnchor(), 0F)
        
        scrollWindow.setLocalTranslation(150F, 0F, 0F)

        labels.forEach { label ->
            scrollWindow.addChild(label)
        }

        skipButton = window.addChild(Button("Skip"))
        skipButton.addClickCommands(object : Command<Button?> {
            override fun execute(source: Button?) {
                skip()
            }
        })
        window.setLocalTranslation(rightAnchor() - 100, bottomAnchor() + 50, 0F)
        skipButton.forceRefresh(true, true, true)
    }

    override fun cleanup(app: Application) {}

    override fun onEnable() {
        application.guiNode.attachChild(backgroundImage)

        // reset the height of the scroll window
        scrollWindow.setLocalTranslation(150F, bottomAnchor(), 0F)
        application.guiNode.attachChild(scrollWindow)

        application.guiNode.attachChild(window)
    }

    override fun onDisable() {
        application.guiNode.detachChild(backgroundImage)
        application.guiNode.detachChild(scrollWindow)
        application.guiNode.detachChild(window)
    }

    override fun update(tpf: Float) {
        scrollWindow.setLocalTranslation(100F, scrollWindow.localTranslation.y + (tpf * scrollSpeed), 0F)
    }

    private fun skip() {
        application.goToCampaignScreen()
    }
}