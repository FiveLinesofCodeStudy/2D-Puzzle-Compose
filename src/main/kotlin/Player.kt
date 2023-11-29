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

    fun moveHorizontal(dx: Int) {
        map[y][x + dx].moveHorizontal(this, dx)
    }

    fun moveVertical(dy: Int) {
        map[y + dy][x].moveVertical(this, dy)
    }

    fun pushHorizontal(tile: Tile, dx: Int) {
        if (map[y][x + dx + dx].isAir() &&
            !map[y + 1][x + dx].isAir()
        ) {
            map[y][x + dx + dx] = tile
            this.moveToTile(x + dx, y)
        }
    }

    private fun moveToTile(newx: Int, newy: Int) {
        map[newy][newx] = PlayerTile()
        map[this.y][this.x] = Air()
        player.value = Player(newx, newy)
    }

    fun move(dx: Int, dy: Int) {
        moveToTile(x + dx, y + dy)
    }
}

val player = mutableStateOf(Player())