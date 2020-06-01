/*
 * The ImageManager class recursively loads all the images in a passed directory.
 */
package utilities

import com.jme3.asset.AssetManager
import com.jme3.asset.TextureKey
import com.jme3.asset.plugins.FileLocator
import com.jme3.texture.Texture2D
import java.net.URL
import java.util.*

/**
 *
 *
 * @author brooks42
 */
// TODO: I'm like 90% sure I don't need any of this and it can all be refactored
class ImageManager(private val assetManager: AssetManager) {

    private val textures: HashMap<String, Texture2D?> = HashMap()

    /**
     * Loads the passed URL as a PNG image, into the textures hashmap with the
     * passed ID key.
     */
    fun loadImage(ID: String, imgUrl: URL) {
        println("Loading $imgUrl")
        val texture = assetManager.loadAsset(imgUrl.toString()) as Texture2D
        textures[ID] = texture
    }

    fun quickLoadImage(assetName: String?): Texture2D? {
        val loaded: Texture2D
        assetManager.registerLocator("/Users/cbrooks/dev/QuickBounceGameKotlin/assets", FileLocator::class.java)
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
            println("ImageManager.getImage($id): could not find image")
            return null
        }

        return textures[id]
    }
}