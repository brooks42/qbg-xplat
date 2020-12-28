package desktopkt.states.fight

// TODO: this should be able to load and customize rules easily
class FightRules() {

    fun costToSummon(unitType: UnitType): Float {
        return when(unitType) {
            UnitType.HumanKnight -> 2.0f
            UnitType.HumanSpearman -> 2.0f
            UnitType.HumanPaladin -> 4.0f
            UnitType.HumanWizard -> 5.0f
            UnitType.HumanArcher -> 3.0f
            UnitType.HumanAssassin -> 4.0f
            UnitType.OrkKnight -> 2.0f
        }
    }

    fun canAffordToSummon(mana: Float, unitType: UnitType): Boolean {
        return mana > costToSummon(unitType)
    }
}