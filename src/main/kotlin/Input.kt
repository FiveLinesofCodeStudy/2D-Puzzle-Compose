interface Input {
    fun isRight(): Boolean
    fun isLeft(): Boolean
    fun isUp(): Boolean
    fun isDown(): Boolean

    // 4.1.4 클래스로 코드 이관하기
    fun handle(mapState: MutableList<MutableList<Tile>>)
}

class Right : Input {
    override fun isRight(): Boolean = true
    override fun isLeft(): Boolean = false
    override fun isUp(): Boolean = false
    override fun isDown(): Boolean = false

    override fun handle(mapState: MutableList<MutableList<Tile>>) {
        map[playeryState.value][playerxState.value + 1].moveHorizontal(1)
    }
}

class Left : Input {
    override fun isRight(): Boolean = false
    override fun isLeft(): Boolean = true
    override fun isUp(): Boolean = false
    override fun isDown(): Boolean = false

    override fun handle(mapState: MutableList<MutableList<Tile>>) {
        map[playeryState.value][playerxState.value + -1].moveHorizontal(-1)
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
        map[playeryState.value + -1][playerxState.value].moveVertical(-1)
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
        map[playeryState.value + 1][playerxState.value].moveVertical(1)
    }
}