/*
 * The GameEndScreen is basically a mirror of the Story screen, with a different
 * set of story for the game (also, at the end of the text, it returns the 
 * user to the main screen instead of the Campaign)
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;
import com.jme3.texture.Texture;
import gui.PButton;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import sprites.PSprite;
import sprites.SpriteFactory;
import utilities.ImageManager;
import utilities.Settings;
import utilities.StringRender;

/**
 *
 * @author brooks42
 */
public class GameEndScreen extends AbstractAppState {

    private PSprite background1, background2;
    private PButton skipButton;
    private ArrayList<String> lines;
    private float scroll_speed = .15f;
    private float draw_starting = 0f;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {

        background1 = new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("game_map"), 0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT));
        background2 = new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("inlay"), 0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT));

        skipButton = new PButton(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("skip_button"), 727, 570, 73, 30)), new Texture[]{
                ImageManager.getImage("skip_button"),
                ImageManager.getImage("skip_button"),
                ImageManager.getImage("skip_button")
        }) {
            @Override
            public void onButtonClicked() {
                super.onButtonClicked();
                skip();
            }
        };

        try {
            // load the story file
            Scanner scanner = new Scanner(new BufferedReader(new FileReader("resources/scripts/ending.checkem")));
            lines = new ArrayList<String>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("There was a problem loading the story file: " + e.getMessage());
        }
    }

    @Override
    public void update(float tpf) {
        draw_starting -= scroll_speed;
        skipButton.update(tpf);
    }

    @Override
    public void render(RenderManager rm) {

        background1.render();
        background2.render();

        // render the text for the story screen here
        for (int i = 0; i < lines.size(); i++) {
            StringRender.drawString(StringRender.font24, lines.get(i), 50, 300 + (int) draw_starting + (24 * i), Color.lightGray);
        }

        skipButton.render();
    }

    // skips the story, going straight to the campaign screen
    public void skip() {
        GameStateController.setState(GameStateController.MAIN_SCREEN);
    }
}
