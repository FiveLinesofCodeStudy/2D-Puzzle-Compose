@file:OptIn(ExperimentalComposeUiApi::class)

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*

fun KeyEvent.inputIfIsRight(): Boolean {
    if (this.key == Key.DirectionRight) {
        inputs.add(Right())
        return true
    }
    return false
}

fun KeyEvent.inputIfIsLeft(): Boolean {
    if (this.key == Key.DirectionLeft) {
        inputs.add(Left())
        return true
    }
    return false
}

fun KeyEvent.inputIfIsUp(): Boolean {
    if (this.key == Key.DirectionUp) {
        inputs.add(Up())
        return true
    }
    return false
}

fun KeyEvent.inputIfIsDown(): Boolean {
    if (this.key == Key.DirectionDown) {
        inputs.add(Down())
        return true
    }
    return false
}

fun KeyEvent.isKeyDownType() = this.type == KeyEventType.KeyDown

fun KeyEvent.processKeyEvent(): Boolean {
    return isKeyDownType() && (this.inputIfIsRight() || this.inputIfIsLeft() || this.inputIfIsUp() || this.inputIfIsDown())
}