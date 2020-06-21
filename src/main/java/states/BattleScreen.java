/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.renderer.RenderManager;
import gui.PButton;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import sprites.PSprite;
import world.Unit;

/**
 *
 * @author brooks42
 */
public class BattleScreen extends BaseAppState {

    private PSprite knightDisp, spearDisp, archerDisp, paladinDisp, wizDisp,
            assDisp, selectedDisp;
    private PSprite background;
    private PSprite arena;
    private PSprite healthBar, oppHealthBar, manaBar;
    private PSprite summonCircle;
    private PSprite abilityCircle;
    private Polygon summonBox;
    private Polygon enemySummonBox;
    private Polygon arenaBox;
    float rot = 0f;
    // various stats for the player and opponent
    int healthBoxWidth = 280;
    float playerManaMax, playerMana, playerManaRegen;
    float playerHealthMax, playerHealth;
    float oppManaMax, oppMana, oppManaRegen;
    float oppHealthMax, oppHealth;
    public ArrayList<Unit> units, enemyUnits;
    public ArrayList<Unit> lightning;
    private boolean clicked = false;
    private Random random;
    private WinLoseInlay inlay;
    private boolean showingInlay = false;
    // on completion this will be passed to the Inlay and, it true, will
    // count as a win in the save file
    private boolean won = false;
    private int unit_bar_start_at = 237;
    private int selected_summon = 0;

