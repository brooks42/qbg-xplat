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
import com.jme3.app.state.AppStateManager;
import com.jme3.app.state.BaseAppState;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Node;
import desktop.QbgApplication;
import sprites.SpriteFactory;
import utilities.ImageManager;
import utilities.Settings;

/**
 *
 * @author brooks42
 */
public class LoadingScreen extends BaseAppState {

    private boolean doneLoading = false;
    private ImageManager.ImageLoadListener loadListener;
    private Node background;

    private QbgApplication application;

    @Override
    protected void initialize(Application app) {

        this.application = (QbgApplication)app;

        background = application.spriteFactory.getSprite(application.imageManager.quickLoadImage("title.png"),
                0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);

        // now start loading the images
        final LoadingScreen screen = this;
        loadListener = new ImageManager.ImageLoadListener() {
            @Override
            public void doneCalculating(int filesToLoad) {
                System.out.println("Going to load " + filesToLoad + " files");
            }

            @Override
            public void startedLoading(File file) {
                System.out.print("Start " + file.getName());
            }

            @Override
            public void doneLoading(File file) {
                System.out.println(", done!");
            }

            @Override
            public void allDone() {
                // go to the next GameState
                System.out.println("All done!");
                doneLoading = true;
            }
        };

//        application.imageManager.loadImages(loadListener, new File("resources/images"));
    }

    @Override
    protected void onEnable() {
        application.getGuiNode().attachChild(background);
    }

    @Override
    protected void onDisable() {
        application.getGuiNode().detachChild(background);
    }

    @Override
    protected void cleanup(Application app) {

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
}
