/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states

import com.jme3.app.Application
import com.jme3.app.state.BaseAppState
import com.jme3.audio.AudioData
import com.jme3.audio.AudioNode
import com.simsilica.lemur.*
import com.simsilica.lemur.component.BoxLayout
import com.simsilica.lemur.component.IconComponent
import desktop.QbgApplication
import desktopkt.models.SaveGame

/**
 *
 * @author brooks42
 */
class MainMenuAppState : BaseAppState() {

    lateinit var window: Container
    lateinit var menu: Container
    lateinit var skirmishButton: Button
    lateinit var campaignButton: Button
    lateinit var exitButton: Button
    lateinit var volumeButton: Button

    lateinit var application: QbgApplication

    lateinit var clickSound: AudioNode

    public override fun initialize(app: Application) {

        application = app as QbgApplication

        initMenu()
        initSounds()
    }

    private fun initMenu() {

        window = Container()

        window.background = IconComponent("title.png")

        val height = application.guiViewPort.camera.height.toFloat()

        window.setLocalTranslation(0F, height, 0F)

        menu = Container()
        val boxLayout = BoxLayout(Axis.X, FillMode.None)

        menu.layout = boxLayout
        menu.setLocalTranslation(100F, 100F, 0F)

        skirmishButton = menu.addChild(Button(null))
        skirmishButton.background = IconComponent("skirmish_btn.png")
        skirmishButton.addClickCommands(object : Command<Button?> {
            override fun execute(source: Button?) {
                println("Skirmish")
                clickSound.playInstance()
            }
        })

        menu.addChild(Label("-|-"))

        campaignButton = menu.addChild(Button(null))
        campaignButton.background = IconComponent("campaign_btn.png")
        campaignButton.addClickCommands(object : Command<Button?> {
            override fun execute(source: Button?) {
                println("Campaign")

                clickSound.playInstance()

//                // if there are any save games, show the load screen
//                if (SaveGame.getListOfFiles().length > 0) {
//                    showInlay();
//                } else {
//                    // if there's no save game file, start a new campaign
//                    startNewCampaign();
//                }

                startNewCampaign()
            }
        })

        menu.addChild(Label("-|-"))

        exitButton = menu.addChild(Button(null))
        exitButton.background = IconComponent("exit_game_btn.png")
        exitButton.addClickCommands(object : Command<Button?> {
            override fun execute(source: Button?) {
                println("Exit")

                clickSound.playInstance()
                application.stop()
            }
        })

        menu.addChild(Label("-|-"))

        volumeButton = menu.addChild(Button(null))
        volumeButton.background = IconComponent("volume_on.png")
        volumeButton.addClickCommands(object : Command<Button?> {
            override fun execute(source: Button?) {
                println("Mute/Unmute")
                clickSound.playInstance()

                toggleMute()
            }
        })
    }

    private fun initSounds() {

        clickSound = AudioNode(application.assetManager, "click.wav", AudioData.DataType.Buffer)
        clickSound.isPositional = false
        clickSound.isLooping = false
        clickSound.volume = 1F
    }

    override fun onEnable() {
        application.guiNode.attachChild(window)
        application.guiNode.attachChild(menu)

        application.audioNode.attachChild(clickSound)
    }

    override fun onDisable() {
        application.guiNode.detachChild(window)
        application.guiNode.detachChild(menu)

        application.audioNode.detachChild(clickSound)
    }

    fun startNewCampaign() {
//        // TODO: After the Story Screen is finished, go there instead

        application.goToStoryScreen()
    }

    fun startCampaign(saveGame: SaveGame) {

    }

    /**
     * Toggles whether the game sound is muted or not.
     */
    fun toggleMute() {

        // if not muted

//        volumeButton.background = IconComponent("volume_on.png")


        // if muted
//        volumeButton.background = IconComponent("volume_off.png")

    }

    override fun cleanup(app: Application) {
        // np-op
    }
}

// TODO: need an interface or something to talk with the LoadGameInlay and load the game, move gamestates etc

/**
 * LoadGameInlay shows a UI with all the user's current save games and lets them load one from the list
 *
 * @author brooks42
 */
class LoadGameInlay: BaseAppState() {

    lateinit var window: Container

    lateinit var loadButton: Button
    lateinit var deleteButton: Button
    lateinit var closeButton: Button

    lateinit var application: QbgApplication

    lateinit var clickSound: AudioNode

    override fun initialize(app: Application) {

        application = app as QbgApplication

        initUi()
        initSounds()
    }

    private fun initUi() {

        window = Container()
    }

    private fun initSounds() {

        clickSound = AudioNode(application.assetManager, "click.wav", AudioData.DataType.Buffer)
        clickSound.isPositional = false
        clickSound.isLooping = false
        clickSound.volume = 1F
    }

    private fun startLoadGameProcess() {

    }

    //    fun loadGame(file: File): Boolean {
//        println("Loading game...")
//        val parser = JSONParser()
//        try {
//            if (!file.exists()) {
//                println("Save file doesn't exist?")
//                return false
//            } else {
//                val save = HashMap<String?, String>()
//                val jsonObject =
//                    parser.parse(FileReader(file)) as JSONObject
//                val obj: Set<String?> = jsonObject.keys
//                for (`object` in obj) {
//                    save[`object`] = jsonObject[`object`] as String
//                }
//                SaveGame.gamedata = save
//            }
//            return true
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return false
//    }

    override fun onEnable() {

    }

    override fun onDisable() {

    }

    override fun cleanup(app: Application) {
        // no-op
    }
}