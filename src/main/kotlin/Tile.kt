import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

interface Tile {
    fun isLock1(): Boolean = false
    fun isLock2(): Boolean = false

    fun isAir(): Boolean = false

    fun draw(drawScope: DrawScope, x: Int, y: Int)

    fun moveHorizontal(dx: Int)

    fun moveVertical(dy: Int)

    fun drop() {}

    fun rest() {}

    fun update(x: Int, y: Int) {}
}


class Flux : Tile {
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xffccffcc), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(dx: Int) {
        moveToTile(playerXState.value + dx, playerYState.value)
    }

    override fun moveVertical(dy: Int) {
        moveToTile(playerXState.value, playerYState.value + dy)
    }
}

class Unbreakable : Tile {
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff999999), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(dx: Int) {}

    override fun moveVertical(dy: Int) {}
}

class Stone(private var falling: FallingState) : Tile {
    private var fallStrategy: FallStrategy = FallStrategy(falling)

    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff0000cc), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(dx: Int) {
        this.fallStrategy.isFalling().moveHorizontal(this, dx)
    }

    override fun moveVertical(dy: Int) {

    }
    override fun drop() {
        this.falling = Falling()
    }

    override fun rest() {
        this.falling = Resting()
    }
    override fun update(x: Int, y: Int) {
        this.fallStrategy.update(this, x, y)
    }
}

class Box(private var falling: FallingState) : Tile {
    private var fallStrategy: FallStrategy = FallStrategy(falling)

    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff8b4513), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(dx: Int) {
        this.falling.moveHorizontal(this, dx)
    }

    override fun moveVertical(dy: Int) {

    }

    override fun drop() {
        this.falling = Falling()
    }

    override fun rest() {
        this.falling = Resting()
    }

    override fun update(x: Int, y: Int) {
        this.fallStrategy.update(this, x, y)
    }

}

class TileKey(private val keyConfiguration: KeyConfiguration) : Tile {

    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = this.keyConfiguration.color, topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(dx: Int) {
        remove(this.keyConfiguration.removeStrategy)
        moveToTile(playerXState.value + dx, playerYState.value)
    }

    override fun moveVertical(dy: Int) {
        remove(this.keyConfiguration.removeStrategy)
        moveToTile(playerXState.value, playerYState.value + dy)
    }

}

class Lock(private val keyConfiguration: KeyConfiguration) : Tile {

    override fun isLock1(): Boolean = keyConfiguration.is1

    override fun isLock2(): Boolean = !keyConfiguration.is1


    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = this.keyConfiguration.color, topLeft = topLeft(x, y), size = size())


    override fun moveHorizontal(dx: Int) {}

    override fun moveVertical(dy: Int) {}
}

class Air : Tile {


    override fun isAir(): Boolean = true

    override fun draw(drawScope: DrawScope, x: Int, y: Int) {}


    override fun moveHorizontal(dx: Int) {
        moveToTile(playerXState.value + dx, playerYState.value)
    }

    override fun moveVertical(dy: Int) {
        moveToTile(playerXState.value, playerYState.value + dy)
    }
}

class Player : Tile {

    override fun draw(drawScope: DrawScope, x: Int, y: Int) {}

    override fun moveHorizontal(dx: Int) {}

    override fun moveVertical(dy: Int) {}
}