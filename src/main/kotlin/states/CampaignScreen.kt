/*
 * The CampaignScreen displays the user's progression through the campaign, and
 * allows the user to select some additional fights.
 */
package desktopkt.states

import com.jme3.app.Application
import com.jme3.app.state.BaseAppState
import com.jme3.math.Vector2f
import com.jme3.math.Vector3f
import com.jme3.scene.Node
import com.simsilica.lemur.Container
import desktop.QbgApplication
import desktopkt.bottomAnchor
import desktopkt.height
import desktopkt.rightAnchor
import gui.PButton
import sprites.PSprite
import states.InlayMenu
import desktopkt.utilities.SaveGame
import desktopkt.width
import sprites.SpriteFactory
import utilities.ImageManager

/**
 *
 * @author brooks42
 */
class CampaignScreen(val saveGame: SaveGame) : BaseAppState() {

    lateinit var application: QbgApplication

    lateinit var mapNode: Node

    lateinit var hudNode: Container

    private var campaignAreas = ArrayList<Node>()

//    private val moneyDisplay: PSprite? = null
//    private val mouse_was_down = false
//    private val upgradesBtn: PButton? = null
//    private val exitBtn: PButton? = null
//    private val inlay: UpgradesInlay? = null
//    private var showingInlay = false

    override fun initialize(app: Application?) {
        application = app as QbgApplication

        app.camera.isParallelProjection = true
        app.camera.location = Vector3f(0F, 0F, -10F)

        initHud()
        initMapView()
        initSounds()
    }

    private fun initHud() {

        hudNode = Container()


//        moneyDisplay = new PSprite(SpriteFactory.getSprite(
//                ImageManager.getImage("campaign_gui"), -100, 0, 214, 61));
//
//        upgradesBtn = new PButton(new PSprite(SpriteFactory.getSprite(
//                ImageManager.getImage("upgrade_btn"), 665, 0, 135, 50)), new Texture[]{
//                    ImageManager.getImage("upgrade_btn"),
//                    ImageManager.getImage("upgrade_hover_btn"),
//                    ImageManager.getImage("upgrade_hover_btn")
//                }) {
//            @Override
//            public void onButtonClicked() {
//                super.onButtonClicked();
//
//                showUpgradesInlay();
//            }
//        };
//
//        exitBtn = new PButton(new PSprite(SpriteFactory.getSprite(
//                ImageManager.getImage("exit_game_btn"), 361, 573, 78, 27)), new Texture[]{
//                    ImageManager.getImage("exit_game_btn"),
//                    ImageManager.getImage("exit_game_btn"),
//                    ImageManager.getImage("exit_game_btn")
//                }) {
//            @Override
//            public void onButtonClicked() {
//                super.onButtonClicked();
//                GameStateController.setState(GameStateController.MAIN_SCREEN);
//            }
//        };
//
//        inlay = new UpgradesInlay();
    }

    private fun initMapView() {

        val mapImage = application.imageManager.getImage("game_map.png")
        val battleFlagImage = application.imageManager.getImage("battle_flag.png")

        if (mapImage != null) {
            mapNode = application.spriteFactory.getSprite(mapImage, 0, 0, width().toInt(), height().toInt())
        }

        // this is pretty jank but shows the battle flags on each fight
        if (battleFlagImage != null) {
            campaignLocations.forEach {
                val yLoc = (height() - it.y).toInt() - 50
                campaignAreas.add(application.spriteFactory.getSprite(battleFlagImage, it.x.toInt(), yLoc, it.x.toInt() + 68, yLoc + 50))
            }
        }
    }

    private fun initSounds() {

    }

    private val campaignLocations =
            arrayListOf(
                    Vector2f(428F, 426F),
                    Vector2f(337F, 300F),
                    Vector2f(152F, 205F),
                    Vector2f(246F, 46F),
                    Vector2f(421F, 81F),
                    Vector2f(516F, 122F),
                    Vector2f(500F, 192F),
                    Vector2f(627F, 334F)
            )

    override fun onEnable() {
        application.guiNode.attachChild(hudNode)
        application.guiNode.attachChild(mapNode)

        campaignAreas.forEach {
            application.guiNode.attachChild(it)
        }
    }

    override fun onDisable() {
        application.guiNode.detachChild(hudNode)
        application.guiNode.detachChild(mapNode)

        campaignAreas.forEach {
            application.guiNode.detachChild(it)
        }
    }

    override fun update(tpf: Float) {
        super.update(tpf)
    }

    override fun cleanup(app: Application?) {

    }

    fun saveGame() {

    }

    //    @Override
    //    public void input() {
    //        if (!showingInlay) {
    //            for (int i = 0; i < campaign_areas.size(); i++) {
    //                if (campaign_areas.get(i).hittest(Mouse.getX(), Settings.SCREEN_HEIGHT - Mouse.getY())) {
    //                    if (!Mouse.isButtonDown(0) && mouse_was_down) {
    //                        startFight(i);
    //                    }
    //                }
    //            }
    //    }

    /**
     * Starts a fight of the passed number (number is for difficulty)
     */
    fun startFight(fightnum: Int) {
//        println("Starting fight: $fightnum")
//        Unit.ENEMY_DIFFICULTY = fightnum
//        GameStateController.setState(GameStateController.BATTLE_SCREEN)
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

    /**
     * The Upgrades Inlay that allow the user to upgrade himself and his units,
     * and unlock existing units.
     */
    internal inner class UpgradesInlay : InlayMenu() {
        var overlay: PSprite? = null
        var upgradeKnightsButton: PButton? = null
        var upgradeSpearmenButton: PButton? = null
        var upgradeArcherButton: PButton? = null
        var upgradeUnlockPaladinButton: PButton? = null
        var upgradeUnlockWizardButton: PButton? = null
        var upgradeUnlockAssassinButton: PButton? = null
        var upgradeManaButton: PButton? = null
        var upgradeManaRegButton: PButton? = null
        var upgradeHPButton: PButton? = null
        var closeButton: PButton? = null
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
        override fun setup() {
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
        }

        override fun destroy() {}

        override fun update(tpf: Float) {

        }

        override fun render() {
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
        }

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
    }
}