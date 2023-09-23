import androidx.compose.ui.graphics.Color

interface Tile2 {
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

}


class Flux : Tile2 {
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
}

class Unbreakable : Tile2 {
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

}

class Stone : Tile2 {
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

}

class FallingStone : Tile2 {
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

}

class Box : Tile2 {
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

}

class FallingBox : Tile2 {
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

}

class Key1 : Tile2 {
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

}

class Lock1 : Tile2 {
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

}

class Key2 : Tile2 {
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

}

class Lock2 : Tile2 {
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

}

class Air : Tile2 {
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
}

class Player : Tile2 {
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
}