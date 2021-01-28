package desktopkt.states.fight

import animation.AnimationFactory
import com.jme3.app.Application
import com.jme3.material.Material
import com.jme3.material.RenderState
import com.jme3.math.Vector3f
import com.jme3.renderer.queue.RenderQueue
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import desktopkt.utils.ImageManager

class Unit(val type: UnitType) {

    var speed = when(type) {
        UnitType.HumanKnight -> 0.03f
        UnitType.HumanAssassin -> 0.05f
        UnitType.OrkKnight -> -0.03f
        else -> 0.02f
    }

    val baseHp: Int
        get() {
            return when(type) {
                UnitType.HumanKnight -> 5
                UnitType.OrkKnight -> 5
                UnitType.HumanWizard -> 2
                UnitType.HumanPaladin -> 7
                else -> 3
            }
        }

    var hp: Int

    init {
        hp = baseHp
    }
}

interface UnitTypeInfo {
    val isHuman: Boolean
        get() = true
}

enum class UnitType: UnitTypeInfo {
    HumanKnight,
    HumanSpearman,
    HumanArcher,
    HumanPaladin,
    HumanWizard,
    HumanAssassin,
    OrkKnight {
        override val isHuman: Boolean
            get() = false
    }
}

class UnitFactory(private val application: Application, private val imageManager: ImageManager) {

    private val animationFactory: AnimationFactory = AnimationFactory(imageManager)

    fun unit(type: UnitType): Unit {
        return Unit(type)
    }

    fun nodeForUnit(location: Vector3f, unit: Unit): UnitView {
        val box = Box(defaultUnitBound, defaultUnitBound, 0.0F)
        val geom = Geometry("unit", box)

        val mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")

        // TODO: need to replace this with an animation list instead
        val unitTexture = when(unit.type) {
            UnitType.HumanKnight -> "human_knight_1.png"
            UnitType.OrkKnight -> "ork_knight_1.png"
            UnitType.HumanArcher -> "human_archer_1.png"
            UnitType.HumanAssassin -> "human_assassin_1.png"
            UnitType.HumanPaladin -> "human_paladin_1.png"
            UnitType.HumanWizard -> "human_wizard_1.png"
            UnitType.HumanSpearman -> "human_spearman_1.png"
        }

        mat.setTexture("ColorMap", imageManager.quickLoadImage(unitTexture))
        mat.additionalRenderState.blendMode = RenderState.BlendMode.Alpha;

        geom.material = mat
        geom.localTranslation = location
        geom.localTranslation.y += defaultUnitBound // so the feet touch the floor
        geom.queueBucket = RenderQueue.Bucket.Transparent;

        return UnitView(unit, geom, animationFactory)
    }

    companion object {
        const val defaultUnitBound = 0.018F

        fun displayTextureNameForUnitType(unitType: UnitType): String {
            return when(unitType) {
                UnitType.HumanKnight -> "human_knight_display.png"
                UnitType.OrkKnight -> "ork_knight_display.png"
                UnitType.HumanArcher -> "human_archer_display.png"
                UnitType.HumanAssassin -> "human_assassin_display.png"
                UnitType.HumanPaladin -> "human_paladin_display.png"
                UnitType.HumanWizard -> "human_wizard_display.png"
                UnitType.HumanSpearman -> "human_spearman_display.png"
            }
        }
    }
}

// a UnitView is a single unit in the game world
// it wraps a Unit and acts as kind of an MVC style wrapper for units
// where this is the view and the battle screen stuff is the controller
class UnitView(val unit: Unit,
               val geom: Geometry,
               private val animationFactory: AnimationFactory) {

    var bounce = 0f
    var friction = .1f
    var current_frame = 0
    var frame_switch = 10
    var counts = true
    var MAX_FRAME_TIME_ANIM = 10

    var location: Vector3f
        get() {
            return geom.localTranslation
        }
        set(value) {
            geom.localTranslation = value
        }

    private val animationFrameList: AnimationFactory.FrameList
        get() {
            return when (unit.type) {
                UnitType.HumanKnight -> AnimationFactory.FrameList.HumanKnight
                UnitType.HumanSpearman -> AnimationFactory.FrameList.HumanSpearman
                UnitType.HumanArcher -> AnimationFactory.FrameList.HumanArcher
                UnitType.HumanPaladin -> AnimationFactory.FrameList.HumanPaladin
                UnitType.HumanWizard -> AnimationFactory.FrameList.HumanWizard
                UnitType.HumanAssassin -> AnimationFactory.FrameList.HumanAssassin
                UnitType.OrkKnight -> AnimationFactory.FrameList.OrkKnight
            }
        }

    fun update(tpf: Float) {

        val loc = geom.localTranslation
        loc.x = loc.x + (unit.speed * tpf) + bounce
        geom.localTranslation = loc

        // this bounce stuff might be a little wonky
        // basically if the speed is negative the bounce will be positive and vice versa
        // so if the speed is negative the bounce would do -friction, if < 0 == 0
        // and if the speed is positive, the other way applies
        if (bounce != 0f) {
            // simple bounce calculation to approach zero
            if (bounce < 0) {
                bounce += friction;
            } else {
                bounce -= friction;
            }
        }

        // now update the animation
        this.frame_switch--;
        if (frame_switch <= 0) {
            this.current_frame++;
            val frameList = animationFactory.getAnimationFrameList(animationFrameList)
            if (current_frame >= frameList.size) {
                current_frame = 0;
            }
            geom.material.setTexture("ColorMap", frameList[current_frame])
            frame_switch = MAX_FRAME_TIME_ANIM;
        }
    }
}

// a unit's ability, like archers shooting arrows or mages casting lightning
class Ability {

}