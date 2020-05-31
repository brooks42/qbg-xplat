/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;
import sprites.Sprite;
import sprites.SpriteFactory;
import utilities.ImageManager;
import utilities.Settings;

/**
 *
 * @author brooks42
 */
public class LoadingScreen extends AbstractAppState {

    private boolean loading = false;
    private boolean doneLoading = false;
    private Color flashyColor = Color.blue;
    private ImageManager.ImageLoadListener loadListener;
    private int filesToLoad;
    private int loaded = 0;
    private String fileBeingLoaded;
    private Sprite background;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {

        try {
            background = SpriteFactory.getSprite(
                    ImageManager.quickLoadImage(new File("tests/title.png").toURI().toURL()),
                    0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        } catch (MalformedURLException e) {
            System.out.println("broken URL: " + e.getMessage());
        }

        // now start loading the images
        final LoadingScreen screen = this;
        loadListener = new ImageManager.ImageLoadListener() {
            @Override
            public void doneCalculating(int filesToLoad) {
                screen.filesToLoad = filesToLoad;
                System.out.println("Going to load " + filesToLoad + " files");
            }

            @Override
            public void startedLoading(File file) {
                screen.fileBeingLoaded = file.getName();
                System.out.print("Start " + file.getName());
            }

            @Override
            public void doneLoading(File file) {
                screen.loaded++;
                System.out.println(", done!");
            }

            @Override
            public void allDone() {
                // go to the next GameState
                System.out.println("All done!");
                doneLoading = true;
            }
        };

        ImageManager.loadImages(app.getAssetManager(), loadListener, new File("resources/images"));
    }

    @Override
    public void update(float dt) {
        /*Resources.loadImages();
         System.out.println("Finished loading resources");
         GameState.getInstance().setState(GameState.MAIN_SCREEN);*/
        // System.out.println("..");
        if (doneLoading) {
            GameStateController.setState(GameStateController.MAIN_SCREEN);
        }
    }

    @Override
    public void render(RenderManager rm) {
        background.render();
    }
}
