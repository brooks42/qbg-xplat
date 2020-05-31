package states;

import java.util.ArrayList;

/**
 * The GameState is a singleton-pattern class containing data for the current
 * state of the game.
 *
 * It also takes action when the user produces input of some sort.
 *
 * @author brooks42
 *
 */
// TODO: this just needs to be completely migrated over to JME's app state stuff
public class GameStateController {

    private static GameStateController instance;
    public static final int LOADING_SCREEN = 0;    // the screen that's shown while loading the game
    public static final int MAIN_SCREEN = 1;
    public static final int BATTLE_SCREEN = 2;
    public static final int CAMPAIGN_SCREEN = 3;
    public static final int STORY_SCREEN = 4;
    public static final int GAME_END_SCREEN = 5;
    public static int currentState = LOADING_SCREEN;
//    public ArrayList<GameState> menuList;
    static boolean lobby = false;

    // sets up the GameState
    protected GameStateController() {
//        System.out.println("GameState init()");
//        menuList = new ArrayList<GameState>();

//        menuList.add(new LoadingScreen());
//        menuList.add(new MainScreen());
//        menuList.add(new BattleScreen());
//        menuList.add(new CampaignScreen());
//        menuList.add(new StoryScreen());
//        menuList.add(new GameEndScreen());

//        currentState = LOADING_SCREEN;
//        menuList.get(LOADING_SCREEN).setup();
    }

    /**
     * Tears down the current menu, and then changes the current menu to the
     * passed one and sets it up.
     *
     * @param state
     */
    public static void setState(int state) {
//        System.out.println("top lel");
//        if (getInstance() != null) {
//            System.out.println("not null");
//            GameState menu = getInstance().menuList.get(currentState);
//            menu.teardown();
//
//            currentState = state;
//            menu = getInstance().menuList.get(currentState);
//            menu.setup();
//        }
    }

    /**
     * Returns the current instance of this GameState.
     *
     * @return
     */
    public static GameStateController getInstance() {
        if (instance == null) {
            instance = new GameStateController();
        }
        return instance;
    }

    /**
     * Updates this GameState.
     */
    public static void update(float tpf) {
//        GameState menu = getInstance().menuList.get(currentState);
//        menu.input();
//        menu.update(dt);
    }

    /**
     * Draws the GameState.
     */
    public static void render() {
//        GameState menu = getInstance().menuList.get(currentState);
//        menu.render();
    }

    public static void mouseInput() {
        // does the mouse input for the current game state
        //((MouseListener)getInstance().menuList.get(currentState)).
        /*MouseEvent event = MouseX.getEvent();
         boolean do_press = event.isPressed || event.isRightPressed;
         boolean do_click = event.isClicked || event.isRightClicked;
         boolean do_move = event.dx != 0 || event.dy != 0;
         boolean do_drag = do_press && do_move;
         if (do_press) {
         ((MouseListener) getInstance().menuList.get(currentState)).mousePressed(event);
         }
         if (do_click) {
         ((MouseListener) getInstance().menuList.get(currentState)).mouseClicked(event);
         }
         if (do_move) {
         ((MouseListener) getInstance().menuList.get(currentState)).mouseMoved(event);
         }
         if (do_drag) {
         ((MouseListener) getInstance().menuList.get(currentState)).mouseDragged(event);
         }*/
    }
}
