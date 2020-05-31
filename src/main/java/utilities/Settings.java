/**
 * The Settings class contains game settings and methods to save/load settings
 * from a file.
 *
 * This class should maybe be redesigned to handle all the settings stuff
 * statically? Meh
 */
package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author brooks42
 */
public class Settings {

    public static HashMap<String, String> settings;
    public static int SCREEN_WIDTH = 800;
    public static int SCREEN_HEIGHT = 600;
    public static String SETTINGS_FILE_LOC = "settings.chkem";
    public static String GAME_TITLE = "Quick Bounce Game";
    public static String SETTING_VOLUME = "volume";
    
    /**
     * Pulls in the Settings from the passed file.
     */
    public static void input() {
        settings = SettingsImporter.importSettings(new File(SETTINGS_FILE_LOC));
    }

    /**
     * Outputs the system settings to the passed file.
     */
    public static void output() {
        SettingsExporter.export(new File(SETTINGS_FILE_LOC));
    }

    /**
     * Returns the "default" settings for this game.
     *
     * @return
     */
    public static HashMap<String, String> defaultSettings() {
        HashMap<String, String> defaults = new HashMap<String, String>();
        defaults.put(SETTING_VOLUME, "1");
        return defaults;
    }

    /**
     *
     */
    static class SettingsImporter {

        /**
         * Imports the settings from the passed File.
         *
         * @param file
         */
        public static HashMap<String, String> importSettings(File file) {
            System.out.println("Importing settings...");
            JSONParser parser = new JSONParser();
            try {
                if (!file.exists()) {
                    System.out.println("Settings file doesn't exist, creating it");
                    settings = Settings.defaultSettings();
                } else {

                    settings = new HashMap<String, String>();
                    JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));

                    Set<String> obj = jsonObject.keySet();

                    for (String object : obj) {
                        settings.put(object, (String) jsonObject.get(object));
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return settings;
        }
    }

    /**
     *
     */
    static class SettingsExporter {

        /**
         * Exports the game settings to the passed File.
         *
         * @param file
         */
        public static void export(File file) {

            try {
                JSONObject json = new JSONObject();
                Set<String> set = settings.keySet();

                for (String setting : set) {
                    json.put(setting, (String) settings.get(setting));
                }

                FileWriter fw = new FileWriter(file);
                fw.write(json.toJSONString());
                fw.flush();
                fw.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
