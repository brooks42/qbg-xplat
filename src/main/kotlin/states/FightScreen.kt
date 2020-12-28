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
import com.simsilica.lemur.Container
import desktop.QbgApplication
import desktopkt.Base3dQbgState
import desktopkt.multiplayer.Message
import desktopkt.multiplayer.MessageProcessor
import desktopkt.multiplayer.Server
import desktopkt.states.fight.*
import desktopkt.states.fight.messages.SummonMessage
import desktopkt.states.fight.messages.SummonMessageProcessor
import models.ImageManager
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

    var selectedUnitType = UnitType.HumanKnight

    var unitList = arrayListOf<UnitView>()

    var lanes = arrayListOf<SummonLane>()

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
        val ray = Ray(rayOrigin, dir);
        println("ray = $ray")

        var collisionCount = application.guiNode.collideWith(ray, results)
        println("collisionCount for gui collision == $collisionCount")

        val consumed = guiHitTest(name, results)

        if (!consumed) {
            collisionCount = application.rootNode.collideWith(ray, results)
            println("collisionCount for root collision == $collisionCount")

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

    // returns whether the click was consumed or not
    private fun guiHitTest(name: String, collisionResults: CollisionResults): Boolean {
        // TODO: iterate through all the GUI elements

        for (i in 0 until collisionResults.size()) {
            // For each "hit", we know distance, impact point, geometry.
            val collision = collisionResults.getCollision(i)
            val dist = collision.distance
            val pt = collision.contactPoint
            val target = collision.geometry.name
            println("Selection #$i: $target at $pt, $dist WU away.")
            return true
        }

        return false
    }

    // quick iterating function to spawn a knight
    fun spawnKnightOnLane(lane: SummonLane, unitType: UnitType) {

        print("spawn human")
        val unit = unitFactory.unit(unitType)
        val unitView = unitFactory.nodeForUnit(lane.geometry.localTranslation, unit)
        unitView.location.x -= SummonLane.laneWidth / 2

        unitList.add(unitView)
        unitNode.apply {
            this.attachChild(unitView.geom)
        }
    }

    // quick iterating function to spawn an orc
    fun spawnOrcOnLane(lane: SummonLane) {

        print("spawn orc")
        val unit = unitFactory.unit(UnitType.OrkKnight)
        val unitView = unitFactory.nodeForUnit(lane.geometry.localTranslation, unit)
        unitView.location.x += SummonLane.laneWidth / 2

        unitList.add(unitView)
        unitNode.apply {
            this.attachChild(unitView.geom)
        }
    }

    private fun processSummon(lane: SummonLane) {
        if (fight.canPlayerOneAffordUnit(selectedUnitType)) {
            // TODO: spend the mana
            val message = SummonMessage(selectedUnitType, lane.index)
            server.send(message)
        }
    }

    // this is some debug code that can be removed in the future...
    private var arrayOfInputsBetweenEnters = arrayListOf<String>()
    private var betweenEnters = false

    private val keyListener = ActionListener { name, down, _ ->
        if (!down) {
            when (name) {
                resetPos -> {
                    application.camera.location = Vector3f(0F, 0F, 0F)
                }
                one -> {
                    arrayOfInputsBetweenEnters.add("1")
                }
                two -> {
                    arrayOfInputsBetweenEnters.add("2")
                }
                three -> {
                    arrayOfInputsBetweenEnters.add("3")
                }
                four -> {
                    arrayOfInputsBetweenEnters.add("4")
                }
                five -> {
                    arrayOfInputsBetweenEnters.add("5")
                }
                six -> {
                    arrayOfInputsBetweenEnters.add("6")
                }
                seven -> {
                    arrayOfInputsBetweenEnters.add("7")
                }
                eight -> {
                    arrayOfInputsBetweenEnters.add("8")
                }
                nine -> {
                    arrayOfInputsBetweenEnters.add("9")
                }
                zero -> {
                    arrayOfInputsBetweenEnters.add("0")
                }
                enter -> {
                    if (betweenEnters) {
                        teleportToEnteredLoc()
                    }
                    arrayOfInputsBetweenEnters.clear()
                    betweenEnters = !betweenEnters
                }
                x -> {
                    arrayOfInputsBetweenEnters.add("x")
                }
                y -> {
                    arrayOfInputsBetweenEnters.add("y")
                }
                z -> {
                    arrayOfInputsBetweenEnters.add("z")
                }
                printPos -> {
                    printCameraInfo()
                }
                upArrow -> {
                    application.camera.frustumNear += 0.1F
                }
                downArrow -> {
                    application.camera.frustumNear -= 0.1F
                }
            }
        }
    }

    // form is <enter> <vector> location <enter>
    private fun teleportToEnteredLoc() {
        var location = ""
        var axis: String? = null

        arrayOfInputsBetweenEnters.forEach {
            if (axis == null) {
                axis = it
            } else {
                location += it
            }
        }

        println("parsed location: $location")
        val pos = location.toFloatOrNull()

        if (pos != null) {
            var newLocation: Vector3f = application.camera.location
            val currentLoc = application.camera.location
            when (axis) {
                x -> {
                    newLocation = Vector3f(pos, currentLoc.y, currentLoc.z)
                }
                y -> {
                    newLocation = Vector3f(currentLoc.x, pos, currentLoc.z)
                }
                z -> {
                    newLocation = Vector3f(currentLoc.x, currentLoc.y, pos)
                }
            }
            println("teleporting to new location: $newLocation")
            application.camera.location = newLocation
            application.camera.lookAt(defaultCameraFacing, application.camera.up)
        }
    }

    private fun printCameraInfo() {
        println("camera info:")
        println("location: ${application.camera.location}")
        println("direction: ${application.camera.direction}")
        println("frustumNear: ${application.camera.frustumNear}")
        println("frustumFar: ${application.camera.frustumFar}")
        println("projection: ${application.camera.viewProjectionMatrix}")
    }

    private fun winFight() {

    }

    private fun loseFight() {

    }

    private val defaultCameraPosition = Vector3f(0F, 0.71F, 1F)
    private val defaultCameraFacing = Vector3f(0F, 0.25F, 0F)

    // projection matrix...
    /*[
 2.0362442  3.5557518E-10  -0.0019885192  0.001988519
 -0.0011548502  2.4439163  -1.1825665  -0.5526139
 -8.825806E-4  -0.43731478  -0.9037628  -0.78975177
 -8.790573E-4  -0.435569  -0.90015495  1.2094089
]*/

    override fun onEnable() {
        application.guiNode.attachChild(hudNode)
        application.rootNode.attachChild(arenaNode)
        application.rootNode.attachChild(unitNode)

//        application.flyByCamera.isEnabled = true
        application.setDisplayStatView(false)
        application.camera.isParallelProjection = false
        application.camera.location = defaultCameraPosition
        application.camera.frustumNear = 0.9F
        application.camera.lookAt(defaultCameraFacing, application.camera.up)

        lanes.forEach {
            arenaNode.attachChild(it.geometry)
        }

        // if (DEBUG) {
//        setAxes()
        // }

        application.inputManager.addMapping(leftClick, MouseButtonTrigger(MouseInput.BUTTON_LEFT))
        application.inputManager.addMapping(rightClick, MouseButtonTrigger(MouseInput.BUTTON_RIGHT))

        application.inputManager.addMapping(resetPos, KeyTrigger(Keyboard.KEY_SPACE))
        application.inputManager.addMapping(one, KeyTrigger(Keyboard.KEY_1))
        application.inputManager.addMapping(two, KeyTrigger(Keyboard.KEY_2))
        application.inputManager.addMapping(three, KeyTrigger(Keyboard.KEY_3))
        application.inputManager.addMapping(four, KeyTrigger(Keyboard.KEY_4))
        application.inputManager.addMapping(five, KeyTrigger(Keyboard.KEY_5))
        application.inputManager.addMapping(six, KeyTrigger(Keyboard.KEY_6))
        application.inputManager.addMapping(seven, KeyTrigger(Keyboard.KEY_7))
        application.inputManager.addMapping(eight, KeyTrigger(Keyboard.KEY_8))
        application.inputManager.addMapping(nine, KeyTrigger(Keyboard.KEY_9))
        application.inputManager.addMapping(zero, KeyTrigger(Keyboard.KEY_0))
        application.inputManager.addMapping(enter, KeyTrigger(Keyboard.KEY_RETURN))
        application.inputManager.addMapping(x, KeyTrigger(Keyboard.KEY_X))
        application.inputManager.addMapping(y, KeyTrigger(Keyboard.KEY_Y))
        application.inputManager.addMapping(z, KeyTrigger(Keyboard.KEY_Z))
        application.inputManager.addMapping(printPos, KeyTrigger(Keyboard.KEY_LCONTROL))
        application.inputManager.addMapping(upArrow, KeyTrigger(Keyboard.KEY_UP))
        application.inputManager.addMapping(downArrow, KeyTrigger(Keyboard.KEY_DOWN))
        application.inputManager.addListener(clickListener, leftClick)
        application.inputManager.addListener(clickListener, rightClick)
        application.inputManager.addListener(keyListener, resetPos)
        application.inputManager.addListener(keyListener, one)
        application.inputManager.addListener(keyListener, two)
        application.inputManager.addListener(keyListener, three)
        application.inputManager.addListener(keyListener, four)
        application.inputManager.addListener(keyListener, five)
        application.inputManager.addListener(keyListener, six)
        application.inputManager.addListener(keyListener, seven)
        application.inputManager.addListener(keyListener, eight)
        application.inputManager.addListener(keyListener, nine)
        application.inputManager.addListener(keyListener, zero)
        application.inputManager.addListener(keyListener, enter)
        application.inputManager.addListener(keyListener, x)
        application.inputManager.addListener(keyListener, y)
        application.inputManager.addListener(keyListener, z)
        application.inputManager.addListener(keyListener, printPos)
        application.inputManager.addListener(keyListener, upArrow)
        application.inputManager.addListener(keyListener, downArrow)
    }

    override fun onDisable() {
        application.guiNode.detachChild(hudNode)
        application.rootNode.detachAllChildren()

        // TODO: gotta delete all the other mappings too
        application.inputManager.deleteMapping(leftClick)
        application.inputManager.deleteMapping(rightClick)
    }

    override fun cleanup(app: Application) {
        cleanupServer()
    }

    override fun update(tpf: Float) {
        unitList.forEach { it.update(tpf) }
    }

    companion object {

        const val leftClick = "leftClick"
        const val rightClick = "rightClick"

        const val resetPos = "resetPos"
        const val one = "one"
        const val two = "two"
        const val three = "three"
        const val four = "four"
        const val five = "five"
        const val six = "six"
        const val seven = "seven"
        const val eight = "eight"
        const val nine = "nine"
        const val zero = "zero"
        const val enter = "enter"
        const val x = "x"
        const val y = "y"
        const val z = "z"
        const val upArrow = "upArrow"
        const val downArrow = "downArrow"
        const val printPos = "printPos"
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
        val laneWidth = 1F

        val arenaHeight = 0.5F
        val laneCount = 16
        val laneHeight = arenaHeight / laneCount

        val laneZPositions: ArrayList<Float> by lazy {
            var lanePositionArray = arrayListOf<Float>()

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
