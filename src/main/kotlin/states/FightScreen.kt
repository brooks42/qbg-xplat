package desktopkt.states

import com.jme3.app.Application
import com.jme3.app.state.BaseAppState
import com.jme3.collision.CollisionResults
import com.jme3.input.MouseInput
import com.jme3.input.controls.ActionListener
import com.jme3.input.controls.KeyTrigger
import com.jme3.input.controls.MouseButtonTrigger
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.FastMath
import com.jme3.math.Ray
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.simsilica.lemur.Container
import desktop.QbgApplication
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
class FightScreen : BaseAppState() {

    lateinit var application: QbgApplication

    lateinit var hudNode: Container

    override fun initialize(app: Application?) {
        application = app as QbgApplication

        initHud()
        initArena()
        initSounds()
        initPlayers()
    }

    private fun initHud() {
        hudNode = Container()
    }

    private fun initArena() {

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

        println("click")

        if (name == pick3d || name == pick2d) {
            // Reset results list.
            val results = CollisionResults()

            if (name == pick2d) {
                println("-----")
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

                // for now we're only testing against the guinode since the map is part of the guinode
                // in the future when the game has 3d parts to it like I want, this will be less consolidated
                val collisionCount = application.guiNode.collideWith(ray, results)
                println("collisionCount for 2d collision == $collisionCount")
                println("-----")

            } else {
                // Aim the ray from camera location in camera direction
                // (assuming crosshairs in center of screen).
                val ray = Ray(application.camera.location, application.camera.direction)
                // Collect intersections between ray and all nodes in results list.
                application.rootNode.collideWith(ray, results)
                println("results.size() for 3d click == ${results.size()}")
            }

            // Print the results so we see what is going on
            for (i in 0 until results.size()) {
                // For each "hit", we know distance, impact point, geometry.
                val collision = results.getCollision(i)
                val dist = collision.distance
                val pt = collision.contactPoint
                val target = collision.geometry.name
                println("Selection #$i: $target at $pt, $dist WU away.")
            }
        }
    }

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

//        application.flyByCamera.isEnabled = true

        application.camera.isParallelProjection = false
        application.camera.location = defaultCameraPosition
        application.camera.frustumFar = 500F
        application.camera.lookAt(defaultCameraFacing, application.camera.up)

        // this way the lane is 1 unit long, could be helpful for speed calculations later on
        val laneWidth = 0.5F
        val laneHeight = 0.1F
        val laneZPositions = arrayOf(-0.1F, 0F, 0.1F)

        laneZPositions.forEach {
            val b = Box(laneWidth, 0F, laneHeight)
            val geom = Geometry("Box($z)", b)

            val mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
            mat.setColor("Color", ColorRGBA.randomColor())
            geom.material = mat
            geom.setLocalTranslation(0F, 0F, it)

            application.rootNode.apply {
                this.attachChild(geom)
            }
        }

        setAxes()

        application.inputManager.addMapping(pick2d, MouseButtonTrigger(MouseInput.BUTTON_LEFT))
        application.inputManager.addMapping(pick3d, MouseButtonTrigger(MouseInput.BUTTON_RIGHT))

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
        application.inputManager.addListener(clickListener, pick2d)
        application.inputManager.addListener(clickListener, pick3d)
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
    }

    private fun setAxes() {
        // x axis
        var b = Box(100F, 0.02F, 0.02F)
        var geom = Geometry("x axis line", b)

        var mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        mat.setColor("Color", ColorRGBA.Red)
        geom.material = mat

        application.rootNode.apply {
            this.attachChild(geom)
        }

        for (x in -50..50) {
            var b = Box(0.03F, 0.03F, 0.03F)
            var geom = Geometry("tickBox x($x)", b)

            var mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
            mat.setColor("Color", ColorRGBA.Gray)
            geom.material = mat
            geom.setLocalTranslation(x.toFloat(), 0F, 0F)

            application.rootNode.apply {
                this.attachChild(geom)
            }
        }

        // y axis
        b = Box(0.02F, 100F, 0.02F)
        geom = Geometry("y axis line", b)

        mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        mat.setColor("Color", ColorRGBA.Green)
        geom.material = mat

        application.rootNode.apply {
            this.attachChild(geom)
        }

        for (y in -50..50) {
            var b = Box(0.03F, 0.03F, 0.03F)
            var geom = Geometry("tickBox y($y)", b)

            var mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
            mat.setColor("Color", ColorRGBA.Gray)
            geom.material = mat
            geom.setLocalTranslation(0F, y.toFloat(), 0F)

            application.rootNode.apply {
                this.attachChild(geom)
            }
        }

        // z axis
        b = Box(0.02F, 0.02F, 100F)
        geom = Geometry("z axis line", b)

        mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        mat.setColor("Color", ColorRGBA.Blue)
        geom.material = mat

        application.rootNode.apply {
            this.attachChild(geom)
        }

        for (z in -50..50) {
            var b = Box(0.03F, 0.03F, 0.03F)
            var geom = Geometry("tickBox z($z)", b)

            var mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
            mat.setColor("Color", ColorRGBA.Gray)
            geom.material = mat
            geom.setLocalTranslation(0F, 0F, z.toFloat())

            application.rootNode.apply {
                this.attachChild(geom)
            }
        }
    }

    override fun onDisable() {
        application.guiNode.detachChild(hudNode)

        application.inputManager.deleteMapping(pick2d)
        application.inputManager.deleteMapping(pick3d)
    }

    override fun cleanup(app: Application?) {

    }

    companion object {

        const val pick2d = "pick2d"
        const val pick3d = "pick3d"

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
        const val printPos = "printPos"
    }
}