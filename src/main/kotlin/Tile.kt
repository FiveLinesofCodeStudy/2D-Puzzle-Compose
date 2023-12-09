import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

interface Tile {
    fun isLock1(): Boolean = false
    fun isLock2(): Boolean = false

    fun isAir(): Boolean = false

    fun draw(drawScope: DrawScope, x: Int, y: Int)

    fun moveHorizontal(map: Map, player: Player, dx: Int) {}

    fun moveVertical(map: Map, player: Player, dy: Int) {}

    fun drop() {}

    fun rest() {}

    fun update(map: Map, x: Int, y: Int) {}

    fun getBlockOnTopState(): FallingState {
        return Resting()
    }

    companion object {
        fun transform(rawTile: RawTile): Tile {
            return rawTile.transform()
        }
    }
}


class Flux : Tile {
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xffccffcc), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(map: Map, player: Player, dx: Int) {
        player.move(map, dx, 0)
    }

    override fun moveVertical(map: Map, player: Player, dy: Int) {
        player.move(map, 0, dy)
    }
}

class Unbreakable : Tile {
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff999999), topLeft = topLeft(x, y), size = size())
}

class Stone(private var falling: FallingState) : Tile {
    private var fallStrategy: FallStrategy = FallStrategy(falling)

    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff0000cc), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(map: Map, player: Player, dx: Int) {
        this.falling.moveHorizontal(map, this, player, dx)
    }

    override fun drop() {
        this.falling = Falling()
    }

    override fun rest() {
        this.falling = Resting()
    }

    override fun update(map: Map, x: Int, y: Int) {
        this.fallStrategy.update(map, this, x, y)
    }
}

class Box(private var falling: FallingState) : Tile {
    private var fallStrategy: FallStrategy = FallStrategy(falling)

    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = Color(0xff8b4513), topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(map: Map, player: Player, dx: Int) {
        this.falling.moveHorizontal(map, this, player, dx)
    }

    override fun drop() {
        this.falling = Falling()
    }

    override fun rest() {
        this.falling = Resting()
    }

    override fun update(map: Map, x: Int, y: Int) {
        this.fallStrategy.update(map, this, x, y)
    }

}

class TileKey(private val keyConfiguration: KeyConfiguration) : Tile {

    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = this.keyConfiguration.color, topLeft = topLeft(x, y), size = size())

    override fun moveHorizontal(map: Map, player: Player, dx: Int) {
        remove(map, this.keyConfiguration.removeStrategy)
        player.move(map, dx, 0)
    }

    override fun moveVertical(map: Map, player: Player, dy: Int) {
        remove(map, this.keyConfiguration.removeStrategy)
        player.move(map, 0, dy)
    }

}

class Lock(private val keyConfiguration: KeyConfiguration) : Tile {

    override fun isLock1(): Boolean = keyConfiguration.is1

    override fun isLock2(): Boolean = !keyConfiguration.is1


    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = this.keyConfiguration.color, topLeft = topLeft(x, y), size = size())

}

class Air : Tile {


    override fun isAir(): Boolean = true

    override fun draw(drawScope: DrawScope, x: Int, y: Int) {}


    override fun moveHorizontal(map: Map, player: Player, dx: Int) {
        player.move(map, dx, 0)
    }

    override fun moveVertical(map: Map, player: Player, dy: Int) {
        player.move(map, 0, dy)
    }

    override fun getBlockOnTopState(): FallingState {
        return Falling()
    }
}

class PlayerTile : Tile {

    override fun draw(drawScope: DrawScope, x: Int, y: Int) {}
}