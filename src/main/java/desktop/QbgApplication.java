package desktop;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import sprites.SpriteFactory;
import states.LoadingScreen;
import utilities.ImageManager;
import utilities.Settings;

public class QbgApplication extends SimpleApplication {

    public ImageManager imageManager;

    public SpriteFactory spriteFactory;

    public static void main(String[] args) {

        QbgApplication app = new QbgApplication();

        AppSettings settings = new AppSettings(false);
        settings.setTitle(Settings.GAME_TITLE);
        settings.setResolution(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
        settings.setFullscreen(false);
        settings.setVSync(true);
        settings.setFrequency(60);
        app.setSettings(settings);

        app.start();
    }

    @Override
    public void simpleInitApp() {

        cam.setParallelProjection(true);
        cam.setLocation(new Vector3f(0,0,0.5f));
        getFlyByCamera().setEnabled(false);

        imageManager = new ImageManager(assetManager);
        spriteFactory = new SpriteFactory(assetManager);

        LoadingScreen loadingScreenState = new LoadingScreen();

        stateManager.attach(loadingScreenState);
//        Box b = new Box(1, 1, 1);
//        Geometry geom = new Geometry("Box", b);
//
//        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
//        mat.setColor("Color", ColorRGBA.Blue);
//        geom.setMaterial(mat);
//
//        rootNode.attachChild(geom);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }
}