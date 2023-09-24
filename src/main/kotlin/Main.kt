import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

const val TITLE_SIZE = 100

fun size() = Size(TITLE_SIZE.toFloat(), TITLE_SIZE.toFloat())

enum class RawTile {
    AIR,
    FLUX,
    UNBREAKABLE,
    PLAYER,
    STONE, FALLING_STONE,
    BOX, FALLING_BOX,
    KEY1, LOCK1,
    KEY2, LOCK2;

    companion object {
        fun order(i: Int): RawTile {
            return when (i) {
                0 -> AIR
                1 -> FLUX
                2 -> UNBREAKABLE
                3 -> PLAYER
                4 -> STONE
                5 -> FALLING_STONE
                6 -> BOX
                7 -> FALLING_BOX
                8 -> KEY1
                9 -> LOCK1
                10 -> KEY2
                11 -> LOCK2
                else -> throw Exception("Unexpected tile: $i")
            }
        }
    }
}

val playerxState = mutableStateOf(1)
val playeryState = mutableStateOf(1)

val rawMap = arrayOf(
    arrayOf(2, 2, 2, 2, 2, 2, 2, 2),
    arrayOf(2, 3, 0, 1, 1, 2, 0, 2),
    arrayOf(2, 4, 2, 6, 1, 2, 0, 2),
    arrayOf(2, 8, 4, 1, 1, 2, 0, 2),
    arrayOf(2, 4, 1, 1, 1, 9, 0, 2),
    arrayOf(2, 2, 2, 2, 2, 2, 2, 2)
)

var map: MutableList<MutableList<Tile>> = mutableListOf()

fun transformTile(tile: RawTile): Tile {
    return when (tile) {
        RawTile.AIR -> Air()
        RawTile.PLAYER -> Player()
        RawTile.UNBREAKABLE -> Unbreakable()
        RawTile.STONE -> Stone()
        RawTile.FALLING_STONE -> FallingStone()
        RawTile.BOX -> Box()
        RawTile.FALLING_BOX -> FallingBox()
        RawTile.FLUX -> Flux()
        RawTile.KEY1 -> Key1()
        RawTile.LOCK1 -> Lock1()
        RawTile.KEY2 -> Key2()
        RawTile.LOCK2 -> Lock2()
    }
}

fun transformMap() {
    map = MutableList(rawMap.size) { MutableList(rawMap[0].size) { Air() } }
    for (y in rawMap.indices) {
        for (x in rawMap[y].indices) {
            map[y][x] = transformTile(RawTile.order(rawMap[y][x]))
        }
    }
}

fun removeLock1() {
    for (y in map.indices) {
        for (x in map[y].indices) {
            if (map[y][x].isLock1()) {
                map[y][x] = Air()
            }
        }
    }
}

fun removeLock2() {
    for (y in map.indices) {
        for (x in map[y].indices) {
            if (map[y][x].isLock2()) {
                map[y][x] = Air()
            }
        }
    }
}

fun moveToTile(newx: Int, newy: Int) {
    map[playeryState.value][playerxState.value] = Air()
    map[newy][newx] = Player()
    playerxState.value = newx
    playeryState.value = newy
}

fun update(inputs: SnapshotStateList<Input>) {
    handleInputs(inputs)
    updateMap()
}

private fun handleInputs(inputs: SnapshotStateList<Input>) {
    while (inputs.size > 0) {
        val current = inputs.removeLast()
        current.handle(map)
    }
}

private fun updateMap() {
    for (y in map.size - 1 downTo 0) {
        for (x in map[y].indices) {
            map[y][x].update(x, y)
        }
    }
}


@Composable
fun draw() {
    // typescript와 달리 여기서는 canvas 객체를 따로 만들어서 재사용하는 건 없음.
    Canvas(modifier = Modifier.width(1200.dp).height(800.dp)) {
        drawMap()
        drawPlayer()  // map 위에 Player 를 그림.
    }
}

private fun DrawScope.drawPlayer() {
    val player = Rect(
        playerxState.value * TITLE_SIZE.toFloat(),
        playeryState.value * TITLE_SIZE.toFloat(),
        TITLE_SIZE.toFloat(),
        TITLE_SIZE.toFloat()
    )
    drawRect(color = Color(0xffff0000), player.topLeft, size())
}

private fun DrawScope.drawMap() {
    for (y in map.indices) {
        for (x in map[y].indices) {
            map[y][x].draw(this, x, y)
        }
    }
}

fun topLeft(x: Int, y: Int) = Offset((x * TITLE_SIZE).toFloat(), (y * TITLE_SIZE).toFloat())


@Composable
fun gameLoop(
    inputs: SnapshotStateList<Input>
) {
    update(inputs)
    draw()
}

@Composable
@Preview
fun App(inputs: SnapshotStateList<Input>) {

    MaterialTheme {
        gameLoop(inputs = inputs)
    }
}

val inputs = mutableStateListOf<Input>()
fun main() = application {

    transformMap()
    Window(
        onCloseRequest = ::exitApplication,
        onKeyEvent = {
            it.processKeyEvent()
        }) {
        App(inputs)
    }
}
