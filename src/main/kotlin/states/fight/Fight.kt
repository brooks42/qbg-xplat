package desktopkt.states.fight

/**
 * The Fight class is the current game state -- players, units, anything that needs to be transferred remotely
 * to sync up playing with other players, etc
 */
class Fight(val playerOne: Player,
            val playerTwo: Player,
            val rules: FightRules) {

    fun update(tpf: Float) {
        playerOne.update(tpf)
        playerTwo.update(tpf)
    }

    fun dealDamage(unit: Unit) {
        if (unit.type.isHuman) {
            playerTwo.health--
        } else {
            playerOne.health--
        }
    }

    fun playerOneSpendMana(unitType: UnitType) {
        playerOne.spendMana(rules.costToSummon(unitType).toInt())
    }

    fun canPlayerOneAffordUnit(unitType: UnitType): Boolean {
        return rules.canAffordToSummon(playerOne.mana.toFloat(), unitType)
    }
}