/*
 * The CampaignScreen displays the user's progression through the campaign, and
 * allows the user to select some additional fights.
 */
package desktopkt.states

import com.jme3.app.Application
import com.jme3.app.state.BaseAppState
import com.jme3.input.MouseInput
import com.jme3.input.controls.ActionListener
import com.jme3.input.controls.MouseButtonTrigger
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector2f
import com.jme3.math.Vector3f
import com.jme3.scene.Node
import com.simsilica.lemur.Button
import com.simsilica.lemur.Command
import com.simsilica.lemur.Container
import com.simsilica.lemur.Label
import com.simsilica.lemur.Panel
import com.simsilica.lemur.component.IconComponent
import desktop.QbgApplication
import desktopkt.height
import desktopkt.models.SaveGame
import desktopkt.width


/**
 * The campaign screen where a player starts new battles and manages their empire
 *
 * @author brooks42
 */
class CampaignScreen(val saveGame: SaveGame) : BaseAppState() {

    lateinit var application: QbgApplication

    lateinit var mapNode: Node

    lateinit var hudNode: Container

    private var campaignAreas = ArrayList<Node>()

    lateinit var moneyDisplayBack: Panel
    lateinit var moneyDisplayLabel: Label

    lateinit var upgradesButton: Button

    lateinit var exitButton: Button

    // these are the locations for the spots where users click to go to the next fight
    private val campaignLocations =
            mapOf(
                    Pair("one", Vector2f(428F, 426F)),
                    Pair("two", Vector2f(337F, 300F)),
                    Pair("three", Vector2f(152F, 205F)),
                    Pair("four", Vector2f(246F, 46F)),
                    Pair("five", Vector2f(421F, 81F)),
                    Pair("six", Vector2f(516F, 122F)),
                    Pair("seven", Vector2f(500F, 192F)),
                    Pair("eight", Vector2f(627F, 334F))
            )

    private val fightNameToNumbers =
            mapOf(
                    Pair("one", 1),
                    Pair("two", 2),
                    Pair("three", 3),
                    Pair("four", 4),
                    Pair("five", 5),
                    Pair("six", 6),
                    Pair("seven", 7),
                    Pair("eight", 8)
            )

    override fun initialize(app: Application?) {
        application = app as QbgApplication

        application.camera.isParallelProjection = true

        initHud()
        initMapView()
        initSounds()
    }

    private fun initHud() {

        hudNode = Container()

        moneyDisplayBack = Panel()
        moneyDisplayBack.background = IconComponent("campaign_gui.png")
        moneyDisplayLabel = Label("$---")
        moneyDisplayLabel.color = ColorRGBA.Black

        moneyDisplayBack.setLocalTranslation(-100F, height() - 61F, 0F) // -100, 0, 214, 61
        moneyDisplayLabel.setLocalTranslation(50F, height() - 90F, 0F) // -100, 0, 214, 61

        upgradesButton = Button("")
        upgradesButton.background = IconComponent("upgrade_btn.png")
        upgradesButton.setLocalTranslation(665F, height() - 100, 0F) //  665, 0, 135, 50
        upgradesButton.addClickCommands(Command { println("sup") })

        exitButton = Button("")
        exitButton.background = IconComponent("exit_game_btn.png")
        exitButton.setLocalTranslation(361F, height() - 573F, 0F) //  361, 573, 78, 27
    }

    private fun initMapView() {

        val mapImage = application.imageManager.getImage("game_map.png")
        val battleFlagImage = application.imageManager.getImage("battle_flag.png")

        mapImage?.let {
            mapNode = application.spriteFactory.getSprite(it, 0, 0, width().toInt(), height().toInt())
        }

        // this is pretty jank but shows the battle flags on each fight
        if (battleFlagImage != null) {
            campaignLocations.forEach {
                val yLoc = (height() - it.value.y).toInt() - 50
                val fightNode = application.spriteFactory
                        .getSprite(battleFlagImage, it.value.x.toInt(), yLoc, it.value.x.toInt() + 68, yLoc + 50)
                fightNode.name = it.key
                campaignAreas.add(fightNode)
            }
        }

        campaignAreas.forEach {
            mapNode.attachChild(it)
        }
    }

    private fun attachInput() {

        application.inputManager.addMapping(click, MouseButtonTrigger(MouseInput.BUTTON_LEFT))
        application.inputManager.addMapping(rClick, MouseButtonTrigger(MouseInput.BUTTON_RIGHT))
        application.inputManager.addListener(clickListener, click)
        application.inputManager.addListener(clickListener, rClick)
    }

