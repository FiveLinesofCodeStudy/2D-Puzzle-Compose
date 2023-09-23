import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

const val TITLE_SIZE = 100

enum class Tile {
    AIR,
    FLUX,
    UNBREAKABLE,
    PLAYER,
    STONE, FALLING_STONE,
    BOX, FALLING_BOX,
    KEY1, LOCK1,
    KEY2, LOCK2
}

enum class RawInput {
    UP, DOWN, LEFT, RIGHT
}



val map = arrayOf(
    arrayOf(2, 2, 2, 2, 2, 2, 2, 2),
    arrayOf(2, 3, 0, 1, 1, 2, 0, 2),
    arrayOf(2, 4, 2, 6, 1, 2, 0, 2),
    arrayOf(2, 8, 4, 1, 1, 2, 0, 2),
    arrayOf(2, 4, 1, 1, 1, 9, 0, 2),
    arrayOf(2, 2, 2, 2, 2, 2, 2, 2)
)

fun remove(tile: Tile, mapState: MutableState<Array<Array<Int>>>) {
    val map = mapState.value
    for (y in map.indices) {
        for (x in map[y].indices) {
            if (map[y][x] == tile.ordinal) {
                map[y][x] = Tile.AIR.ordinal
            }
        }
    }
}

fun moveToTile(
    newx: Int,
    newy: Int,
    mapState: MutableState<Array<Array<Int>>>,
    playerxState: MutableState<Int>,
    playeryState: MutableState<Int>
) {
    val map = mapState.value
    val playerx = playerxState.value
    val playery = playeryState.value
    map[playery][playerx] = Tile.AIR.ordinal
    map[newy][newx] = Tile.PLAYER.ordinal
    playerxState.value = newx
    playeryState.value = newy
}

fun moveHorizontal(
    dx: Int,
    mapState: MutableState<Array<Array<Int>>>,
    playerxState: MutableState<Int>,
    playeryState: MutableState<Int>
) {
    val map = mapState.value
    val playerx = playerxState.value
    val playery = playeryState.value

    if (map[playery][playerx + dx] == Tile.FLUX.ordinal || map[playery][playerx + dx] == Tile.AIR.ordinal) {
        moveToTile(playerx + dx, playery, mapState, playerxState, playeryState)
    } else if ((map[playery][playerx + dx] == Tile.STONE.ordinal || map[playery][playerx + dx] == Tile.BOX.ordinal)
        && map[playery][playerx + dx + dx] == Tile.AIR.ordinal && map[playery + 1][playerx + dx] != Tile.AIR.ordinal
    ) {
        map[playery][playerx + dx + dx] = map[playery][playerx + dx]
        moveToTile(playerx + dx, playery, mapState, playerxState, playeryState)
    } else if (map[playery][playerx + dx] == Tile.KEY1.ordinal) {
        remove(Tile.LOCK1, mapState)
        moveToTile(playerx + dx, playery, mapState, playerxState, playeryState)
    } else if (map[playery][playerx + dx] == Tile.KEY2.ordinal) {
        remove(Tile.LOCK2, mapState)
        moveToTile(playerx + dx, playery, mapState, playerxState, playeryState)
    }
}

fun moveVertical(
    dy: Int,
    mapState: MutableState<Array<Array<Int>>>,
    playerxState: MutableState<Int>,
    playeryState: MutableState<Int>
) {
    val map = mapState.value
    val playerx = playerxState.value
    val playery = playeryState.value
    if (map[playery + dy][playerx] == Tile.FLUX.ordinal
        || map[playery + dy][playerx] == Tile.AIR.ordinal
    ) {
        moveToTile(playerx, playery + dy, mapState, playerxState, playeryState)
    } else if (map[playery + dy][playerx] == Tile.KEY1.ordinal) {
        remove(Tile.LOCK1, mapState)
        moveToTile(playerx, playery + dy, mapState, playerxState, playeryState)
    } else if (map[playery + dy][playerx] == Tile.KEY2.ordinal) {
        remove(Tile.LOCK2, mapState)
        moveToTile(playerx, playery + dy, mapState, playerxState, playeryState)
    }
}

fun update(
    inputs: SnapshotStateList<Input>,
    mapState: MutableState<Array<Array<Int>>>,
    playerx: MutableState<Int>,
    playery: MutableState<Int>
) {
    handleInputs(inputs, mapState, playerx, playery)
    updateMap(mapState)
}

private fun handleInputs(
    inputs: SnapshotStateList<Input>,
    mapState: MutableState<Array<Array<Int>>>,
    playerx: MutableState<Int>,
    playery: MutableState<Int>
) {
    while (inputs.size > 0) {
        val current = inputs.removeLast()
        current.handle(mapState, playerx, playery)
    }
}

private fun updateMap(mapState: MutableState<Array<Array<Int>>>) {
    val map = mapState.value
    for (y in map.size - 1 downTo 0) {
        for (x in map[y].indices) {
            updateTile(y, x)
        }
    }
}

