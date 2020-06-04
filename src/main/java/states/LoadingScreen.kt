/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states

import com.jme3.app.Application
import com.jme3.app.state.BaseAppState
import com.jme3.scene.Node
import com.simsilica.lemur.Button
import com.simsilica.lemur.Command
import com.simsilica.lemur.Container
import com.simsilica.lemur.Label
import desktop.QbgApplication
import utilities.Settings

/**
 *
 * @author brooks42
 */
class LoadingScreen : BaseAppState() {

    var doneLoading = false

    lateinit var background: Node

    lateinit var application: QbgApplication

    override fun initialize(app: Application) {
        application = app as QbgApplication

        background = application.spriteFactory.getSprite(application.imageManager.quickLoadImage("title.png")!!,
                0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT)
        doneLoading = true
    }

    override fun onEnable() {
        application.guiNode.attachChild(background)
    }

    override fun onDisable() {
        application.guiNode.detachChild(background)
    }

    override fun cleanup(app: Application) {}

    override fun update(tpf: Float) {
        if (doneLoading) {
            application.goToMainMenuAppState()
        }
    }
}