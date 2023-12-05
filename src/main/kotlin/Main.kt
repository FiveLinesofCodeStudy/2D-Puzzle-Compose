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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

const val TITLE_SIZE = 100

fun size() = Size(TITLE_SIZE.toFloat(), TITLE_SIZE.toFloat())

private var map = Map()

val inputs = mutableStateListOf<Input>()

fun remove(map: Map, shouldRemove: RemoveStrategy) {
    for (y in map.getMap().indices) {
        for (x in map.getMap()[y].indices) {
            if (shouldRemove.check(map.getMap()[y][x])) {
                map.getMap()[y][x] = Air()
            }
        }
    }
}

private fun handleInputs() {
    while (inputs.size > 0) {
        val current = inputs.removeLast()
        current.handle(map)
    }
}


@Composable
fun draw() {
    // typescript와 달리 여기서는 canvas 객체를 따로 만들어서 재사용하는 건 없음.
    Canvas(modifier = Modifier.width(1200.dp).height(800.dp)) {
        map.draw(this)
        player.value.draw(this)
    }
}

fun topLeft(x: Int, y: Int) = Offset((x * TITLE_SIZE).toFloat(), (y * TITLE_SIZE).toFloat())


@Composable
@Preview
fun app() {
    MaterialTheme {
        handleInputs()
        map.update()
        draw()
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        onKeyEvent = { it.processKeyEvent() }) {
        app()
    }
}
