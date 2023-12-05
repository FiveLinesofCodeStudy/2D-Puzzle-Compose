import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

class Player(var x: Int = 1, var y: Int = 1) {
    fun draw(drawScope: DrawScope) {
        val rect = Rect(
            x * TITLE_SIZE.toFloat(),
            y * TITLE_SIZE.toFloat(),
            TITLE_SIZE.toFloat(),
            TITLE_SIZE.toFloat()
        )
        drawScope.drawRect(color = Color(0xffff0000), rect.topLeft, size())
    }

    fun moveHorizontal(map: Map, dx: Int) {
        map.moveHorizontal(this, this.x, this.y, dx)
    }

    fun moveVertical(map: Map, dy: Int) {
        map.moveVertical(this, this.x, this.y, dy)
    }

    fun pushHorizontal(map: Map, tile: Tile, dx: Int) {
        map.pushHorizontal(this, this.x, this.y, tile, dx)
    }

    fun moveToTile(map: Map, newx: Int, newy: Int) {
        map.movePlayer(x, y, newx, newy)
        player.value = Player(newx, newy)
    }

    fun move(map: Map, dx: Int, dy: Int) {
        moveToTile(map, x + dx, y + dy)
    }
}

val player = mutableStateOf(Player())