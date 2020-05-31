/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;
import com.jme3.texture.Texture;
import engine.GameDisplay;
import gui.PButton;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.lwjgl.input.Mouse;
import sounds.Sounds;
import sprites.PSprite;
import sprites.SpriteFactory;
import utilities.ImageManager;
import utilities.SaveGame;
import utilities.Settings;
import utilities.StringRender;

/**
 *
 * @author User
 */
public class MainScreen extends AbstractAppState {

    private boolean showLoadGameInlay = false;
    private PSprite background;
    private PButton skirmishBtn, campaignBtn, exitBtn, volumeBtn;
    private LoadGameInlay loadGameMenu;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {

        showLoadGameInlay = false;

        background = new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("title"), 0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT));
        skirmishBtn = new PButton(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("skirmish_btn"), 100, 525, 168, 27)), new Texture[]{
                    ImageManager.getImage("skirmish_btn"), ImageManager.getImage("skirmish_btn"), ImageManager.getImage("skirmish_btn")
                }) {
            @Override
            public void onButtonClicked() {
                super.onButtonClicked();

                dismissInlay();
                // go to the skirmish screen?
                System.out.println("Skirmish!");
            }
        };
        campaignBtn = new PButton(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("campaign_btn"), 500, 525, 185, 27)), new Texture[]{
                    ImageManager.getImage("campaign_btn"), ImageManager.getImage("campaign_btn"), ImageManager.getImage("campaign_btn")
                }) {
            @Override
            public void onButtonClicked() {
                super.onButtonClicked();

                // if there are any save games, show the load screen
                if (SaveGame.getListOfFiles().length > 0) {
                    showInlay();
                } else {
                    // if there's no save game file, start a new campaign
                    startNewCampaign();
                }
            }
        };

        exitBtn = new PButton(new PSprite(SpriteFactory.getSprite(
                ImageManager.getImage("exit_game_btn"), 361, 573, 78, 27)), new Texture[]{
                    ImageManager.getImage("exit_game_btn"),
                    ImageManager.getImage("exit_game_btn"),
                    ImageManager.getImage("exit_game_btn")
                }) {
            @Override
            public void onButtonClicked() {
                super.onButtonClicked();
                GameDisplay.end();
            }
        };

        if (Sounds.volume < 1) {
            volumeBtn = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("volume_off"), 750, 550, 50, 50)), new Texture[]{
                        ImageManager.getImage("volume_off"),
                        ImageManager.getImage("volume_on"),
                        ImageManager.getImage("volume_on")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();

                    toggleMute();
                    // go to the skirmish screen?
                    System.out.println("Skirmish!");
                }
            };
        } else {
            volumeBtn = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("volume_on"), 750, 550, 50, 50)), new Texture[]{
                        ImageManager.getImage("volume_on"),
                        ImageManager.getImage("volume_off"),
                        ImageManager.getImage("volume_off")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();

                    toggleMute();
                    // go to the skirmish screen?
                    System.out.println("Skirmish!");
                }
            };
        }
        
        Sounds.playMusic("song_one");
    }

    /**
     * Shows the Inlay for this screen.
     */
    public void showInlay() {
        loadGameMenu = new LoadGameInlay();
        loadGameMenu.setup();
        showLoadGameInlay = true;
    }

    /**
     *
     */
    public void dismissInlay() {
        if (loadGameMenu != null) {
            loadGameMenu.destroy();
        }
        loadGameMenu = null;
        showLoadGameInlay = false;
    }

    /**
     * Starts a new Campaign.
     */
    public void startNewCampaign() {
        // TODO: After the Story Screen is finished, go there instead
        GameStateController.setState(GameStateController.STORY_SCREEN);
    }

    public void startCampaign() {
        GameStateController.setState(GameStateController.CAMPAIGN_SCREEN);
    }

    /**
     * Has this MenuScreen load the passed file as a saved game
     */
    public boolean loadGame(File file) {

        System.out.println("Loading game...");
        JSONParser parser = new JSONParser();
        try {
            if (!file.exists()) {
                System.out.println("Save file doesn't exist?");
                return false;
            } else {
                HashMap<String, String> save = new HashMap<String, String>();
                JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));

                Set<String> obj = jsonObject.keySet();

                for (String object : obj) {
                    save.put(object, (String) jsonObject.get(object));
                }
                SaveGame.gamedata = save;
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void update(float tpf) {

        if (showLoadGameInlay) {
            loadGameMenu.update(tpf);
        } else {
            skirmishBtn.update(tpf);
            campaignBtn.update(tpf);
            exitBtn.update(tpf);
            volumeBtn.update(tpf);
        }
    }

    @Override
    public void render(RenderManager rm) {

        background.render();
        skirmishBtn.render();
        campaignBtn.render();
        exitBtn.render();
        volumeBtn.render();

        if (showLoadGameInlay) {
            loadGameMenu.render();
        }
    }

    /**
     * Toggles whether the game sound is muted or not.
     */
    public void toggleMute() {
        if (Sounds.volume != 1) {
            // stop being muted and start playing the music again
            Sounds.volume = 1;
            volumeBtn = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("volume_off"), 750, 550, 50, 50)), new Texture[]{
                        ImageManager.getImage("volume_off"),
                        ImageManager.getImage("volume_on"),
                        ImageManager.getImage("volume_on")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();
                    toggleMute();
                }
            };

            Sounds.playMusic("song_one");
        } else {
            // mute
            Sounds.volume = 0;
            Sounds.stopAllSounds();
            volumeBtn = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("volume_on"), 750, 550, 50, 50)), new Texture[]{
                        ImageManager.getImage("volume_on"),
                        ImageManager.getImage("volume_off"),
                        ImageManager.getImage("volume_off")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();

                    toggleMute();
                }
            };
        }
        // now store the volume in the settings
        Settings.settings.put(Settings.SETTING_VOLUME, "" + Sounds.volume);
    }

    /**
     * An InlayMenu used to show the player any available used games and then
     * allow them to load a game.
     *
     * @author brooks42
     */
    class LoadGameInlay extends InlayMenu {

        private PSprite inlayBackground;
        private PButton newGameButton, loadGameButton, closeButton;
        private File[] ourFiles;
        private int selectedIndex = -1;
        private ArrayList<String> fileInfo;

        @Override
        public void setup() {
            inlayBackground = new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("inlay"), 100, 100, 600, 400));
            ourFiles = SaveGame.getListOfFiles();

            newGameButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("new_game_btn"), 105, 469, 80, 26)), new Texture[]{
                        ImageManager.getImage("new_game_btn"),
                        ImageManager.getImage("new_game_btn"),
                        ImageManager.getImage("new_game_btn")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();

                    startNewCampaign();
                }
            };

            loadGameButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("load_game_btn"), 598, 469, 93, 26)), new Texture[]{
                        ImageManager.getImage("load_game_btn"),
                        ImageManager.getImage("load_game_btn"),
                        ImageManager.getImage("load_game_btn")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();

                    if (selectedIndex != -1) {
                        boolean worked = loadGame(ourFiles[selectedIndex]);
                        if (worked) {
                            startCampaign();
                        } else {
                            System.out.println("Loading game failed? (index=" + selectedIndex
                                    + ", filename=" + ourFiles[selectedIndex].getName() + ")");
                        }
                    }
                }
            };

            closeButton = new PButton(new PSprite(SpriteFactory.getSprite(
                    ImageManager.getImage("close_inlay"), 669, 100, 31, 33)), new Texture[]{
                        ImageManager.getImage("close_inlay"),
                        ImageManager.getImage("close_inlay"),
                        ImageManager.getImage("close_inlay")
                    }) {
                @Override
                public void onButtonClicked() {
                    super.onButtonClicked();
                    dismissInlay();
                }
            };

            fileInfo = new ArrayList<String>();
            for (int i = 0; i < 10; i++) {
                if (i >= ourFiles.length) {
                    break;
                }
                HashMap<String, String> data = SaveGame.importFromFile(ourFiles[i]);
                fileInfo.add("Wins: " + data.get("battle_wins") + "   $"
                        + data.get("money") + "   Date: " + data.get("save_date"));
            }
        }

        @Override
        public void update(float tpf) {
            newGameButton.update(tpf);
            loadGameButton.update(tpf);
            closeButton.update(tpf);

            if (Mouse.isButtonDown(0)) {
                if (Mouse.getX() > 120 && Mouse.getX() < 600) {
                    // get the Mouse's y position
                    int mouse_y = Settings.SCREEN_HEIGHT - Mouse.getY();
                    if (mouse_y > 120 && mouse_y < 370) {
                        selectedIndex = (mouse_y - 120) / 25;
                        System.out.println("Selected index " + selectedIndex);
                    }
                    //120 + (i * 25),
                }
            }
        }

        @Override
        public void render() {
            inlayBackground.render();
            StringRender.drawString(StringRender.font24, "Choose a game:", 102, 100, Color.white);
            newGameButton.render();
            loadGameButton.render();
            closeButton.render();

            // now draw a list of all of the save files (or as many as will fit)
            for (int i = 0; i < fileInfo.size(); i++) {
                StringRender.drawString(StringRender.font24, fileInfo.get(i), 120, 120 + (i * 25),
                        (i == selectedIndex ? Color.red : Color.white));
            }
        }

        @Override
        public void destroy() {
            // no-op
        }
    }
}