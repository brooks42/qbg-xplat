package desktop

import com.jme3.app.SimpleApplication
import com.jme3.math.Vector3f
import com.jme3.system.AppSettings
import sprites.SpriteFactory
import states.LoadingScreen
import utilities.ImageManager
import utilities.Settings

class QbgApplication : SimpleApplication() {

    lateinit var imageManager: ImageManager

    lateinit var spriteFactory: SpriteFactory

    override fun simpleInitApp() {
        
        cam.isParallelProjection = true
        cam.location = Vector3f(0F, 0F, 0.5f)
        flyByCamera.isEnabled = false

        imageManager = ImageManager(assetManager)
        spriteFactory = SpriteFactory(assetManager)

        val loadingScreenState = LoadingScreen()
        stateManager.attach(loadingScreenState)
    }

    override fun simpleUpdate(tpf: Float) {
        //TODO: add update code
    }

    fun goToMainMenuScreen() {

//        stateManager.detach(LoadingScreen.class);
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val app = QbgApplication()
            val settings = AppSettings(false)
            settings.title = Settings.GAME_TITLE
            settings.setResolution(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT)
            settings.isFullscreen = false
            settings.isVSync = true
            settings.frequency = 60
            app.setSettings(settings)
            app.start()
        }
    }
}