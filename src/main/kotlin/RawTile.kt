class RawTile(private val rawTileValue: RawTileValue) {
    companion object {
        val AIR = RawTile(AirValue())
        val FLUX = RawTile(FluxValue())
        val UNBREAKABLE = RawTile(UnbreakableValue())
        val PLAYER = RawTile(PlayerValue())
        val STONE = RawTile(StoneValue())
        val FALLING_STONE = RawTile(StoneFallingValue())
        val BOX = RawTile(BoxValue())
        val FALLING_BOX = RawTile(BoxFallingValue())
        val KEY1 = RawTile(Key1Value())
        val LOCK1 = RawTile(Lock1Value())
        val KEY2 = RawTile(Key2Value())
        val LOCK2 = RawTile(Lock2Value())
    }

    fun transform(): Tile {
        return rawTileValue.transform()
    }
}

val RAW_TILES = arrayOf(
    arrayOf(
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE
    ),
    arrayOf(
        RawTile.UNBREAKABLE,
        RawTile.PLAYER,
        RawTile.AIR,
        RawTile.FLUX,
        RawTile.FLUX,
        RawTile.UNBREAKABLE,
        RawTile.AIR,
        RawTile.UNBREAKABLE
    ),
    arrayOf(
        RawTile.UNBREAKABLE,
        RawTile.STONE,
        RawTile.UNBREAKABLE,
        RawTile.BOX,
        RawTile.FLUX,
        RawTile.UNBREAKABLE,
        RawTile.AIR,
        RawTile.UNBREAKABLE
    ),
    arrayOf(
        RawTile.UNBREAKABLE,
        RawTile.KEY1,
        RawTile.STONE,
        RawTile.FLUX,
        RawTile.FLUX,
        RawTile.UNBREAKABLE,
        RawTile.AIR,
        RawTile.UNBREAKABLE
    ),
    arrayOf(
        RawTile.UNBREAKABLE,
        RawTile.STONE,
        RawTile.FLUX,
        RawTile.FLUX,
        RawTile.FLUX,
        RawTile.LOCK1,
        RawTile.AIR,
        RawTile.UNBREAKABLE
    ),
    arrayOf(
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE,
        RawTile.UNBREAKABLE
    )
)
