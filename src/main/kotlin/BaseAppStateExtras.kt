package desktopkt

import com.jme3.app.state.BaseAppState
import com.jme3.math.Vector2f

// extension functions for using our GUI stuff

fun BaseAppState.leftAnchor(): Float {
    return 0F
}

fun BaseAppState.rightAnchor(): Float {
    return width()
}

// NOTE: this is flipped because of OpenGL+Lemur, the top of the screen is height and the bottom is 0
fun BaseAppState.topAnchor(): Float {
    return height()
}

fun BaseAppState.bottomAnchor(): Float {
    return 0F
}

fun BaseAppState.centerAnchor(): Vector2f {
    return Vector2f(rightAnchor() / 2, topAnchor() / 2)
}

fun BaseAppState.height(): Float {
    return this.application.context.settings.height.toFloat()
}

fun BaseAppState.width(): Float {
    return this.application.context.settings.width.toFloat()
}