package desktopkt.states

import com.jme3.app.Application
import com.jme3.asset.AssetManager
import com.jme3.collision.CollisionResults
import com.jme3.input.MouseInput
import com.jme3.input.controls.ActionListener
import com.jme3.input.controls.KeyTrigger
import com.jme3.input.controls.MouseButtonTrigger
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.Ray
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Node
import com.jme3.scene.shape.Box
import com.simsilica.lemur.*
import desktopkt.Base3dQbgState
import desktopkt.multiplayer.Server
import desktopkt.states.fight.*
import desktopkt.states.fight.messages.SummonMessage
import desktopkt.states.fight.messages.SummonMessageProcessor
import desktopkt.states.fight.views.PlayerStatsView
import desktopkt.states.fight.views.UnitSummonView
import desktopkt.utils.ImageManager
import org.lwjgl.input.Keyboard


/**
 * The FightScreen is so that users can battle on a pseudo-3d plane
 *
 * The fights occur between 2 teams, with each team having an amount of mana and deploying soldiers to attack
 * across the board toward the enemy. When 2 units fight they bounce backwards a bit and then return, with different
 * units having different amounts that they throw enemies back.
 *
 * At the end of the fight if the player loses it's game over or something
 */
class FightScreen : Base3dQbgState() {

    lateinit var hudNode: Container

    lateinit var unitNode: Node

    lateinit var arenaNode: Node

    lateinit var fight: Fight

    lateinit var unitFactory: UnitFactory

    lateinit var server: Server

    lateinit var unitSummonView: UnitSummonView

    lateinit var playerStatsView: PlayerStatsView

    var selectedUnitType = UnitType.HumanKnight

    var unitList = arrayListOf<UnitView>()

    var lanes = arrayListOf<SummonLane>()

    private val defaultCameraPosition = Vector3f(0F, 0.71F, 1F)
    private val defaultCameraFacing = Vector3f(0F, 0.25F, 0F)

    override fun initialize(app: Application) {
        super.initialize(app)

        unitFactory = UnitFactory(application, ImageManager(application.assetManager))

        initServer()
        initHud()
        initArena()
        initSounds()
        initPlayers()
    }

    private fun initServer() {
        server = Server()
        val processor = SummonMessageProcessor(this)

        // register each of the message processor classes here, we'll need to unregister them later as well
        server.registerProcessor(processor, SummonMessage::class.java)
    }

    private fun cleanupServer() {
        server.unregisterProcessor(SummonMessage::class.java)
    }

    fun startFight(singlePlayer: Boolean = true, fight: Fight) {
        this.fight = fight
    }

    private fun initHud() {
        hudNode = Container()

        val width = application.guiViewPort.camera.width.toFloat()
        val height = application.guiViewPort.camera.height.toFloat()

        hudNode.setLocalTranslation(0F, height, 0F)

        playerStatsView = PlayerStatsView()

        playerStatsView.setLocalTranslation(50F, height - 100F, 0F)

        val unitSummonViewBuilder = UnitSummonView.Builder()
        unitSummonViewBuilder.addButton(UnitType.HumanKnight) {
            println("HumanKnight")
            selectSummonUnitType(UnitType.HumanKnight)
        }
        unitSummonViewBuilder.addButton(UnitType.HumanSpearman) {
            println("HumanSpearman")
            selectSummonUnitType(UnitType.HumanSpearman)
        }
        unitSummonViewBuilder.addButton(UnitType.HumanArcher) {
            println("HumanArcher")
            selectSummonUnitType(UnitType.HumanArcher)
        }
        unitSummonViewBuilder.addButton(UnitType.HumanPaladin) {
            println("HumanPaladin")
            selectSummonUnitType(UnitType.HumanPaladin)
        }
        unitSummonViewBuilder.addButton(UnitType.HumanAssassin) {
            println("HumanAssassin")
            selectSummonUnitType(UnitType.HumanAssassin)
        }
        unitSummonViewBuilder.addButton(UnitType.HumanWizard) {
            println("HumanWizard")
            selectSummonUnitType(UnitType.HumanWizard)
        }
        unitSummonView = unitSummonViewBuilder.view
        unitSummonView.setLocalTranslation(50F, height - 50F, 0F)
    }

