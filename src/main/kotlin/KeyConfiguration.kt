import androidx.compose.ui.graphics.Color

data class KeyConfiguration(
    val color: Color,
    val removeStrategy: RemoveStrategy,
    val is1: Boolean = false
)