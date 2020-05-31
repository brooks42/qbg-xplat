/*
 * The ImageManager class recursively loads all the images in a passed directory.
 */
package utilities;

import com.jme3.asset.AssetManager;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import javax.swing.JOptionPane;

/**
 *
 *
 * @author brooks42
 */
public class ImageManager {

    /*
     * 
     */
    private static HashMap<String, Texture> textures;

    private static AssetManager mAssetManager;

    /**
     * Loads all of the images in the passed directory, recursively.
     *
     * You should almost certainly call this in a different thread.
     *
     * @param listener the listener to send events to
     * @param directory the directory to recurse, loading all .pngs
     */
    public static void loadImages(AssetManager assetManager, ImageLoadListener listener, File directory) {

        if (textures == null) {
            textures = new HashMap<>();
        }

        if (!directory.isDirectory() || !directory.exists()) {
            throw new IllegalArgumentException("The argument 'directory' must be a directory. Passed: " + directory.getAbsolutePath());
        }

        mAssetManager = assetManager;

        int file_num = numberOfFilesInDir(directory);

        listener.doneCalculating(file_num);

        // now load the images recursively, and keep the listener aware of what
        // is going on
        loadImagesInDir(listener, directory);

        listener.allDone();
    }

    /*
     * Returns the number of files in the passed directory, recursively.
     */
    private static int numberOfFilesInDir(File directory) {
        int num = 1;
        if (directory.isDirectory()) {
            num = 0;
            File[] files = directory.listFiles();
            for (int i = 0; i < files.length; i++) {
                num += numberOfFilesInDir(files[i]);
            }
        }
        return num;
    }

    /*
     * Returns the number of files in the passed directory, recursively.
     */
    private static void loadImagesInDir(ImageLoadListener listener, File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            for (int i = 0; i < files.length; i++) {
                loadImagesInDir(listener, files[i]);
            }
        } else {
            try {
                listener.startedLoading(directory);
                
                // gosh dangit macs
                if (!directory.getName().equals(".DS_Store")) {
                    loadImage(directory.getName().substring(0, directory.getName().indexOf('.')), directory.toURI().toURL());
                }
                listener.doneLoading(directory);
            } catch (MalformedURLException e) {
                System.out.println("There was a MalformedURLException for the following file: " + directory.getName());
            }
        }
    }

    /*
     * Loads the passed URL as a PNG image, into the textures hashmap with the 
     * passed ID key.
     */
    private static void loadImage(String ID, URL imgUrl) {
        System.out.println("Loading " + imgUrl.toString());

//            textures.put(ID, TextureLoader.getTexture("PNG", imgUrl.openStream()));

        Texture texture = mAssetManager.loadTexture(imgUrl.toString());
        textures.put(ID, texture);
    }

    /**
     * Loads the passed URL as a PNG image and returns it. This is used to quickly
     * get an image as there's no recursion or anything.
     *
     * @param imgUrl the URL of the image to load. Use File.toURI().toURL();
     */
    @Nullable
    public static Texture quickLoadImage(URL imgUrl) {
        return mAssetManager.loadTexture(imgUrl.toString());
    }

    /**
     * Returns the Texture (if any) with the passed ID.
     *
     * @param ID the ID of the Image to get
     * @return the Image with the passed ID
     */
    public static Texture getImage(String ID) {
        if (textures == null) {
            throw new RuntimeException("ImageManager getting image without loading first.");
        }

        if (textures.get(ID) == null) {
            System.out.println("ImageManager.getImage(" + ID + "): could not find image");
            return null;
        }
        return textures.get(ID);
    }

    /**
     * This is an interface that gets called by methods in the ImageManager.
     *
     * Allows the implementer to keep track of the ImageManager's activity.
     */
    public interface ImageLoadListener {

        /**
         * Called when the ImageManager is finished counting the number of Image
         * files to load.
         *
         * @param filesToLoad
         */
        public abstract void doneCalculating(int filesToLoad);

        /**
         * Called when the ImageManager is starting to load the passed File.
         *
         * @param file
         */
        public abstract void startedLoading(File file);

        /**
         * Called when the ImageManager is finished loading the passed File.
         *
         * @param file
         */
        public abstract void doneLoading(File file);

        /**
         * Called when the ImageManager is completely finished loading images.
         */
        public abstract void allDone();
    }
}