    @Override
    protected void initialize(Application app) {

        won = false;
        showingInlay = false;

//        background = new PSprite(SpriteFactory.getSprite(
//                ImageManager.getImage("background"), -100, -200, 1000, 1000));
//        arena = new PSprite(SpriteFactory.getSprite(
//                ImageManager.getImage("arena"), 0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT));
//        summonCircle = new PSprite(SpriteFactory.getSprite(
//                ImageManager.getImage("summon_circle"), 0, 0, 196, 96));
//        abilityCircle = new PSprite(SpriteFactory.getSprite(
//                ImageManager.getImage("summon_circle_dark"), 0, 0, 196, 96));
//
//        units = new ArrayList<Unit>();
//        lightning = new ArrayList<Unit>(); // lightnings are neutral units that just show up and then leave
//        enemyUnits = new ArrayList<Unit>();
//
//        healthBar = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("health_bar"), 8, 6, healthBoxWidth, 22));
//        oppHealthBar = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("health_bar"), 512, 6, healthBoxWidth, 22));
//        manaBar = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("mana_bar"), 8, 38, healthBoxWidth, 22));
//
//        summonBox = new Polygon();
//        summonBox.addPoint(78, 248);
//        summonBox.addPoint(155, 248);
//        summonBox.addPoint(104, 518);
//        summonBox.addPoint(7, 518);
//
//        enemySummonBox = new Polygon();
//        enemySummonBox.addPoint(644, 249);
//        enemySummonBox.addPoint(721, 249);
//        enemySummonBox.addPoint(790, 518);
//        enemySummonBox.addPoint(698, 518);
//
//        arenaBox = new Polygon();
//        arenaBox.addPoint(155, 248);
//        arenaBox.addPoint(640, 248);
//        arenaBox.addPoint(693, 518);
//        arenaBox.addPoint(104, 518);
//
//        loadStats();
//
//        random = new Random();
//
//        knightDisp = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("human_knight_display"),
//                unit_bar_start_at, 130, 50, 50));
//        spearDisp = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("human_spearman_display"),
//                unit_bar_start_at + 55, 130, 50, 50));
//        archerDisp = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("human_archer_display"),
//                unit_bar_start_at + 110, 130, 50, 50));
//        paladinDisp = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("cannot_select_display"),
//                unit_bar_start_at + 165, 130, 50, 50));
//        wizDisp = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("cannot_select_display"),
//                unit_bar_start_at + 220, 130, 50, 50));
//        assDisp = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("cannot_select_display"),
//                unit_bar_start_at + 275, 130, 50, 50));
//        selectedDisp = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("selected_unit_display"),
//                unit_bar_start_at, 130, 50, 50));
//
//        // set the textures for unlocked units
//        if (SaveGame.gamedata.containsKey(SaveGame.UNLOCKED_PALADINS)) {
//            paladinDisp = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("human_paladin_display"),
//                    unit_bar_start_at + 165, 130, 50, 50));
//        }
//        if (SaveGame.gamedata.containsKey(SaveGame.UNLOCKED_WIZARDS)) {
//            wizDisp = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("human_wizard_display"),
//                    unit_bar_start_at + 220, 130, 50, 50));
//        }
//        if (SaveGame.gamedata.containsKey(SaveGame.UNLOCKED_ASSASSINS)) {
//            assDisp = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("human_assassin_display"),
//                    unit_bar_start_at + 275, 130, 50, 50));
//        }
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    public void loadStats() {
        // hp first
//        this.playerHealth = 20;
//        try {
//            playerHealth += Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_HP)) * 5;
//        } catch (Exception e) {
//            // oh well
//        }
//        this.playerHealthMax = playerHealth;
//
//        this.oppHealth = 20 + Unit.ENEMY_DIFFICULTY * 10;
//        this.oppHealthMax = oppHealth;
//
//        // mana time
//        this.playerMana = 100;
//        try {
//            playerMana += Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_MANA)) * 10;
//        } catch (Exception e) {
//            // oh well
//        }
//        this.playerManaMax = playerMana;
//        this.playerManaRegen = .005f;
//        try {
//            playerManaRegen += Float.parseFloat(SaveGame.gamedata.get(SaveGame.UPGRADE_MANAREG)) * .0025;
//        } catch (Exception e) {
//            // oh well
//        }
//
//        this.oppMana = 0 + Unit.ENEMY_DIFFICULTY * 10;
//        this.oppManaMax = 50 + Unit.ENEMY_DIFFICULTY * 20;
//        this.oppManaRegen = .005f + (float) Unit.ENEMY_DIFFICULTY / 100;
    }

//    @Override
//    public void input() {
//        if (Keyboard.isKeyDown(Keyboard.KEY_1)) {
//            this.selectUnitType(0);
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_2)) {
//            this.selectUnitType(1);
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
//            this.selectUnitType(2);
//        }
//        // for the next ones, only allow them to be selected if the unit is unlocked
//        if (Keyboard.isKeyDown(Keyboard.KEY_4)) {
//            if (SaveGame.gamedata.containsKey(SaveGame.UNLOCKED_PALADINS)) {
//                this.selectUnitType(3);
//            }
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_5)) {
//            if (SaveGame.gamedata.containsKey(SaveGame.UNLOCKED_WIZARDS)) {
//                this.selectUnitType(4);
//            }
//        }
//        if (Keyboard.isKeyDown(Keyboard.KEY_6)) {
//            if (SaveGame.gamedata.containsKey(SaveGame.UNLOCKED_ASSASSINS)) {
//                this.selectUnitType(5);
//            }
//        }
//
//        // if the user is clicking on one of the summon displays, set it
//        if (Mouse.isButtonDown(0)) {
//            int x = Mouse.getX();
//            int y = Settings.SCREEN_HEIGHT - Mouse.getY();
//
//            if (this.knightDisp.hittest(x, y)) {
//                this.selectUnitType(0);
//            }
//            if (this.spearDisp.hittest(x, y)) {
//                this.selectUnitType(1);
//            }
//            if (this.archerDisp.hittest(x, y)) {
//                this.selectUnitType(2);
//            }
//            // for the next ones, only allow them to be selected if the unit is unlocked
//            if (this.paladinDisp.hittest(x, y)) {
//                if (SaveGame.gamedata.containsKey(SaveGame.UNLOCKED_PALADINS)) {
//                    this.selectUnitType(3);
//                }
//            }
//            if (this.wizDisp.hittest(x, y)) {
//                if (SaveGame.gamedata.containsKey(SaveGame.UNLOCKED_WIZARDS)) {
//                    this.selectUnitType(4);
//                }
//            }
//            if (this.assDisp.hittest(x, y)) {
//                if (SaveGame.gamedata.containsKey(SaveGame.UNLOCKED_ASSASSINS)) {
//                    this.selectUnitType(5);
//                }
//            }
//        }
//    }

    @Override
    public void update(float tpf) {

//        if (!showingInlay) {
//            rot += 0.25f;
//            background.setRotation(rot);
//
//            // first update the players' mana pools
//            this.playerMana += playerManaRegen * tpf;
//            this.oppMana += oppManaRegen * tpf;
//            // top them off if appropriate
//            if (playerMana > playerManaMax) {
//                playerMana = playerManaMax;
//            }
//            if (oppMana > oppManaMax) {
//                oppMana = oppManaMax;
//            }
//
//            // now layout the circle if appropriate
//            summonCircle.setX(10000);
//            abilityCircle.setX(10000);
//            if (summonBox.contains(Mouse.getX(), Settings.SCREEN_HEIGHT - Mouse.getY())) {
//                summonCircle.setX(Mouse.getX() - 98);
//                summonCircle.setY(Settings.SCREEN_HEIGHT - Mouse.getY() - 48);
//
//                if (Mouse.isButtonDown(0)) {
//                    if (!clicked) {
//                        int spent_mana = (int) Math.min(12, playerMana);
//                        int summon_units = (spent_mana / 4);
//                        spent_mana = summon_units * 4;
//                        // summon up to 3 units
//                        for (int i = 0; i < summon_units; i++) {
//                            createUnit(selected_summon, Mouse.getX(), Settings.SCREEN_HEIGHT - Mouse.getY());
//                        }
//                        playerMana -= spent_mana;
//                        clicked = true;
//                    }
//                } else {
//                    clicked = false;
//                }
//            } else if (arenaBox.contains(Mouse.getX(), Settings.SCREEN_HEIGHT - Mouse.getY())) {
//                abilityCircle.setX(Mouse.getX() - 98);
//                abilityCircle.setY(Settings.SCREEN_HEIGHT - Mouse.getY() - 48);
//
//                // maybe use abilities if appropriate
//                if (Mouse.isButtonDown(0)) {
//                    if (!clicked) {
//                        for (int i = 0; i < units.size(); i++) {
//                            if (units.get(i).sprite.hittest(abilityCircle)) {
//                                activateAbility(units.get(i));
//                                playerMana--;
//
//                                // costs 1 mana to use abilities; if you have no more
//                                // mana, stop
//                                if (playerMana <= 0) {
//                                    break;
//                                }
//                            }
//                        }
//                        clicked = true;
//                    }
//                } else {
//                    clicked = false;
//                }
//            }
//
//            // go through and kill lightning if appropriate
//            for (int i = 0; i < lightning.size(); i++) {
//                lightning.get(i).update(tpf);
//                if (lightning.get(i).def <= 0) {
//                    lightning.remove(i);
//                }
//            }
//
//            knightDisp.update(tpf);
//            spearDisp.update(tpf);
//            archerDisp.update(tpf);
//            paladinDisp.update(tpf);
//            wizDisp.update(tpf);
//            assDisp.update(tpf);
//            selectedDisp.update(tpf);
//
//            updateUnits(tpf);
//            doAI();
//            this.doHealthAndManaWidthCalc();
//        } else {
//            inlay.update(tpf);
//        }
    }

    @Override
    public void render(RenderManager rm) {

//        background.render();
//        arena.render();
//
//        // draw lightning behind things
//        for (int i = 0; i < lightning.size(); i++) {
//            lightning.get(i).sprite.render();
//        }
//
//        summonCircle.render();
//        abilityCircle.render();
//
//        for (int i = 0; i < units.size(); i++) {
//            units.get(i).sprite.render();
//        }
//        for (int i = 0; i < enemyUnits.size(); i++) {
//            enemyUnits.get(i).sprite.render();
//        }
//
//        healthBar.render();
//        oppHealthBar.render();
//        manaBar.render();
//
//        StringRender.getInstance();
//        StringRender.drawString(StringRender.font24, "Health: "
//                + (int) this.playerHealth + "/" + (int) this.playerHealthMax, 11, 3, Color.white);
//        StringRender.drawString(StringRender.font24, "Mana: "
//                + (int) this.playerMana + "/" + (int) this.playerManaMax, 11, 35, Color.white);
//        StringRender.drawString(StringRender.font24, "Health: "
//                + (int) this.oppHealth + "/" + (int) this.oppHealthMax, 511, 3, Color.white);
//
//        knightDisp.render();
//        spearDisp.render();
//        archerDisp.render();
//        paladinDisp.render();
//        wizDisp.render();
//        assDisp.render();
//        selectedDisp.render();
//
//        if (showingInlay) {
//            inlay.render();
//        }
    }

    //
    // GAME-SPECIFIC CODE:
    //
    /**
     * Selects the Unit Type to summon as follows:
     *
     * 0 - Knight 1 - Spearman 2 - Archer 3 - Paladin 4 - Wizard 5 - Assassin
     *
     * @param num
     */
    public void selectUnitType(int num) {
        selected_summon = num;
//        selectedDisp = new PSprite(SpriteFactory.getSprite(ImageManager.getImage("selected_unit_display"),
//                unit_bar_start_at + (num * 55), 130, 50, 50));
    }

    /**
     * Creates a unit around the passed (x, y). This will add a random amount of
     * drift to the x and y before creating the unit.
     *
     * @param x
     * @param y
     */
    public void createUnit(int type, int x, int y) {
        // creates a unit centered at the (x, y) where the screen was clicked
        // values range inside of the spawn circle
//        while (true) {
//            int x_diff = random.nextInt(140) - 70;
//            int y_diff = random.nextInt(80) - 40;
//
//            Unit unit = Unit.getUnit(type, x + x_diff, y + y_diff);
//            if (summonBox.contains(unit.sprite.getBounds())) {
//                units.add(unit);
//                return;
//            }
//        }
    }

    /**
     * Creates an enemy unit.
     *
     * @param type
     * @param x
     * @param y
     */
    public void createEnemyUnit(int type, int x, int y) {
        System.out.println("Creating enemy unit at (" + x + ", " + y + ")");
        // creates a unit centered at the (x, y) with a small random offset
        int x_diff = random.nextInt(140) - 70;
        int y_diff = random.nextInt(80) - 40;

        // if it isn't in bounds, then remove the x_ and y_diffs
        if (!enemySummonBox.contains(x + x_diff, y + y_diff)) {
            x_diff = 0;
            y_diff = 0;
        }
        Unit unit = Unit.getUnit(type, x + x_diff, y + y_diff);
        enemyUnits.add(unit);
    }

    /**
     * Performs the AI tasks (mostly summoning orks)
     */
    public void doAI() {
        int spent_mana = (int) Math.min(12, oppMana);
        int summon_units = (spent_mana / 4);
        spent_mana = summon_units * 4;

        if (summon_units != 0) {
            // summon up to 3 units
            int x = random.nextInt(enemySummonBox.getBounds().width) + enemySummonBox.getBounds().x;
            int y = random.nextInt(enemySummonBox.getBounds().height) + enemySummonBox.getBounds().y - 20;
            System.out.println("Testing (" + x + ", " + y + ") against " + enemySummonBox.toString());

            if (enemySummonBox.contains(x, y)) {
                for (int i = 0; i < summon_units; i++) {
                    createEnemyUnit(Unit.ORK_KNIGHT, x, y);
                }
            }
        }
        oppMana -= spent_mana;
    }

    /**
     * Updates the units on the board, doing hit detection and bouncing them if
     * appropriate.
     */
    public void updateUnits(float tpf) {

        //
//        for (int i = 0; i < units.size(); i++) {
//            // if the unit is dead, remove it
//            if (units.get(i).def <= 0) {
//                units.remove(i);
//                Sounds.playSoundEffect("bounce");
//                continue;
//            }
//
//            // else update it
//            units.get(i).update(tpf);
//            if (enemySummonBox.intersects(units.get(i).sprite.getBounds())) {
//                if (units.get(i).counts) {
//                    this.oppHealth--;
//                }
//
//                if (oppHealth <= 0) {
//                    win();
//                }
//                units.remove(i);
//                continue;
//            }
//
//            // just in case
////            if (units.get(i).sprite.getX() > 1000) {
////                units.remove(i);
////            }
//        }
//
//        // update enemy units
//        for (int i = 0; i < enemyUnits.size(); i++) {
//            // if the unit is dead, remove it
//            if (enemyUnits.get(i).def <= 0) {
//                SaveGame.incrementMoney();
//                enemyUnits.remove(i);
//                Sounds.playSoundEffect("bounce");
//                continue;
//            }
//
//            // else update it
//            enemyUnits.get(i).update(tpf);
//            if (summonBox.intersects(enemyUnits.get(i).sprite.getBounds())) {
//                this.playerHealth--;
//                if (playerHealth <= 0) {
//                    lose();
//                }
//                enemyUnits.remove(i);
//                continue;
//            }
//
//            // just in case
////            if (enemyUnits.get(i).sprite.getX() < -100) {
////                enemyUnits.remove(i);
////            }
//        }
//
//        // do hit detection
//        for (Unit unit : units) {
//            for (Unit enemyUnit : enemyUnits) {
//                if (unit.sprite.hittest(enemyUnit.sprite)) {
//
//                    // first bounce the units that are hitting eachother
//                    unit.bounce(enemyUnit.push);
//                    enemyUnit.bounce(unit.push);
//
//                    // then decide about damage
//                    unit.def -= enemyUnit.atk;
//                    enemyUnit.def -= unit.atk;
//                }
//            }
//        }
    }

    /**
     * Has the passed unit use its ability, if appropriate.
     *
     * @param unit
     */
    public void activateAbility(Unit unit) {
        unit.useAbility(this);
    }

    /**
     * Calculates the width of the health and mana bars for the game.
     */
    public void doHealthAndManaWidthCalc() {
//        float barwidth = this.healthBoxWidth * (playerHealth / playerHealthMax);
//        this.healthBar.setWidth((int) Math.floor(barwidth));
//        barwidth = this.healthBoxWidth * (playerMana / playerManaMax);
//        this.manaBar.setWidth((int) Math.floor(barwidth));
//        barwidth = this.healthBoxWidth * (oppHealth / oppHealthMax);
//        this.oppHealthBar.setWidth((int) Math.floor(barwidth));
    }

    ///
    /// METHODS FOR WINNING/LOSING AND DISPLAYING APPROPRIATE INLAY
    ///
    public void win() {
        won = true;
        showInlay();
    }

    public void lose() {
        won = false;
        showInlay();
    }

    public void showInlay() {
        inlay = new WinLoseInlay();
        inlay.won = won;
        inlay.setup();
        showingInlay = true;
    }

    /**
     * The WinLoseInlay that displays whether the player won or lost, and allows
     * the player to continue back to the CampaignScreen
     */
    class WinLoseInlay extends InlayMenu {

        private PSprite winlosesprite;
        private PButton continueBtn;
        public boolean won;

        @Override
        public void setup() {
            // set up the sprite to display victory/defeat
//            if (won) {
//                winlosesprite = new PSprite(SpriteFactory.getSprite(
//                        ImageManager.getImage("youwin"),
//                        208, 340, 384, 63));
//            } else {
//                winlosesprite = new PSprite(SpriteFactory.getSprite(
//                        ImageManager.getImage("youlose"),
//                        177, 340, 446, 63));
//            }
//
//            // set up the continue button to move back to the Campaign Screen
//            continueBtn = new PButton(new PSprite(SpriteFactory.getSprite(
//                    ImageManager.getImage("continue"), 339, 410, 122, 21)), new Texture[]{
//                        ImageManager.getImage("continue"),
//                        ImageManager.getImage("continue"),
//                        ImageManager.getImage("continue")
//                    }) {
//                @Override
//                public void onButtonClicked() {
//                    super.onButtonClicked();
//
//                    if (Unit.ENEMY_DIFFICULTY >= 6) {
//                        GameStateController.setState(GameStateController.GAME_END_SCREEN);
//                    } else {
//                        GameStateController.setState(GameStateController.CAMPAIGN_SCREEN);
//                    }
//                }
//            };
        }

        @Override
        public void destroy() {
        }

        @Override
        public void update(float tpf) {
//            winlosesprite.update(tpf);
//            continueBtn.update(tpf);
        }

        @Override
        public void render() {
//            winlosesprite.render();
//            continueBtn.render();
        }
    }
}
