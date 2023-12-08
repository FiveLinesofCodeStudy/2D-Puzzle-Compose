import androidx.compose.ui.graphics.drawscope.DrawScope

private val rawMap = arrayOf(
    arrayOf(2, 2, 2, 2, 2, 2, 2, 2),
    arrayOf(2, 3, 0, 1, 1, 2, 0, 2),
    arrayOf(2, 4, 2, 6, 1, 2, 0, 2),
    arrayOf(2, 8, 4, 1, 1, 2, 0, 2),
    arrayOf(2, 4, 1, 1, 1, 9, 0, 2),
    arrayOf(2, 2, 2, 2, 2, 2, 2, 2)
)

class Map {
    private var map: MutableList<MutableList<Tile>> = MutableList(rawMap.size) { MutableList(rawMap[0].size) { Air() } }

    // 6.4 transform을 init 블록으로 옮기기
    init {
        for (y in rawMap.indices) {
            for (x in rawMap[y].indices) {
                map[y][x] = Tile.transform(rawMap[y][x])
            }
        }
    }

    fun getMap() = map

    fun update() {
        for (y in map.size - 1 downTo 0) {
            for (x in map[y].indices) {
                map[y][x].update(this, x, y)
            }
        }
    }

    fun draw(drawScope: DrawScope) {
        for (y in map.indices) {
            for (x in map[y].indices) {
                map[y][x].draw(drawScope, x, y)
            }
        }
    }

    fun drop(tile: Tile, x: Int, y: Int) {
        map[y + 1][x] = tile
        map[y][x] = Air()
    }

    fun getBlockOnTopState(x: Int, y: Int): FallingState {
        return map[y][x].getBlockOnTopState()
    }

    fun moveHorizontal(player: Player, x: Int, y: Int, dx: Int) {
        map[y][x + dx].moveHorizontal(this, player, dx)
    }

    fun moveVertical(player: Player, x: Int, y: Int, dy: Int) {
        map[y + dy][x].moveVertical(this, player, dy)
    }

    fun movePlayer(x: Int, y: Int, newx: Int, newy: Int) {
        map[newy][newx] = PlayerTile()
        map[y][x] = Air()
    }

    fun pushHorizontal(player: Player, x: Int, y: Int, tile: Tile, dx: Int) {
        if (this.map[y][x + dx + dx].isAir() &&
            !this.map[y+1][x + dx].isAir()
        ) {
            this.map[y][x + dx + dx] = tile
            player.moveToTile(this, x + dx, y)
        }
    }

}