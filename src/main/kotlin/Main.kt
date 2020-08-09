
package desktopkt

import com.jme3.system.AppSettings

fun main() {
    val app = SimpleApp()
    val settings = AppSettings(true)
    settings.title = "QuickBounceGame -- Desktop"
    app.setSettings(settings)
    app.start()
}