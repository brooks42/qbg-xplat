package desktop

import com.jme3.app.SimpleApplication
import com.jme3.asset.plugins.FileLocator
import com.jme3.audio.AudioData
import com.jme3.audio.AudioNode
import com.jme3.math.Vector3f
import com.jme3.system.AppSettings
import com.simsilica.lemur.GuiGlobals
import desktopkt.states.CampaignScreen
import desktopkt.utilities.SaveGame
import sprites.SpriteFactory
import states.LoadingScreen
import states.MainMenuAppState
import states.StoryScreen
import utilities.ImageManager
import utilities.Settings

class QbgApplication : SimpleApplication() {

    lateinit var imageManager: ImageManager

    lateinit var spriteFactory: SpriteFactory

    lateinit var audioNode: AudioNode

    lateinit var mainThemeSong: AudioNode

    override fun simpleInitApp() {

        // register sources for finding images and other resources
        // TODO: this should just be able to point to a place in the current directory...
        // TODO: for some reason the "root directory" doesn't actually mean root, it means "only one I look at" :/ need to recurse or something...
        val executionPath = System.getProperty("user.dir")
        assetManager.registerLocator("${executionPath}/assets/main/sounds", FileLocator::class.java)
        assetManager.registerLocator("${executionPath}/assets/main/images/skins/default", FileLocator::class.java)

        cam.isParallelProjection = true
        cam.location = Vector3f(0F, 0F, 0.5f)
        flyByCamera.isEnabled = false

        imageManager = ImageManager(assetManager)
        spriteFactory = SpriteFactory(assetManager)

        // setup Lemur
        GuiGlobals.initialize(this)

        audioNode = AudioNode()

        val loadingScreenState = LoadingScreen()
        stateManager.attach(loadingScreenState)

        mainThemeSong = AudioNode(assetManager, "song_one.ogg", AudioData.DataType.Stream)
        mainThemeSong.isPositional = false
        mainThemeSong.isLooping = true
        mainThemeSong.volume = 1F

        audioNode.attachChild(mainThemeSong)

        mainThemeSong.play()
    }

    fun goToMainMenuAppState() {
        stateManager.detach(stateManager.getState(LoadingScreen::class.java))

        val mainGameState = MainMenuAppState()
        stateManager.attach(mainGameState)
    }

    fun goToStoryScreen() {
        stateManager.detach(stateManager.getState(MainMenuAppState::class.java))

        val storyScreen = StoryScreen()
        stateManager.attach(storyScreen)
    }

    fun goToCampaignScreen(saveGame: SaveGame? = null) {
        stateManager.detach(stateManager.getState(StoryScreen::class.java))

        val campaignScreen = CampaignScreen(saveGame ?: SaveGame.defaultSave)
        stateManager.attach(campaignScreen)
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