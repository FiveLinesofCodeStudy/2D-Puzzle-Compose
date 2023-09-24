import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
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

fun transformMap(): MutableList<MutableList<Tile>> {
    map = mutableListOf()
    for (y in rawMap.indices) {
        map.add(MutableList(rawMap[y].size) { Air() })
        for (x in rawMap[y].indices) {
            map[y][x] = transformTile(RawTile.order(rawMap[y][x]))
        }
    }
    return map
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

fun moveToTile(
    newx: Int,
    newy: Int,
    playerxState: MutableState<Int>,
    playeryState: MutableState<Int>
) {
    val playerx = playerxState.value
    val playery = playeryState.value
    map[playery][playerx] = Air()
    map[newy][newx] = Player()
    playerxState.value = newx
    playeryState.value = newy
}

fun moveHorizontal(
    dx: Int,
    playerxState: MutableState<Int>,
    playeryState: MutableState<Int>
) {
    val playerx = playerxState.value
    val playery = playeryState.value

    if (map[playery][playerx + dx].isFlux() || map[playery][playerx + dx].isAir()) {
        moveToTile(playerx + dx, playery, playerxState, playeryState)
    } else if ((map[playery][playerx + dx].isStone() || map[playery][playerx + dx].isBox())
        && map[playery][playerx + dx + dx].isAir() && !map[playery + 1][playerx + dx].isAir()
    ) {
        map[playery][playerx + dx + dx] = map[playery][playerx + dx]
        moveToTile(playerx + dx, playery, playerxState, playeryState)
    } else if (map[playery][playerx + dx].isKey1()) {
        removeLock1()
        moveToTile(playerx + dx, playery, playerxState, playeryState)
    } else if (map[playery][playerx + dx].isKey2()) {
        removeLock2()
        moveToTile(playerx + dx, playery, playerxState, playeryState)
    }
}

fun moveVertical(
    dy: Int,
    playerxState: MutableState<Int>,
    playeryState: MutableState<Int>
) {
    val playerx = playerxState.value
    val playery = playeryState.value
    if (map[playery + dy][playerx].isFlux() || map[playery + dy][playerx].isAir()
    ) {
        moveToTile(playerx, playery + dy, playerxState, playeryState)
    } else if (map[playery + dy][playerx].isKey1()) {
        removeLock1()
        moveToTile(playerx, playery + dy, playerxState, playeryState)
    } else if (map[playery + dy][playerx].isKey2()) {
        removeLock2()
        moveToTile(playerx, playery + dy, playerxState, playeryState)
    }
}

fun update(
    inputs: SnapshotStateList<Input>,
    playerx: MutableState<Int>,
    playery: MutableState<Int>
) {
    handleInputs(inputs, playerx, playery)
    updateMap()
}

private fun handleInputs(
    inputs: SnapshotStateList<Input>,
    playerx: MutableState<Int>,
    playery: MutableState<Int>
) {
    while (inputs.size > 0) {
        val current = inputs.removeLast()
        current.handle(map, playerx, playery)
    }
}

private fun updateMap() {
    for (y in map.size - 1 downTo 0) {
        for (x in map[y].indices) {
            updateTile(y, x)
        }
    }
}

private fun updateTile(y: Int, x: Int) {
    if ((map[y][x].isStone() || map[y][x].isFallingStone())
        && map[y + 1][x].isAir()
    ) {
        map[y + 1][x] = FallingStone()
        map[y][x] = Air()
    } else if ((map[y][x].isBox() || map[y][x].isFallingBox())
        && map[y + 1][x].isAir()
    ) {
        map[y + 1][x] = FallingBox()
        map[y][x] = Air()
    } else if (map[y][x].isFallingStone()) {
        map[y][x] = Stone()
    } else if (map[y][x].isFallingBox()) {
        map[y][x] = Box()
    }
}


@Composable
fun draw(
    playerxState: MutableState<Int>,
    playeryState: MutableState<Int>
) {
    // typescript와 달리 여기서는 canvas 객체를 따로 만들어서 재사용하는 건 없음.
    Canvas(modifier = Modifier.width(1200.dp).height(800.dp)) {
        drawMap()
        drawPlayer(playerxState, playeryState)  // map 위에 Player 를 그림.
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
    inputs: SnapshotStateList<Input>,
    playerxState: MutableState<Int>,
    playeryState: MutableState<Int>,
) {
    update(inputs, playerxState, playeryState)
    draw(playerxState, playeryState)
}

@Composable
@Preview
fun App(inputs: SnapshotStateList<Input>) {

    val playerx = remember { mutableStateOf(1) }
    val playery = remember { mutableStateOf(1) }


    MaterialTheme {
        gameLoop(
            inputs = inputs,
            playerxState = playerx,
            playeryState = playery,
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {

    val inputs = remember { mutableStateListOf<Input>() }
    transformMap()
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
