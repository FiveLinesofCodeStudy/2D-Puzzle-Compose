import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

interface Tile {
    fun isFlux(): Boolean
    fun isUnbreakable(): Boolean
    fun isStone(): Boolean
    fun isBox(): Boolean
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

    fun drop()

    fun rest()

    fun isFalling(): Boolean
    fun canFall(): Boolean

    fun update(x: Int, y: Int)
}

class FallStrategy(private var falling: FallingState) {
    fun update(tile: Tile, x: Int, y: Int) {
        this.falling = if (map[y + 1][x].isAir()) Falling() else Resting()
        drop(y, x, tile)
    }
    private fun drop(y: Int, x: Int, tile: Tile) {
        if (this.falling.isFalling()) {
            map[y + 1][x] = tile
            map[y][x] = Air()
        }
    }

    fun isFalling(): FallingState {
        return this.falling
    }

}


class Flux : Tile {
    override fun isFlux(): Boolean = true
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false

    override fun isBox(): Boolean = false

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
    override fun drop() {

    }

    override fun rest() {

    }

    override fun isFalling(): Boolean {
        return false
    }

    override fun canFall(): Boolean {
        return false
    }

    override fun update(x: Int, y: Int) {

    }
}

class Unbreakable : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = true
    override fun isStone(): Boolean = false

    override fun isBox(): Boolean = false

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
    override fun drop() {

    }

    override fun rest() {

    }

    override fun isFalling(): Boolean {
        return false
    }

    override fun canFall(): Boolean {
        return false
    }

    override fun update(x: Int, y: Int) {

    }
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

class Stone(private var falling: FallingState) : Tile {
    private var fallStrategy: FallStrategy = FallStrategy(falling)

    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = true

    override fun isBox(): Boolean = false

    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff0000cc), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(dx: Int) {
        this.fallStrategy.isFalling().moveHorizontal(this, dx)
    }

    override fun moveVertical(dy: Int) {

    }

    override fun isStony(): Boolean = true

    override fun isBoxy(): Boolean = false
    override fun drop() {
        this.falling = Falling()
    }

    override fun rest() {
        this.falling = Resting()
    }

    override fun isFalling(): Boolean {
        return this.falling.isFalling()
    }

    override fun canFall(): Boolean {
        return true
    }

    override fun update(x: Int, y: Int) {
        this.fallStrategy.update(this, x, y)
    }
}

class Box(private var falling: FallingState) : Tile {
    private var fallStrategy: FallStrategy = FallStrategy(falling)

    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false

    override fun isBox(): Boolean = true
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff8b4513), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(dx: Int) {
        this.falling.moveHorizontal(this, dx)
    }

    override fun moveVertical(dy: Int) {

    }

    override fun isStony(): Boolean {
        return false
    }

    override fun isBoxy(): Boolean {
        return true
    }

    override fun drop() {
        this.falling = Falling()
    }

    override fun rest() {
        this.falling = Resting()
    }

    override fun isFalling(): Boolean {
        return this.falling.isFalling()
    }

    override fun canFall(): Boolean {
        return true
    }

    override fun update(x: Int, y: Int) {
        this.fallStrategy.update(this, x, y)
    }

}

class Key1 : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false

    override fun isBox(): Boolean = false

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

    override fun drop() {

    }

    override fun rest() {

    }

    override fun isFalling(): Boolean {
        return false
    }

    override fun canFall(): Boolean {
        return false
    }

    override fun update(x: Int, y: Int) {

    }

}

class Lock1 : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false

    override fun isBox(): Boolean = false

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

    override fun drop() {

    }

    override fun rest() {

    }

    override fun isFalling(): Boolean {
        return false
    }

    override fun canFall(): Boolean {
        return false
    }

    override fun update(x: Int, y: Int) {

    }


}

class Key2 : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false

    override fun isBox(): Boolean = false

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

    override fun drop() {

    }

    override fun rest() {

    }

    override fun isFalling(): Boolean {
        return false
    }

    override fun canFall(): Boolean {
        return false
    }

    override fun update(x: Int, y: Int) {

    }


}

class Lock2 : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false

    override fun isBox(): Boolean = false

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

    override fun drop() {

    }

    override fun rest() {

    }

    override fun isFalling(): Boolean {
        return false
    }

    override fun canFall(): Boolean {
        return false
    }

    override fun update(x: Int, y: Int) {

    }
}

class Air : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false

    override fun isBox(): Boolean = false

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

    override fun drop() {

    }

    override fun rest() {

    }

    override fun isFalling(): Boolean {
        return false
    }

    override fun canFall(): Boolean {
        return false
    }

    override fun update(x: Int, y: Int) {

    }
}

class Player : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false

    override fun isBox(): Boolean = false

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
    override fun drop() {}

    override fun rest() {}

    override fun isFalling(): Boolean {
        return false
    }

    override fun canFall(): Boolean {
        return false
    }

    override fun update(x: Int, y: Int) {

    }


}