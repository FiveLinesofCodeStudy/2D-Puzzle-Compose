interface Input {
    fun isRight(): Boolean
    fun isLeft(): Boolean
    fun isUp(): Boolean
    fun isDown(): Boolean

    fun handle(mapState: MutableList<MutableList<Tile>>)
}

class Right : Input {
    override fun isRight(): Boolean = true
    override fun isLeft(): Boolean = false
    override fun isUp(): Boolean = false
    override fun isDown(): Boolean = false

    override fun handle(mapState: MutableList<MutableList<Tile>>) {
        player.value.moveHorizontal(1)
    }
}

class Left : Input {
    override fun isRight(): Boolean = false
    override fun isLeft(): Boolean = true
    override fun isUp(): Boolean = false
    override fun isDown(): Boolean = false

    override fun handle(mapState: MutableList<MutableList<Tile>>) {
        player.value.moveHorizontal(-1)
    }
}

class Up : Input {
    override fun isRight(): Boolean = false
    override fun isLeft(): Boolean = false
    override fun isUp(): Boolean = true
    override fun isDown(): Boolean = false

    override fun handle(
        mapState: MutableList<MutableList<Tile>>
    ) {
        player.value.moveVertical(-1)
    }
}

class Down : Input {
    override fun isRight(): Boolean = false
    override fun isLeft(): Boolean = false
    override fun isUp(): Boolean = false
    override fun isDown(): Boolean = true

    override fun handle(
        mapState: MutableList<MutableList<Tile>>
    ) {
        player.value.moveVertical(1)
    }
}