    private fun initSounds() {

    }

    override fun onEnable() {
        application.guiNode.attachChild(hudNode)
        application.guiNode.attachChild(mapNode)

        attachInput()

        application.guiNode.attachChild(moneyDisplayBack)
        application.guiNode.attachChild(moneyDisplayLabel)
        application.guiNode.attachChild(upgradesButton)
        application.guiNode.attachChild(exitButton)
    }

    override fun onDisable() {
        application.guiNode.detachChild(hudNode)
        application.guiNode.detachChild(mapNode)

        application.inputManager.deleteMapping(click)
        application.inputManager.deleteMapping(rClick)

        application.guiNode.detachChild(moneyDisplayBack)
        application.guiNode.detachChild(moneyDisplayLabel)
        application.guiNode.detachChild(upgradesButton)
        application.guiNode.detachChild(exitButton)
    }

    override fun update(tpf: Float) {
        super.update(tpf)

        moneyDisplayLabel.text = "$${saveGame.money}"
    }

    override fun cleanup(app: Application?) {

    }

    fun saveGame() {

    }

    /**
     * Starts a fight of the passed number (number is for difficulty)
     */
    fun startFight(fightnum: Int) {
        println("Starting fight: $fightnum")
        application.startFight(fightnum)
    }

    /**
     * Shows the upgrades Inlay.
     */
    fun showUpgradesInlay() {
//        inlay!!.setup()
//        showingInlay = true
    }

    /**
     * Dismisses the upgrades Inlay
     */
    fun dismissInlay() {
//        showingInlay = false
//        SaveGame.exportToFile()
    }

    private val clickListener = ActionListener { name, mouseDown, _ ->
        // only respond on mouse up events
        if (mouseDown) {
            return@ActionListener
        }

        val cursorPosition = application.inputManager.cursorPosition
        val cursorPosition3f = Vector3f(cursorPosition.x, cursorPosition.y, 0F)

        if (name == click) {
            campaignAreas.forEach {
                println("$cursorPosition3f <=> ${it.worldBound}")
                if (it.worldBound.intersects(cursorPosition3f)) {
                    println("hit ${it.name}")
                    fightNameToNumbers[it.name]?.apply {
                        startFight(this)
                    }
                }
            }
        }
    }

