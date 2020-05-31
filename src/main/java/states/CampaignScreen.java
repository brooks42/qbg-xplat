/*
 * The CampaignScreen displays the user's progression through the campaign, and
 * allows the user to select some additional fights.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;
import com.jme3.texture.Texture;
import gui.PButton;

import java.awt.*;
import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import sprites.PSprite;
import sprites.SpriteFactory;
import utilities.ImageManager;
import utilities.SaveGame;
import utilities.Settings;
import utilities.StringRender;
import world.Unit;

/**
 *
 * @author brooks42
 */
public class CampaignScreen extends AbstractAppState {

    private PSprite background, moneyDisplay;
    private ArrayList<PSprite> campaign_areas;
    private boolean mouse_was_down = false;
    private PButton upgradesBtn, exitBtn;
    private UpgradesInlay inlay;
    private boolean showingInlay = false;
    public int money = 0;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        background = new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("game_map"), 0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT));

        campaign_areas = new ArrayList<PSprite>();
        campaign_areas.add(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("battle_flag"), 428, 426, 68, 50)));
        campaign_areas.add(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("battle_flag"), 337, 300, 68, 50)));
        campaign_areas.add(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("battle_flag"), 152, 205, 68, 50)));
        campaign_areas.add(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("battle_flag"), 246, 46, 68, 50)));
        campaign_areas.add(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("battle_flag"), 421, 81, 68, 50)));
        campaign_areas.add(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("battle_flag"), 516, 122, 68, 50)));
        campaign_areas.add(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("battle_flag"), 500, 192, 68, 50)));
        campaign_areas.add(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("battle_flag"), 627, 334, 68, 50)));

        moneyDisplay = new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("campaign_gui"), -100, 0, 214, 61));

        upgradesBtn = new PButton(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("upgrade_btn"), 665, 0, 135, 50)), new Texture[]{
                    ImageManager.getImage("upgrade_btn"),
                    ImageManager.getImage("upgrade_hover_btn"),
                    ImageManager.getImage("upgrade_hover_btn")
                }) {
            @Override
            public void onButtonClicked() {
                super.onButtonClicked();

                showUpgradesInlay();
            }
        };

        exitBtn = new PButton(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("exit_game_btn"), 361, 573, 78, 27)), new Texture[]{
                    ImageManager.getImage("exit_game_btn"),
                    ImageManager.getImage("exit_game_btn"),
                    ImageManager.getImage("exit_game_btn")
                }) {
            @Override
            public void onButtonClicked() {
                super.onButtonClicked();
                GameStateController.setState(GameStateController.MAIN_SCREEN);
            }
        };

        inlay = new UpgradesInlay();

        // save the current Save Game
        if (SaveGame.gamedata == null) {
            SaveGame.gamedata = SaveGame.getDefaultSave();
        }
        SaveGame.exportToFile();
        money = Integer.parseInt(SaveGame.gamedata.get("money"));
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
//
//            if (Mouse.isButtonDown(0)) {
//                mouse_was_down = true;
//            } else {
//                mouse_was_down = false;
//            }
//        }
//    }

    @Override
    public void update(float tpf) {
        if (!showingInlay) {
            for (int i = 0; i < campaign_areas.size(); i++) {
                campaign_areas.get(i).update(tpf);
            }
            moneyDisplay.update(tpf);
            upgradesBtn.update(tpf);
            exitBtn.update(tpf);
        } else {
            inlay.update(tpf);
        }
    }

    @Override
    public void render(RenderManager rm) {

        background.render();
        for (int i = 0; i < campaign_areas.size(); i++) {
            campaign_areas.get(i).render();
        }
        moneyDisplay.render();
        StringRender.drawString(StringRender.font24, "$" + money, 20, 20, Color.yellow);
        upgradesBtn.render();
        exitBtn.render();

        if (showingInlay) {
            inlay.render();
        }
    }

    /**
     * Starts a fight of the passed number (number is for difficulty)
     */
    public void startFight(int fightnum) {
        System.out.println("Starting fight: " + fightnum);
        Unit.ENEMY_DIFFICULTY = fightnum;
        GameStateController.setState(GameStateController.BATTLE_SCREEN);
    }

    /**
     * Shows the upgrades Inlay.
     */
    public void showUpgradesInlay() {
        inlay.setup();
        showingInlay = true;
    }

    /**
     * Dismisses the upgrades Inlay
     */
    public void dismissInlay() {
        showingInlay = false;
        SaveGame.exportToFile();
    }

    /**
     * The Upgrades Inlay that allow the user to upgrade himself and his units,
     * and unlock existing units.
     */
    class UpgradesInlay extends InlayMenu {

        public PSprite overlay;
        public PButton upgradeKnightsButton, upgradeSpearmenButton, upgradeArcherButton,
                upgradeUnlockPaladinButton, upgradeUnlockWizardButton, upgradeUnlockAssassinButton,
                upgradeManaButton, upgradeManaRegButton, upgradeHPButton;
        public PButton closeButton;
        int upgradeHP, upgradeMana, upgradeManaReg, upgradeKnights, upgradeSpearmen, upgradeArchers,
                upgradePaladins, upgradeWizards, upgradeAssassins = 0;
        boolean mustUnlockPaladins, mustUnlockWizards, mustUnlockAssassins = true;

        @Override
        public void setup() {
            overlay = new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("inlay"), 100, 100, 600, 450));

            upgradeHPButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("upgradeHP"), 105, 105, 100, 100)), new Texture[]{
                        ImageManager.getImage("upgradeHP"),
                        ImageManager.getImage("upgradeHP"),
                        ImageManager.getImage("upgradeHP")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();
                    System.out.println("going");
                    upgradeHP();
                }
            };

            upgradeManaButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("upgradeMana"), 105, 210, 100, 100)), new Texture[]{
                        ImageManager.getImage("upgradeMana"),
                        ImageManager.getImage("upgradeMana"),
                        ImageManager.getImage("upgradeMana")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();
                    upgradeMana();
                }
            };

            upgradeManaRegButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("upgradeManaReg"), 105, 315, 100, 100)), new Texture[]{
                        ImageManager.getImage("upgradeManaReg"),
                        ImageManager.getImage("upgradeManaReg"),
                        ImageManager.getImage("upgradeManaReg")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();
                    upgradeManaReg();
                }
            };

            upgradeKnightsButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("human_knight_display"), 300, 105, 100, 100)), new Texture[]{
                        ImageManager.getImage("human_knight_display"),
                        ImageManager.getImage("human_knight_display"),
                        ImageManager.getImage("human_knight_display")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();
                    upgradeKnights();
                }
            };

            upgradeSpearmenButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("human_spearman_display"), 300, 210, 100, 100)), new Texture[]{
                        ImageManager.getImage("human_spearman_display"),
                        ImageManager.getImage("human_spearman_display"),
                        ImageManager.getImage("human_spearman_display")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();
                    upgradeSpearmen();
                }
            };

            upgradeArcherButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("human_archer_display"), 300, 315, 100, 100)), new Texture[]{
                        ImageManager.getImage("human_archer_display"),
                        ImageManager.getImage("human_archer_display"),
                        ImageManager.getImage("human_archer_display")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();
                    upgradeArchers();
                }
            };

            upgradeUnlockPaladinButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("human_paladin_display"), 495, 105, 100, 100)), new Texture[]{
                        ImageManager.getImage("human_paladin_display"),
                        ImageManager.getImage("human_paladin_display"),
                        ImageManager.getImage("human_paladin_display")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();
                    unlockOrUpgradePaladin();
                }
            };

            upgradeUnlockWizardButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("human_wizard_display"), 495, 210, 100, 100)), new Texture[]{
                        ImageManager.getImage("human_wizard_display"),
                        ImageManager.getImage("human_wizard_display"),
                        ImageManager.getImage("human_wizard_display")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();
                    unlockOrUpgradeWizard();
                }
            };

            upgradeUnlockAssassinButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("human_assassin_display"), 495, 315, 100, 100)), new Texture[]{
                        ImageManager.getImage("human_assassin_display"),
                        ImageManager.getImage("human_assassin_display"),
                        ImageManager.getImage("human_assassin_display")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();
                    unlockOrUpgradeAssassin();
                }
            };

            closeButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("close_inlay"), 669, 100, 31, 33)), new Texture[]{
                        ImageManager.getImage("close_inlay"),
                        ImageManager.getImage("close_inlay"),
                        ImageManager.getImage("close_inlay")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();
                    dismissInlay();
                }
            };

            loadCosts();
        }

        @Override
        public void destroy() {
        }

        @Override
        public void update(float tpf) {
            overlay.update(tpf);
            upgradeManaButton.update(tpf);
            upgradeManaRegButton.update(tpf);
            upgradeHPButton.update(tpf);
            upgradeKnightsButton.update(tpf);
            closeButton.update(tpf);
            upgradeSpearmenButton.update(tpf);
            upgradeArcherButton.update(tpf);
            upgradeUnlockPaladinButton.update(tpf);
            upgradeUnlockWizardButton.update(tpf);
            upgradeUnlockAssassinButton.update(tpf);
        }

        @Override
        public void render() {
            overlay.render();

            // now draw buttons
            upgradeHPButton.render();
            StringRender.drawString(StringRender.font24, "$" + upgradeHP, 210, 145, Color.yellow);
            upgradeManaButton.render();
            StringRender.drawString(StringRender.font24, "$" + upgradeMana, 210, 250, Color.yellow);
            upgradeManaRegButton.render();
            StringRender.drawString(StringRender.font24, "$" + upgradeManaReg, 210, 355, Color.yellow);
            upgradeKnightsButton.render();
            StringRender.drawString(StringRender.font24, "$" + upgradeKnights, 405, 145, Color.yellow);
            upgradeSpearmenButton.render();
            StringRender.drawString(StringRender.font24, "$" + upgradeSpearmen, 405, 250, Color.yellow);
            upgradeArcherButton.render();
            StringRender.drawString(StringRender.font24, "$" + upgradeArchers, 405, 355, Color.yellow);
            upgradeUnlockPaladinButton.render();
            StringRender.drawString(StringRender.font24, "$" + upgradePaladins, 600, 145, Color.yellow);
            upgradeUnlockWizardButton.render();
            StringRender.drawString(StringRender.font24, "$" + upgradeWizards, 600, 250, Color.yellow);
            upgradeUnlockAssassinButton.render();
            StringRender.drawString(StringRender.font24, "$" + upgradeAssassins, 600, 355, Color.yellow);

            closeButton.render();
        }

        // inlay-specific code to perform upgrades 
        public void loadCosts() {
            float bonus = 0;
            try {
                bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_HP));
            } catch (Exception e) {
                // bonus should be 0 then
            }
            upgradeHP = 25 + (int) bonus * 25;

            bonus = 0;
            try {
                bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_MANA));
            } catch (Exception e) {
                // bonus should be 0 then
            }
            upgradeMana = 25 + (int) bonus * 25;

            bonus = 0;
            try {
                bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_MANAREG));
            } catch (Exception e) {
                // bonus should be 0 then
            }
            upgradeManaReg = 25 + (int) bonus * 25;

            bonus = 0;
            try {
                bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_KNIGHTS));
            } catch (Exception e) {
                // bonus should be 0 then
            }
            upgradeKnights = 100 + (int) bonus * 100;

            bonus = 0;
            try {
                bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_SPEARMEN));
            } catch (Exception e) {
                // bonus should be 0 then
            }
            upgradeSpearmen = 100 + (int) bonus * 100;

            bonus = 0;
            try {
                bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_ARCHERS));
            } catch (Exception e) {
                // bonus should be 0 then
            }
            upgradeArchers = 100 + (int) bonus * 100;

            bonus = 0;
            try {
                bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_PALADINS));
            } catch (Exception e) {
                // bonus should be 0 then
            }
            upgradePaladins = 100 + (int) bonus * 100;

            bonus = 0;
            try {
                bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_WIZARDS));
            } catch (Exception e) {
                // bonus should be 0 then
            }
            upgradeWizards = 100 + (int) bonus * 100;

            bonus = 0;
            try {
                bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_ASSASSINS));
            } catch (Exception e) {
                // bonus should be 0 then
            }
            upgradeAssassins = 100 + (int) bonus * 100;

            bonus = 0;
            try {
                mustUnlockPaladins = !Boolean.parseBoolean(SaveGame.gamedata.get(SaveGame.UNLOCKED_PALADINS));
            } catch (Exception e) {
                // bonus should be 0 then
                mustUnlockPaladins = true;
            }

            bonus = 0;
            try {
                mustUnlockWizards = !Boolean.parseBoolean(SaveGame.gamedata.get(SaveGame.UNLOCKED_WIZARDS));
            } catch (Exception e) {
                // bonus should be 0 then
                mustUnlockWizards = true;
            }

            bonus = 0;
            try {
                mustUnlockAssassins = !Boolean.parseBoolean(SaveGame.gamedata.get(SaveGame.UNLOCKED_ASSASSINS));
            } catch (Exception e) {
                // bonus should be 0 then
                mustUnlockAssassins = true;
            }
        }

        /**
         * Upgrades the player's HP by 5
         */
        public void upgradeHP() {
            System.out.println("Upgrade HP");
            if (money >= upgradeHP) {
                money -= upgradeHP;

                int bonus = 0;
                try {
                    bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_HP));
                } catch (Exception e) {
                    // bonus should be 0 then
                }
                SaveGame.gamedata.put(SaveGame.UPGRADE_HP, "" + (bonus + 1));
                SaveGame.gamedata.put(SaveGame.MONEY, "" + money);
                loadCosts();
            }
        }

        /**
         * Upgrades the player's Mana by 10
         */
        public void upgradeMana() {
            System.out.println("Upgrade Mana");
            if (money >= upgradeMana) {
                money -= upgradeMana;

                int bonus = 0;
                try {
                    bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_MANA));
                } catch (Exception e) {
                    // bonus should be 0 then
                }
                SaveGame.gamedata.put(SaveGame.UPGRADE_MANA, "" + (bonus + 1));
                SaveGame.gamedata.put(SaveGame.MONEY, "" + money);
                loadCosts();
            }
        }

        /**
         * Upgrades the player's Mana Regen by .0025
         */
        public void upgradeManaReg() {
            System.out.println("Upgrade Mana Regen");
            if (money >= upgradeManaReg) {
                money -= upgradeManaReg;

                int bonus = 0;
                try {
                    bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_MANAREG));
                } catch (Exception e) {
                    // bonus should be 0 then
                }
                SaveGame.gamedata.put(SaveGame.UPGRADE_MANAREG, "" + (bonus + 1));
                SaveGame.gamedata.put(SaveGame.MONEY, "" + money);
                loadCosts();
            }
        }

        /**
         * Upgrades the player's Knights by 1
         */
        public void upgradeKnights() {
            System.out.println("Upgrade Knights");
            if (money >= upgradeKnights) {
                money -= upgradeKnights;

                int bonus = 0;
                try {
                    bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_KNIGHTS));
                } catch (Exception e) {
                    // bonus should be 0 then
                }
                SaveGame.gamedata.put(SaveGame.UPGRADE_KNIGHTS, "" + (bonus + 1));
                SaveGame.gamedata.put(SaveGame.MONEY, "" + money);
                loadCosts();
            }
        }

        /**
         * Upgrades the player's Spearmen by 1
         */
        public void upgradeSpearmen() {
            System.out.println("Upgrade Spearmen");
            if (money >= upgradeSpearmen) {
                money -= upgradeSpearmen;

                int bonus = 0;
                try {
                    bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_SPEARMEN));
                } catch (Exception e) {
                    // bonus should be 0 then
                }
                SaveGame.gamedata.put(SaveGame.UPGRADE_SPEARMEN, "" + (bonus + 1));
                SaveGame.gamedata.put(SaveGame.MONEY, "" + money);
                loadCosts();
            }
        }

        /**
         * Upgrades the player's Archers by 1
         */
        public void upgradeArchers() {
            System.out.println("Upgrade Archers");
            if (money >= upgradeArchers) {
                money -= upgradeArchers;

                int bonus = 0;
                try {
                    bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_ARCHERS));
                } catch (Exception e) {
                    // bonus should be 0 then
                }
                SaveGame.gamedata.put(SaveGame.UPGRADE_ARCHERS, "" + (bonus + 1));
                SaveGame.gamedata.put(SaveGame.MONEY, "" + money);
                loadCosts();
            }
        }

        /**
         * Upgrades the player's Paladins by 1, or unlocks them if they're not
         * available
         */
        public void unlockOrUpgradePaladin() {
            System.out.println("Upgrade/Unlock Paladin");
            if (mustUnlockPaladins) {
                if (money >= upgradePaladins) {
                    SaveGame.gamedata.put(SaveGame.UNLOCKED_PALADINS, "true");
                    SaveGame.gamedata.put(SaveGame.MONEY, "" + money);
                    loadCosts();
                }
            } else if (money >= upgradePaladins) {
                money -= upgradePaladins;

                int bonus = 0;
                try {
                    bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_PALADINS));
                } catch (Exception e) {
                    // bonus should be 0 then
                }
                SaveGame.gamedata.put(SaveGame.UPGRADE_PALADINS, "" + (bonus + 1));
                SaveGame.gamedata.put(SaveGame.MONEY, "" + money);
                loadCosts();
            }
        }

        /**
         * Upgrades the player's Wizards by 1, or unlocks them if they're not
         * available
         */
        public void unlockOrUpgradeWizard() {
            System.out.println("Upgrade/Unlock Wizards");
            if (mustUnlockWizards) {
                if (money >= upgradeWizards) {
                    SaveGame.gamedata.put(SaveGame.UNLOCKED_WIZARDS, "true");
                    SaveGame.gamedata.put(SaveGame.MONEY, "" + money);
                    loadCosts();
                }
            } else if (money >= upgradeWizards) {
                money -= upgradeWizards;

                int bonus = 0;
                try {
                    bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_WIZARDS));
                } catch (Exception e) {
                    // bonus should be 0 then
                }
                SaveGame.gamedata.put(SaveGame.UPGRADE_WIZARDS, "" + (bonus + 1));
                SaveGame.gamedata.put(SaveGame.MONEY, "" + money);
                loadCosts();
            }
        }

        /**
         * Upgrades the player's Assassins by 1, or unlocks them if they're not
         * available
         */
        public void unlockOrUpgradeAssassin() {
            System.out.println("Upgrade/Unlock Assassins");
            if (mustUnlockAssassins) {
                if (money >= upgradeAssassins) {
                    SaveGame.gamedata.put(SaveGame.UNLOCKED_ASSASSINS, "true");
                    SaveGame.gamedata.put(SaveGame.MONEY, "" + money);
                    loadCosts();
                }
            } else if (money >= upgradeAssassins) {
                money -= upgradeAssassins;

                int bonus = 0;
                try {
                    bonus = Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_ASSASSINS));
                } catch (Exception e) {
                    // bonus should be 0 then
                }
                SaveGame.gamedata.put(SaveGame.UPGRADE_ASSASSINS, "" + (bonus + 1));
                SaveGame.gamedata.put(SaveGame.MONEY, "" + money);
                loadCosts();
            }
        }
    }
}
