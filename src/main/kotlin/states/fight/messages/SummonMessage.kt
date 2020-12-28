package desktopkt.states.fight.messages

import desktopkt.multiplayer.Message
import desktopkt.multiplayer.MessageProcessor
import desktopkt.states.FightScreen
import desktopkt.states.fight.UnitType

class SummonMessage(val unitType: UnitType,
                    val lane: Int): Message() {

    override fun toString(): String {
        return "SummonMessage($uid, unitType=$unitType, lane=$lane)"
    }
}

class SummonMessageProcessor(val fightScreen: FightScreen): MessageProcessor<SummonMessage> {

    override fun process(message: SummonMessage) {
        print("received message $message")
        fightScreen.spawnKnightOnLane(fightScreen.lanes[message.lane], UnitType.HumanKnight)
        fightScreen.spawnOrcOnLane(fightScreen.lanes[message.lane])
    }
}