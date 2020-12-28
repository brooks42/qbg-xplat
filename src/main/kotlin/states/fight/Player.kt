package desktopkt.states.fight

class Player(val name: String) {

    var mana: Float = defaultMana

    var units = arrayListOf<Unit>()

    /*
        public void loadStats() {
        // hp first
//        this.playerHealth = 20;
//        try {
//            playerHealth += Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_HP)) * 5;
//        } catch (Exception e) {
//            // oh well
//        }
//        this.playerHealthMax = playerHealth;
//
//        this.oppHealth = 20 + Unit.ENEMY_DIFFICULTY * 10;
//        this.oppHealthMax = oppHealth;
//
//        // mana time
//        this.playerMana = 100;
//        try {
//            playerMana += Integer.parseInt(SaveGame.gamedata.get(SaveGame.UPGRADE_MANA)) * 10;
//        } catch (Exception e) {
//            // oh well
//        }
//        this.playerManaMax = playerMana;
//        this.playerManaRegen = .005f;
//        try {
//            playerManaRegen += Float.parseFloat(SaveGame.gamedata.get(SaveGame.UPGRADE_MANAREG)) * .0025;
//        } catch (Exception e) {
//            // oh well
//        }
//
//        this.oppMana = 0 + Unit.ENEMY_DIFFICULTY * 10;
//        this.oppManaMax = 50 + Unit.ENEMY_DIFFICULTY * 20;
//        this.oppManaRegen = .005f + (float) Unit.ENEMY_DIFFICULTY / 100;
    }
     */

    companion object {

        val defaultMana = 10.0f

        val defaultHealth = 20
    }
}