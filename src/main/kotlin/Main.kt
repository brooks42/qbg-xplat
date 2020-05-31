
package desktopkt

import com.jme3.system.AppSettings

fun main(args: Array<String>) {
    val app = SimpleApp()
    val settings = AppSettings(true)
    settings.title = "My Awesome Game"
    app.setSettings(settings)
    app.start()
}