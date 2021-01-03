/**
 * The Settings class contains game settings and methods to save/load settings
 * from a file.
 *
 * This class should maybe be redesigned to handle all the settings stuff
 * statically? Meh
 */
package desktopkt.utils

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.*

/**
 *
 * @author brooks42
 */
object Settings {

    lateinit var settings: MutableMap<String, Any>
    var SCREEN_WIDTH = 800
    var SCREEN_HEIGHT = 600
    var SETTINGS_FILE_LOC = "settings.chkem"
    var GAME_TITLE = "Quick Bounce Game"
    var SETTING_VOLUME = "volume"

    /**
     * Pulls in the Settings from the passed file.
     */
    fun input() {
        settings = SettingsImporter.importSettings(File(SETTINGS_FILE_LOC))
    }

    /**
     * Outputs the system settings to the passed file.
     */
    fun output() {
        SettingsExporter.export(File(SETTINGS_FILE_LOC))
    }

    /**
     * Returns the "default" settings for this game.
     *
     * @return
     */
    fun defaultSettings(): MutableMap<String, Any> {
        val defaults = mutableMapOf<String, Any>()
        defaults[SETTING_VOLUME] = 1
        return defaults
    }

    /**
     *
     */
    internal object SettingsImporter {
        /**
         * Imports the settings from the passed File.
         *
         * @param file
         */
        fun importSettings(file: File): MutableMap<String, Any> {
            println("Importing settings...")
            val parser = JSONParser()
            val settings = defaultSettings()
            try {
                if (file.exists()) {
                    val jsonObject = parser.parse(FileReader(file)) as JSONObject
                    jsonObject.keys.forEach {
                        val key = it as String
                        jsonObject[it]?.let {
                            settings[key] = it
                        }
                    }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return settings
        }
    }

    /**
     *
     */
    internal object SettingsExporter {

        /**
         * Exports the game settings to the passed File.
         *
         * @param file
         */
        fun export(file: File) {
            try {
                val json = JSONObject()
                val set: Set<String?> = settings.keys
                for (setting in set) {
                    json[setting] = settings[setting]
                }
                val fw = FileWriter(file)
                fw.write(json.toJSONString())
                fw.flush()
                fw.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}