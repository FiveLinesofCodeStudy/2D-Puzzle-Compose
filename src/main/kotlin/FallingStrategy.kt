class FallStrategy(private var falling: FallingState) {
    fun update(tile: Tile, x: Int, y: Int) {
        this.falling = map[y + 1][x].getBlockOnTopState()
        drop(y, x, tile)
    }

    private fun drop(y: Int, x: Int, tile: Tile) {
        if (this.falling.isFalling()) {
            map[y + 1][x] = tile
            map[y][x] = Air()
        }
    }
}