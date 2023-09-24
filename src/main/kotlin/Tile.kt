import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

interface Tile {
    fun isFlux(): Boolean
    fun isUnbreakable(): Boolean
    fun isStone(): Boolean
    fun isFallingStone(): Boolean
    fun isBox(): Boolean
    fun isFallingBox(): Boolean
    fun isKey1(): Boolean
    fun isLock1(): Boolean
    fun isKey2(): Boolean
    fun isLock2(): Boolean

    fun isAir(): Boolean

    fun isPlayer(): Boolean

    fun color(): Color

    fun draw(drawScope: DrawScope, x: Int, y: Int)

    fun isEdible(): Boolean

    fun isPushable(): Boolean

    fun moveHorizontal(dx: Int)

    fun moveVertical(dy: Int)
    fun update(x: Int, y: Int)
}


class Flux : Tile {
    override fun isFlux(): Boolean = true
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun color(): Color = Color(0xffccffcc)

    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = color(), topLeft = topLeft(x, y), size = size())

    override fun isEdible(): Boolean = true
    override fun isPushable(): Boolean = false

    override fun moveHorizontal(dx: Int) {
        moveToTile(playerxState.value + dx, playeryState.value)
    }

    override fun moveVertical(dy: Int) {
        moveToTile(playerxState.value, playeryState.value + dy)
    }

    override fun update(x: Int, y: Int) {}
}

class Unbreakable : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = true
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun color(): Color = Color(0xff999999)
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = color(), topLeft = topLeft(x, y), size = size())

    override fun isEdible(): Boolean = false
    override fun isPushable(): Boolean = false
    override fun moveHorizontal(dx: Int) {}

    override fun moveVertical(dy: Int) {}

    override fun update(x: Int, y: Int) {}
}

class Stone : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = true
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun color(): Color = Color(0xff0000cc)
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = color(), topLeft = topLeft(x, y), size = size())

    override fun isEdible(): Boolean = false
    override fun isPushable(): Boolean = true

    override fun moveHorizontal(dx: Int) {
        val playerx = playerxState.value
        val playery = playeryState.value

        if (map[playery][playerx + dx + dx].isAir() && !map[playery + 1][playerx + dx].isAir()) {
            map[playery][playerx + dx + dx] = this
            moveToTile(playerx + dx, playery)
        }
    }

    override fun moveVertical(dy: Int) {

    }

    override fun update(x: Int, y: Int) {
        if (map[y + 1][x].isAir()) {
            map[y + 1][x] = FallingStone()
            map[y][x] = Air()
        }
    }
}

class FallingStone : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = true
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun color(): Color = Color(0xff0000cc)
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = color(), topLeft = topLeft(x, y), size = size())

    override fun isEdible(): Boolean = false
    override fun isPushable(): Boolean = false
    override fun moveHorizontal(dx: Int) {}

    override fun moveVertical(dy: Int) {}
    override fun update(x: Int, y: Int) {
        if (map[y + 1][x].isAir()) {
            map[y + 1][x] = FallingStone()
            map[y][x] = Air()
        } else {
            map[y][x] = Stone()
        }
    }
}

class Box : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = true
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun color(): Color = Color(0xff8b4513)
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = color(), topLeft = topLeft(x, y), size = size())

    override fun isEdible(): Boolean = false
    override fun isPushable(): Boolean = true

    override fun moveHorizontal(dx: Int) {
        val playerx = playerxState.value
        val playery = playeryState.value

        if (map[playery][playerx + dx + dx].isAir() && !map[playery + 1][playerx + dx].isAir()) {
            map[playery][playerx + dx + dx] = this
            moveToTile(playerx + dx, playery)
        }
    }

    override fun moveVertical(dy: Int) {

    }

    override fun update(x: Int, y: Int) {
        if (map[y + 1][x].isAir()) {
            map[y + 1][x] = FallingBox()
            map[y][x] = Air()
        }
    }
}

