/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.lwjgl.LWJGLException;
import org.lwjgl.LWJGLUtil;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import sounds.Sounds;
import utilities.Settings;
import utilities.StringRender;

/**
 *
 * @author brooks42
 */
// TODO: this can be removed and merged into the SimpleApplication along with the GameStateController class
public class GameDisplay {

    private static boolean end = false;

    /**
     * The main method begins program execution, loading the resources,
     * connecting to any appropriate sites and beginning the engine.
     *
     * @param args
     */
    public static void main(String[] args) {
        // this loads the natives from the current location. Should allow the jar to be 
        // packed with natives as well. 
        setupLWJGLJars();

        // create a new instance of the game display
        System.out.println("Initializing game display: " + Settings.SCREEN_WIDTH + "x" + Settings.SCREEN_HEIGHT);
        GameDisplay display = new GameDisplay(Settings.GAME_TITLE, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        display.start();
    }

    /**
     * Sets the system properties to use this project's jars for LWJGL.
     *
     * If this isn't called when you're setting up the GameDisplay, it almost
     * certainly will break in a confusing way.
     */
    public static void setupLWJGLJars() {
        try {
            System.setProperty("org.lwjgl.librarypath", new File(
                    new File(System.getProperty("user.dir"), "lwjgl-2.9.3" + File.separator + "native"),
                    LWJGLUtil.getPlatformName()).getAbsolutePath());
            System.setProperty("net.java.games.input.librarypath",
                    System.getProperty("org.lwjgl.librarypath"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was a problem setting up the LWJGL jars: " + e.getMessage());
        }
    }

    /**
     * Creates a new GameDisplay with the passed width and height.
     *
     * @param width
     * @param height
     */
    public GameDisplay(String title, int width, int height) {
        initialize(title, width, height);
    }

    /**
     * Intialize the common elements for the game
     */
    public final void initialize(String title, int width, int height) {
        try {
            Display.setTitle(title);
            setIcon();
            setDisplayMode();
            Display.create();
            Display.setVSyncEnabled(true);
            //Display.setFullscreen(true);

            GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping

            GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
            GL11.glClearDepth(1.0f); // Depth Buffer Setup
            GL11.glDisable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            GL11.glDepthMask(false);
            GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
            GL11.glLoadIdentity(); // Reset The Projection Matrix

            /*GLU.gluOrtho2D(-(int) Settings.SCREEN_WIDTH / 2, (int) Settings.SCREEN_WIDTH / 2,
             (int) -Settings.SCREEN_HEIGHT / 2, (int) Settings.SCREEN_HEIGHT / 2);*/
            GL11.glOrtho(0, width, height, 0, 1, -1);

            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glLoadIdentity(); // Reset The Projection Matrix

        } catch (LWJGLException e) {
            System.out.println("Problem creating the display");
        }
    }

    /**
     * Sets the Display's icon.
     */
    public static void setIcon() {
        ByteBuffer[] icons = loadIcons("resources/images/skins/default/misc/battle_flag.png");
        Display.setIcon(icons);
    }

    public static ByteBuffer[] loadIcons(String filepath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = loadIconInstance(image, 128);
        buffers[1] = loadIconInstance(image, 32);
        buffers[2] = loadIconInstance(image, 16);
        return buffers;
    }

    private static ByteBuffer loadIconInstance(BufferedImage image, int dimension) {
        BufferedImage scaledIcon = new BufferedImage(dimension, dimension, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaledIcon.createGraphics();
        double ratio = 1;
        if (image.getWidth() > scaledIcon.getWidth()) {
            ratio = (double) (scaledIcon.getWidth()) / image.getWidth();
        } else {
            ratio = (int) (scaledIcon.getWidth() / image.getWidth());
        }
        if (image.getHeight() > scaledIcon.getHeight()) {
            double r2 = (double) (scaledIcon.getHeight()) / image.getHeight();
            if (r2 < ratio) {
                ratio = r2;
            }
        } else {
            double r2 = (int) (scaledIcon.getHeight() / image.getHeight());
            if (r2 < ratio) {
                ratio = r2;
            }
        }
        double width = image.getWidth() * ratio;
        double height = image.getHeight() * ratio;
        g.drawImage(image, (int) ((scaledIcon.getWidth() - width) / 2), (int) ((scaledIcon.getHeight() - height) / 2),
                (int) (width), (int) (height), null);
        g.dispose();

        byte[] imageBuffer = new byte[dimension * dimension * 4];
        int counter = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int colorSpace = scaledIcon.getRGB(j, i);
                imageBuffer[counter + 0] = (byte) ((colorSpace << 8) >> 24);
                imageBuffer[counter + 1] = (byte) ((colorSpace << 16) >> 24);
                imageBuffer[counter + 2] = (byte) ((colorSpace << 24) >> 24);
                imageBuffer[counter + 3] = (byte) (colorSpace >> 24);
                counter += 4;
            }
        }
        return ByteBuffer.wrap(imageBuffer);
    }

    /**
     * Returns all of the DisplayModes available to this application.
     *
     * @param width
     * @param height
     * @return
     */
    public static DisplayMode[] getAvailableDisplayModes(int width, int height) {
//        try {
//            return org.lwjgl.util.Display.getAvailableDisplayModes(width,
//                    height, -1, -1, -1, -1, 60, 60);
//        } catch (Exception e) {
//            System.out.println("getAvailableDisplayModes(" + width + ", " + height + "): " + e.getMessage());
//            return new DisplayMode[0];
//        }
        return null;
    }

    /**
     * Sets the display mode for fullscreen mode
     */
    private boolean setDisplayMode() {
//        try {
//            DisplayMode[] dm = getAvailableDisplayModes(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
//            System.out.println("There are " + dm.length + " available modes.");
//            if (dm.length > 0) {
//                org.lwjgl.util.Display.setDisplayMode(dm, new String[]{
//                            "width=" + Settings.SCREEN_WIDTH, "height=" + Settings.SCREEN_HEIGHT,
//                            "freq=" + 60,
//                            "bpp=" + Display.getDisplayMode().getBitsPerPixel()
//                        });
//                return true;
//            }
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, "Unable to enter fullscreen mode.");
//        }
//        return false;
        return false;
    }

    /**
     * Starts this GameDisplay rendering.
     */
    public void start() {
//        GameLoop.begin();
//        StringRender.getInstance();
//
//        Settings.input();
//        Sounds.init();
//        // apply the settings real quick
//        try {
//            System.out.println("Volume: " + Float.parseFloat(Settings.settings.get(Settings.SETTING_VOLUME)));
//            Sounds.volume = Float.parseFloat(Settings.settings.get(Settings.SETTING_VOLUME));
//        } catch (Exception e) {
//            // oh well
//        }
//
//        while (!Display.isCloseRequested() && !end) {
//            GameLoop.update();
//
//            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
//            GL11.glLoadIdentity();
//            GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//            GL11.glEnable(GL11.GL_BLEND);
//            GL11.glColor3f(1f, 1f, 1f);
//
//            //Render stuff in window coordinates here
//            GameLoop.render();
//
//            Display.update();
//            Display.sync(60);
//        }
//
//        //Communication.kill();
//        GameLoop.end();
//        Display.destroy();
//        Settings.output();
//        Sounds.cleanUp();
//        System.exit(0);
    }

    /**
     * Ends the game.
     */
    public static void end() {
        end = true;
    }
}
