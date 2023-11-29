import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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
        3 -> PlayerTile()
        4 -> Stone(Falling())
        5 -> Stone(Resting())
        6 -> Box(Resting())
        7 -> Box(Falling())
        8 -> TileKey(KeyConfiguration(Color(0xffffcc00), RemoveLock1(), true))
        9 -> Lock(KeyConfiguration(Color(0xffffcc00), RemoveLock1(), is1 = true))
        10 -> TileKey(KeyConfiguration(Color(0xff00ccff), RemoveLock2(), is1 = false))
        11 -> Lock(KeyConfiguration(Color(0xff00ccff), RemoveLock2(), is1 = false))
        else -> throw Exception("Unexpected tile: $i")
    }
}

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

fun update() {
    handleInputs()
    updateMap()
}

private fun handleInputs() {
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
        player.value.draw(this)
    }
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
fun gameLoop() {
    update()
    draw()
}

@Suppress("FunctionName")
@Composable
@Preview
fun App() {
    MaterialTheme {
        gameLoop()
    }
}

fun main() = application {

    transformMap()
    Window(
        onCloseRequest = ::exitApplication,
        onKeyEvent = {
            it.processKeyEvent()
        }) {
        App()
    }
}
