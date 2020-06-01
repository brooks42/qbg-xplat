/*
 * The SaveGame class stores information for the current save game
 */
package utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author brooks42
 */
public class SaveGame {

    public static HashMap<String, String> gamedata;
    public static final String SAVE_LOC = "saves/";
    public static final String MONEY = "money";
    public static final String BATTLE_WINS = "battle_wins";
    public static final String SAVE_DATE = "save_date";
    public static final String UPGRADE_HP = "upgrade_hp";
    public static final String UPGRADE_MANA = "upgrade_mana";
    public static final String UPGRADE_MANAREG = "upgrade_manareg";
    public static final String UPGRADE_KNIGHTS = "upgrade_knights";
    public static final String UPGRADE_SPEARMEN = "upgrade_spear";
    public static final String UPGRADE_ARCHERS = "upgrade_archer";
    public static final String UPGRADE_PALADINS = "upgrade_paladin";
    public static final String UPGRADE_WIZARDS = "upgrade_wizard";
    public static final String UPGRADE_ASSASSINS = "upgrade_assassin";
    public static final String UNLOCKED_PALADINS = "unlocked_paladin";
    public static final String UNLOCKED_WIZARDS = "unlocked_wizard";
    public static final String UNLOCKED_ASSASSINS = "unlocked_assassin";

    /**
     * Returns a list of all of the files in the SAVE_LOC directory.
     *
     * If no files exist, a 0-element array is returned.
     *
     * @return
     */
    public static File[] getListOfFiles() {
        File dir = new File(SAVE_LOC);
        if (dir.exists() && dir.isDirectory()) {
            File[] checkem = dir.listFiles();
            Arrays.sort(checkem);
            List ls = Arrays.asList(checkem);
            Collections.reverse(ls);
            return (File[]) ls.toArray();

        }
        return new File[0];
    }

    /**
     * Adds 1 to the player's money
     */
    public static void incrementMoney() {
        gamedata.put("money", "" + (Integer.parseInt(gamedata.get("money")) + 1));
    }

    /**
     * Adds 1 to the player's wins
     */
    public static void incrementWins() {
        gamedata.put("battle_wins", "" + (Integer.parseInt(gamedata.get("battle_wins")) + 1));
    }

    public static HashMap<String, String> getDefaultSave() {
        HashMap<String, String> defaults = new HashMap<String, String>();
        defaults.put("battle_wins", "0");
        defaults.put("money", "100");
        return defaults;
    }

    /**
     * Imports a HashMap from a passed File
     *
     * @param file
     * @return a HashMap of data from the file, or null if the file doesn't
     * exist (might also throw an error)
     */
    public static HashMap<String, String> importFromFile(File file) {
        HashMap<String, String> loaded = new HashMap<String, String>();
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file));

            Set<String> obj = jsonObject.keySet();

            for (String object : obj) {
                loaded.put(object, (String) jsonObject.get(object));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return loaded;
    }

    /**
     * Exports the current save game to a file.
     */
    public static void exportToFile() {

        try {
            JSONObject json = new JSONObject();
            Date date = new Date();
            Set<String> set = gamedata.keySet();

            json.put("save_date", DateFormat.getInstance().format(date));

            for (String setting : set) {
                json.put(setting, (String) gamedata.get(setting));
            }

            File dir = new File(SAVE_LOC);
            if (!dir.exists()) {
                System.out.print("Save directory doesn't exist, creating it... ");
                boolean worked = dir.mkdirs();
                System.out.println(worked);
            }

            File file = new File(SAVE_LOC + "savegame" + System.currentTimeMillis());
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
