interface RemoveStrategy {
    fun check(tile: Tile): Boolean
}

class RemoveLock1 : RemoveStrategy {
    override fun check(tile: Tile): Boolean {
        return tile.isLock1()
    }
}

class RemoveLock2 : RemoveStrategy {
    override fun check(tile: Tile): Boolean {
        return tile.isLock2()
    }
}