/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sprites

import com.jme3.asset.AssetManager
import com.jme3.material.Material
import com.jme3.material.RenderState
import com.jme3.scene.Node
import com.jme3.texture.Texture2D
import com.jme3.ui.Picture

/**
 * @author brooks42
 */
class SpriteFactory(private val assetManager: AssetManager) {
    fun getSprite(text: Texture2D, x: Int, y: Int, x2: Int, y2: Int): Node {
        return constructNodeForTextureAndBounds(text, x, y, x2, y2)
    }

    private fun constructNodeForTextureAndBounds(texture: Texture2D, x: Int, y: Int, x2: Int, y2: Int): Node {
        val node = Node("test")
        val pic = getSpritePicture(texture, x2 - x, y2 - y)

        val picMat = Material(assetManager, "Common/MatDefs/Gui/Gui.j3md")
        picMat.additionalRenderState.blendMode = RenderState.BlendMode.AlphaAdditive
        node.setMaterial(picMat)

        node.attachChild(pic)
        node.move(x.toFloat(), y.toFloat(), 0f)
        return node
    }

    private fun getSpritePicture(texture: Texture2D, width: Int, height: Int): Picture {
        val pic = Picture("test")
        pic.setTexture(assetManager, texture, true)
        pic.setWidth(width.toFloat())
        pic.setHeight(height.toFloat())
        return pic
    }
}