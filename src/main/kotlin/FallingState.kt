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