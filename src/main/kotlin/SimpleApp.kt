package desktopkt

import com.jme3.app.SimpleApplication
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box

class SimpleApp: SimpleApplication() {

    override fun simpleInitApp() {
        val b = Box(1F, 1F, 1F)
        val geom = Geometry("Box", b)
        val mat = Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        mat.setColor("Color", ColorRGBA.Blue)
        geom.material = mat
        rootNode.attachChild(geom)
    }

    override fun simpleUpdate(tpf: Float) {
        //TODO: add update code
    }
}