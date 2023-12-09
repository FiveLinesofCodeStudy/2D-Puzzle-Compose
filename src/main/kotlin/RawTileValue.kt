import androidx.compose.ui.graphics.Color

// enum class RawTile {
//    AIR,
//    FLUX,
//    UNBREAKABLE,
//    PLAYER,
//    STONE,
//    FALLING_STONE,
//    BOX,
//    FALLING_BOX,
//    KEY1, LOCK1,
//    KEY2, LOCK2,
//}
interface RawTileValue {
    fun transform(): Tile
}

class AirValue: RawTileValue {
    override fun transform(): Tile {
        return Air()
    }
}

class FluxValue: RawTileValue {
    override fun transform(): Tile {
        return Flux()
    }
}

class UnbreakableValue: RawTileValue {
    override fun transform(): Tile {
        return Unbreakable()
    }
}

class PlayerValue: RawTileValue {
    override fun transform(): Tile {
        return PlayerTile()
    }
}


class StoneValue: RawTileValue {
    override fun transform(): Tile {
        return Stone(Resting())
    }
}

class StoneFallingValue: RawTileValue {
    override fun transform(): Tile {
        return Stone(Falling())
    }
}

class BoxValue: RawTileValue {
    override fun transform(): Tile {
        return Box(Resting())
    }
}

class BoxFallingValue: RawTileValue {
    override fun transform(): Tile {
        return Box(Falling())
    }
}

class Key1Value: RawTileValue {
    override fun transform(): Tile {
        return TileKey(KeyConfiguration(Color(0xffffcc00), RemoveLock1(), true))
    }
}

class Lock1Value: RawTileValue {
    override fun transform(): Tile {
        return Lock(KeyConfiguration(Color(0xffffcc00), RemoveLock1(), is1 = true))
    }
}

class Key2Value: RawTileValue {
    override fun transform(): Tile {
        return TileKey(KeyConfiguration(Color(0xff00ccff), RemoveLock2(), is1 = false))
    }
}

class Lock2Value: RawTileValue {
    override fun transform(): Tile {
        return Lock(KeyConfiguration(Color(0xff00ccff), RemoveLock2(), is1 = false))
    }
}