private fun updateTile(y: Int, x: Int) {
    if ((map[y][x] == Tile.STONE.ordinal || map[y][x] == Tile.FALLING_STONE.ordinal)
        && map[y + 1][x] == Tile.AIR.ordinal
    ) {
        map[y + 1][x] = Tile.FALLING_STONE.ordinal
        map[y][x] = Tile.AIR.ordinal
    } else if ((map[y][x] == Tile.BOX.ordinal || map[y][x] == Tile.FALLING_BOX.ordinal)
        && map[y + 1][x] == Tile.AIR.ordinal
    ) {
        map[y + 1][x] = Tile.FALLING_BOX.ordinal
        map[y][x] = Tile.AIR.ordinal
    } else if (map[y][x] == Tile.FALLING_STONE.ordinal) {
        map[y][x] = Tile.STONE.ordinal
    } else if (map[y][x] == Tile.FALLING_BOX.ordinal) {
        map[y][x] = Tile.BOX.ordinal
    }
}


@Composable
fun draw(mapState: MutableState<Array<Array<Int>>>, playerxState: MutableState<Int>, playeryState: MutableState<Int>) {
    // typescript와 달리 여기서는 canvas 객체를 따로 만들어서 재사용하는 건 없음.
    Canvas(modifier = Modifier.width(1200.dp).height(800.dp)) {
        drawMap(mapState)
        drawPlayer(playerxState, playeryState)
    }
}

private fun DrawScope.drawPlayer(
    playerxState: MutableState<Int>,
    playeryState: MutableState<Int>
) {
    val playerx = playerxState.value
    val playery = playeryState.value
    val player = Rect(
        playerx * TITLE_SIZE.toFloat(),
        playery * TITLE_SIZE.toFloat(),
        TITLE_SIZE.toFloat(),
        TITLE_SIZE.toFloat()
    )
    drawRect(color = Color(0xffff0000), player.topLeft, Size(TITLE_SIZE.toFloat(), TITLE_SIZE.toFloat()))
}

private fun DrawScope.drawMap(mapState: MutableState<Array<Array<Int>>>) {
    val map = mapState.value

    for (y in map.indices) {
        for (x in map[y].indices) {
            val rect = Rect(
                (x * TITLE_SIZE).toFloat(),
                (y * TITLE_SIZE).toFloat(),
                TITLE_SIZE.toFloat(),
                TITLE_SIZE.toFloat()
            )
            val color = if (map[y][x] == Tile.FLUX.ordinal) {
                Color(0xffccffcc)
            } else if (map[y][x] == Tile.UNBREAKABLE.ordinal) {
                Color(0xff999999)
            } else if (map[y][x] == Tile.STONE.ordinal || map[y][x] == Tile.FALLING_STONE.ordinal) {
                Color(0xff0000cc)
            } else if (map[y][x] == Tile.BOX.ordinal || map[y][x] == Tile.FALLING_BOX.ordinal) {
                Color(0xff8b4513)
            } else if (map[y][x] == Tile.KEY1.ordinal || map[y][x] == Tile.LOCK1.ordinal) {
                Color(0xffffcc00)
            } else if (map[y][x] == Tile.KEY2.ordinal || map[y][x] == Tile.LOCK2.ordinal) {
                Color(0xff00ccff)
            } else {
                Color.White
            }
            drawRect(color = color, topLeft = rect.topLeft, size = Size(TITLE_SIZE.toFloat(), TITLE_SIZE.toFloat()))
        }
    }
}

@Composable
fun gameLoop(
    inputs: SnapshotStateList<Input>,
    playerxState: MutableState<Int>,
    playeryState: MutableState<Int>,
    mapState: MutableState<Array<Array<Int>>>
) {
    update(inputs, mapState, playerxState, playeryState)
    draw(mapState, playerxState, playeryState)
}

@Composable
@Preview
fun App(inputs: SnapshotStateList<Input>) {

    val playerx = remember { mutableStateOf(1) }
    val playery = remember { mutableStateOf(1) }
    val mapState = remember { mutableStateOf(map) }

    MaterialTheme {
        gameLoop(
            inputs = inputs,
            playerxState = playerx,
            playeryState = playery,
            mapState = mapState
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {

    val inputs = remember { mutableStateListOf<Input>() }

    Window(onCloseRequest = ::exitApplication, onKeyEvent = {
        if (it.key == Key.DirectionUp && it.type == KeyEventType.KeyDown) {
            inputs.add(Up())
            true
        } else if (it.key == Key.DirectionDown && it.type == KeyEventType.KeyDown) {
            inputs.add(Down())
            true
        } else if (it.key == Key.DirectionLeft && it.type == KeyEventType.KeyDown) {
            inputs.add(Left())
            true
        } else if (it.key == Key.DirectionRight && it.type == KeyEventType.KeyDown) {
            inputs.add(Right())
            true
        } else {
            false
        }
    }) {
        App(inputs)
    }
}
