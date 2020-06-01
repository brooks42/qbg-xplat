/*
 * The Animation class contains animations, duh. It's used to store the game's 
 * animations and retrieve them.
 */
package animation

import com.jme3.texture.Texture
import utilities.ImageManager

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
                return arrayOf(imageManager.getImage("human_knight_1"),
                        imageManager.getImage("human_knight_2"),
                        imageManager.getImage("human_knight_3"))
            }
            FrameList.HumanSpearman -> {
                return arrayOf(imageManager.getImage("human_spearman_1"),
                        imageManager.getImage("human_spearman_2"),
                        imageManager.getImage("human_spearman_3"))
            }
            FrameList.HumanArcher -> {
                return arrayOf(imageManager.getImage("human_archer_1"),
                        imageManager.getImage("human_archer_2"),
                        imageManager.getImage("human_archer_3"))
            }
            FrameList.HumanPaladin -> {
                return arrayOf(imageManager.getImage("human_paladin_1"),
                        imageManager.getImage("human_paladin_2"),
                        imageManager.getImage("human_paladin_3"))
            }
            FrameList.HumanWizard -> {
                return arrayOf(imageManager.getImage("human_wizard_1"),
                        imageManager.getImage("human_wizard_2"),
                        imageManager.getImage("human_wizard_3"))
            }
            FrameList.HumanAssassin -> {
                return arrayOf(imageManager.getImage("human_assassin_1"),
                        imageManager.getImage("human_assassin_2"),
                        imageManager.getImage("human_assassin_3"))
            }
            FrameList.HumanArrow -> {
                return arrayOf(imageManager.getImage("arrow_1"),
                        imageManager.getImage("arrow_2"),
                        imageManager.getImage("arrow_3"))
            }
            FrameList.HumanLightning1 -> {
                return arrayOf(imageManager.getImage("lightning_1"))
            }
            FrameList.HumanLightning2 -> {
                return arrayOf(imageManager.getImage("lightning_2"))
            }
            FrameList.HumanLightning3 -> {
                return arrayOf(imageManager.getImage("lightning_3"))
            }
            FrameList.OrkKnight -> {
                return arrayOf(imageManager.getImage("ork_knight_1"),
                        imageManager.getImage("ork_knight_2"),
                        imageManager.getImage("ork_knight_3"))
            }
        }

        return arrayOf()
    }
}