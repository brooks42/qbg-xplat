/*
 * The Animation class contains animations, duh. It's used to store the game's 
 * animations and retrieve them.
 */
package animation;

import com.jme3.texture.Texture;
import utilities.ImageManager;

/**
 *
 * @author brooks42
 */
public class Animation {

    public static final int HUMAN_KNIGHT_ANIM = 0;
    public static final int ORK_KNIGHT_ANIM = 1;
    public static final int HUMAN_SPEARMAN_ANIM = 2;
    public static final int HUMAN_ARCHER_ANIM = 3;
    public static final int HUMAN_PALADIN_ANIM = 4;
    public static final int HUMAN_WIZARD_ANIM = 5;
    public static final int HUMAN_ASSASSIN_ANIM = 6;
    public static final int HUMAN_ARROW_ANIM = 7;
    public static final int HUMAN_LIGHTNING_1_ANIM = 8;
    public static final int HUMAN_LIGHTNING_2_ANIM = 9;
    public static final int HUMAN_LIGHTNING_3_ANIM = 10;

    /**
     * Returns an array of textures for the passed animation. Later this should
     * be wrapped in an object of some sort
     *
     * @param anim
     * @return
     */
    public static Texture[] getAnimation(int anim) {
        switch (anim) {
            case HUMAN_KNIGHT_ANIM:
                return new Texture[]{ImageManager.getImage("human_knight_1"),
                            ImageManager.getImage("human_knight_2"), ImageManager.getImage("human_knight_3")};

            case HUMAN_SPEARMAN_ANIM:
                return new Texture[]{ImageManager.getImage("human_spearman_1"),
                            ImageManager.getImage("human_spearman_2"), ImageManager.getImage("human_spearman_3")};

            case HUMAN_ARCHER_ANIM:
                return new Texture[]{ImageManager.getImage("human_archer_1"),
                            ImageManager.getImage("human_archer_2"), ImageManager.getImage("human_archer_3")};

            case HUMAN_PALADIN_ANIM:
                return new Texture[]{ImageManager.getImage("human_paladin_1"),
                            ImageManager.getImage("human_paladin_2"), ImageManager.getImage("human_paladin_3")};

            case HUMAN_WIZARD_ANIM:
                return new Texture[]{ImageManager.getImage("human_wizard_1"),
                            ImageManager.getImage("human_wizard_2"), ImageManager.getImage("human_wizard_3")};

            case HUMAN_ASSASSIN_ANIM:
                return new Texture[]{ImageManager.getImage("human_assassin_1"),
                            ImageManager.getImage("human_assassin_2"), ImageManager.getImage("human_assassin_3")};

            case HUMAN_ARROW_ANIM:
                return new Texture[]{ImageManager.getImage("arrow_1"),
                            ImageManager.getImage("arrow_2")};

            case HUMAN_LIGHTNING_1_ANIM:
                return new Texture[]{ImageManager.getImage("lightning_1")};
            case HUMAN_LIGHTNING_2_ANIM:
                return new Texture[]{ImageManager.getImage("lightning_2")};
            case HUMAN_LIGHTNING_3_ANIM:
                return new Texture[]{ImageManager.getImage("lightning_3")};

            case ORK_KNIGHT_ANIM:
                return new Texture[]{ImageManager.getImage("ork_knight_1"),
                            ImageManager.getImage("ork_knight_2"), ImageManager.getImage("ork_knight_3")};
        }
        return new Texture[0];
    }
}
