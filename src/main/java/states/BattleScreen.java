/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;

/**
 *
 * @author brooks42
 */
public class BattleScreen /*extends BaseAppState*/ {

//    float rot = 0f;
//    // various stats for the player and opponent
//    int healthBoxWidth = 280;
//    float playerManaMax, playerMana, playerManaRegen;
//    float playerHealthMax, playerHealth;
//    float oppManaMax, oppMana, oppManaRegen;
//    float oppHealthMax, oppHealth;
//    public ArrayList<Unit> units, enemyUnits;
//    public ArrayList<Unit> lightning;
//    private boolean clicked = false;
//    private Random random;
//    private WinLoseInlay inlay;
//    private boolean showingInlay = false;
//    // on completion this will be passed to the Inlay and, it true, will
//    // count as a win in the save file
//    private boolean won = false;
//    private int unit_bar_start_at = 237;
//    private int selected_summon = 0;

    protected void initialize(Application app) {

//        won = false;
//        showingInlay = false;

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

    /**
     * Performs the AI tasks (mostly summoning orks)
     */
//    public void doAI() {
//        int spent_mana = (int) Math.min(12, oppMana);
//        int summon_units = (spent_mana / 4);
//        spent_mana = summon_units * 4;
//
//        if (summon_units != 0) {
//            // summon up to 3 units
//            int x = random.nextInt(enemySummonBox.getBounds().width) + enemySummonBox.getBounds().x;
//            int y = random.nextInt(enemySummonBox.getBounds().height) + enemySummonBox.getBounds().y - 20;
//            System.out.println("Testing (" + x + ", " + y + ") against " + enemySummonBox.toString());
//
//            if (enemySummonBox.contains(x, y)) {
//                for (int i = 0; i < summon_units; i++) {
//                    createEnemyUnit(Unit.ORK_KNIGHT, x, y);
//                }
//            }
//        }
//        oppMana -= spent_mana;
//    }

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

    /**
     * The WinLoseInlay that displays whether the player won or lost, and allows
     * the player to continue back to the CampaignScreen
     */
    class WinLoseInlay {

        public boolean won;

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
    }
}
