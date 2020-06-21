/*
 * The SaveGame class stores information for the current save game
 */
package desktopkt.utilities

import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import java.io.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.HashMap

/**
 *
 * @author brooks42
 */
class SaveGame(private val gameData: HashMap<String, Any?>) {

    var money: Int
        get() {
            return gameData[MONEY] as Int
        }
        set(value) {
            gameData[MONEY] = value
        }

    var wins: Int
        get() {
            return gameData[BATTLE_WINS] as Int
        }
        set(value) {
            gameData[BATTLE_WINS] = value
        }

    fun incrementMoney() {
        money += 1
    }

    fun incrementWins() {
        wins += 1
    }

    /**
     * Imports a HashMap from a passed File
     *
     * @param file
     * @return a HashMap of data from the file, or null if the file doesn't
     * exist (might also throw an error)
     */
    fun importFromFile(file: File?): SaveGame {
//        val loaded = HashMap<String?, String?>()
//        val parser = JSONParser()
//        try {
//            val jsonObject = parser.parse(FileReader(file)) as JSONObject
//            val obj: Set<String?> = jsonObject.keys
//            for (`object` in obj) {
//                loaded[`object`] = jsonObject[`object`] as String?
//            }
//        } catch (e: FileNotFoundException) {
//            e.printStackTrace()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        return loaded

        return SaveGame(HashMap())
    }