class FallingBox : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = true
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun color(): Color = Color(0xff8b4513)
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = color(), topLeft = topLeft(x, y), size = size())

    override fun isEdible(): Boolean = false
    override fun isPushable(): Boolean = false
    override fun moveHorizontal(dx: Int) {}
    override fun moveVertical(dy: Int) {}
    override fun update(x: Int, y: Int) {
        if (map[y + 1][x].isAir()) {
            map[y + 1][x] = FallingBox()
            map[y][x] = Air()
        } else {
            map[y][x] = Box()
        }
    }
}

class Key1 : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = true
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun color(): Color = Color(0xffffcc00)
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = color(), topLeft = topLeft(x, y), size = size())

    override fun isEdible(): Boolean = false
    override fun isPushable(): Boolean = false

    override fun moveHorizontal(dx: Int) {
        removeLock1()
        moveToTile(playerxState.value + dx, playeryState.value)
    }

    override fun moveVertical(dy: Int) {
        removeLock1()
        moveToTile(playerxState.value, playeryState.value + dy)
    }

    override fun update(x: Int, y: Int) {}
}

class Lock1 : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = true
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun color(): Color = Color(0xffffcc00)
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = color(), topLeft = topLeft(x, y), size = size())

    override fun isEdible(): Boolean = false
    override fun isPushable(): Boolean = false
    override fun moveHorizontal(dx: Int) {}

    override fun moveVertical(dy: Int) {}

    override fun update(x: Int, y: Int) {}
}

class Key2 : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = true
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun color(): Color = Color(0xff00ccff)
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = color(), topLeft = topLeft(x, y), size = size())

    override fun isEdible(): Boolean = false
    override fun isPushable(): Boolean = false

    override fun moveHorizontal(dx: Int) {
        removeLock2()
        moveToTile(playerxState.value + dx, playeryState.value)
    }

    override fun moveVertical(dy: Int) {
        removeLock2()
        moveToTile(playerxState.value, playeryState.value + dy)
    }

    override fun update(x: Int, y: Int) {}
}

class Lock2 : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = true
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = false
    override fun color(): Color = Color(0xff00ccff)
    override fun draw(drawScope: DrawScope, x: Int, y: Int) =
        drawScope.drawRect(color = color(), topLeft = topLeft(x, y), size = size())

    override fun isEdible(): Boolean = false
    override fun isPushable(): Boolean = false
    override fun moveHorizontal(dx: Int) {}

    override fun moveVertical(dy: Int) {}

    override fun update(x: Int, y: Int) {}
}

class Air : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = true
    override fun isPlayer(): Boolean = false
    override fun color(): Color = Color.Transparent
    override fun draw(drawScope: DrawScope, x: Int, y: Int) {}
    override fun isEdible(): Boolean = true
    override fun isPushable(): Boolean = false

    override fun moveHorizontal(dx: Int) {
        moveToTile(playerxState.value + dx, playeryState.value)
    }

    override fun moveVertical(dy: Int) {
        moveToTile(playerxState.value, playeryState.value + dy)
    }

    override fun update(x: Int, y: Int) {}
}

class Player : Tile {
    override fun isFlux(): Boolean = false
    override fun isUnbreakable(): Boolean = false
    override fun isStone(): Boolean = false
    override fun isFallingStone(): Boolean = false
    override fun isBox(): Boolean = false
    override fun isFallingBox(): Boolean = false
    override fun isKey1(): Boolean = false
    override fun isLock1(): Boolean = false
    override fun isKey2(): Boolean = false
    override fun isLock2(): Boolean = false
    override fun isAir(): Boolean = false
    override fun isPlayer(): Boolean = true
    override fun color(): Color = Color.Transparent

    override fun draw(drawScope: DrawScope, x: Int, y: Int) {}
    override fun isEdible(): Boolean = false
    override fun isPushable(): Boolean = false
    override fun moveHorizontal(dx: Int) {}

    override fun moveVertical(dy: Int) {}

    override fun update(x: Int, y: Int) {}
}