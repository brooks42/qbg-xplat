package desktopkt.states.fight

/**
 * The Fight class is the current game state -- players, units, anything that needs to be transferred remotely
 * to sync up playing with other players, etc
 */
class Fight(val playerOne: Player,
            val playerTwo: Player) {

    val rules = FightRules()

    fun canPlayerOneAffordUnit(unitType: UnitType): Boolean {
        return rules.canAffordToSummon(playerOne.mana, unitType)
    }
}