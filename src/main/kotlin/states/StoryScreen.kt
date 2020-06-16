/*
 * Shows a mostly black screen, with the story of the game scrolling up behind 
 * it. When complete (or when the player hits the Skip button), transitions to 
 * the Campaign screen.
 */
package states

import com.jme3.app.Application
import com.jme3.app.state.BaseAppState
import gui.PButton
import sprites.PSprite
import java.util.*

/**
 *
 * @author brooks42
 */
class StoryScreen : BaseAppState() {
    private val background1: PSprite? = null
    private val background2: PSprite? = null
    private val skipButton: PButton? = null
    private val lines: ArrayList<String>? = null
    private val scroll_speed = .15f
    private val draw_starting = 0f

    //        background1 = new PSprite(SpriteFactory.getSprite(
    //                ImageManager.getImage("game_map"), 0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT));
    //        background2 = new PSprite(SpriteFactory.getSprite(
    //                ImageManager.getImage("inlay"), 0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT));
    //
    //        skipButton = new PButton(new PSprite(SpriteFactory.getSprite(
    //                ImageManager.getImage("skip_button"), 727, 570, 73, 30)), new Texture[]{
    //                    ImageManager.getImage("skip_button"),
    //                    ImageManager.getImage("skip_button"),
    //                    ImageManager.getImage("skip_button")
    //                }) {
    //            @Override
    //            public void onButtonClicked() {
    //                super.onButtonClicked();
    //                skip();
    //            }
    //        };
    //
    //        try {
    //            // load the story file
    //            Scanner scanner = new Scanner(new BufferedReader(new FileReader("resources/scripts/storyline.checkem")));
    //            lines = new ArrayList<String>();
    //            while (scanner.hasNextLine()) {
    //                lines.add(scanner.nextLine());
    //            }
    //        } catch (FileNotFoundException e) {
    //            System.out.println("There was a problem loading the story file: " + e.getMessage());
    //        }
    // skips the story, going straight to the campaign screen
    fun skip() {
        GameStateController.setState(GameStateController.CAMPAIGN_SCREEN)
    }

    override fun initialize(app: Application) {}
    override fun cleanup(app: Application) {}
    override fun onEnable() {}
    override fun onDisable() {}
}