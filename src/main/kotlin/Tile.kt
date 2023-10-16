import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

interface Tile {
    fun isFlux(): Boolean
    fun isUnbreakable(): Boolean
    fun isStone(): Boolean
    fun isFallingStone(): Boolean
    fun isBox(): Boolean
    fun isFallingBox(): Boolean
    fun isKey1(): Boolean
    fun isLock1(): Boolean
    fun isKey2(): Boolean
    fun isLock2(): Boolean

    fun isAir(): Boolean

    fun isPlayer(): Boolean

    fun draw(drawScope: DrawScope, x: Int, y: Int)

    fun moveHorizontal(dx: Int)

    fun moveVertical(dy: Int)

    fun isStony(): Boolean
    fun isBoxy(): Boolean
}


class Flux : Tile {
    override fun isFlux(): Boolean = true
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false

    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xffccffcc), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(dx: Int) {
        moveToTile(playerXState.value + dx, playerYState.value)
    }

    override fun moveVertical(dy: Int) {
        moveToTile(playerXState.value, playerYState.value + dy)
    }

    override fun isStony(): Boolean = false

    override fun isBoxy(): Boolean = false
}

class Unbreakable : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = true
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff999999), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(dx: Int) {}

    override fun moveVertical(dy: Int) {}
    override fun isStony(): Boolean = false

    override fun isBoxy(): Boolean = false
}

interface FallingState {
    fun isFalling(): Boolean

    fun moveHorizontal(tile: Tile, dx: Int)
}

class Falling : FallingState {
    override fun isFalling(): Boolean = true
    override fun moveHorizontal(tile: Tile, dx: Int) {

    }
}

class Resting : FallingState {
    override fun isFalling(): Boolean = false
    override fun moveHorizontal(tile: Tile, dx: Int) {
        val playerX = playerXState.value
        val playerY = playerYState.value

        if (map[playerY][playerX + dx + dx].isAir() &&
            !map[playerY + 1][playerX + dx].isAir()
        ) {
            map[playerY][playerX + dx + dx] = tile
            moveToTile(playerX + dx, playerY)
        }
    }
}

class Stone(fallingState: FallingState) : Tile {

    private val fallingState: FallingState

    init {
        this.fallingState = fallingState
    }

    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = true
    override fun isFallingStone(): Boolean = fallingState.isFalling()
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff0000cc), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(dx: Int) {
        this.fallingState.moveHorizontal(this, dx)
    }

    override fun moveVertical(dy: Int) {

    }

    override fun isStony(): Boolean = true

    override fun isBoxy(): Boolean = false
}

class Box(fallingState: FallingState) : Tile {

    private val fallingState: FallingState

    init {
        this.fallingState = fallingState
    }

    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = true
    override fun isFallingBox(): Boolean = this.fallingState.isFalling()
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff8b4513), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(dx: Int) {
        this.fallingState.moveHorizontal(this, dx)
    }

    override fun moveVertical(dy: Int) {

    }

    override fun isStony(): Boolean {
        return false
    }

    override fun isBoxy(): Boolean {
        return true
    }

}

class Key1 : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = true
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xffffcc00), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(dx: Int) {
        removeLock1()
        moveToTile(playerXState.value + dx, playerYState.value)
    }

    override fun moveVertical(dy: Int) {
        removeLock1()
        moveToTile(playerXState.value, playerYState.value + dy)
    }

    override fun isStony(): Boolean {
        return false
    }

    override fun isBoxy(): Boolean {
        return false
    }

}

class Lock1 : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = true
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xffffcc00), topLeft = topLeft(x, y), size = size())


    override fun moveHorizontal(dx: Int) {}

    override fun moveVertical(dy: Int) {}
    override fun isStony(): Boolean {
        return false
    }

    override fun isBoxy(): Boolean {
        return false
    }


}

class Key2 : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = true
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff00ccff), topLeft = topLeft(x, y), size = size())


    override fun moveHorizontal(dx: Int) {
        removeLock2()
        moveToTile(playerXState.value + dx, playerYState.value)
    }

    override fun moveVertical(dy: Int) {
        removeLock2()
        moveToTile(playerXState.value, playerYState.value + dy)
    }

    override fun isStony(): Boolean {
        return false
    }

    override fun isBoxy(): Boolean {
        return false
    }


}

class Lock2 : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = true
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff00ccff), topLeft = topLeft(x, y), size = size())


    override fun moveHorizontal(dx: Int) {}

    override fun moveVertical(dy: Int) {}
    override fun isStony(): Boolean {
        return false
    }

    override fun isBoxy(): Boolean {
        return false
    }


}

class Air : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = true
    override fun isPlayer(): Boolean = false
    override fun draw(drawScope: DrawScope, x: Int, y: Int) {}


    override fun moveHorizontal(dx: Int) {
        moveToTile(playerXState.value + dx, playerYState.value)
    }

    override fun moveVertical(dy: Int) {
        moveToTile(playerXState.value, playerYState.value + dy)
    }

    override fun isStony(): Boolean {
        return false
    }

    override fun isBoxy(): Boolean {
        return false
    }


}

class Player : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = true

    override fun draw(drawScope: DrawScope, x: Int, y: Int) {}


    override fun moveHorizontal(dx: Int) {}

    override fun moveVertical(dy: Int) {}
    override fun isStony(): Boolean = false

    override fun isBoxy(): Boolean = false


}