    private fun initArena() {

        arenaNode = Node()
        unitNode = Node()

        SummonLane.laneZPositions.forEachIndexed { index, _ ->
            lanes.add(SummonLane(index, application.assetManager))
        }
    }

    private fun initSounds() {

    }

    private fun initPlayers() {

    }

    private val clickListener = ActionListener { name, mouseDown, _ ->
        // only respond on mouse up events
        if (mouseDown) {
            return@ActionListener
        }

        // Reset results list.
        val results = CollisionResults()

        println("----- click -----")
        val cursorPosition = application.inputManager.cursorPosition
        println("cursorPosition = $cursorPosition")
        val rayOrigin = application.camera.getWorldCoordinates(cursorPosition, 0f).clone()
        println("rayOrigin = $rayOrigin")
        val dir = application.camera.getWorldCoordinates(cursorPosition, 1f)
                .subtractLocal(rayOrigin).normalizeLocal()
        println("dir = $dir")
        // Aim the ray from the clicked spot forwards.
        val ray = Ray(rayOrigin, dir)
        println("ray = $ray")

        val collisionCount = application.rootNode.collideWith(ray, results)

        if (collisionCount > 0) {

            pickHitsForPick(results).forEach {
                // if the player's click struck a lane, doing some iteration dev so just spawn a knight at that lane for now
                for (lane in lanes) {
                    if (lane.geometry == it.geometry) {
                        processSummon(lane)
                    }
                }
            }
        }
        println("-----")
    }

    // returns a list of all the unique geometries hit by the collision results, in the
    // reversed order that they were struck (so this returns the closest hits for each geometry)
    private fun pickHitsForPick(results: CollisionResults): List<PickHit> {

        val uniqueGeometryHitMap = mutableMapOf<Geometry, PickHit>()

        results.reversed().forEach {
            uniqueGeometryHitMap[it.geometry] = PickHit(it.geometry, it.contactPoint, it.contactNormal)
        }

        return uniqueGeometryHitMap.values.toList()
    }

    fun spawnUnitOnLane(lane: SummonLane, unitType: UnitType) {

        val unit = unitFactory.unit(unitType)
        val unitView = unitFactory.nodeForUnit(lane.geometry.localTranslation, unit)

        if (unitType.isHuman) {
            unitView.location.x -= SummonLane.laneWidth / 2
        } else {
            unitView.location.x += SummonLane.laneWidth / 2
        }

        unitList.add(unitView)
        unitNode.apply {
            this.attachChild(unitView.geom)
        }
    }

    private fun processSummon(lane: SummonLane) {
        if (fight.canPlayerOneAffordUnit(selectedUnitType)) {
            fight.playerOneSpendMana(selectedUnitType)
            val message = SummonMessage(selectedUnitType, lane.index)
            server.send(message)
        }
    }

    private val keyListener = ActionListener { name, down, _ ->
        if (!down) {
            when (name) {
                selectKnight -> {
                    selectSummonUnitType(UnitType.HumanKnight)
                }
                selectSpearman -> {
                    selectSummonUnitType(UnitType.HumanSpearman)
                }
                selectArcher -> {
                    selectSummonUnitType(UnitType.HumanArcher)
                }
                selectPaladin -> {
                    selectSummonUnitType(UnitType.HumanPaladin)
                }
                selectAssassin -> {
                    selectSummonUnitType(UnitType.HumanAssassin)
                }
                selectWizard -> {
                    selectSummonUnitType(UnitType.HumanWizard)
                }
            }
        }
    }

    private fun selectSummonUnitType(unitType: UnitType) {
        selectedUnitType = unitType

        // TODO: update the UI to show which unit is selected
    }

    private fun isUnitInDamageSlice(unit: UnitView): Boolean {
        if (unit.location.x > (SummonLane.laneWidth / 2) ||
                unit.location.x < -(SummonLane.laneWidth / 2)) {
            return true
        }
        return false
    }

