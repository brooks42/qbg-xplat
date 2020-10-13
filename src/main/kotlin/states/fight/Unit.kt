package desktopkt.states.fight

class Unit(val type: Type) {

    enum class Type {
        HumanKnight,
        HumanSpearman,
        HumanArcher,
        HumanPaladin,
        HumanWizard,
        HumanAssassin,
        OrkKnight
    }
}