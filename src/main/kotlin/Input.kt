import androidx.compose.runtime.MutableState

interface Input {
    fun isRight(): Boolean
    fun isLeft(): Boolean
    fun isUp(): Boolean
    fun isDown(): Boolean

    // 4.1.4 클래스로 코드 이관하기
    fun handle(mapState: MutableState<Array<Array<Int>>>, playerx: MutableState<Int>, playery: MutableState<Int>)
}

class Right : Input {
    override fun isRight(): Boolean = true
    override fun isLeft(): Boolean = false
    override fun isUp(): Boolean = false
    override fun isDown(): Boolean = false

    override fun handle(
        mapState: MutableState<Array<Array<Int>>>,
        playerx: MutableState<Int>,
        playery: MutableState<Int>
    ) {
        moveHorizontal(1, mapState, playerx, playery)
    }
}
class Left : Input {
    override fun isRight(): Boolean = false
    override fun isLeft(): Boolean = true
    override fun isUp(): Boolean = false
    override fun isDown(): Boolean = false

    override fun handle(
        mapState: MutableState<Array<Array<Int>>>,
        playerx: MutableState<Int>,
        playery: MutableState<Int>
    ) {
        moveHorizontal(-1, mapState, playerx, playery)
    }
}

class Up : Input {
    override fun isRight(): Boolean = false
    override fun isLeft(): Boolean = false
    override fun isUp(): Boolean = true
    override fun isDown(): Boolean = false

    override fun handle(
        mapState: MutableState<Array<Array<Int>>>,
        playerx: MutableState<Int>,
        playery: MutableState<Int>
    ) {
        moveVertical(-1, mapState, playerx, playery)
    }
}

class Down : Input {
    override fun isRight(): Boolean = false
    override fun isLeft(): Boolean = false
    override fun isUp(): Boolean = false
    override fun isDown(): Boolean = true

    override fun handle(
        mapState: MutableState<Array<Array<Int>>>,
        playerx: MutableState<Int>,
        playery: MutableState<Int>
    ) {
        moveVertical(1, mapState, playerx, playery)
    }
}