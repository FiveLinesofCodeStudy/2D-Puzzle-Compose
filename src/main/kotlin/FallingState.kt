interface FallingState {
    fun isFalling(): Boolean

    fun moveHorizontal(map: Map, tile: Tile, player: Player, dx: Int) {}
}

class Falling : FallingState {
    override fun isFalling(): Boolean = true
}

class Resting : FallingState {
    override fun isFalling(): Boolean = false
    override fun moveHorizontal(map: Map, tile: Tile, player: Player, dx: Int) {
        player.pushHorizontal(map, tile, dx)
    }
}