    override fun onEnable() {
        application.guiNode.attachChild(hudNode)
        application.rootNode.attachChild(arenaNode)
        application.rootNode.attachChild(unitNode)

        application.guiNode.attachChild(playerStatsView)
        application.guiNode.attachChild(unitSummonView)

        application.setDisplayStatView(false)
        application.camera.isParallelProjection = false
        application.camera.location = defaultCameraPosition
        application.camera.frustumNear = 0.9F
        application.camera.lookAt(defaultCameraFacing, application.camera.up)

        lanes.forEach {
            arenaNode.attachChild(it.geometry)
        }

        application.inputManager.addMapping(leftClick, MouseButtonTrigger(MouseInput.BUTTON_LEFT))
        application.inputManager.addMapping(rightClick, MouseButtonTrigger(MouseInput.BUTTON_RIGHT))

        application.inputManager.addMapping(selectKnight, KeyTrigger(Keyboard.KEY_1))
        application.inputManager.addMapping(selectSpearman, KeyTrigger(Keyboard.KEY_2))
        application.inputManager.addMapping(selectArcher, KeyTrigger(Keyboard.KEY_3))
        application.inputManager.addMapping(selectPaladin, KeyTrigger(Keyboard.KEY_4))
        application.inputManager.addMapping(selectAssassin, KeyTrigger(Keyboard.KEY_5))
        application.inputManager.addMapping(selectWizard, KeyTrigger(Keyboard.KEY_6))
        application.inputManager.addListener(clickListener, leftClick)
        application.inputManager.addListener(clickListener, rightClick)
        application.inputManager.addListener(keyListener, selectKnight)
        application.inputManager.addListener(keyListener, selectSpearman)
        application.inputManager.addListener(keyListener, selectArcher)
        application.inputManager.addListener(keyListener, selectPaladin)
        application.inputManager.addListener(keyListener, selectAssassin)
        application.inputManager.addListener(keyListener, selectWizard)
    }

    override fun onDisable() {
        application.guiNode.detachChild(hudNode)

        application.guiNode.attachChild(playerStatsView)
        application.guiNode.attachChild(unitSummonView)

        application.rootNode.detachAllChildren()

        // TODO: gotta delete all the other mappings too
        application.inputManager.deleteMapping(leftClick)
        application.inputManager.deleteMapping(rightClick)
    }

    override fun cleanup(app: Application) {
        cleanupServer()
    }

    override fun update(tpf: Float) {

        // TODO: all the stuff below should be moved into this fight update instead...
        fight.update(tpf)

        playerStatsView.update(fight.playerOne)

        val unitsToRemove = mutableListOf<UnitView>()
        unitList.forEach { it.update(tpf) }

        for (unit in unitList) {
            if (isUnitInDamageSlice(unit)) {
                print("ping")
                unitsToRemove.add(unit)
                fight.dealDamage(unit.unit)
            }
        }

        unitsToRemove.forEach {
            destroyUnit(it)
        }
    }

    private fun destroyUnit(unit: UnitView) {
        unit.geom.removeFromParent()
        unitList.remove(unit)
    }

    companion object {

        const val leftClick = "leftClick"
        const val rightClick = "rightClick"

        const val selectKnight = "one"
        const val selectSpearman = "two"
        const val selectArcher = "three"
        const val selectPaladin = "four"
        const val selectAssassin = "five"
        const val selectWizard = "six"
    }
}

class SummonLane(val index: Int, assetManager: AssetManager) {

    val geometry: Geometry

    val box: Box

    init {
        box = Box(laneWidth / 2, 0F, laneHeight / 2)
        geometry = Geometry("Lane($index)", box)

        val mat = Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        mat.setColor("Color", ColorRGBA.randomColor())
        geometry.material = mat
        geometry.setLocalTranslation(0F, 0F, laneZPositions[index])
    }

    companion object {

        // this way the lane is 1 unit long, could be helpful for speed calculations later on
        const val laneWidth = 1F

        const val arenaHeight = 0.5F
        const val laneCount = 16
        val laneHeight = arenaHeight / laneCount

        val laneZPositions: ArrayList<Float> by lazy {
            val lanePositionArray = arrayListOf<Float>()

                // lanes are 1/laneCount of the arena height
                val initPos = -(arenaHeight / 2)

                lanePositionArray.add(initPos)

                for (lane in 1 until laneCount) {
                    lanePositionArray.add(initPos + (laneHeight * lane))
                }

            lanePositionArray
        }
    }
}

data class PickHit(val geometry: Geometry,
                   val hitLocation: Vector3f,
                   val hitNormal: Vector3f)
