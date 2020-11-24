package desktopkt.states.fight

import com.jme3.app.Application
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Node
import com.jme3.scene.shape.Box
import com.jme3.scene.shape.Quad
import models.ImageManager

class Unit(val type: UnitType) {

}

enum class UnitType {
    HumanKnight,
    HumanSpearman,
    HumanArcher,
    HumanPaladin,
    HumanWizard,
    HumanAssassin,
    OrkKnight
}

class UnitFactory(val application: Application, val imageManager: ImageManager) {

    fun nodeForUnit(location: Vector3f, type: UnitType): UnitView {
        val box = Box(defaultUnitBound, defaultUnitBound, 0.0F)
        val geom = Geometry("unit", box)

        val mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        mat.setTexture("ColorMap", imageManager.quickLoadImage("human_knight_1.png"))
        geom.material = mat
        geom.localTranslation = location

        val unitView = UnitView(type, geom)

        return unitView
    }

    companion object {
        const val defaultUnitBound = 0.012F
    }
}

// a UnitView is a single unit in the game world
// it wraps a Unit and acts as kind of an MVC style wrapper for units
// where this is the view and the battle screen stuff is the controller
class UnitView(val type: UnitType,
               val geom: Geometry) {

    var bounce = 0f
    var friction = .1f
    var current_frame = 0
    var frame_switch = 10
    var counts = true
    var MAX_FRAME_TIME_ANIM = 10

    var speed = 0.03f

    fun update(tpf: Float) {

        val loc = geom.localTranslation
        loc.x = loc.x + (speed * tpf) + bounce
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
//        this.frame_switch--;
//        if (frame_switch <= 0) {
//            this.current_frame++;
//            if (current_frame >= anim.length) {
//                current_frame = 0;
//            }
//            sprite.setTexture(anim[current_frame])
//            frame_switch = MAX_FRAME_TIME_ANIM;
//        }
    }
}