/*
 * The ImageManager class recursively loads all the images in a passed directory.
 */
package models

import com.jme3.asset.AssetManager
import com.jme3.asset.TextureKey
import com.jme3.texture.Texture2D
import java.util.*

/**
 *
 *
 * @author brooks42
 */
// TODO: I'm like 90% sure I don't need any of this and it can all be refactored
class ImageManager(private val assetManager: AssetManager) {

    private val textures: HashMap<String, Texture2D> = HashMap()

    fun quickLoadImage(assetName: String?): Texture2D {
        val loaded: Texture2D
        val textureKey = TextureKey(assetName)
        loaded = assetManager.loadAsset(textureKey) as Texture2D
        return loaded
    }

    /**
     * Returns the Texture (if any) with the passed ID.
     *
     * @param ID the ID of the Image to get
     * @return the Image with the passed ID
     */
    fun getImage(id: String): Texture2D? {

        if (textures[id] == null) {
            println("ImageManager.getImage($id): could not find image, loading it")
            textures[id] = quickLoadImage(id)
        }

        return textures[id]
    }
}