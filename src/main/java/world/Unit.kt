/*
 * The Unit class acts as an animated unit moving across the battlefield.
 */
package world

import com.jme3.texture.Texture
import sprites.PSprite
import states.BattleScreen
import java.util.*

/**
 *
 * @author brooks42
 */
class Unit(var sprite: PSprite,
           var anim: Array<Texture>,
           var atk: Float,
           var def: Float,
           var push: Float,
           var speed: Float) {
    var bounce = 0f
    var friction = .1f
    var current_frame = 0
    var frame_switch = 10
    var counts = true
    var MAX_FRAME_TIME_ANIM = 10

    fun update(tpf: Float) {
//        sprite.update(tpf);
//        sprite.setX(sprite.getX() + (speed * tpf) + bounce);
//
//        // this bounce stuff might be a little wonky
//        // basically if the speed is negative the bounce will be positive and vice versa
//        // so if the speed is negative the bounce would do -friction, if < 0 == 0
//        // and if the speed is positive, the other way applies
//        if (bounce != 0) {
//            // simple bounce calculation to approach zero
//            if (bounce < 0) {
//                bounce += friction;
//            } else {
//                bounce -= friction;
//            }
//        }
//
//        // now update the animation
//        this.frame_switch--;
//        if (frame_switch <= 0) {
//            this.current_frame++;
//            if (current_frame >= anim.length) {
//                current_frame = 0;
//            }
//            sprite.setTexture(anim[current_frame]);
//            frame_switch = MAX_FRAME_TIME_ANIM;
//        }
    }

    /**
     * Sets this unit's Bounce value, making it "bounce" in a direction
     *
     * @param amount
     */
    fun bounce(amount: Float) {
        bounce = amount
    }

    /**
     * Uses the unit's ability.
     */
    fun useAbility(screen: BattleScreen?) {}

    companion object {
        const val HUMAN_KNIGHT = 0
        const val HUMAN_SPEARMAN = 1
        const val HUMAN_ARCHER = 2
        const val HUMAN_PALADIN = 3
        const val HUMAN_WIZARD = 4
        const val HUMAN_ASSASSIN = 5
        const val ORK_KNIGHT = 6
        const val ORK_SPEARMAN = 7
        const val ORK_ARCHER = 8
        const val ORK_PALADIN = 9
        const val ORK_WIZARD = 10
        const val ORK_ASSASSIN = 11

        // special "units" used for abilities
        const val HUMAN_ARROW = 12
        const val HUMAN_LIGHTNING = 13
        var ENEMY_DIFFICULTY = 0
        private val random: Random? = null

        /**
         * Returns a Unit of the passed type, with the passed (x, y) as its
         * position.
         *
         * @param unittype
         * @param x
         * @param y
         * @return
         */
        @JvmStatic
        fun getUnit(unittype: Int, x: Int, y: Int): Unit? {

//        switch (unittype) {
//            case HUMAN_KNIGHT:
//                float upgrade = 0.0f;
//                try {
//                    upgrade = Float.parseFloat(SaveGame.gamedata.get(SaveGame.UPGRADE_KNIGHTS));
//                } catch (Exception e) {
//                    // oh well, guess there's no upgrade :P
//                }
//                return new Unit(new PSprite(SpriteFactory.getSprite(
//                        ImageManager.getImage("human_knight_1"), x - 20, y - 20, 40, 40)),
//                        Animation.getAnimation(Animation.HUMAN_KNIGHT_ANIM), 1.5f + upgrade, 4f + upgrade, 2f + upgrade, .07f) {
//                    // ability countdown timer
//                    int ab = 0;
//                    int SPEED_MOD = 10;
//                    int MAX_AB = 10;
//
//                    @Override
//                    public void useAbility(BattleScreen screen) {
//                        ab = MAX_AB;
//                    }
//
//                    @Override
//                    public void update(float tpf) {
//                        super.update(tpf);
//                        // if the ability is going, the unit can't be pushed back
//                        if (ab == MAX_AB) {
//                            bounce = 0;
//                        }
//                        ab--;
//                        if (ab > 0) {
//                            sprite.setX(sprite.getX() + ((speed * tpf) * SPEED_MOD));
//                        } else {
//                            // set ab to 0 for now
//                            ab = 0;
//                        }
//                    }
//                };
//
//            case HUMAN_SPEARMAN:
//                upgrade = 0.0f;
//                try {
//                    upgrade = Float.parseFloat(SaveGame.gamedata.get(SaveGame.UPGRADE_SPEARMEN));
//                } catch (Exception e) {
//                    // oh well, guess there's no upgrade :P
//                }
//                return new Unit(new PSprite(SpriteFactory.getSprite(
//                        ImageManager.getImage("human_spearman_1"), x - 20, y - 20, 40, 40)),
//                        Animation.getAnimation(Animation.HUMAN_SPEARMAN_ANIM), 2f + upgrade, 2f + upgrade, 4f + upgrade, .07f) {
//                    // ability countdown timer
//                    int ab = 0;
//                    int SPEED_MOD = 12;
//                    int MAX_AB = 10;
//
//                    @Override
//                    public void useAbility(BattleScreen screen) {
//                        ab = MAX_AB;
//                    }
//
//                    @Override
//                    public void update(float tpf) {
//                        super.update(tpf);
//                        // if the ability is going, the unit can't be pushed back
//                        if (ab == MAX_AB) {
//                            bounce = 0;
//                        }
//                        ab--;
//                        if (ab > 0) {
//                            sprite.setX(sprite.getX() + ((speed * tpf) * SPEED_MOD));
//                        } else {
//                            // set ab to 0 for now
//                            ab = 0;
//                        }
//                    }
//                };
//
//            case HUMAN_ARCHER:
//                upgrade = 0.0f;
//                try {
//                    upgrade = Float.parseFloat(SaveGame.gamedata.get(SaveGame.UPGRADE_ARCHERS));
//                } catch (Exception e) {
//                    // oh well, guess there's no upgrade :P
//                }
//                return new Unit(new PSprite(SpriteFactory.getSprite(
//                        ImageManager.getImage("human_archer_1"), x - 20, y - 20, 40, 40)),
//                        Animation.getAnimation(Animation.HUMAN_ARCHER_ANIM), 3f + upgrade, 2f + upgrade, 1f + upgrade, .07f) {
//                    int cooldown = 50;
//
//                    @Override
//                    public void useAbility(BattleScreen screen) {
//                        if (cooldown == 0) {
//                            // fire an arrow
//                            screen.units.add(getUnit(Unit.HUMAN_ARROW,
//                                    (int) this.sprite.getX() + 10, (int) this.sprite.getY() + 5));
//                            cooldown = 50;
//                        }
//                    }
//
//                    @Override
//                    public void update(float tpf) {
//                        super.update(tpf);
//                        if (cooldown > 0) {
//                            cooldown--;
//                        }
//                    }
//                };
//
//            /**
//             * Creates a Paladin, a human unit whose special ability is healing
//             * himself by .5 HP
//             */
//            case HUMAN_PALADIN:
//                upgrade = 0.0f;
//                try {
//                    upgrade = Float.parseFloat(SaveGame.gamedata.get(SaveGame.UPGRADE_PALADINS));
//                } catch (Exception e) {
//                    // oh well, guess there's no upgrade :P
//                }
//                return new Unit(new PSprite(SpriteFactory.getSprite(
//                        ImageManager.getImage("human_paladin_1"), x - 20, y - 20, 40, 40)),
//                        Animation.getAnimation(Animation.HUMAN_PALADIN_ANIM), 3f + upgrade, 5f + upgrade, 2f + upgrade, .04f) {
//                    float max_def = def;
//
//                    @Override
//                    public void useAbility(BattleScreen screen) {
//                        if (def < max_def) {
//                            def += 0.5f;
//                        }
//                    }
//                };
//
//            case HUMAN_WIZARD:
//                upgrade = 0.0f;
//                try {
//                    upgrade = Float.parseFloat(SaveGame.gamedata.get(SaveGame.UPGRADE_WIZARDS));
//                } catch (Exception e) {
//                    // oh well, guess there's no upgrade :P
//                }
//                return new Unit(new PSprite(SpriteFactory.getSprite(
//                        ImageManager.getImage("human_wizard_1"), x - 20, y - 20, 40, 40)),
//                        Animation.getAnimation(Animation.HUMAN_WIZARD_ANIM), 5f + upgrade, 2f + upgrade, 3f + upgrade, .02f) {
//                    // ability countdown timer
//                    int check = 0;
//                    int COOLDOWN = 60;
//
//                    @Override
//                    public void useAbility(BattleScreen screen) {
//                        if (check == 0) {
//                            if (random == null) {
//                                random = new Random();
//                            }
//                            // target a random enemy unit
//                            try {
//                                Unit target = screen.enemyUnits.get(random.nextInt(screen.enemyUnits.size() - 1));
//                                // create a Lightning centered on that unit and add it to the list
//                                screen.lightning.add(Unit.getUnit(Unit.HUMAN_LIGHTNING, (int) target.sprite.getBounds().getCenterX(),
//                                        (int) target.sprite.getBounds().getCenterY() - 600));
//                                // deal damage
//                                target.def -= atk;
//                                Sounds.playSoundEffect("lightning");
//                            } catch (Exception e) {
//                                // no units to target, wasted mana
//                            }
//                        }
//                        check = COOLDOWN;
//                    }
//
//                    @Override
//                    public void update(float tpf) {
//                        super.update(tpf);
//                        // if the ability is going, the unit can't be pushed back
//                        if (check != 0) {
//                            check--;
//                        }
//                    }
//                };
//
//            case HUMAN_ASSASSIN:
//                upgrade = 0.0f;
//                try {
//                    upgrade = Float.parseFloat(SaveGame.gamedata.get(SaveGame.UPGRADE_ASSASSINS));
//                } catch (Exception e) {
//                    // oh well, guess there's no upgrade :P
//                }
//                return new Unit(new PSprite(SpriteFactory.getSprite(
//                        ImageManager.getImage("human_assassin_1"), x - 20, y - 20, 40, 40)),
//                        Animation.getAnimation(Animation.HUMAN_ASSASSIN_ANIM), 5f + upgrade, 4f + upgrade, 1f + upgrade, .09f) {
//                    @Override
//                    public void useAbility(BattleScreen screen) {
//                        this.atk += 0.5f;
//                    }
//                };
//
//            case HUMAN_ARROW:
//                upgrade = 0.0f;
//                try {
//                    upgrade = Float.parseFloat(SaveGame.gamedata.get(SaveGame.UPGRADE_ARCHERS));
//                } catch (Exception e) {
//                    // oh well, guess there's no upgrade :P
//                }
//                Unit unit = new Unit(new PSprite(SpriteFactory.getSprite(
//                        ImageManager.getImage("arrow_1"), x, y, 20, 20)),
//                        Animation.getAnimation(Animation.HUMAN_ARROW_ANIM), 6f + upgrade, 1f, 2f + upgrade, .28f);
//                unit.counts = false;
//                return unit;
//
//            // create a Lightning, which stays in one position and slowly loses alpha until it disappears
//            case HUMAN_LIGHTNING:
//
//                if (random == null) {
//                    random = new Random();
//                }
//
//                // select the type of lightning bolt to show
//                int which = random.nextInt(3);
//                Texture[] an = null;
//                switch (which) {
//                    case 0:
//                        an = Animation.getAnimation(Animation.HUMAN_LIGHTNING_1_ANIM);
//                        break;
//                    case 1:
//                        an = Animation.getAnimation(Animation.HUMAN_LIGHTNING_2_ANIM);
//                        break;
//                    case 2:
//                        an = Animation.getAnimation(Animation.HUMAN_LIGHTNING_3_ANIM);
//                        break;
//                }
//
//                return new Unit(new PSprite(SpriteFactory.getSprite(an[0], x, y, 20, 600)), an, 0, 10f, 0, 0f) {
//                    @Override
//                    public void update(float tpf) {
//                        super.update(tpf);
//                        def--;
//                        sprite.setAlpha(sprite.getAlpha() - sprite.getAlpha() / 10);
//                    }
//                };
//
//            // the Ork Knight; a heavy basic soldier
//            case ORK_KNIGHT:
//                return new Unit(new PSprite(SpriteFactory.getSprite(
//                        ImageManager.getImage("ork_knight_1"), x - 20, y - 20, 40, 40)),
//                        Animation.getAnimation(Animation.ORK_KNIGHT_ANIM),
//                        1.5f + ENEMY_DIFFICULTY, 4f + ENEMY_DIFFICULTY, -2f - ENEMY_DIFFICULTY, -.07f);
//
//            default:
//                return null;
//        }
            return null
        }
    }
}