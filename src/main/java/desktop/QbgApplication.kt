package desktop

import com.jme3.app.SimpleApplication
import com.jme3.asset.plugins.FileLocator
import com.jme3.math.Vector3f
import com.jme3.system.AppSettings
import com.simsilica.lemur.GuiGlobals
import com.simsilica.lemur.style.BaseStyles
import sprites.SpriteFactory
import states.LoadingScreen
import states.MainMenuAppState
import utilities.ImageManager
import utilities.Settings

class QbgApplication : SimpleApplication() {

    lateinit var imageManager: ImageManager

    lateinit var spriteFactory: SpriteFactory

    override fun simpleInitApp() {

        // register sources for finding images and other resources
        // TODO: this should just be able to point to a place in the current directory...
        // TODO: for some reason the "root directory" doesn't actually mean root, it means "only one I look at" :/ need to recurse or something...
        assetManager.registerLocator("/Users/cbrooks/dev/QuickBounceGameKotlin/assets/main/images/skins/default", FileLocator::class.java)

        cam.isParallelProjection = true
        cam.location = Vector3f(0F, 0F, 0.5f)
        flyByCamera.isEnabled = false

        imageManager = ImageManager(assetManager)
        spriteFactory = SpriteFactory(assetManager)

        // setup Lemur
        GuiGlobals.initialize(this)
        BaseStyles.loadGlassStyle()
        GuiGlobals.getInstance().styles.defaultStyle = "glass"

        val loadingScreenState = LoadingScreen()
        stateManager.attach(loadingScreenState)
    }

    override fun simpleUpdate(tpf: Float) {
        //TODO: add update code
    }

    fun goToMainMenuAppState() {
        stateManager.detach(stateManager.getState(LoadingScreen::class.java))

        val mainGameState = MainMenuAppState()
        stateManager.attach(mainGameState)
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