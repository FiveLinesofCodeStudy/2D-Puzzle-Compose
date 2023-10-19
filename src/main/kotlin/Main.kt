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


fun transform(i: Int): Tile {
    return when (i) {
        0 -> Air()
        1 -> Flux()
        2 -> Unbreakable()
        3 -> Player()
        4 -> Stone(Falling())
        5 -> Stone(Resting())
        6 -> Box(Resting())
        7 -> Box(Falling())
        8 -> Key1()
        9 -> Lock1()
        10 -> Key2()
        11 -> Lock2()
        else -> throw Exception("Unexpected tile: $i")
    }
}

val playerXState = mutableStateOf(1)
val playerYState = mutableStateOf(1)

val rawMap = arrayOf(
    arrayOf(2, 2, 2, 2, 2, 2, 2, 2),
    arrayOf(2, 3, 0, 1, 1, 2, 0, 2),
    arrayOf(2, 4, 2, 6, 1, 2, 0, 2),
    arrayOf(2, 8, 4, 1, 1, 2, 0, 2),
    arrayOf(2, 4, 1, 1, 1, 9, 0, 2),
    arrayOf(2, 2, 2, 2, 2, 2, 2, 2)
)

var map: MutableList<MutableList<Tile>> = mutableListOf()

val inputs = mutableStateListOf<Input>()

fun transformMap() {
    map = MutableList(rawMap.size) { MutableList(rawMap[0].size) { Air() } }
    for (y in rawMap.indices) {
        for (x in rawMap[y].indices) {
            map[y][x] = transform(rawMap[y][x])
        }
    }
}

fun remove(shouldRemove: RemoveStrategy) {
    for (y in map.indices) {
        for (x in map[y].indices) {
            if (shouldRemove.check(map[y][x])) {
                map[y][x] = Air()
            }
        }
    }
}

fun removeLock2() {
    remove(RemoveLock2())
}

fun moveToTile(newx: Int, newy: Int) {
    map[playerYState.value][playerXState.value] = Air()
    map[newy][newx] = Player()
    playerXState.value = newx
    playerYState.value = newy
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
        playerXState.value * TITLE_SIZE.toFloat(),
        playerYState.value * TITLE_SIZE.toFloat(),
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

@Suppress("FunctionName")
@Composable
@Preview
fun App(inputs: SnapshotStateList<Input>) {

    MaterialTheme {
        gameLoop(inputs = inputs)
    }
}

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