    /**
     * Exports the current save game to a file.
     */
    fun exportToFile() {
        try {
            val json = JSONObject()
            val date = Date()
            val set: Set<String> = gameData.keys
            json["save_date"] = DateFormat.getInstance().format(date)
            for (setting in set) {
                json[setting] = gameData[setting]
            }
            val dir = File(SAVE_LOC)
            if (!dir.exists()) {
                print("Save directory doesn't exist, creating it... ")
                val worked = dir.mkdirs()
                println(worked)
            }
            val file = File(SAVE_LOC + "savegame" + System.currentTimeMillis())
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


    /**
     * Upgrades the player's HP by 5
     */
    fun upgradeHP() {
        println("Upgrade HP")
//        if (money >= upgradeHP) {
//            money -= upgradeHP
//            var bonus = 0
//            try {
//                bonus = gameData[UPGRADE_HP]!!.toInt()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            SaveGame.gamedata[UPGRADE_HP] = "" + (bonus + 1)
//            SaveGame.gamedata[MONEY] = "" + money
//        }
    }

    /**
     * Upgrades the player's Mana by 10
     */
    fun upgradeMana() {
        println("Upgrade Mana")
//        if (money >= upgradeMana) {
//            money -= upgradeMana
//            var bonus = 0
//            try {
//                bonus = gameData[UPGRADE_MANA]!!.toInt()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            gameData[UPGRADE_MANA] = "" + (bonus + 1)
//            gameData[MONEY] = "" + money
//        }
    }

    /**
     * Upgrades the player's Mana Regen by .0025
     */
    fun upgradeManaReg() {
        println("Upgrade Mana Regen")
//        if (money >= upgradeManaReg) {
//            money -= upgradeManaReg
//            var bonus = 0
//            try {
//                bonus = gameData[UPGRADE_MANAREG]!!.toInt()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            gameData[UPGRADE_MANAREG] = "" + (bonus + 1)
//            gameData[MONEY] = "" + money
//        }
    }

    /**
     * Upgrades the player's Knights by 1
     */
    fun upgradeKnights() {
        println("Upgrade Knights")
//        if (money >= upgradeKnights) {
//            money -= upgradeKnights
//            var bonus = 0
//            try {
//                bonus = gameData[UPGRADE_KNIGHTS]!!.toInt()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            gameData[UPGRADE_KNIGHTS] = "" + (bonus + 1)
//            gameData[MONEY] = "" + money
//        }
    }

    /**
     * Upgrades the player's Spearmen by 1
     */
    fun upgradeSpearmen() {
        println("Upgrade Spearmen")
//        if (money >= upgradeSpearmen) {
//            money -= upgradeSpearmen
//            var bonus = 0
//            try {
//                bonus = gameData[UPGRADE_SPEARMEN]!!.toInt()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            gameData[UPGRADE_SPEARMEN] = "" + (bonus + 1)
//            gameData[MONEY] = "" + money
//        }
    }

    /**
     * Upgrades the player's Archers by 1
     */
    fun upgradeArchers() {
        println("Upgrade Archers")
//        if (money >= upgradeArchers) {
//            money -= upgradeArchers
//            var bonus = 0
//            try {
//                bonus = gameData[UPGRADE_ARCHERS]!!.toInt()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            gameData[UPGRADE_ARCHERS] = "" + (bonus + 1)
//            gameData[MONEY] = "" + money
//        }
    }

    /**
     * Upgrades the player's Paladins by 1, or unlocks them if they're not
     * available
     */
    fun unlockOrUpgradePaladin() {
        println("Upgrade/Unlock Paladin")
//        if (mustUnlockPaladins) {
//            if (money >= upgradePaladins) {
//                gameData[UNLOCKED_PALADINS] = "true"
//                gameData[MONEY] = "" + money
//            }
//        } else if (money >= upgradePaladins) {
//            money -= upgradePaladins
//            var bonus = 0
//            try {
//                bonus = SaveGame.gameData[UPGRADE_PALADINS]!!.toInt()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            gameData[UPGRADE_PALADINS] = "" + (bonus + 1)
//            gameData[MONEY] = "" + money
//        }
    }

    /**
     * Upgrades the player's Wizards by 1, or unlocks them if they're not
     * available
     */
    fun unlockOrUpgradeWizard() {
        println("Upgrade/Unlock Wizards")
//        if (mustUnlockWizards) {
//            if (money >= upgradeWizards) {
//                gameData[UNLOCKED_WIZARDS] = "true"
//                gameData[MONEY] = "" + money
//            }
//        } else if (money >= upgradeWizards) {
//            money -= upgradeWizards
//            var bonus = 0
//            try {
//                bonus = gameData[UPGRADE_WIZARDS]!!.toInt()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            gameData[UPGRADE_WIZARDS] = "" + (bonus + 1)
//            gameData[MONEY] = "" + money
//        }
    }

    /**
     * Upgrades the player's Assassins by 1, or unlocks them if they're not
     * available
     */
    fun unlockOrUpgradeAssassin() {
        println("Upgrade/Unlock Assassins")
//        if (mustUnlockAssassins) {
//            if (money >= upgradeAssassins) {
//                gameData[UNLOCKED_ASSASSINS] = "true"
//                gameData[MONEY] = "" + money
//            }
//        } else if (money >= upgradeAssassins) {
//            money -= upgradeAssassins
//            var bonus = 0
//            try {
//                bonus = gameData[UPGRADE_ASSASSINS]!!.toInt()
//            } catch (e: Exception) {
//                // bonus should be 0 then
//            }
//            gameData[UPGRADE_ASSASSINS] = "" + (bonus + 1)
//            gameData[MONEY] = "" + money
//        }
    }

    companion object {

        const val SAVE_LOC = "saves/"
        const val MONEY = "money"
        const val BATTLE_WINS = "battle_wins"
        const val SAVE_DATE = "save_date"
        const val UPGRADE_HP = "upgrade_hp"
        const val UPGRADE_MANA = "upgrade_mana"
        const val UPGRADE_MANAREG = "upgrade_manareg"
        const val UPGRADE_KNIGHTS = "upgrade_knights"
        const val UPGRADE_SPEARMEN = "upgrade_spear"
        const val UPGRADE_ARCHERS = "upgrade_archer"
        const val UPGRADE_PALADINS = "upgrade_paladin"
        const val UPGRADE_WIZARDS = "upgrade_wizard"
        const val UPGRADE_ASSASSINS = "upgrade_assassin"
        const val UNLOCKED_PALADINS = "unlocked_paladin"
        const val UNLOCKED_WIZARDS = "unlocked_wizard"
        const val UNLOCKED_ASSASSINS = "unlocked_assassin"

        /**
         * Returns a list of all of the files in the SAVE_LOC directory.
         *
         * If no files exist, a 0-element array is returned.
         *
         * @return
         */
        val listOfFiles: Array<File?>
            get() {
                val dir = File(SaveGame.SAVE_LOC)
                if (dir.exists() && dir.isDirectory) {
                    val checkem = dir.listFiles()
                    Arrays.sort(checkem)
                    val ls: List<*> = Arrays.asList(*checkem)
                    Collections.reverse(ls)
                    return ls.toTypedArray() as Array<File?>
                }
                return arrayOfNulls(0)
            }

        val defaultSave: SaveGame
            get() {
                val defaults = HashMap<String, Any?>()
                defaults[MONEY] = 100
                defaults[BATTLE_WINS] = 0
                return SaveGame(defaults)
            }
    }
}