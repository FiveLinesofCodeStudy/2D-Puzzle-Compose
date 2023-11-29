interface FallingState {
    fun isFalling(): Boolean

    fun moveHorizontal(tile: Tile, player: Player, dx: Int)
}

class Falling : FallingState {
    override fun isFalling(): Boolean = true
    override fun moveHorizontal(tile: Tile, player: Player, dx: Int) {

    }
}

class Resting : FallingState {
    override fun isFalling(): Boolean = false
    override fun moveHorizontal(tile: Tile, player: Player, dx: Int) {
        player.pushHorizontal(tile, dx)
    }
}