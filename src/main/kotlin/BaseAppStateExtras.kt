package desktopkt

import com.jme3.app.Application
import com.jme3.app.state.BaseAppState
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector2f
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.simsilica.lemur.Container
import desktop.QbgApplication

// extension functions for using our GUI stuff

fun BaseAppState.leftAnchor(): Float {
    return 0F
}

fun BaseAppState.rightAnchor(): Float {
    return width()
}

// NOTE: this is flipped because of OpenGL+Lemur, the top of the screen is height and the bottom is 0
fun BaseAppState.topAnchor(): Float {
    return height()
}

fun BaseAppState.bottomAnchor(): Float {
    return 0F
}

fun BaseAppState.centerAnchor(): Vector2f {
    return Vector2f(rightAnchor() / 2, topAnchor() / 2)
}

fun BaseAppState.height(): Float {
    return this.application.context.settings.height.toFloat()
}

fun BaseAppState.width(): Float {
    return this.application.context.settings.width.toFloat()
}

open class BaseQbgState: BaseAppState() {

    lateinit var application: QbgApplication

    override fun initialize(app: Application?) {
        application = app as QbgApplication
    }

    override fun cleanup(p0: Application?) {

    }

    override fun onEnable() {

    }

    override fun onDisable() {

    }
}

open class Base3dQbgState: BaseQbgState() {

    // attaches axes to the root ui
    fun setAxes() {
        // x axis
        var b = Box(100F, 0.02F, 0.02F)
        var geom = Geometry("x axis line", b)

        var mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        mat.setColor("Color", ColorRGBA.Red)
        geom.material = mat

        application.rootNode.apply {
            this.attachChild(geom)
        }

        for (x in -50..50) {
            var b = Box(0.03F, 0.03F, 0.03F)
            var geom = Geometry("tickBox x($x)", b)

            var mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
            mat.setColor("Color", ColorRGBA.Gray)
            geom.material = mat
            geom.setLocalTranslation(x.toFloat(), 0F, 0F)

            application.rootNode.apply {
                this.attachChild(geom)
            }
        }

        // y axis
        b = Box(0.02F, 100F, 0.02F)
        geom = Geometry("y axis line", b)

        mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        mat.setColor("Color", ColorRGBA.Green)
        geom.material = mat

        application.rootNode.apply {
            this.attachChild(geom)
        }

        for (y in -50..50) {
            var b = Box(0.03F, 0.03F, 0.03F)
            var geom = Geometry("tickBox y($y)", b)

            var mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
            mat.setColor("Color", ColorRGBA.Gray)
            geom.material = mat
            geom.setLocalTranslation(0F, y.toFloat(), 0F)

            application.rootNode.apply {
                this.attachChild(geom)
            }
        }

        // z axis
        b = Box(0.02F, 0.02F, 100F)
        geom = Geometry("z axis line", b)

        mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        mat.setColor("Color", ColorRGBA.Blue)
        geom.material = mat

        application.rootNode.apply {
            this.attachChild(geom)
        }

        for (z in -50..50) {
            var b = Box(0.03F, 0.03F, 0.03F)
            var geom = Geometry("tickBox z($z)", b)

            var mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
            mat.setColor("Color", ColorRGBA.Gray)
            geom.material = mat
            geom.setLocalTranslation(0F, 0F, z.toFloat())

            application.rootNode.apply {
                this.attachChild(geom)
            }
        }
    }
}