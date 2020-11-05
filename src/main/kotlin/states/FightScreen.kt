package desktopkt.states

import com.jme3.app.Application
import com.jme3.app.state.BaseAppState
import com.jme3.input.controls.ActionListener
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.simsilica.lemur.Container
import desktop.QbgApplication


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

        app.camera.isParallelProjection = true

        initHud()
        initArena()
        initSounds()
        initPlayers()
    }

    private fun initHud() {
        hudNode = Container()
    }

    private fun initArena() {

        val b = Box(1F, 1F, 1F)
        val geom = Geometry("Box", b)

        val mat = Material(application.assetManager, "Common/MatDefs/Misc/Unshaded.j3md")
        mat.setColor("Color", ColorRGBA.Blue)
        geom.material = mat
        geom.setLocalTranslation(0F, 0F, 10F)

        application.flyByCamera.isEnabled = true

        application.rootNode.apply {
            this.attachChild(geom)
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

//        if (name == pick3d || name == pick2d) {
//            // Reset results list.
//            val results = CollisionResults()
//
//            if (name == pick2d) {
//                println("-----")
//                val cursorPosition = application.inputManager.cursorPosition
//                println("cursorPosition = $cursorPosition")
//                val rayOrigin = application.camera.getWorldCoordinates(cursorPosition, 0f).clone()
//                println("rayOrigin = $rayOrigin")
//                val dir = application.camera.getWorldCoordinates(cursorPosition, 1f)
//                        .subtractLocal(rayOrigin).normalizeLocal()
//                println("dir = $dir")
//                // Aim the ray from the clicked spot forwards.
//                val ray = Ray(rayOrigin, dir);
//                println("ray = $ray")
//
//                // for now we're only testing against the guinode since the map is part of the guinode
//                // in the future when the game has 3d parts to it like I want, this will be less consolidated
//                val collisionCount = application.rootNode.collideWith(ray, results)
//                println("collisionCount for 2d collision == $collisionCount")
//                println("-----")
//
//                mapNode.children.forEach {
//                    println("${it.name} is at ${it.worldBound} <-> ($rayOrigin)")
//                }
//            } else {
//                // Aim the ray from camera location in camera direction
//                // (assuming crosshairs in center of screen).
//                val ray = Ray(application.camera.location, application.camera.direction)
//                // Collect intersections between ray and all nodes in results list.
//                application.guiNode.collideWith(ray, results)
//                println("results.size() for 3d click == ${results.size()}")
//            }
//
//            // Print the results so we see what is going on
//            for (i in 0 until results.size()) {
//                // For each "hit", we know distance, impact point, geometry.
//                val collision = results.getCollision(i)
//                val dist = collision.distance
//                val pt = collision.contactPoint
//                val target = collision.geometry.name
//                println("Selection #$i: $target at $pt, $dist WU away.")
//
//                fightNameToNumbers[target]?.let { startFight(it) }
//            }
//        }


    }

    private fun winFight() {

    }

    private fun loseFight() {

    }

    override fun onEnable() {
        application.guiNode.attachChild(hudNode)
    }

    override fun onDisable() {
        application.guiNode.detachChild(hudNode)
    }

    override fun update(tpf: Float) {
        super.update(tpf)
    }

    override fun cleanup(app: Application?) {

    }

    companion object {

        val pick2d = "pick2d"
        val pick3d = "pick3d"
    }
}