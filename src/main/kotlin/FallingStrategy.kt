class FallStrategy(private var falling: FallingState) {
    fun update(map: Map, tile: Tile, x: Int, y: Int) {
        this.falling = map.getBlockOnTopState(x, y+1)
        drop(map, y, x, tile)
    }

    private fun drop(map: Map, y: Int, x: Int, tile: Tile) {
        if (this.falling.isFalling()) {
            map.drop(tile, x, y)
        }
    }
}