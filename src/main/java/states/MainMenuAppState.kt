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

    lateinit var menuSound: AudioNode

    public override fun initialize(app: Application) {

        application = app as QbgApplication

        initMenu()
        initSounds()
    }

    private fun initMenu() {

        window = Container()

        window.background = IconComponent("title.png")

        // TODO: this sucks and should be changed ASAP when I build in rendering UI for different resolutions
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

//                // if there are any save games, show the load screen
//                if (SaveGame.getListOfFiles().length > 0) {
//                    showInlay();
//                } else {
//                    // if there's no save game file, start a new campaign
//                    startNewCampaign();
//                }
                clickSound.playInstance()
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

        menuSound = AudioNode(application.assetManager, "song_one.ogg", AudioData.DataType.Stream)
        menuSound.isPositional = false
        menuSound.isLooping = true
        menuSound.volume = 1F
    }

    override fun onEnable() {
        application.guiNode.attachChild(window)
        application.guiNode.attachChild(menu)
        application.guiNode.attachChild(clickSound)
        application.guiNode.attachChild(menuSound)

        menuSound.play()
    }

    override fun onDisable() {
        application.guiNode.detachChild(window)
        application.guiNode.detachChild(menu)
        application.guiNode.detachChild(clickSound)
        application.guiNode.detachChild(menuSound)
    }

    fun startNewCampaign() {
//        // TODO: After the Story Screen is finished, go there instead
//        GameStateController.setState(GameStateController.STORY_SCREEN)
    }

    fun startCampaign() {
//        GameStateController.setState(GameStateController.CAMPAIGN_SCREEN)
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

    /**
     * Toggles whether the game sound is muted or not.
     */
    fun toggleMute() {

        // if not muted

//        volumeButton.background = IconComponent("volume_on.png")


        // if muted
//        volumeButton.background = IconComponent("volume_off.png")



//        if (Sounds.volume != 1) {//
//            Sounds.playMusic("song_one");
//        } else {
//            // mute
//            Sounds.volume = 0;
//            Sounds.stopAllSounds();
//            volumeBtn = new PButton(new PSprite(SpriteFactory.getSprite(
//                    ImageManager.getImage("volume_on"), 750, 550, 50, 50)), new Texture[]{
//                        ImageManager.getImage("volume_on"),
//                        ImageManager.getImage("volume_off"),
//                        ImageManager.getImage("volume_off")
//                    }) {
//                @Override
//                public void onButtonClicked() {
//                    super.onButtonClicked();
//
//                    toggleMute();
//                }
//            };
//        }
//        // now store the volume in the settings
//        Settings.settings.put(Settings.SETTING_VOLUME, "" + Sounds.volume);
    }

    override fun cleanup(app: Application?) {

    }

    // TODO: this should be a separate BaseGameState instance
    internal inner class LoadGameInlay : InlayMenu() {
        override fun setup() {}
        override fun destroy() {}
        override fun update(tpf: Float) {}
        override fun render() {} //        private PSprite inlayBackground;
        //        private PButton newGameButton, loadGameButton, closeButton;
        //        private File[] ourFiles;
        //        private int selectedIndex = -1;
        //        private ArrayList<String> fileInfo;
        //
        //        @Override
        //        public void setup() {
        //            inlayBackground = new PSprite(SpriteFactory.getSprite(
        //                    ImageManager.getImage("inlay"), 100, 100, 600, 400));
        //            ourFiles = SaveGame.getListOfFiles();
        //
        //            newGameButton = new PButton(new PSprite(SpriteFactory.getSprite(
        //                    ImageManager.getImage("new_game_btn"), 105, 469, 80, 26)), new Texture[]{
        //                        ImageManager.getImage("new_game_btn"),
        //                        ImageManager.getImage("new_game_btn"),
        //                        ImageManager.getImage("new_game_btn")
        //                    }) {
        //                @Override
        //                public void onButtonClicked() {
        //                    super.onButtonClicked();
        //
        //                    startNewCampaign();
        //                }
        //            };
        //
        //            loadGameButton = new PButton(new PSprite(SpriteFactory.getSprite(
        //                    ImageManager.getImage("load_game_btn"), 598, 469, 93, 26)), new Texture[]{
        //                        ImageManager.getImage("load_game_btn"),
        //                        ImageManager.getImage("load_game_btn"),
        //                        ImageManager.getImage("load_game_btn")
        //                    }) {
        //                @Override
        //                public void onButtonClicked() {
        //                    super.onButtonClicked();
        //
        //                    if (selectedIndex != -1) {
        //                        boolean worked = loadGame(ourFiles[selectedIndex]);
        //                        if (worked) {
        //                            startCampaign();
        //                        } else {
        //                            System.out.println("Loading game failed? (index=" + selectedIndex
        //                                    + ", filename=" + ourFiles[selectedIndex].getName() + ")");
        //                        }
        //                    }
        //                }
        //            };
        //
        //            closeButton = new PButton(new PSprite(SpriteFactory.getSprite(
        //                    ImageManager.getImage("close_inlay"), 669, 100, 31, 33)), new Texture[]{
        //                        ImageManager.getImage("close_inlay"),
        //                        ImageManager.getImage("close_inlay"),
        //                        ImageManager.getImage("close_inlay")
        //                    }) {
        //                @Override
        //                public void onButtonClicked() {
        //                    super.onButtonClicked();
        //                    dismissInlay();
        //                }
        //            };
        //
        //            fileInfo = new ArrayList<String>();
        //            for (int i = 0; i < 10; i++) {
        //                if (i >= ourFiles.length) {
        //                    break;
        //                }
        //                HashMap<String, String> data = SaveGame.importFromFile(ourFiles[i]);
        //                fileInfo.add("Wins: " + data.get("battle_wins") + "   $"
        //                        + data.get("money") + "   Date: " + data.get("save_date"));
        //            }
        //        }
        //
        //        @Override
        //        public void update(float tpf) {
        //            newGameButton.update(tpf);
        //            loadGameButton.update(tpf);
        //            closeButton.update(tpf);
        //
        //            if (Mouse.isButtonDown(0)) {
        //                if (Mouse.getX() > 120 && Mouse.getX() < 600) {
        //                    // get the Mouse's y position
        //                    int mouse_y = Settings.SCREEN_HEIGHT - Mouse.getY();
        //                    if (mouse_y > 120 && mouse_y < 370) {
        //                        selectedIndex = (mouse_y - 120) / 25;
        //                        System.out.println("Selected index " + selectedIndex);
        //                    }
        //                    //120 + (i * 25),
        //                }
        //            }
        //        }
        //
        //        @Override
        //        public void render() {
        //            inlayBackground.render();
        //            StringRender.drawString(StringRender.font24, "Choose a game:", 102, 100, Color.white);
        //            newGameButton.render();
        //            loadGameButton.render();
        //            closeButton.render();
        //
        //            // now draw a list of all of the save files (or as many as will fit)
        //            for (int i = 0; i < fileInfo.size(); i++) {
        //                StringRender.drawString(StringRender.font24, fileInfo.get(i), 120, 120 + (i * 25),
        //                        (i == selectedIndex ? Color.red : Color.white));
        //            }
        //        }
        //
        //        @Override
        //        public void destroy() {
        //            // no-op
        //        }
    }
}