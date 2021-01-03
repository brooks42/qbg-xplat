/*
 * The Animation class contains animations, duh. It's used to store the game's 
 * animations and retrieve them.
 */
package animation

import com.jme3.texture.Texture
import desktopkt.utils.ImageManager

/**
 *
 * @author brooks42
 */
class AnimationFactory(private val imageManager: ImageManager) {

    enum class FrameList {
        HumanKnight,
        HumanSpearman,
        HumanArcher,
        HumanPaladin,
        HumanWizard,
        HumanAssassin,
        HumanArrow,
        HumanLightning1,
        HumanLightning2,
        HumanLightning3,
        OrkKnight
    }

    /**
     * Returns an array of textures for the passed animation. Later this should
     * be wrapped in an object of some sort
     *
     * @param anim
     * @return
     */
    fun getAnimationFrameList(anim: FrameList): Array<Texture?> {

        when (anim) {
            FrameList.HumanKnight -> {
                return arrayOf(imageManager.getImage("human_knight_1.png"),
                        imageManager.getImage("human_knight_2.png"),
                        imageManager.getImage("human_knight_3.png"))
            }
            FrameList.HumanSpearman -> {
                return arrayOf(imageManager.getImage("human_spearman_1.png"),
                        imageManager.getImage("human_spearman_2.png"),
                        imageManager.getImage("human_spearman_3.png"))
            }
            FrameList.HumanArcher -> {
                return arrayOf(imageManager.getImage("human_archer_1.png"),
                        imageManager.getImage("human_archer_2.png"),
                        imageManager.getImage("human_archer_3.png"))
            }
            FrameList.HumanPaladin -> {
                return arrayOf(imageManager.getImage("human_paladin_1.png"),
                        imageManager.getImage("human_paladin_2.png"),
                        imageManager.getImage("human_paladin_3.png"))
            }
            FrameList.HumanWizard -> {
                return arrayOf(imageManager.getImage("human_wizard_1.png"),
                        imageManager.getImage("human_wizard_2.png"),
                        imageManager.getImage("human_wizard_3.png"))
            }
            FrameList.HumanAssassin -> {
                return arrayOf(imageManager.getImage("human_assassin_1.png"),
                        imageManager.getImage("human_assassin_2.png"),
                        imageManager.getImage("human_assassin_3.png"))
            }
            FrameList.HumanArrow -> {
                return arrayOf(imageManager.getImage("arrow_1.png"),
                        imageManager.getImage("arrow_2.png"),
                        imageManager.getImage("arrow_3.png"))
            }
            FrameList.HumanLightning1 -> {
                return arrayOf(imageManager.getImage("lightning_1.png"))
            }
            FrameList.HumanLightning2 -> {
                return arrayOf(imageManager.getImage("lightning_2.png"))
            }
            FrameList.HumanLightning3 -> {
                return arrayOf(imageManager.getImage("lightning_3.png"))
            }
            FrameList.OrkKnight -> {
                return arrayOf(imageManager.getImage("ork_knight_1.png"),
                        imageManager.getImage("ork_knight_2.png"),
                        imageManager.getImage("ork_knight_3.png"))
            }
        }
    }
}