    /**
     * The Upgrades Inlay that allow the user to upgrade himself and his units,
     * and unlock existing units.
     */
    internal inner class UpgradesInlay : BaseAppState() {

//        var overlay: PSprite? = null
//        var upgradeKnightsButton: PButton? = null
//        var upgradeSpearmenButton: PButton? = null
//        var upgradeArcherButton: PButton? = null
//        var upgradeUnlockPaladinButton: PButton? = null
//        var upgradeUnlockWizardButton: PButton? = null
//        var upgradeUnlockAssassinButton: PButton? = null
//        var upgradeManaButton: PButton? = null
//        var upgradeManaRegButton: PButton? = null
//        var upgradeHPButton: PButton? = null
//        var closeButton: PButton? = null
        var upgradeHP = 0
        var upgradeMana = 0
        var upgradeManaReg = 0
        var upgradeKnights = 0
        var upgradeSpearmen = 0
        var upgradeArchers = 0
        var upgradePaladins = 0
        var upgradeWizards = 0
        var upgradeAssassins = 0
        var mustUnlockPaladins = false
        var mustUnlockWizards = false
        var mustUnlockAssassins = true

        override fun initialize(app: Application?) {

        }
//            overlay = new PSprite(SpriteFactory.getSprite(
//                    ImageManager.getImage("inlay"), 100, 100, 600, 450));
//
//            upgradeHPButton = new PButton(new PSprite(SpriteFactory.getSprite(
//                    ImageManager.getImage("upgradeHP"), 105, 105, 100, 100)), new Texture[]{
//                        ImageManager.getImage("upgradeHP"),
//                        ImageManager.getImage("upgradeHP"),
//                        ImageManager.getImage("upgradeHP")
//                    }) {
//                @Override
//                public void onButtonClicked() {
//                    super.onButtonClicked();
//                    System.out.println("going");
//                    upgradeHP();
//                }
//            };
//
//            upgradeManaButton = new PButton(new PSprite(SpriteFactory.getSprite(
//                    ImageManager.getImage("upgradeMana"), 105, 210, 100, 100)), new Texture[]{
//                        ImageManager.getImage("upgradeMana"),
//                        ImageManager.getImage("upgradeMana"),
//                        ImageManager.getImage("upgradeMana")
//                    }) {
//                @Override
//                public void onButtonClicked() {
//                    super.onButtonClicked();
//                    upgradeMana();
//                }
//            };
//
//            upgradeManaRegButton = new PButton(new PSprite(SpriteFactory.getSprite(
//                    ImageManager.getImage("upgradeManaReg"), 105, 315, 100, 100)), new Texture[]{
//                        ImageManager.getImage("upgradeManaReg"),
//                        ImageManager.getImage("upgradeManaReg"),
//                        ImageManager.getImage("upgradeManaReg")
//                    }) {
//                @Override
//                public void onButtonClicked() {
//                    super.onButtonClicked();
//                    upgradeManaReg();
//                }
//            };
//
//            upgradeKnightsButton = new PButton(new PSprite(SpriteFactory.getSprite(
//                    ImageManager.getImage("human_knight_display"), 300, 105, 100, 100)), new Texture[]{
//                        ImageManager.getImage("human_knight_display"),
//                        ImageManager.getImage("human_knight_display"),
//                        ImageManager.getImage("human_knight_display")
//                    }) {
//                @Override
//                public void onButtonClicked() {
//                    super.onButtonClicked();
//                    upgradeKnights();
//                }
//            };
//
//            upgradeSpearmenButton = new PButton(new PSprite(SpriteFactory.getSprite(
//                    ImageManager.getImage("human_spearman_display"), 300, 210, 100, 100)), new Texture[]{
//                        ImageManager.getImage("human_spearman_display"),
//                        ImageManager.getImage("human_spearman_display"),
//                        ImageManager.getImage("human_spearman_display")
//                    }) {
//                @Override
//                public void onButtonClicked() {
//                    super.onButtonClicked();
//                    upgradeSpearmen();
//                }
//            };
//
//            upgradeArcherButton = new PButton(new PSprite(SpriteFactory.getSprite(
//                    ImageManager.getImage("human_archer_display"), 300, 315, 100, 100)), new Texture[]{
//                        ImageManager.getImage("human_archer_display"),
//                        ImageManager.getImage("human_archer_display"),
//                        ImageManager.getImage("human_archer_display")
//                    }) {
//                @Override
//                public void onButtonClicked() {
//                    super.onButtonClicked();
//                    upgradeArchers();
//                }
//            };
//
//            upgradeUnlockPaladinButton = new PButton(new PSprite(SpriteFactory.getSprite(
//                    ImageManager.getImage("human_paladin_display"), 495, 105, 100, 100)), new Texture[]{
//                        ImageManager.getImage("human_paladin_display"),
//                        ImageManager.getImage("human_paladin_display"),
//                        ImageManager.getImage("human_paladin_display")
//                    }) {
//                @Override
//                public void onButtonClicked() {
//                    super.onButtonClicked();
//                    unlockOrUpgradePaladin();
//                }
//            };
//
//            upgradeUnlockWizardButton = new PButton(new PSprite(SpriteFactory.getSprite(
//                    ImageManager.getImage("human_wizard_display"), 495, 210, 100, 100)), new Texture[]{
//                        ImageManager.getImage("human_wizard_display"),
//                        ImageManager.getImage("human_wizard_display"),
//                        ImageManager.getImage("human_wizard_display")
//                    }) {
//                @Override
//                public void onButtonClicked() {
//                    super.onButtonClicked();
//                    unlockOrUpgradeWizard();
//                }
//            };
//
//            upgradeUnlockAssassinButton = new PButton(new PSprite(SpriteFactory.getSprite(
//                    ImageManager.getImage("human_assassin_display"), 495, 315, 100, 100)), new Texture[]{
//                        ImageManager.getImage("human_assassin_display"),
//                        ImageManager.getImage("human_assassin_display"),
//                        ImageManager.getImage("human_assassin_display")
//                    }) {
//                @Override
//                public void onButtonClicked() {
//                    super.onButtonClicked();
//                    unlockOrUpgradeAssassin();
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
//            loadCosts();

//        override fun render() {
//            // now draw buttons
//            upgradeHPButton.render();
//            StringRender.drawString(StringRender.font24, "$" + upgradeHP, 210, 145, Color.yellow);
//            upgradeManaButton.render();
//            StringRender.drawString(StringRender.font24, "$" + upgradeMana, 210, 250, Color.yellow);
//            upgradeManaRegButton.render();
//            StringRender.drawString(StringRender.font24, "$" + upgradeManaReg, 210, 355, Color.yellow);
//            upgradeKnightsButton.render();
//            StringRender.drawString(StringRender.font24, "$" + upgradeKnights, 405, 145, Color.yellow);
//            upgradeSpearmenButton.render();
//            StringRender.drawString(StringRender.font24, "$" + upgradeSpearmen, 405, 250, Color.yellow);
//            upgradeArcherButton.render();
//            StringRender.drawString(StringRender.font24, "$" + upgradeArchers, 405, 355, Color.yellow);
//            upgradeUnlockPaladinButton.render();
//            StringRender.drawString(StringRender.font24, "$" + upgradePaladins, 600, 145, Color.yellow);
//            upgradeUnlockWizardButton.render();
//            StringRender.drawString(StringRender.font24, "$" + upgradeWizards, 600, 250, Color.yellow);
//            upgradeUnlockAssassinButton.render();
//            StringRender.drawString(StringRender.font24, "$" + upgradeAssassins, 600, 355, Color.yellow);
//
//            closeButton.render();
//        }

//        fun loadCosts() {
//            var bonus = 0f
//            try {
//                bonus = SaveGame.gamedata[SaveGame.UPGRADE_HP]!!.toInt().toFloat()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            upgradeHP = 25 + bonus.toInt() * 25
//            bonus = 0f
//            try {
//                bonus = SaveGame.gamedata[SaveGame.UPGRADE_MANA]!!.toInt().toFloat()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            upgradeMana = 25 + bonus.toInt() * 25
//            bonus = 0f
//            try {
//                bonus = SaveGame.gamedata[SaveGame.UPGRADE_MANAREG]!!.toInt().toFloat()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            upgradeManaReg = 25 + bonus.toInt() * 25
//            bonus = 0f
//            try {
//                bonus = SaveGame.gamedata[SaveGame.UPGRADE_KNIGHTS]!!.toInt().toFloat()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            upgradeKnights = 100 + bonus.toInt() * 100
//            bonus = 0f
//            try {
//                bonus = SaveGame.gamedata[SaveGame.UPGRADE_SPEARMEN]!!.toInt().toFloat()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            upgradeSpearmen = 100 + bonus.toInt() * 100
//            bonus = 0f
//            try {
//                bonus = SaveGame.gamedata[SaveGame.UPGRADE_ARCHERS]!!.toInt().toFloat()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            upgradeArchers = 100 + bonus.toInt() * 100
//            bonus = 0f
//            try {
//                bonus = SaveGame.gamedata[SaveGame.UPGRADE_PALADINS]!!.toInt().toFloat()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            upgradePaladins = 100 + bonus.toInt() * 100
//            bonus = 0f
//            try {
//                bonus = SaveGame.gamedata[SaveGame.UPGRADE_WIZARDS]!!.toInt().toFloat()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            upgradeWizards = 100 + bonus.toInt() * 100
//            bonus = 0f
//            try {
//                bonus = SaveGame.gamedata[SaveGame.UPGRADE_ASSASSINS]!!.toInt().toFloat()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            upgradeAssassins = 100 + bonus.toInt() * 100
//            bonus = 0f
//            mustUnlockPaladins = try {
//                !Boolean.parseBoolean(SaveGame.gamedata[SaveGame.UNLOCKED_PALADINS])
//            } catch (e: Exception) {
//                // bonus should be 0 then
//                true
//            }
//            bonus = 0f
//            mustUnlockWizards = try {
//                !Boolean.parseBoolean(SaveGame.gamedata[SaveGame.UNLOCKED_WIZARDS])
//            } catch (e: Exception) {
//                // bonus should be 0 then
//                true
//            }
//            bonus = 0f
//            mustUnlockAssassins = try {
//                !Boolean.parseBoolean(SaveGame.gamedata[SaveGame.UNLOCKED_ASSASSINS])
//            } catch (e: Exception) {
//                // bonus should be 0 then
//                true
//            }
//        }

        override fun onEnable() {

        }

        override fun onDisable() {

        }

        override fun cleanup(app: Application?) {

        }
    }

    companion object {

        val click = "click"
        val rClick = "rClick"
    }
}